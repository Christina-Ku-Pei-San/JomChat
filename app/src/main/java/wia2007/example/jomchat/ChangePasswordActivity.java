package wia2007.example.jomchat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import android.util.Patterns;
import android.view.LayoutInflater;

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
    CircleImageView ivProfilePic;
    TextInputLayout textInputCurrentPassword;
    TextInputLayout textInputNewPassword;
    TextInputLayout textInputConfirmNewPassword;
    Button btnConfirm;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    String password, username, current_passwordInput, new_passwordInput, confirm_new_passwordInput, userURL;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ivBack = findViewById(R.id.IVBack);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePic = findViewById(R.id.IVProfilePic);
        textInputCurrentPassword = findViewById(R.id.current_password);
        textInputNewPassword = findViewById(R.id.new_password);
        textInputConfirmNewPassword = findViewById(R.id.new_password2);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        btnConfirm = findViewById(R.id.btn_confirm);

        //another
        firebaseAuth = FirebaseAuth.getInstance();
        //checkUserStatus()
        //get some info of current user to include in post

        current_passwordInput = getIntent().getStringExtra("current_password");
        userURL = getIntent().getStringExtra("userURL");
        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

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
        }  else if (!password.equals(current_passwordInput)) {
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
                    if (snapshot.hasChild(password)) {
                        Toast.makeText(getApplicationContext(), "Password for this Username exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // sending data to firebase Realtime Database
                        // we are using username as unique identity for every user
                        // so all the other details of user comes under username
                        databaseReference.child("Passwords").child(password).child("newpassword").setValue(new_passwordInput);
                        databaseReference.child("Passwords").child(password).child("confirm_newpassword").setValue(confirm_new_passwordInput);

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