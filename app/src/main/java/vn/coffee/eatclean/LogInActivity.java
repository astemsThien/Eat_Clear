package vn.coffee.eatclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import vn.coffee.eatclean.account.EditProfileActivity;

public class LogInActivity extends AppCompatActivity {
    ImageView Login, Fingerpint, signin;
    EditText Email, Pass;
    CheckBox Remember;
    TextView Register, Forgotpass;
    FirebaseAuth auth;

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        initUI();
    }

    private void initUI() {
        // ??nh x???
        Login = findViewById(R.id.login);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.pass);
        Register = findViewById(R.id.register);
        Forgotpass = findViewById(R.id.quenmatkhau);
        Fingerpint = findViewById(R.id.fingerpint);
        Remember = findViewById(R.id.remember);
        auth = FirebaseAuth.getInstance();
        signin = findViewById(R.id.Google);

        // x??? l?? s??? ki???n v??n tay
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LogInActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(LogInActivity.this, "Error: " +errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LogInActivity.this, "Successfully Fingerpint", Toast.LENGTH_SHORT).show();
                Intent promptInfo = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(promptInfo);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LogInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint login")
                .setSubtitle("Login with fingerprint")
                .setNegativeButtonText("Exit")
                .build();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Google:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        // x??? l?? s??? ki???n click
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        Forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forgot();
            }
        });
        Fingerpint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInFingerpint();
            }
        });
        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(LogInActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(LogInActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Remember
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")){
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (checkbox.equals("false")){
            Toast.makeText(LogInActivity.this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }
    }

    private void Forgot() {
        Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // K???t qu??? tr??? v??? khi kh???i ch???y Intent t??? GoogleSignInClient.getSignInIntent (...);
        if (requestCode == RC_SIGN_IN) {
            // T??c v??? tr??? v??? t??? l???nh g???i n??y lu??n ho??n th??nh, kh??ng c???n ????nh k??m
            // m???t ng?????i nghe.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            // ???? ????ng nh???p th??nh c??ng, hi???n th??? giao di???n ng?????i d??ng ???? x??c th???c.
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // M?? tr???ng th??i ApiException ch??? ra l?? do l???i chi ti???t.
            // Vui l??ng tham kh???o tham chi???u l???p GoogleSignInStatusCodes ????? bi???t th??m th??ng tin.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void LogInFingerpint() {
        biometricPrompt.authenticate(promptInfo);
    }

    private void SignUp() {
        Intent signup = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(signup);
    }

    private void LogIn() {
        // ng?????i d??ng nh???p
        String email = Email.getText().toString().trim();
        String pass = Pass.getText().toString().trim();

        // kiem tra nguoi dung nhap thong tin
        if (email.isEmpty()) {
            Email.setError("Vui l??ng nh???p email");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Email kh??ng h???p l???!!");
            Email.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            Pass.setError("Vui l??ng nh???p m???t kh???u");
            Pass.requestFocus();
            return;
        }
        if (pass.length() < 6){
            Pass.setError("??t nh???t 6 k?? t???!!");
            Pass.requestFocus();
            return;
        }

        // kiem tra email/ pass khi nguoi dung nhap
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // ????ng nh???p th??nh c??ng
                if (task.isSuccessful()){
                    // csdl duoc luu ve Firebase database

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    // kiem tra email ???????c g???i v???
                    if (user.isEmailVerified()){
                        startActivity(new Intent(LogInActivity.this, EditProfileActivity.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(LogInActivity.this, "Ki???m tra email c???a b???n ????? x??c minh t??i kho???n c???a b???n!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LogInActivity.this, "????ng nh???p th???t b???i! Vui l??ng ki???m tra th??ng tin ????ng nh???p c???a b???n", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}