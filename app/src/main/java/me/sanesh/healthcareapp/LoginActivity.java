package me.sanesh.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    Toast toast;
    String TAG = "Login GUI";
    FirebaseAuth firebaseAuth;

    TextInputLayout emailLayout, passwordLayout;
    TextInputEditText email, password;
    TextView navSignUp;

    private String enteredUsername = null;
    private String enteredPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLayout = findViewById(R.id.txtLogEmail);
        passwordLayout = findViewById(R.id.txtLogPassword);
        email = findViewById(R.id.txtLogEmailValue);
        password = findViewById(R.id.txtLogPasswordValue);
        login = findViewById(R.id.btnLogin);
        navSignUp = findViewById(R.id.ui_crateaccount_text);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        navSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void realUser(View view) {
        if (validateCredentials()) {
            firebaseAuth.signInWithEmailAndPassword(enteredUsername, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed!, You are not a registered user.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "realUser : onComplete: User Not Exist");
                    }
                }
            });
        }
    }

    private boolean validateCredentials() {

        enteredUsername = email.getText().toString().trim();
        enteredPassword = password.getText().toString().trim();

        if (enteredUsername.isEmpty() || enteredUsername.equals(null) || enteredUsername.equals("")) {
            emailLayout.setError("Invalid Email!");
            Log.e(TAG, "userAuthenticator Error: Invalid Email!");
            return false;
        } else {
            if (enteredPassword.isEmpty() || enteredPassword.equals(null) || enteredPassword.equals("")) {
                passwordLayout.setError("Invalid Password!");
                Log.e(TAG, "userAuthenticator, Error: Invalid Passwords!");
                return false;
            } else {
                Log.e(TAG, "userAuthenticator, Successfully Validated!");
                return true;
            }
        }
    }
}