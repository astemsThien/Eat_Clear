package vn.coffee.eatclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    Button btnreset;
    EditText Pass, Resetpass;
    FirebaseUser user;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnreset = findViewById(R.id.reset_password);
        Pass = findViewById(R.id.pass);
        Resetpass = findViewById(R.id.password);
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void resetPass() {
        String pass = Pass.getText().toString().trim();
        String passreset = Resetpass.getText().toString().trim();

        if (pass.isEmpty()){
            Pass.setError("Vui lòng nhập mật khẩu mới");
            Pass.requestFocus();
            return;
        }
        if (pass.length() < 6){
            Pass.setError("Ít nhất 6 ký tự!!");
            Pass.requestFocus();
            return;
        }
        if (passreset.isEmpty()){
            Resetpass.setError("Vui lòng nhập lại mật khẩu mới");
            Resetpass.requestFocus();
            return;
        }
        if (passreset.length() < 6){
            Resetpass.setError("Ít nhất 6 ký tự!!");
            Resetpass.requestFocus();
            return;
        }
        if (!passreset.equals(pass)){
            Resetpass.setError("Mật khẩu không hợp lệ");
            return;
        }
        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String S = "User/"+auth.getCurrentUser().getUid()+"/pass";

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(S);
                myRef.setValue(pass, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
                Toast.makeText(ResetPassword.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    public void gobackIcon(View view) {
        Intent intent = new Intent(ResetPassword.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}