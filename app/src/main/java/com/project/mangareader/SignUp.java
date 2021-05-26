package com.project.mangareader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.mangareader.DatabaseManagment.SharePerfrence;
import com.project.mangareader.DatabaseManagment.SignUpDB;
import com.project.mangareader.DatabaseManagment.User;
import com.project.mangareader.Home.MainActivity;

import java.io.ByteArrayOutputStream;

public class SignUp extends AppCompatActivity {
    private ImageView profile;
    private EditText UserName;
    private EditText Email;
    private EditText Password;
    private Button Submit;
    private Uri ImageUri;
    private String encodedImage;
    SignUpDB signUpDB;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUp_View();
        insertDatatoDB();
        signUpDB = new SignUpDB(this);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, 1);
            }
        });


    }

    private void insertDatatoDB() {
        String userName = UserName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            UserName.setError("لطفا نام کاربری را وارد کنید ");
            UserName.requestFocus();

        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("لطفا پسورد را وارد کنید ");
            Password.requestFocus();

        }

        if (TextUtils.isEmpty(email)) {
            Email.setError("لطفا پسورد را وارد کنید ");
            Email.requestFocus();

        }


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  if(Email.getText().toString()!=null && Password.getText().toString()!=null &&
  UserName.getText().toString()!=null ){

      setData();



  }else {
      Toast.makeText(SignUp.this,"لطفا فیلد هارا پر کنید ",Toast.LENGTH_LONG).show();


  }
            }
        });



    }


   public void setData(){

       user.setEmail(Email.getText().toString());
       user.setPassword(Password.getText().toString());
       user.setId(UserName.getText().toString());
       user.setImage(encodedImage);
       signUpDB.addDataToDatabase(user, new SignUpDB.signUp() {
           @Override
           public void onReceived(int status) {
               switch (status) {
                   case 0:
                       Toast.makeText(SignUp.this, "عملیات با شکست مواجه شد ", Toast.LENGTH_LONG).show();
                       break;
                   case 1: {
                       Toast.makeText(SignUp.this, "عملیات موفق امیز بود  ", Toast.LENGTH_LONG).show();
                       setInformation(user);
                   }
                   break;

                   case 2:
                       Toast.makeText(SignUp.this, "ایمیل یا نام کاربری وجود دارد  ", Toast.LENGTH_LONG).show();
                       break;


               }
           }
       });



   }

    private void setInformation(User user) {
        SharePerfrence.getInstance(getApplicationContext()).userLogin(user);
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    private void setUp_View() {
        profile = (ImageView) findViewById(R.id.profile_signup);
        UserName = (EditText) findViewById(R.id.sign_up_id);
        Email = (EditText) findViewById(R.id.signUp_email);
        Password = (EditText) findViewById(R.id.sign_up_password);
        Submit = (Button) findViewById(R.id.sign_up_submit);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                this.ImageUri = data.getData();
                Bitmap bitmap;

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                profile.setImageURI(ImageUri);
                final int COMPRESSION_QUALITY = 100;

                ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                        byteArrayBitmapStream);
                byte[] b = byteArrayBitmapStream.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                Toast.makeText(this, "     بارگزاری موفقیعت امیز بود    " + ImageUri, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this, "در بارگزاری عکس مشکلی پیش امده است ", Toast.LENGTH_LONG).show();
            }
        }
    }


}