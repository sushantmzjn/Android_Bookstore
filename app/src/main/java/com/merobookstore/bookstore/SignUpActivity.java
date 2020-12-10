package com.merobookstore.bookstore;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.bll.SignUpBLL;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.serverresponse.ImageResponse;
import com.merobookstore.bookstore.serverresponse.SignUpResponse;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Button back;
    private RelativeLayout rlayout;
    private Animation animation;
    private CircleImageView noImage;
    String imagePath;
    private String imageName = "";

    private EditText fullName, address, Username, password, confirmPassword;
    private RadioButton male, female;
    private Button signUp;
    private RadioGroup radioGroup;

    private NotificationManagerCompat notificationManagerCompat;
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.FullName);
        address = findViewById(R.id.Address);
        Username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        male = findViewById(R.id.Male);
        female = findViewById(R.id.Female);
        signUp = findViewById(R.id.btnSignUP);

        radioGroup = findViewById(R.id.RadioGroup);


        back = findViewById(R.id.btnBack);
        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        rlayout.setAnimation(animation);
        noImage = findViewById(R.id.noimage);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkPermission();

        noImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browserImage();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    saveImageOnly();
                    signUp();
                    DisplayNotification();
                }
            }
        });
    }

    private boolean validate() {
        boolean status = true;
        if (TextUtils.isEmpty(fullName.getText().toString().trim())) {
            fullName.setError("Type your name");
            return false;
        } else if (TextUtils.isEmpty(address.getText().toString().trim())) {
            address.setError("Input your address");
            return false;
        } else if (TextUtils.isEmpty(Username.getText().toString().trim())) {
            Username.setError("Input Username");
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("please type your password");

            if (!password.equals(confirmPassword)) {
                confirmPassword.setError("password didn't matched");
                return false;
            }
        } else if (Username.getText().toString().trim().length() < 6) {
            Username.setError("Minimum 6 character");
            status = false;
        }

        return status;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                browserImage();
                                break;
                            case 1:
                                loadCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 3);
        }
    }

    private void loadCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap imageBitmap = (Bitmap) bundle.get("data");
            noImage.setImageBitmap(imageBitmap);

        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(SignUpActivity.this, "select an image", Toast.LENGTH_LONG).show();
                return;
            }
            Uri uri = data.getData();
            noImage.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        }
    }

    public void browserImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection,
                null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;

    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signUp() {
        String fullname = fullName.getText().toString().trim();
        String addresss = address.getText().toString().trim();
        String username = Username.getText().toString().trim();

        int selectGender = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectGender);
        String gender = radioButton.getText().toString().trim();

        String passwordd = password.getText().toString().trim();
        String Cpassword = confirmPassword.getText().toString().trim();


        SignUpBLL signUpBLL = new SignUpBLL();
        StrictModeClass.StrictMode();

        if (signUpBLL.signupUser(fullname, addresss, username, gender, passwordd, imageName)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

//        User user = new User(fullname, addresss, username, gender, passwordd, imageName);
//
//        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
//        Call<SignUpResponse> signUpCall = usersAPI.registerUser(user);
//
//        signUpCall.enqueue(new Callback<SignUpResponse>() {
//            @Override
//            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(SignUpActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Toast.makeText(SignUpActivity.this, "user registered", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignUpResponse> call, Throwable t) {
//                Toast.makeText(SignUpActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void DisplayNotification() {
        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle("Sign Up Notification")
                .setContentText("Sign Up Successful")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(counter, notification);
        counter++;

    }
}
