package wia2007.example.jomchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
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

    private TextInputLayout textInputName;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputYear;
    private TextInputLayout textInputDepartment;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private ImageView ivBack;
    private Button btnSignUp;

    private String nameInput, usernameInput, emailInput, passwordInput, confirm_passwordInput, yearInput, departmentInput;

    private String[] departments = {"Artificial Intelligence", "Computer System Networking", "Software Engineering",
            "Information System", "Data Science", "Multimedia Computing"};

    private String[] years = {"1","2","3","4","Alumni"};

    private AutoCompleteTextView autoCompleteDepartment;
    private ArrayAdapter<String> adapterDepartments;

    private AutoCompleteTextView autoCompleteYear;
    private ArrayAdapter<String> adapterYears;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputName = findViewById(R.id.til_name);
        textInputUsername = findViewById(R.id.til_username);
        textInputEmail = findViewById(R.id.til_email);
        textInputYear = findViewById(R.id.til_year);
        textInputDepartment = findViewById(R.id.til_department);
        textInputPassword = findViewById(R.id.til_password);
        textInputConfirmPassword = findViewById(R.id.til_confirm_password);

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(startintent);
            }
        });

        autoCompleteYear = findViewById(R.id.auto_complete_year);
        adapterYears = new ArrayAdapter<String>(this,R.layout.list_year,years);
        autoCompleteYear.setAdapter(adapterYears);
        autoCompleteYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                yearInput = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Year: " + yearInput, Toast.LENGTH_SHORT).show();

            }
        });

        autoCompleteDepartment = findViewById(R.id.auto_complete_department);
        adapterDepartments = new ArrayAdapter<String>(this,R.layout.list_department,departments);
        autoCompleteDepartment.setAdapter(adapterDepartments);
        autoCompleteDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentInput = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Department: " + departmentInput, Toast.LENGTH_SHORT).show();

            }
        });

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput = textInputName.getEditText().getText().toString().trim();
                usernameInput = textInputUsername.getEditText().getText().toString().trim();
                emailInput = textInputEmail.getEditText().getText().toString().trim();
                yearInput = textInputYear.getEditText().getText().toString();
                departmentInput = textInputDepartment.getEditText().getText().toString();
                passwordInput = textInputPassword.getEditText().getText().toString().trim();
                confirm_passwordInput = textInputConfirmPassword.getEditText().getText().toString();
                signUpInput(view);
            }
        });

    }

    private boolean validateName() {
        if (nameInput.isEmpty()) {
            textInputName.setError("Field can't be empty");
            return false;
        } else if (nameInput.length() > 30) {
            textInputName.setError("Name too long");
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("Please enter a valid email address");
            return false;
        }
        else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateYear() {
        if (yearInput.isEmpty()) {
            textInputYear.setError("Field can't be empty");
            return false;
        } else  {
            textInputYear.setError(null);
            return true;
        }
    }

    private boolean validateDepartment() {
        if (departmentInput.isEmpty()) {
            textInputDepartment.setError("Field can't be empty");
            return false;
        } else {
            textInputDepartment.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        }  else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        }
        else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        if (confirm_passwordInput.isEmpty()) {
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        }  else if (!passwordInput.equals(confirm_passwordInput)) {
            textInputConfirmPassword.setError("Confirm password does not match");
            return false;
        }
        else {
            textInputConfirmPassword.setError(null);
            return true;
        }
    }

    public void signUpInput(View v) {
        if (!validateName() | !validateUsername() | !validateEmail() | !validateYear() | !validateDepartment() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }
        else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        databaseReference.child("users").child(usernameInput).child("name").setValue(nameInput);
                        databaseReference.child("users").child(usernameInput).child("email").setValue(emailInput);
                        databaseReference.child("users").child(usernameInput).child("year").setValue(yearInput);
                        databaseReference.child("users").child(usernameInput).child("department").setValue(departmentInput);
                        databaseReference.child("users").child(usernameInput).child("password").setValue(passwordInput);

                        Toast.makeText(getApplicationContext(), "User Profile Added Sucessfully", Toast.LENGTH_SHORT).show();

                        Intent startintent = new Intent(SignUpActivity.this, LoginActivity.class);
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