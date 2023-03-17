package com.ksr.ksrinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private TextView forgotpass;
    private Button Btn, signupnew;
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        forgotpass = findViewById(R.id.forgot);

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.loginemail);
        passwordTextView = findViewById(R.id.loginpassword);
        Btn = findViewById(R.id.loginbtn);
        progressbar = findViewById(R.id.progressbar);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(login.this, home.class);
            startActivity(i);
            finish();
        }

        btSignIn = findViewById(R.id.bt_sign_in);

        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("336596569681-8i9q2mjurtp4eh9hjhf8o7d1t4k7lnru.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(login.this, googleSignInOptions);

        btSignIn.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            // Check condition
            if (requestCode == 100) {
                // When request code is equal to 100 initialize task
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                // check condition
                if (signInAccountTask.isSuccessful()) {
                    // When google sign in successful initialize string
                    String s = "Google sign in successful";
                    // Display Toast
                    //displayToast(s);
                    //Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                    // Initialize sign in account
                    try {
                        // Initialize sign in account
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        // Check condition
                        if (googleSignInAccount != null) {
                            // When sign in account is not equal to null initialize auth credential
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            // Check credential
                            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Check condition
                                    if (task.isSuccessful()) {
                                        // When task is successful redirect to profile activity display Toast
                                        startActivity(new Intent(login.this, home.class));
                                        //displayToast("Firebase authentication successful");
                                        Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // When task is unsuccessful display Toast
                                        Toast.makeText(getApplicationContext(), "Login failed.. Try with manual email id and password!", Toast.LENGTH_SHORT).show();
                                        //displayToast("Authentication Failed :" + task.getException().getMessage());
                                    }
                                }
                            });
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }





        signupnew = findViewById(R.id.neednew);

        signupnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(login.this,
                        signup.class);
                startActivity(intent);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(login.this,
                        signup.class);
                startActivity(intent);
            }
        });

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                            "Login successful!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            // hide the progress bar
                                            progressbar.setVisibility(View.GONE);

                                            // if sign-in is successful
                                            // intent to home activity
                                            Intent intent
                                                    = new Intent(login.this,
                                                    home.class);
                                            startActivity(intent);
                                        } else {

                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                            "Login failed!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            // hide the progress bar
                                            progressbar.setVisibility(View.GONE);
                                        }
                                    }
                            });
    }
}