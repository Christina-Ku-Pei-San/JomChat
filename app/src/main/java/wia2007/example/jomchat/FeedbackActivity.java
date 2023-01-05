package wia2007.example.jomchat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FeedbackActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    String rating_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RatingBar rb =  findViewById(R.id.ratingBar);
        TextView TVRating = findViewById(R.id.TVRating);
        Button submit = findViewById(R.id.BtnFeedbackSubmit);
        EditText response = findViewById(R.id.ETFeedback);


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if(!response.getText().toString().isEmpty()){
                   store_feedback sf = new store_feedback(rating_marks,response.getText().toString());
                   databaseReference.child("Feedback").child(LoginActivity.usernameInput).setValue(sf);
                   AlertDialog.Builder alert = new AlertDialog.Builder(FeedbackActivity.this);
                   alert.setTitle("Submitted Feedback!");
                   alert.setNegativeButton("Ok", (dialogInterface, i) -> dialogInterface.cancel());
                   alert.setCancelable(false);
                   alert.show();
                  response.setText("");
                   rb.setRating(0F);

               }else{
                   AlertDialog.Builder alert = new AlertDialog.Builder(FeedbackActivity.this);
                   alert.setTitle("Empty Content!");
                   alert.setMessage("Please enter your feedback.");
                   alert.setNegativeButton("Ok", (dialogInterface, i) -> dialogInterface.cancel());
                   alert.setCancelable(false);
                   alert.show();

               }
            }
        });
        
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TVRating.setText("You have rated "+ rating);
                rating_marks = Float.toString(rating);
            }
        });

    }
}
class store_feedback {
    public String rating;
    public String feedback_response;

    public store_feedback() {
    }

    public store_feedback(String rating, String feedback_response) {
        this.rating = rating;
        this.feedback_response = feedback_response;
    }

    public String getRating() {
        return rating;
    }

    public String getFeedback_response() {
        return feedback_response;
    }
}