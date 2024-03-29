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

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfileActivity extends AppCompatActivity {
    ImageButton IBEditProfilePicture; // added Edit Profile Picture Button
    EditText editName, editUserYear, editUserDepartment;
    TextView displayUsername;
    Button BtnSaveChanges;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    //int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    Uri image_rui=null;
    FirebaseAuth firebaseAuth;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    ImageView ivBack, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;
    CircleImageView ivProfilePic;

    String username, name, year, department, userURL;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ivBack = findViewById(R.id.IVBack);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePic = findViewById(R.id.IVProfilePic);
        //added Edit Profile Picture Button
        IBEditProfilePicture = findViewById(R.id.IBEditProfilePicture);
        displayUsername = findViewById(R.id.displayUsername);
        editName = findViewById(R.id.editName);
        editUserYear = findViewById(R.id.editUserYear);
        editUserDepartment = findViewById(R.id.editUserDepartment);
        BtnSaveChanges = findViewById(R.id.BtnSaveChanges);

        //anaother
        firebaseAuth = FirebaseAuth.getInstance();
        //checkUserStatus();
        //get some info of current user to include in post

        username = getIntent().getStringExtra("username");
        name = getIntent().getStringExtra("name");
        year = getIntent().getStringExtra("year");
        department = getIntent().getStringExtra("department");
        userURL = getIntent().getStringExtra("userURL");

        if (userURL.equals("")) {
            ivProfilePic.setImageResource(R.drawable.ic_baseline_account_circle_24);
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePic);
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        displayUsername.setText(username);
        editName.setText(name);
        editUserYear.setText(year);
        editUserDepartment.setText(department);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ViewProfileActivity.this, ProfileActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ViewProfileActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ViewProfileActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");


        progressDialog = new ProgressDialog(ViewProfileActivity.this);

        IBEditProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkAndRequestPermissions(ViewProfileActivity.this)){
                    chooseImage(ViewProfileActivity.this);
                }



            }
        });

        BtnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String year = editUserYear.getText().toString();
                String department = editUserDepartment.getText().toString();

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()) {
                            if (data.getKey().equals(username)) {
                                databaseReference.child("users").child(username).child("name").setValue(name);
                                databaseReference.child("users").child(username).child("year").setValue(year);
                                databaseReference.child("users").child(username).child("department").setValue(department);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent startintent = new Intent(ViewProfileActivity.this, ProfileActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("name", name);
                startintent.putExtra("year", year);
                startintent.putExtra("department", department);

//                UploadImage();
                if (image_rui != null) {

                    progressDialog.setTitle("Image is Uploading...");
                    progressDialog.show();
                    StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(image_rui));
                    storageReference2.putFile(image_rui)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String userurl = uri.toString();
                                            databaseReference.child("users").child(username).child("userURL").setValue(userurl);
                                            startintent.putExtra("userURL", userurl);
                                            startActivity(startintent);
//                                            ivProfilePic.setImageURI(uri);
                                        }
                                    });
                                }
                            });
                }

                String message = "Your changes are successfully updated!";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
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
                ivProfilePic.setImageURI(image_rui);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //image is picked from camera, get uri of image

                ivProfilePic.setImageURI(image_rui);

            }

        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

//    public void UploadImage() {
//
//        if (image_rui != null) {
//
//            progressDialog.setTitle("Image is Uploading...");
//            progressDialog.show();
//            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(image_rui));
//            storageReference2.putFile(image_rui)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//
//                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    String url = uri.toString();
//                                    databaseReference.child("users").child(username).child("userURL").setValue(url);
////                                    ivProfilePic.setImageURI(uri);
//                                }
//                            });
//                        }
//                    });
//        }
//    }

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
                if (ContextCompat.checkSelfPermission(ViewProfileActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(ViewProfileActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(ViewProfileActivity.this);
                }
                break;
        }
    }

}