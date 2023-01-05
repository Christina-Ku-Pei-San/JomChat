package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewProfileActivity extends AppCompatActivity {

    ImageButton IBEditProfilePicture; // added Edit Profile Picture Button
    EditText editNickName, editUserName, editUserYear, editUserDepartment;
    TextView displayNickName, displayUserName, displayUserYear, displayUserDepartment;
    Button BtnSaveChanges;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        EditText editNickName = findViewById(R.id.editNickName);
        EditText editUserName = findViewById(R.id.editUserName);
        EditText editUserYear = findViewById(R.id.editUserYear);
        EditText editUserDepartment = findViewById(R.id.editUserDepartment);
        TextView displayNickName = findViewById(R.id.displayNickName);
        TextView displayUserName = findViewById(R.id.displayUserName);
        TextView displayUserYear = findViewById(R.id.displayUserYear);
        TextView displayUserDepartment = findViewById(R.id.displayUserDepartment);

        //added Edit Profile Picture Button
        IBEditProfilePicture = findViewById(R.id.IBEditProfilePicture);
        IBEditProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ViewProfileActivity.this, ProfileActivity.class);
                startActivity(startintent);
            }
        });

        BtnSaveChanges = findViewById(R.id.BtnSaveChanges);
        BtnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = editNickName.getText().toString();
                String name = editUserName.getText().toString();
                Integer year = Integer.parseInt(editUserYear.getText().toString());
                String department = editUserDepartment.getText().toString();
                displayNickName.setText(nickname);
                displayUserName.setText(name);
                displayUserYear.setText(Integer.toString(year));
                displayUserDepartment.setText(department);

                String message = "Your changes are successfully updated!";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                Intent startintent = new Intent(ViewProfileActivity.this, ProfileActivity.class);
                startActivity(startintent);
            }
        });
    }
}