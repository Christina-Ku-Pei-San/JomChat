package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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

    private TextInputLayout textInputCurrentPassword;
    private TextInputLayout textInputNewPassword;
    private TextInputLayout textInputConfirmNewPassword;

    private ImageView ivBack, ivHome, ivMessenger, ivNotification;
    private CircleImageView ivProfilePhoto;
    private Button btnConfirm;

    private String usernameInput, current_passwordInput, new_passwordInput, confirm_new_passwordInput;
    String username, userURL;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userURL = getIntent().getStringExtra("userURL");
        username = getIntent().getStringExtra("username");
        textInputCurrentPassword = findViewById(R.id.current_password);
        textInputNewPassword = findViewById(R.id.new_password);
        textInputConfirmNewPassword = findViewById(R.id.new_password2);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);

                current_passwordInput = textInputCurrentPassword.getEditText().getText().toString().trim();
                new_passwordInput = textInputNewPassword.getEditText().getText().toString().trim();
                confirm_new_passwordInput = textInputConfirmNewPassword.getEditText().getText().toString().trim();
                changePasswordInput(view);
            }
        });
    }

    private boolean validateCurrentPassword() {
        if (current_passwordInput.isEmpty()) {
        textInputNewPassword.setError("Field can't be empty");
        return false;
        }  else if (!current_passwordInput.equals(current_passwordInput)) {
            textInputConfirmNewPassword.setError("Current Password does not match");
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
        if (!validateCurrentPassword() | validateNewPassword() | validateConfirmNewPassword()) {
            return;
        }
        else {
            databaseReference.child("passwords").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // check if username is not registered before
                    if (snapshot.hasChild(usernameInput)) {
                        Toast.makeText(getApplicationContext(), "Username exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // sending data to firebase Realtime Database
                        // we are using username as unique identity for every user
                        // so all the other details of user comes under username
                        databaseReference.child("passwords").child(usernameInput).child("newpassword").setValue(new_passwordInput);

                        Toast.makeText(getApplicationContext(), "New Password Added Sucessfully", Toast.LENGTH_SHORT).show();

                        Intent startintent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                        startintent.putExtra("username", username);
                        startintent.putExtra("userURL", userURL);
                        startActivity(startintent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}