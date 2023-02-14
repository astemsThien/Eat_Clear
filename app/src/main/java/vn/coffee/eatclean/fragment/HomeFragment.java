package vn.coffee.eatclean.fragment;



import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


import me.itangqi.waveloadingview.WaveLoadingView;
import vn.coffee.eatclean.R;
import vn.coffee.eatclean.addFoodManual.BCScannerActivity;


public class HomeFragment extends Fragment {
    WaveLoadingView pbxuly;
    ImageView cardView250ml, cardView1500ml;
    TextView waterCardGlasses, Weight, Calo, Water, Food;
    Button button, ScanBtn;
    int progress = 0;
    FirebaseAuth auth;
    String b, c, d;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pbxuly=view.findViewById(R.id.waterLevelView);
        waterCardGlasses =view.findViewById(R.id.home_water_card_glasses);
        cardView250ml =view.findViewById(R.id.ima7h30);
        cardView1500ml =view.findViewById(R.id.ima8h30);
        button = view.findViewById(R.id.buttonreminder);
        ScanBtn = view.findViewById(R.id.qr_scanner);
        Weight = view.findViewById(R.id.home_weight_card_current_weight);
        Calo = view.findViewById(R.id.home_summary_card_final_calories);
        Food = view.findViewById(R.id.home_summary_card_food_calories);
        Water = view.findViewById(R.id.home_summary_card_TDEE_02);

        auth = FirebaseAuth.getInstance();


        cardView250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add250ml();
            }
        });
        cardView1500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add500ml();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remid();
            }
        });
        ScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();
            }
        });

        ShowData();

        updateOrogressBar();

        CreaterNotification();
        return view;
    }

    private void CreaterNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLenumbit",name ,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);
        }

    }

    private void ScanCode() {
        Intent intent = new Intent(getContext(), BCScannerActivity.class);
        startActivity(intent);
    }

    private void ShowData() {
        String s = auth.getCurrentUser().getUid().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
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
        DatabaseReference reference3 = firebaseDatabase.getReference().child("FoodCalories").child(s).child("calo");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot == null){
                    return;
                }
                String s = snapshot.getKey();
                System.out.println(s);
                c = snapshot.getValue().toString();
                System.out.println(c);
                Calo.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        DatabaseReference reference5 = firebaseDatabase.getReference().child("FoodCalories").child(s).child("calo");
        reference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot == null){
                    return;
                }
                String s = snapshot.getKey();
                System.out.println(s);
                c = snapshot.getValue().toString();
                System.out.println(c);
                Food.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        DatabaseReference reference4 = firebaseDatabase.getReference().child("Water").child(s).child("water");
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getKey();
                System.out.println(s);
                d = snapshot.getValue().toString();
                System.out.println(d);
                Water.setText(d);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private void remid() {
        Toast.makeText( getActivity(), "Nhắc nhở uống nua!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(),NotificationWater.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        long timeAtButtonclick = System.currentTimeMillis();

        long tensecondsInMillis = 1000*10;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonclick,tensecondsInMillis,pendingIntent);
    }

    private void Add500ml() {
        FirebaseDatabase.getInstance().getReference("Water")
                .child(auth.getCurrentUser().getUid()).child("water")
                .setValue(progress+"ml", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@androidx.annotation.Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    }
                });
        if(progress <100){
            progress += 20;
            updateOrogressBar();
        }
    }

    private void Add250ml() {
        FirebaseDatabase.getInstance().getReference("Water")
                .child(auth.getCurrentUser().getUid()).child("water")
                .setValue(progress+ "ml", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@androidx.annotation.Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    }
                });
        if(progress <100){
            progress += 10;
            updateOrogressBar();
        }
    }

    private void updateOrogressBar(){
        pbxuly.setProgressValue(progress);
        waterCardGlasses.setText(String.valueOf(progress));
    }

}