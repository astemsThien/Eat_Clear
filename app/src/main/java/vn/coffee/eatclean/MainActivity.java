package vn.coffee.eatclean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import vn.coffee.eatclean.account.EditProfileActivity;
import vn.coffee.eatclean.addFoodManual.BCScannerActivity;
import vn.coffee.eatclean.fragment.FoodFragment;
import vn.coffee.eatclean.fragment.HomeFragment;
import vn.coffee.eatclean.fragment.ProfileFragment;
import vn.coffee.eatclean.fragment.ReminderFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FOOD = 1;
    private static final int FRAGMENT_ABOUT = 3;
    private static final int FRAGMENT_PROFILE = 2;



    private int mCurrentFragment = FRAGMENT_HOME;

    BottomNavigationView bottomNavigation;
    DrawerLayout mDrawerlayout;
    TextView tvEmail, tvName;
    NavigationView mNavigationView;

    TextView name, email;
    ImageView imageView;
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    String c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Google API
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // ánh xạ
        initUi();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerlayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open_nav, R.string.close_nav);
        mDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        // su kien click vao Navigation_view

        mNavigationView.setNavigationItemSelectedListener(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // hien form Fragment_home tren mDrawent
        replaceFrament(new HomeFragment());
        bottomNavigation.getMenu().findItem(R.id.home).setChecked(true);

        showuser();

        showuserFirebase();
    }

    private void showuserFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String s = auth.getCurrentUser().getUid().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference2 = firebaseDatabase.getReference().child("User").child(s).child("name");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                String email = user.getEmail();
                tvEmail.setText(email);
                System.out.println(s);
                c = snapshot.getValue().toString();
                System.out.println(c);
                tvName.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void replaceFrament(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void showuser() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }
    }

    private void initUi() {
        auth = FirebaseAuth.getInstance();
        mNavigationView = findViewById(R.id.navigation_view);
        // ánh xạ Firebase
        tvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_email);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_user);
        bottomNavigation = findViewById(R.id.bottom_nav_view);
        // ánh xạ Google API
        imageView = mNavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        name = mNavigationView.getHeaderView(0).findViewById(R.id.txt_user);
        email = mNavigationView.getHeaderView(0).findViewById(R.id.txt_email);
        signOut = mNavigationView.getHeaderView(0).findViewById(R.id.btn_logout);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        LoadLocale();
        String str = "android.intent.extra.TEXT";
        String str2 = "android.intent.extra.SUBJECT";
        if (id == R.id.home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFrament(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.food) {
            if (mCurrentFragment != FRAGMENT_FOOD) {
                replaceFrament(new FoodFragment());
                mCurrentFragment = FRAGMENT_FOOD;
            }
        } else if (id == R.id.About) {
            if (mCurrentFragment != FRAGMENT_ABOUT) {
                replaceFrament(new ReminderFragment());
                mCurrentFragment = FRAGMENT_ABOUT;
            }
        }else if (id == R.id.Proilef) {
            if (mCurrentFragment != FRAGMENT_PROFILE) {
                replaceFrament(new ProfileFragment());
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.user) {
            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
        } else if (id == R.id.resetpassword) {
            startActivity(new Intent(getApplicationContext(), ResetPassword.class));
        } else if (id == R.id.btn_logout) {
            // =====================================================================================
            FirebaseUser user = auth.getCurrentUser();
            Toast.makeText(MainActivity.this, "Sign out from " + user.getEmail().toString(), Toast.LENGTH_LONG).show();
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            // =====================================================================================
            mGoogleSignInClient.signOut();
            // =====================================================================================
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
        } else if (id == R.id.Rate_us) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        } else if (id == R.id.share) {
            Intent share1 = new Intent("android.intent.action.SEND");
            share1.setType("text/plain");
            StringBuilder BD = new StringBuilder();
            BD.append("Tải xuống ứng dụng Sức khỏe CUW miễn phí tốt nhất hiện nay.\n Cảm ơn!\n  https://play.google.com/store/apps/details?id=" + getPackageName());
            BD.append(getApplicationContext().getPackageName());
            String S = BD.toString();
            share1.putExtra(str2, "Chia sẽ App");
            share1.putExtra(str, S);
            startActivity(Intent.createChooser(share1, "Chia sẽ cho mọi người"));
        }else if (id == R.id.Change) {
            final String[] listItems = {"VietNam","English"};
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setTitle("Choose Language ...");
            mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if(i==0){
                                setLocale("vi");
                                recreate();
                            }
                            if(i==1){
                                setLocale("en");
                                recreate();
                            }
                            dialog.dismiss();
                        }
            });
            AlertDialog mDialog=mBuilder.create();
            mDialog.show();
        }

        // su kien dong Drawer
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //
        SharedPreferences.Editor editor = getSharedPreferences("Setting",MODE_PRIVATE).edit();
        editor.putString("My_Language",lang);
        editor.apply();
    }
    public void LoadLocale(){
        SharedPreferences pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Language","");
        setLocale(language);

    }

    public void scanbtn(View view) {
        Intent intent = new Intent(MainActivity.this, BCScannerActivity.class);
        startActivity(intent);
    }
}