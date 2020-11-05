package com.example.necip.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText edtEnterEmail,  edtUserName, edtPassword;
    Button btnSActSignUp, btnSActLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        edtEnterEmail = findViewById(R.id.edtEnterEmail);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    onClick(btnSActSignUp);
                return false;
            }
        });
        btnSActSignUp = findViewById(R.id.btnSActSignUp);
        btnSActLogin = findViewById(R.id.btnSActLogIn);

        btnSActSignUp.setOnClickListener(this);
        btnSActLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSActSignUp:
                if (edtEnterEmail.getText().toString().equals("") || edtUserName.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "Email, Username, Password are required!", Toast.LENGTH_LONG, FancyToast.INFO, true).show();
                }
                else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEnterEmail.getText().toString());
                    appUser.setUsername(edtUserName.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtUserName.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up successfully", Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitionSocialMediaActivity();
                            } else
                                FancyToast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            progressDialog.dismiss();
                        }
                    });

                }

                break;
            case R.id.btnSActLogIn:
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
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
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
        finish(); //this activity wont show up again.
    }
}
