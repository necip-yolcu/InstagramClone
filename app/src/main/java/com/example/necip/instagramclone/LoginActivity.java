package com.example.necip.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtLoginEmail, edtLoginPassword;
    Button btnLActLogin, btnLActSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLActLogin);
                }
                return false;
            }
        });
        btnLActLogin = findViewById(R.id.btnLActLogin);
        btnLActSignUp = findViewById(R.id.btnLActSignUp);

        btnLActLogin.setOnClickListener(this);
        btnLActSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLActLogin:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(LoginActivity.this, user.getUsername() + " is logged in successfully", Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            transitionSocialMediaActivity();
                        } else
                            FancyToast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                });

                break;
            case R.id.btnLActSignUp:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * for hide the keyboard
     * @param view
     */
    public void rootLayoutTapped(View view) {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transitionSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish(); //this activity wont show up again.
    }
}