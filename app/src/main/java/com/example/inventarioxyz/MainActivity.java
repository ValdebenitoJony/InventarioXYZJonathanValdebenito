package com.example.inventarioxyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void IniciarSesion(View view){
        mAuth.signInWithEmailAndPassword(correo.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "ha ingresado correctamente", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    mostrarMensajeBienvenida();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, Lista.class);
                            startActivity(intent);
                            //finish();
                        }
                    }, 3000);
                }else {
                    Toast.makeText(getApplicationContext(), "Autorizacion Fallida", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarMensajeBienvenida() {

        Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}