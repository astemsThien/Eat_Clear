package vn.coffee.eatclean.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.coffee.eatclean.R;


public class ProfileFragment extends Fragment {

    TextView UserName, Weight, Height, Birthday, Email, Gender, Calo, Water;
    FirebaseAuth Auth;
    String a, b, c, d, e, f, g;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserName = view.findViewById(R.id.profile_username);
        Weight = view.findViewById(R.id.profile_weight);
        Height = view.findViewById(R.id.profile_height);
        Birthday = view.findViewById(R.id.profile_birthday);
        Email = view.findViewById(R.id.profile_email);
        Gender = view.findViewById(R.id.profile_gender);
        Calo = view.findViewById(R.id.profile_points);
        Water = view.findViewById(R.id.profile_activity_level);

        Auth = FirebaseAuth.getInstance();

        ShowData();

        return view;
    }

    private void ShowData() {
        String s = Auth.getCurrentUser().getUid().trim();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("User").child(s).child("name");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                a = snapshot.getValue().toString();
                System.out.println(a);
                UserName.setText(a);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference1 = firebaseDatabase.getReference().child("User").child(s).child("weight");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                b = snapshot.getValue().toString();
                System.out.println(b);
                Weight.setText(b);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference2 = firebaseDatabase.getReference().child("User").child(s).child("height");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                c = snapshot.getValue().toString();
                System.out.println(c);
                Height.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference3 = firebaseDatabase.getReference().child("User").child(s).child("birthday");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                d = snapshot.getValue().toString();
                System.out.println(d);
                Birthday.setText(d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference4 = firebaseDatabase.getReference().child("User").child(s).child("email");
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                e = snapshot.getValue().toString();
                System.out.println(e);
                Email.setText(e);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference5 = firebaseDatabase.getReference().child("User").child(s).child("gender");
        reference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                f = snapshot.getValue().toString();
                System.out.println(f);
                Gender.setText(f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference6 = firebaseDatabase.getReference().child("FoodCalories").child(s).child("calo");
        reference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                g = snapshot.getValue().toString();
                System.out.println(g);
                Calo.setText(g + "calo");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference reference7 = firebaseDatabase.getReference().child("Water").child(s).child("water");
        reference7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                g = snapshot.getValue().toString();
                System.out.println(g);
                Water.setText(g);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}