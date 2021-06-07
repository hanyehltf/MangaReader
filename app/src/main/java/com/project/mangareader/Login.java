package com.project.mangareader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mangareader.DatabaseManagment.SharePerfrence;
import com.project.mangareader.DatabaseManagment.User;
import com.project.mangareader.Home.MainActivity;

public class Login extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button login;
    private TextView goSignUp;
    private LoginDB loginDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginDB = new LoginDB(this);

        if (SharePerfrence.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        setView();

        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });

    }

    private void athentication() {
        String username = userName.getText().toString();
        String Password = password.getText().toString();


        if (TextUtils.isEmpty(username)) {
            userName.setError("لطفا نام کاربری را وارد کنید ");
            userName.requestFocus();

        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("لطفا پسورد را وارد کنید ");
            password.requestFocus();

        }
        loginDB.login(username, Password, new LoginDB.login() {
            @Override
            public void onReceived(User user) {
                if (user != null) {
                    SharePerfrence.getInstance(getApplicationContext()).userLogin(user);
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                } else {
                    Toast.makeText(Login.this, "نام کاربری یا پسورد وجود ندارد  ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setView() {
        userName = (EditText) findViewById(R.id.login_user_name);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_submit);
        goSignUp = (TextView) findViewById(R.id.gotosignup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                athentication();
            }
        });
    }


}