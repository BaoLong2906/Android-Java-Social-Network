package com.example.blfood.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blfood.Connection.IPadress;
import com.example.blfood.R;
import com.example.blfood.Storage.user;
import com.example.blfood.Token.MyToken;
import com.example.blfood.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText SignUpUsername, SignUpPassword, SignUpName, SignUpAge, SignUpSex;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 0;
    public static final int PERMISSON_CODE_GALLERY = 1000;

    public static String base64String;
    // một thuộc tính hình ảnh đẻ up lên server tạm thời chưa có
    ImageView btnTakeAvatarFromCamera, btnChoseAvatarFromGallary, SignUpAvatar;

    Button btnInsertUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();

        AnhXa();

        SignUpUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpUsername.getText().clear();
            }
        });

        SignUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpPassword.getText().clear();
            }
        });

        SignUpName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpName.getText().clear();
            }
        });

        SignUpAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpAge.getText().clear();
            }
        });

        SignUpSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpSex.getText().clear();
            }
        });

        btnTakeAvatarFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        btnChoseAvatarFromGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // nếu user chưa cấp quyền, thì xin quyền
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSON_CODE_GALLERY);
                    } else {
                        // nếu user trước đó đã cấp quyền
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, REQUEST_CODE_GALLERY);
                    }
                } else {
                    // nếu phiên bản andriod < 23 thì chỉ cần cấp quyền trong mainfests
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                }
            }
        });

        btnInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload image lên webservice
                UploadImage();
                //Toast.makeText(SignUp.this, base64String, Toast.LENGTH_LONG).show();
                // insert vào bảng user
                InsertUser();
                startActivity(new Intent(SignUp.this, login.class));
                Toast.makeText(SignUp.this, "Đăng ký xong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // hàm trả về kết quả xin cấp quyền truy cập của user, hàm này sẽ được gọi nếu như user lần đầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSON_CODE_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK && data != null) {
                    Toast.makeText(SignUp.this, "camera", Toast.LENGTH_LONG).show();
                    // lấy ảnh từ camera
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    SignUpAvatar.setImageBitmap(bitmap);
                    // convert ảnh bitmap sang base64 để gửi qua Volley đến Webservice sẽ decode chuổi base64
                    // và image sẽ lưu vào folder ảnh trong Werservice
                    BitmapToBase64(bitmap);
                    break;
                }
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    Toast.makeText(SignUp.this, "thu vien", Toast.LENGTH_LONG).show();
                    // lấy ảnh từ thư viện gallary
                        //Bitmap bitmap = (Bitmap) data.getExtras().get("data"); gallery không trả về bitmap chỉ có camera mới trả về một bản sao bitmap mà thôi
                        //SignUpAvatar.setImageBitmap(bitmap);
                    SignUpAvatar.setImageURI(data.getData());

                    // convert URI sang ảnh bitmap
                    Uri ImageCaptureUri = data.getData();
                    Bitmap bitmap = null;
                    if (ImageCaptureUri != null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(SignUp.this.getContentResolver(), ImageCaptureUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // convert ảnh bitmap sang base64 để gửi qua Volley đến Webservice sẽ decode chuổi base64
                    // và image sẽ lưu vào folder ảnh trong Werservice
                    BitmapToBase64(bitmap);
                    break;
                }
        }
    }

    void AnhXa() {
        SignUpUsername = (EditText) findViewById(R.id.SignUpUsername);
        SignUpPassword = (EditText) findViewById(R.id.SignUpPassword);
        SignUpName     = (EditText) findViewById(R.id.SignUpName);
        SignUpAge      = (EditText) findViewById(R.id.SignUpAge);
        SignUpSex      = (EditText) findViewById(R.id.SignUpSex);
        SignUpAvatar   = (ImageView) findViewById(R.id.SignUpAvatar);
        btnChoseAvatarFromGallary = (ImageView) findViewById(R.id.btnChoseAvatarFromGallary);
        btnTakeAvatarFromCamera = (ImageView) findViewById(R.id.btnTakeAvatarFromCamera);
        btnInsertUser  = (Button) findViewById(R.id.btnInsertUser);
    }

    synchronized void InsertUser() {
        String url = IPadress.ip + "Werservice/InsertUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("SignUpUsername", SignUpUsername.getText().toString().trim());
                params.put("SignUpPassword", SignUpPassword.getText().toString().trim());
                params.put("SignUpName", SignUpName.getText().toString().trim());
                params.put("SignUpAge", SignUpAge.getText().toString().trim());
                params.put("SignUpSex", SignUpSex.getText().toString().trim());
                String filename = "useravatar"+ SignUpUsername.getText().toString().trim() + ".png";
                params.put("avatarURL", filename);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(SignUp.this).getRequestQueue().add(stringRequest);
    }

    String BitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return base64String;
    }

    synchronized void UploadImage() {
        String url = IPadress.ip + "Werservice/UploadToImagesFolder.php";
        //Toast.makeText(SignUp.this, base64String, Toast.LENGTH_LONG).show();
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(SignUp.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // gửi lên webservice chuỗi base64 để decode sang image lưu trong folder image của webservice
                params.put("base64String", base64String);
                // gửi lên webservice username
                params.put("username", SignUpUsername.getText().toString().trim());
                //Toast.makeText(SignUp.this, base64String, Toast.LENGTH_LONG).show();
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        VolleySingleton.getInstance(SignUp.this).getRequestQueue().add(stringRequest2);
    }
}