package wia2007.example.jomchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnSignUp, btnLogin;
    private TextInputLayout textInputUsername;
    static String usernameInput;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputUsername = findViewById(R.id.username_login);
        TextInputLayout textInputPassword = findViewById(R.id.password_login);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signup);
            }
        });

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInput = textInputUsername.getEditText().getText().toString();
                String passwordInput = textInputPassword.getEditText().getText().toString();

                if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your username or password", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if username is exist in firebase database
                            if (snapshot.hasChild(usernameInput)) {
                                // username is exist in firebase database
                                // now get password of user from firebase data and match it with user entered password
                                final String getPassword = snapshot.child(usernameInput).child("password").getValue(String.class);
                                if (getPassword.equals(passwordInput)) {
                                    sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    sharedPreferences.edit().putString("username", usernameInput).apply();

                                    Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    Intent login = new Intent(LoginActivity.this, PostListActivity.class);
                                    login.putExtra("username", usernameInput);
                                    startActivity(login);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Username not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                // After login is successful, set the flag in shared preferences
                sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("logged_in", true).apply();

                // Launch the homepage activity
//                Intent intent = new Intent(LoginActivity.this, PostListActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

}