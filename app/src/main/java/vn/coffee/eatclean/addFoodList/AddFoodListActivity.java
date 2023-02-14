package vn.coffee.eatclean.addFoodList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.coffee.eatclean.MainActivity;
import vn.coffee.eatclean.R;
import vn.coffee.eatclean.User;

public class AddFoodListActivity extends AppCompatActivity {

    ImageView img;
    TextView ten,calories,carbs,fat,pro,name,name1,name2,gram,gram1,gram2;
    Button food;
    FirebaseAuth auth;
    int valueCalories,tong;
    int Caloo;
    int TongCalories;
    String q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_list);

        food = findViewById(R.id.addbutton);
        auth =  FirebaseAuth.getInstance();

        img = findViewById(R.id.img);
        ten = findViewById(R.id.ten);
        calories = findViewById(R.id.Cal);
        carbs = findViewById(R.id.carbs);
        fat = findViewById(R.id.fat);
        pro = findViewById(R.id.protein);
        name = findViewById(R.id.textView);
        name1 = findViewById(R.id.textView1);
        name2 = findViewById(R.id.textView2);
        gram = findViewById(R.id.gram);
        gram1 = findViewById(R.id.gram1);
        gram2 = findViewById(R.id.gram2);

        img.setImageResource(getIntent().getIntExtra("img",0));
        ten.setText(getIntent().getStringExtra("ten"));
        calories.setText(getIntent().getStringExtra("calo"));
        carbs.setText(getIntent().getStringExtra("carbs"));
        fat.setText(getIntent().getStringExtra("fat"));
        pro.setText(getIntent().getStringExtra("pro"));
        name.setText(getIntent().getStringExtra("name"));
        name1.setText(getIntent().getStringExtra("name1"));
        name2.setText(getIntent().getStringExtra("name2"));
        gram.setText(getIntent().getStringExtra("gram"));
        gram1.setText(getIntent().getStringExtra("gram1"));
        gram2.setText(getIntent().getStringExtra("gram2"));
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddFood();
            }
        });

        auth = FirebaseAuth.getInstance();
        String s = auth.getCurrentUser().getUid().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference8 = firebaseDatabase.getReference().child("FoodCalories").child(s).child("calo");
        reference8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                q = snapshot.getValue().toString();
                System.out.println(q);
                valueCalories = (int) (Integer.parseInt(q));
                System.out.println(valueCalories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onClickAddFood(){

        String calo = calories.getText().toString().trim();
        Caloo = Integer.parseInt(calo);
        TongCalories = Caloo+valueCalories;
        tong = (int) TongCalories;
        System.out.println(tong);

        String s = auth.getCurrentUser().getUid().trim();
        FirebaseDatabase database =  FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("FoodCalories").child(s).child("calo");
        myRef.setValue(tong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(AddFoodListActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddFoodListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}