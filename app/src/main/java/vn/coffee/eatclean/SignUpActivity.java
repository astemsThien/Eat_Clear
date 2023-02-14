package vn.coffee.eatclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ImageView SignUp;
    EditText UserName, Email, Pass;
    TextView Login;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        initUI();

    }

    private void initUI() {
        auth = FirebaseAuth.getInstance();
        SignUp = findViewById(R.id.signup);
        UserName = findViewById(R.id.username);
        Email = findViewById(R.id.emails);
        Pass = findViewById(R.id.password);
        Login = findViewById(R.id.Login);
        progressDialog = new ProgressDialog(this);
        // sự kiện click
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }

    private void LogIn() {
        Intent login = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(login);
        finish();
    }

    private void Register() {
// lấy thông tin người dùng
        String name = UserName.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String pass = Pass.getText().toString().trim();

        // Kiểm tra người dùng
        if (name.isEmpty()) {
            UserName.setError("Vui lòng nhập tên đăng nhập");
            UserName.requestFocus();
            return;
        }
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
        if (pass.isEmpty()) {
            Pass.setError("Vui lòng nhập mật khẩu");
            Pass.requestFocus();
            return;
        }
        if (pass.length() < 6){
            Pass.setError("Ít nhất 6 ký tự!!");
            Pass.requestFocus();
            return;
        }

        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    User us = new User(name,email,pass);
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Đăng kí thất bại! thử lại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(SignUpActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}