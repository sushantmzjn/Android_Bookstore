package com.merobookstore.bookstore.ui.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.model.UserUpdate;
import com.merobookstore.bookstore.serverresponse.ImageResponse;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    CircleImageView profile;
    private EditText fname, address, uname, gender;
    private Button update;

    String imagePath;
    private String imageName = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profile = root.findViewById(R.id.profile);
        fname = root.findViewById(R.id.uFullname);
        address = root.findViewById(R.id.uaddress);
        uname = root.findViewById(R.id.uUsername);
        gender = root.findViewById(R.id.gender);
        update = root.findViewById(R.id.btnupdate);

        loadCurrentUser();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                browserImage();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userUpdate();
//                saveImageOnly();

            }
        });


        return root;
    }

    private void loadCurrentUser() {
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<User> userCall = usersAPI.getUserDetails(Url.token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = Url.imagePath + response.body().getImage();

                Picasso.get().load(imgPath).into(profile);

                StrictModeClass.StrictMode();
                try {

                    String fullNAME = response.body().getFullName();
                    String Address = response.body().getAddress();
                    String Username = response.body().getUsername();
                    String Gender = response.body().getGender();


                    URL url = new URL(imgPath);
                    profile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

                    fname.setText(fullNAME);
                    address.setText(Address);
                    uname.setText(Username);
                    gender.setText(Gender);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (data == null) {
//                Toast.makeText(getActivity(), "select an image", Toast.LENGTH_LONG).show();
//            }
//            Uri uri = data.getData();
//            profile.setImageURI(uri);
//            imagePath = getRealPathFromUri(uri);
//        }
//    }

//    public void browserImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, 1);
//    }

//    private String getRealPathFromUri(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext(), uri, projection,
//                null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(colIndex);
//        cursor.close();
//        return result;
//    }
//
//    private void saveImageOnly() {
//        File file = new File(imagePath);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
//                file.getName(), requestBody);
//
//        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
//        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);
//
//        StrictModeClass.StrictMode();
//        //Synchronous method
//        try {
//            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
//            imageName = imageResponseResponse.body().getFilename();
//            Toast.makeText(getActivity(), "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Toast.makeText(getActivity(), "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    private void userUpdate() {
        String pname = fname.getText().toString().trim();
        String paddress = address.getText().toString().trim();
        String pusername = uname.getText().toString().trim();
        String pgender = gender.getText().toString().trim();


        UserUpdate userUpdate = new UserUpdate(pname, paddress, pusername, pgender);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);

        Call<String> stringCall = usersAPI.userUpdate(Url.token, userUpdate);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getActivity(), "user updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "user updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}