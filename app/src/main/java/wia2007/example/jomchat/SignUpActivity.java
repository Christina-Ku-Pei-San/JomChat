package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
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
}