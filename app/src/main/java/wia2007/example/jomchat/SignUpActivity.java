package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    ImageView ivBack;
    Button btnSignUp;

    String[] departments = {"Artificial Intelligence", "Computer System Networking", "Software Engineering",
            "Information System", "Data Science", "Multimedia Computing"};

    String[] years = {"1","2","3","4","Alumni"};

    AutoCompleteTextView autoCompleteDepartment;
    ArrayAdapter<String> adapterDepartments;

    AutoCompleteTextView autoCompleteYear;
    ArrayAdapter<String> adapterYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputName = findViewById(R.id.til_name);
        textInputUsername = findViewById(R.id.til_username);
        textInputEmail = findViewById(R.id.til_email);
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

        autoCompleteDepartment = findViewById(R.id.auto_complete_department);

        adapterDepartments = new ArrayAdapter<String>(this,R.layout.list_department,departments);

        autoCompleteDepartment.setAdapter(adapterDepartments);

        autoCompleteDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String department = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Department: " + department, Toast.LENGTH_SHORT).show();

            }
        });

        autoCompleteYear = findViewById(R.id.auto_complete_year);

        adapterYears = new ArrayAdapter<String>(this,R.layout.list_year,years);

        autoCompleteYear.setAdapter(adapterYears);

        autoCompleteYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String years = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Year: " + years, Toast.LENGTH_SHORT).show();

            }
        });

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(startintent);
            }
        });

    }
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

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

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

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
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

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

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            textInputName.setError("Field can't be empty");
            return false;
        } else if (nameInput.length() > 30) {
            textInputName.setError("Username too long");
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirm_passwordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (confirm_passwordInput.isEmpty()) {
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        }  else if (textInputPassword != textInputConfirmPassword) {
            textInputConfirmPassword.setError("Confirm password does not match");
            return false;
        }
        else {
            textInputConfirmPassword.setError(null);
            return true;
        }
    }
    public void signUpInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword() | !validateName() | !validateConfirmPassword()) {
            return;
        }

        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();
        input += "\n";
        input += "Name: " + textInputName.getEditText().getText().toString();
        input += "\n";
        input += "Confirm Password: " + textInputConfirmPassword.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}