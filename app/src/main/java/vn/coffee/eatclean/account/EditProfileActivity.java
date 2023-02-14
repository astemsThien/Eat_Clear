package vn.coffee.eatclean.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.coffee.eatclean.MainActivity;
import vn.coffee.eatclean.R;
import vn.coffee.eatclean.User;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText Weight, Height, Birthday, Username;
    RadioButton Male, Female;
    String a, b, c, d;
    ProgressDialog loadingbar;
    Button setupCompleteBtn;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initUI();

        showData();

    }

    private void showData() {
        String s= firebaseAuth.getCurrentUser().getUid().trim();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference1 = database.getReference().child("User").child(s).child("name");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                a = snapshot.getValue().toString();
                System.out.println(a);
                Username.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        // ánh xạ User
        Username = findViewById(R.id.username_edit_dialog_input);
        Weight = findViewById(R.id.weight_edit_dialog_inputcurrentweight);
        Height = findViewById(R.id.height_edit_dialog_input_height);
        Birthday = findViewById(R.id.birthday_edit_dialog_inputcurrentbirthday);

        Male = findViewById(R.id.setup_account_male_button);
        Female = findViewById(R.id.setup_account_female_button);

        loadingbar = new ProgressDialog(this);

        setupCompleteBtn = findViewById(R.id.setup_setup_complete_button);
        setupCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
        Male.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String S = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/gender";
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(S);
                myRef.setValue("Male");
                Male.setChecked(true);
                Female.setChecked(false);
            }
        });


        Female.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String S = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/gender";
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(S);
                myRef.setValue("Female");
                Female.setChecked(true);
                Male.setChecked(false);
            }
        });

    }

    private void Submit() {
        String name = Username.getText().toString().trim();
        String weight = Weight.getText().toString().trim();
        String height = Height.getText().toString().trim();
        String birthday = Birthday.getText().toString().trim();

        String S = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/name";
        String S1 = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/weight";
        String S2 = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/height";
        String S3 = "User/"+ firebaseAuth.getCurrentUser().getUid().trim()+"/birthday";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(S);
        myRef.setValue(name);
        DatabaseReference myRef1 = database.getReference(S1);
        myRef1.setValue(weight+"Kg");
        DatabaseReference myRef2 = database.getReference(S2);
        myRef2.setValue(height+"Cm");
        DatabaseReference myRef3 = database.getReference(S3);
        myRef3.setValue(birthday);
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}