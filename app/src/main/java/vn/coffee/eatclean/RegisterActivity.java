package vn.coffee.eatclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    ImageView Forgotpass;
    EditText Email;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initUi();

        Forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpassword();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent ic = new Intent(RegisterActivity.this,LogInActivity.class);
        startActivity(ic);
        finish();
    }

    private void forgotpassword() {
        String email = Email.getText().toString().trim();

        // kiểm tra người dùng nhập
        if (email.isEmpty()) {
            Email.setError("Vui lòng nhập email");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Email không hợp lệ!!");
            Email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Kiểm tra email của bạn để thiết lập lại mật khẩu của bạn!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterActivity.this, "Thử lại! Có gì đó không ổn đã xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initUi(){
        auth = FirebaseAuth.getInstance();
        Forgotpass = findViewById(R.id.forgotpassword);
        Email = findViewById(R.id.email);

    }


}