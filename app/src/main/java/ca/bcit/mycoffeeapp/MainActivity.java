package ca.bcit.mycoffeeapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    MainFragment.OnFragmentInteractionListener,
                    RegisterFragment.OnFragmentInteractionListener
{

    private Fragment fragment;
    private Class fragmentClass;
    private Snackbar snackbar;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            initFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = null;

        if (id == R.id.nav_customize) {
            // Handle the camera action
        } else if (id == R.id.nav_featuredrinks) {

        } else if (id == R.id.nav_currentorder) {

        } else if (id == R.id.nav_my_saved) {

        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_add) {
            fragmentClass = RegisterFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            Log.wtf("TAG", "Fragment not loaded");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragments, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initFragment() {

        fragment = null;
        fragmentClass = MainFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragentManager = getSupportFragmentManager();
        fragentManager.beginTransaction().replace(R.id.fragments,fragment).commit();
    }

    @Override
    public void sendInfoToFireBase(){
        final EditText userEmail;
        final EditText userPassword;


        firebaseAuth = FirebaseAuth.getInstance();

        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        //progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(MainActivity.this, "user is created " + task.isSuccessful(), Toast.LENGTH_LONG ).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
    }

    public void printMessage() {
//        final View coordinator;
//        coordinator = view.findViewById(R.id.coordinator);
//        snackbar.make(coordinator, "Register", Snackbar.LENGTH_LONG).show();
    }



}

