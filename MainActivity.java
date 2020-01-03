package com.example.demologinsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        button = findViewById(R.id.signin_button);

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String EMAIL = email.getText().toString();
                String PASS = password.getText().toString();

                if (EMAIL.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();
                    return;
                }

                if (PASS.isEmpty()) {
                    password.setError("Password required");
                    password.requestFocus();
                    return;
                }

                if (PASS.length() < 6) {
                    password.setError("Minimum length 6");
                    password.requestFocus();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {x
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    public void openSignupActivity(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
