package com.ruthbeeler.possibleapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sign In";
    SharedPreferences myPreferences;
    private String shredPreFile = "com.ruthbeeler.possibleapplication";

    private EditText myName;
    private EditText myEmailField;
    private EditText myPasswordField;
    private String lastName;
    private String lastEmail;
    private String lastPassword;
    public static final String Name = "";
    public static final String Email = "email";
    public static final String Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, About.class);
                this.startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void displayToast(View view) {
        Toast.makeText(getApplicationContext(),"Hello World",Toast.LENGTH_SHORT).show();
    }

    public void getMovieList(View view) {
        Intent intent = new Intent(this, listMoviesActivity.class);
        startActivity(intent);
    }

    public void getMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void getTrafficList(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }


    private void signIn () {
        Log.d(TAG, "login");
        if (!validateForm()) {
            return;
        }

        final String username = myName.getText().toString();
        String email = myEmailField.getText().toString();
        String password = myPasswordField.getText().toString();

        /*
        SharedPreferences.Editor preferenceEditor = myPreferences.edit();
        preferenceEditor.putString(Name, username);
        preferenceEditor.putString(Email, email);
        preferenceEditor.putString(Password, password);
        preferenceEditor.commit();
        */

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("ruthbeeler3@gmail.com", "strawberry")
                .addOnCompleteListener(this, new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("FIREBASE", "signIn:onComplete:" +
                                        task.isSuccessful());
                                if (task.isSuccessful()) {
                                    // update profile
                                    FirebaseUser user =
                                            FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new
                                            UserProfileChangeRequest.Builder()
                                            .setDisplayName("value")
                                            .build();
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull
                                                                               Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("FIREBASE", "User profile updated.");
                                                        // Go to FirebaseActivity
                                                        startActivity(new
                                                                Intent(MainActivity.this, TeamActivity.class));
                                                    }
                                                }
                                            });
                                } else {
                                    Log.d("FIREBASE", "sign-in failed");
                                    Toast.makeText(MainActivity.this, "Sign In Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }


    public boolean validateForm() {
        boolean result = true;

        if(TextUtils.isEmpty(myName.getText().toString())) {
            myName.setError("Name required");
            result = false;
        } else {
            myName.setError(null);
        }

        if(TextUtils.isEmpty(myEmailField.getText().toString())) {
            myEmailField.setError("Email Required");
            result = false;
        } else {
            myEmailField.setError(null);
        }

        if(TextUtils.isEmpty(myPasswordField.getText().toString())) {
            myPasswordField.setError("Password Required");
            result = false;
        } else {
            myPasswordField.setError(null);
        }
        return result;
    }

    public void signIn(View view) {
    signIn();
    }
}

