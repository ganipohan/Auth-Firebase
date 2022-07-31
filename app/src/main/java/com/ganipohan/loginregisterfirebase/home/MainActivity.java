package com.ganipohan.loginregisterfirebase.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ganipohan.loginregisterfirebase.R;
import com.ganipohan.loginregisterfirebase.auth.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    TextView nama, emailaddress;
    CircularImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.name);
        emailaddress = findViewById(R.id.email);
        imgProfile = findViewById(R.id.img_profile);

        getUserProfile();

        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(getApplicationContext(), Login.class);
            startActivity(logout);
            finish();
        });
    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            Log.w("info : ", user.getDisplayName() + user.getPhotoUrl() + user.getEmail());
            String name = user.getDisplayName();
            if (name.equals("")) {
                nama.setText("Not Found");
            } else {
                nama.setText(name);
            }
            String email = user.getEmail();
            emailaddress.setText(email);
            Uri photoUrl = user.getPhotoUrl();
            if (name.equals("")) {
                imgProfile.setImageResource(R.mipmap.ic_launcher_round);
            } else {
                Picasso.get()
                        .load(photoUrl)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(imgProfile);
            }


        } else {
            Toast.makeText(getApplicationContext(), "Anda telah logout!", Toast.LENGTH_SHORT).show();
            nama.setText("Silahkan login kembali");
        }
        // [END get_user_profile]
    }
}