package me.sanesh.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.channels.SelectableChannel;

import me.sanesh.healthcareapp.models.User;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    TextInputEditText txtRegisterEmail, txtRegisterPassword, txtRegisterPhoneNo, txtRegisterUserName,txtRegisterName;
    TextInputLayout txtRegisterEmailLayout, txtRegisterPasswordLayout, txtRegisterPhoneNoLayout, txtRegisterUserNameLayout,txtRegisterNameLayout;
    RadioGroup btnAccountType;
    RadioButton btnSelectedAccountType;
    Button btnRegister;
    TextView navLogin;
    String TAG = "Register GUI";

    private String regName;
    private String regUserName;
    private String regEmail;
    private String regPassword;
    private String regPhoneNo;
    private String regAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtRegisterEmail = findViewById(R.id.txtRegEmailValue);
        txtRegisterPassword = findViewById(R.id.txtRegPasswordValue);
        txtRegisterPhoneNo = findViewById(R.id.txtRegPhonenoValue);
        txtRegisterUserName = findViewById(R.id.txtRegUsernameValue);
        txtRegisterName = findViewById(R.id.txtRegNameValue);
        navLogin = findViewById(R.id.ui_signinaccount_text);

        btnAccountType = findViewById(R.id.ui_radiogroup_signup);

        txtRegisterEmailLayout = findViewById(R.id.txtRegEmail);
        txtRegisterPasswordLayout = findViewById(R.id.txtRegPassword);
        txtRegisterPhoneNoLayout = findViewById(R.id.txtRegPhoneno);
        txtRegisterUserNameLayout = findViewById(R.id.txtRegUsername);
        txtRegisterNameLayout = findViewById(R.id.txtRegName);

        btnRegister = findViewById(R.id.btnRegisterUsr);

        firebaseAuth = FirebaseAuth.getInstance();

        navLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void registerUser(View view) {
        if (validateUserDetails()) {
            try {
                Log.e(TAG, "registerUser: "+regEmail );
                Log.e(TAG, "registerUser: "+regPassword );
                firebaseAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String currentUser = firebaseAuth.getUid();
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("users");
                            User user = new User(currentUser, regUserName, regName, regEmail, regPassword, regPhoneNo, regAccountType);
                            reference.child(currentUser).setValue(user);

                            Log.i(TAG, "registerUser: onComplete: Successfully User Created");

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed! \n Please Try Again...", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "userAuthenticator: Registration Failed!");
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "registerUser: Error: " + e.getMessage());
            }
        }

    }

    private boolean validateUserDetails () {
        txtRegisterEmailLayout.setErrorEnabled(false);
        txtRegisterEmailLayout.setError(null);
        txtRegisterPasswordLayout.setErrorEnabled(false);
        txtRegisterPasswordLayout.setError(null);
        txtRegisterPhoneNoLayout.setErrorEnabled(false);
        txtRegisterPhoneNoLayout.setError(null);
        txtRegisterUserNameLayout.setErrorEnabled(false);
        txtRegisterUserNameLayout.setError(null);
        txtRegisterNameLayout.setErrorEnabled(false);
        txtRegisterNameLayout.setError(null);

        regEmail = txtRegisterEmail.getText().toString().trim();
        regPassword = txtRegisterPassword.getText().toString().trim();
        regUserName = txtRegisterUserName.getText().toString().trim();
        regName = txtRegisterName.getText().toString().trim();
        regPhoneNo = txtRegisterPhoneNo.getText().toString().trim();

        int accountTypeId = btnAccountType.getCheckedRadioButtonId();
        btnSelectedAccountType = findViewById(accountTypeId);

        if (regEmail.isEmpty() || regEmail.equals(null) || regEmail.equals("")) {
            txtRegisterEmailLayout.setError("Invalid Email!");
            Log.e(TAG, "userAuthenticator Error: Invalid Email!");
            return false;
        } else {
            if (regPassword.isEmpty() || regPassword.equals(null) || regPassword.equals("")) {
                txtRegisterPasswordLayout.setError("Invalid Password!");
                Log.e(TAG, "userAuthenticator, Error: Invalid Passwords!");
                return false;
            } else  {
                if (regUserName.isEmpty() || regUserName.equals(null) || regUserName.equals("")) {
                    txtRegisterUserNameLayout.setError("Invalid UserName!");
                    Log.e(TAG, "userAuthenticator Error: Invalid UserName!");
                    return false;
                } else {
                    if (regName.isEmpty() || regName.equals(null) || regName.equals("")) {
                        txtRegisterNameLayout.setError("Invalid Name!");
                        Log.e(TAG, "userAuthenticator Error: Invalid Name!");
                        return false;
                    } else {
                        if (regPhoneNo.isEmpty() || regPhoneNo.equals(null) || regPhoneNo.equals("")) {
                            txtRegisterPhoneNoLayout.setError("Invalid Phone Number!");
                            Log.e(TAG, "userAuthenticator Error: Invalid Phone Number!");
                            return false;
                        } else {
                            if (btnSelectedAccountType.getText().equals("Professional")) {
                                regAccountType = btnSelectedAccountType.getText().toString().trim();
                                Log.i(TAG, "userAuthenticator: Authenticated Successfully");
                                return true;
                            } else if (btnSelectedAccountType.getText().equals("Visitor")) {
                                regAccountType = btnSelectedAccountType.getText().toString().trim();
                                Log.i(TAG, "userAuthenticator: Authenticated Successfully");
                                return true;
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed! \n Please Try Again...", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "userAuthenticator Error: Invalid Phone Number!");
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }
}