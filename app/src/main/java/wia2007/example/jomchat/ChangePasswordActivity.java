package wia2007.example.jomchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#_$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    ImageView ivBack, ivHome, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;
    TextInputLayout textInputCurrentPassword;
    TextInputLayout textInputNewPassword;
    TextInputLayout textInputConfirmNewPassword;
    Button btnConfirm;
    FirebaseAuth firebaseAuth;

    String current_password, username, current_passwordInput, new_passwordInput, confirm_new_passwordInput, userURL;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ivHome = findViewById(R.id.IVHome);
        ivBack = findViewById(R.id.IVBack);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        textInputCurrentPassword = findViewById(R.id.current_password);
        textInputNewPassword = findViewById(R.id.new_password);
        textInputConfirmNewPassword = findViewById(R.id.new_password2);
        btnConfirm = findViewById(R.id.btn_confirm);

        //another
        firebaseAuth = FirebaseAuth.getInstance();
        //checkUserStatus()
        //get some info of current user to include in post

        userURL = getIntent().getStringExtra("userURL");
        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        username = getIntent().getStringExtra("username");
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if (data.getKey().equals(username)) {
                        current_password = data.child("password").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_passwordInput = textInputCurrentPassword.getEditText().getText().toString().trim();
                new_passwordInput = textInputNewPassword.getEditText().getText().toString().trim();
                confirm_new_passwordInput = textInputConfirmNewPassword.getEditText().getText().toString().trim();
                changePasswordInput(view);
            }
        });
    }

    private boolean validateCurrentPassword() {
        if (current_passwordInput.isEmpty()) {
            textInputCurrentPassword.setError("Field can't be empty");
            return false;
        }  else if (!current_password.equals(current_passwordInput)) {
            textInputCurrentPassword.setError("Current Password does not match");
            return false;
        }
        else {
            textInputCurrentPassword.setError(null);
            return true;
        }
    }

    private boolean validateNewPassword() {
        if (new_passwordInput.isEmpty()) {
            textInputNewPassword.setError("Field can't be empty");
            return false;
        }  else if (!PASSWORD_PATTERN.matcher(new_passwordInput).matches()) {
            textInputNewPassword.setError("New Password too weak");
            return false;
        }
        else {
            textInputNewPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmNewPassword() {
        if (confirm_new_passwordInput.isEmpty()) {
            textInputConfirmNewPassword.setError("Field can't be empty");
            return false;
        }  else if (!new_passwordInput.equals(confirm_new_passwordInput)) {
            textInputConfirmNewPassword.setError("Confirm New Password does not match");
            return false;
        }
        else {
            textInputConfirmNewPassword.setError(null);
            return true;
        }
    }

    private void changePasswordInput(View v) {
        if (!validateCurrentPassword() | !validateNewPassword() | !validateConfirmNewPassword()) {
            return;
        }
        else {
            databaseReference.child("users").child(username).child("password").setValue(new_passwordInput);
            Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
            startintent.putExtra("username", username);
            startintent.putExtra("userURL", userURL);
            startActivity(startintent);
        }
    }
}

