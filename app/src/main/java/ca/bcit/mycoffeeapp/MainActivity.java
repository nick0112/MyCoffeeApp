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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    MainFragment.OnFragmentInteractionListener,
                    RegisterFragment.OnFragmentInteractionListener,
                    LoginFragment.OnFragmentInteractionListener,
                    FeatureDrinksFragment.OnFragmentInteractionListener,
                    DrinkDetailsFragment.OnFragmentInteractionListener,
                    CurrentOrdersFragment.OnFragmentInteractionListener,
                    MyOrderFragment.OnFragmentInteractionListener



{
    private Menu menu;
    private String currentUserName;
    private MenuItem menuItem;
    private Boolean hasLoggedIn = false;
    private Fragment fragment;
    private Class fragmentClass;
    private Snackbar snackbar;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            fragmentClass = LoginFragment.class;
            initFragment(fragmentClass);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        currentUserName = "Guest";
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
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
        return super.onCreateOptionsMenu(menu);
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
//        Intent intent;
//        intent = new Intent(MainActivity.this, CurrentOrders.class);

        if (id == R.id.nav_featuredrinks) {
            fragmentClass = FeatureDrinksFragment.class;
        } else if (id == R.id.nav_currentorder) {
            fragmentClass = CurrentOrdersFragment.class;
        } else if (id == R.id.nav_my_saved) {
            fragmentClass = MyOrderFragment.class;
        } else if (id == R.id.nav_login) {
            if (currentUserName.equals("Guest")) {
                fragmentClass = LoginFragment.class;
            } else {
                fragmentClass = MyOrderFragment.class;
            }
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

    public void initFragment(Class fragClass) {

        fragment = null;
        fragmentClass = fragClass;
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
        final EditText userName;

        userName = (EditText) findViewById(R.id.nameText);
        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final String name = userName.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();

        //progressBar.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please Fill in All 3 Fields", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Handle sign-in auth success/fail
                            generateUser(name, email, password);
                            if (task.isSuccessful()) {
                                final Intent intent;
                                intent = new Intent(MainActivity.this, RegistrationSuccess.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Creation Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void printMessage() {
//        final View coordinator;
//        coordinator = view.findViewById(R.id.coordinator);
//        snackbar.make(coordinator, "Register", Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void userLogin() {
        final EditText userEmail;
        final EditText userPassword;
        final String email;
        final String password;

        userEmail = (EditText) findViewById(R.id.userEmailInput);
        userPassword = (EditText) findViewById(R.id.userPasswordInput);

        email = userEmail.getText().toString();
        password = userPassword.getText().toString();

        // Make sure user enter all info
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Fill in All Fields", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_LONG).show();
                            } else {
                                updateUiAfterLogin();
                                fragmentClass = FeatureDrinksFragment.class;
                                initFragment(fragmentClass);

                            }
                        }
                    });
        }

    }

    public void updateUiAfterLogin() {
        final String email;
        final TextView textView;
        textView = (TextView) findViewById(R.id.welcome);
        DatabaseReference ref = firebaseDatabase.getReference("users");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        menuItem = menu.findItem(R.id.nav_login);
        email = firebaseAuth.getCurrentUser().getEmail();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (email != null) {
                    for (DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                        User user = postSnapShot.getValue(User.class);
                        if (user.getUserEmail().equals(email)) {
                            menuItem.setTitle(user.getUserName());
                            currentUserName = user.getUserName();
                            textView.setText("Good Morning " + currentUserName);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    public void generateUser(String userName, String userEmail, String userPassword) {
        DatabaseReference ref = firebaseDatabase.getReference("users"); //users is a node in your Firebase Database.
        User user = new User(userName, userEmail, userPassword); //ObjectClass for Users
        ref.push().setValue(user);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void orderDrink() {
        DatabaseReference ref = firebaseDatabase.getReference("Orders");
        Drink drink;
        Order order;
        String   coffeeName;
        int shots;
        String temperature;
        String flavours;
        String milkType;

        TextView textCoffeeName = (TextView) findViewById(R.id.seeCoffeeNamePlease);
        EditText shotsSelected = (EditText) findViewById(R.id.shotSelection);
        Spinner milkSpinner = (Spinner) findViewById(R.id.milktype);
        Spinner flavourSpinner = (Spinner) findViewById(R.id.flavours);

        CheckBox hotSelected = (CheckBox) findViewById(R.id.hot);
        //CheckBox coldSelected = (CheckBox) findViewById(R.id.cold);

        if (hotSelected.isEnabled()) {
            temperature = "Hot";
        } else {
            temperature = "Cold";
        }

        coffeeName = textCoffeeName.getText().toString().trim();
        shots = Integer.parseInt(shotsSelected.getText().toString());
        flavours = flavourSpinner.getSelectedItem().toString().trim();
        milkType = milkSpinner.getSelectedItem().toString().trim();

        drink = new Drink (coffeeName, shots, flavours, temperature, milkType);
        order = new Order (currentUserName, drink);

        ref.push().setValue(order);


        fragmentClass = CurrentOrdersFragment.class;
        initFragment(fragmentClass);

    }

    @Override
    public void displayMyCoffeeInfo() {

    }
}

