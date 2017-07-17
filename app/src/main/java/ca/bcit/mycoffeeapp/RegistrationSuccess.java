package ca.bcit.mycoffeeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);
    }

    public void login(final View view) {
        final Intent intent;
        intent = new Intent(RegistrationSuccess.this, MainActivity.class);
        startActivity(intent);
    }
}
