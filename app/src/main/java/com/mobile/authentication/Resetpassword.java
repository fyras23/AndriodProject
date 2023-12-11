package com.mobile.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resetpassword extends AppCompatActivity {
    EditText email;
    Button reset;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        email = findViewById(R.id.email);
        reset = findViewById(R.id.send);
reset.setOnClickListener(v -> {
    resetpassword();
});

}



    private void resetpassword(){
        String emailAddress = email.getText().toString();
        if(emailAddress.isEmpty()) {
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Resetpassword.this,"Email sent", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Resetpassword.this,"Email not sent ", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }
}