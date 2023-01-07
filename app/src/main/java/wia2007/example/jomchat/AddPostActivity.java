package wia2007.example.jomchat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends AppCompatActivity {
    Button btnsubmit;
    ImageButton gallery;
    TextInputEditText postContent;
    ImageView imgview;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    //int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    //another
    //permission constants
    //private static final int CAMERA_REQUEST_CODE =100;
    //private static final int GALLERY_REQUEST_CODE =200;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    Uri image_rui=null;
    //user info
    String name, email,uid,dp;
    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    ImageView ivBack, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        username = getIntent().getStringExtra("username");

        //anaother
        firebaseAuth = FirebaseAuth.getInstance();
        //checkUserStatus();
        //get some info of current user to include in post

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(AddPostActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(AddPostActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(AddPostActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(AddPostActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");
          gallery = (ImageButton) findViewById(R.id.IBGallery);
          btnsubmit= (Button)findViewById(R.id.BtnSubmit);
          imgview = (ImageView)findViewById(R.id.IVUploadedImage);
          postContent =(TextInputEditText) findViewById(R.id.TETAddPost);

        progressDialog = new ProgressDialog(AddPostActivity.this);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkAndRequestPermissions(AddPostActivity.this)){
                    chooseImage(AddPostActivity.this);
                }



            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!postContent.getText().toString().isEmpty() && image_rui == null){
                    uploadText();
                }else{
                    UploadImage();
                }




            }
        });


    }
    //ass
    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    pickFromCamera();
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    pickFromGallery();
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    //another
    private void pickFromCamera(){


        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Descr");
        image_rui = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }
    private void pickFromGallery(){
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (  resultCode == RESULT_OK ) {
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //image is picked from gallery, get uri of image
                image_rui = data.getData();
                //set to imageview
                imgview.setImageURI(image_rui);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //image is picked from camera, get uri of image

                imgview.setImageURI(image_rui);

            }

        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    public void uploadText(){
        if(!postContent.getText().toString().isEmpty()){


            uploadinfo textUploadInfo = new uploadinfo(LoginActivity.usernameInput,postContent.getText().toString(), null, "");

            String ImageUploadId = databaseReference.push().getKey();
            databaseReference.child("Post").child(ImageUploadId).setValue(textUploadInfo);

            Toast.makeText(getApplicationContext(), "Post Uploaded Successfully ", Toast.LENGTH_LONG).show();
            postContent.setText("");
        }
    }

    private void prepareNotification(String pID, String title, String description, String notificationType, String notificationTopic){
        //prepare data for notification

        String NOTIFICATION_TOPIC = "/topics/" + notificationTopic;
        String NOTIFICATION_TITLE = title;
        String NOTIFICATION_MESSAGE = description;
        String NOTIFICATION_TYPE = notificationType;

        JSONObject notificationJo = new JSONObject();
        JSONObject notificationBodyJo = new JSONObject();
        try {
            //what to send
            notificationBodyJo.put("notificationType",NOTIFICATION_TYPE);
            notificationBodyJo.put("sender",uid);
            notificationBodyJo.put("pID",pID);
            notificationBodyJo.put("pTitle",NOTIFICATION_TITLE);
            notificationBodyJo.put("pDescription",NOTIFICATION_MESSAGE);
            //where to send
            notificationJo.put("to", NOTIFICATION_TOPIC);

            notificationJo.put("data",notificationBodyJo);
        } catch (JSONException e){
            Toast.makeText(this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        sendPostNotification(notificationJo);

    }

    private void sendPostNotification(JSONObject notificationJo) {
        // send volley object request
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       Log.d("FCM_RESPONSE","onResponse: "+response.toString());
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(AddPostActivity.this,""+error.toString(), Toast.LENGTH_SHORT);
                   }
               })
       {
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
             //put required headers
               Map<String,String> headers = new HashMap<>();
               headers.put("Content-Type", "application/json");
               headers.put("Authorization","key=AAAAWNvrod8:APA91bFCxJ-IorFH_FBLi6q6Ddh9vfDuXHGelkB-VAIBUetcSUJkVZNuhRwo5QRkGmokaxpoejAL3RVeJAUfAD2Yv6h-5a6pj3QH8acu8v0_ErvwE6d9LybrFLE378TlyHebqsV_J5HL");

               return headers;
           }
       };
       //enqueue the volley request
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


        public void UploadImage() {

        if (image_rui != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(image_rui));
            storageReference2.putFile(image_rui)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempImageContent =postContent.getText().toString().trim();
                            //String username = LoginActivity.usernameInput;
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                            @SuppressWarnings("VisibleForTests")
//                            uploadinfo imageUploadInfo = new uploadinfo(LoginActivity.usernameInput,TempImageContent, taskSnapshot.getUploadSessionUri().toString(), "");
//                            // databaseReference.child("users").child("test").child("name").setValue("ck");
//                            String ImageUploadId = databaseReference.push().getKey();
//                            //databaseReference.child("user").child(MainActivity.ETusername.getText().toString()).setValue(imageUploadInfo);
//                            databaseReference.child("Post").child(ImageUploadId).setValue(imageUploadInfo);
//                            postContent.setText("");
//                            imgview.setImageURI(null);

                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    uploadinfo imageUploadInfo = new uploadinfo(LoginActivity.usernameInput,TempImageContent, url, "");
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child("Post").child(ImageUploadId).setValue(imageUploadInfo);
//                                    Upload upload = new Upload(et_localization, url);
//                                    String uploadId = mDataBaseRef.push().getKey();
//                                    mDataBaseRef.child(uploadId).setValue(upload);
                                    postContent.setText("");
                                    imgview.setImageURI(null);
                                }
                            });



                        }
                    });
        }
        else {

            Toast.makeText(AddPostActivity.this, "Please Select Image ", Toast.LENGTH_LONG).show();

        }
    }
    // function to check permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(AddPostActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(AddPostActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(AddPostActivity.this);
                }
                break;
        }
    }


}