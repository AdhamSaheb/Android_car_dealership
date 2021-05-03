package com.example.car_dealership_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeUser extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*Build the drawer based on user type */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_view_all_reserves,
                R.id.nav_home_admin,
                R.id.nav_delete_user,
                R.id.nav_make_admin,
                R.id.nav_profile,
                R.id.nav_home_user,
                R.id.nav_all_cars,
                R.id.nav_user_reservations,
                R.id.nav_special_offers,
                R.id.nav_favorites,
                R.id.nav_call_us,

                R.id.logoutButton)
                .setDrawerLayout(drawer)
                .build();
        //hide un needed items from drawer
        if(isAdmin()) {
            navigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_all_cars).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_user_reservations).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_special_offers).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_favorites).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_call_us).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        }else{
            navigationView.getMenu().findItem(R.id.nav_make_admin).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_delete_user).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_view_all_reserves).setVisible(false);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //set logout button action
        navigationView.getMenu().findItem(R.id.logoutButton).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                HomeUser.this.logout();
                return true;
            }
        });


        //set first name in nav bar
        String firstName = getFirstName();
        View headerView = navigationView.getHeaderView(0);
        final TextView firstNameNav = (TextView) headerView.findViewById(R.id.nav_firstName);
        firstNameNav.setText(firstName);
        //TODO : set image


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void logout() {
        //clear shared prefrences
        SharedPreferences.Editor editor = getSharedPreferences("SignedIn", MODE_PRIVATE).edit();
        editor.putString("Email", "Default");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    //loads email from local preferences
    String loadEmailFromLocal() {
        SharedPreferences prefs = this.getSharedPreferences(
                "SignedIn", Context.MODE_PRIVATE);
        return prefs.getString("Email", "Default");
    }

    String getFirstName() {
        String email = loadEmailFromLocal();
        DatabaseHelper dataBaseHelper = new
                DatabaseHelper(getApplicationContext(), "Project", null, 1);
        /*Navigate based on user  type */
        final Cursor firstName = dataBaseHelper.getFirstName(email);
        if (firstName.moveToFirst()) {
            return firstName.getString(0);
        }
        return "";
    }

    boolean isAdmin() {
        DatabaseHelper dataBaseHelper = new
                DatabaseHelper(getApplicationContext(), "Project", null, 1);
        final Cursor isAdmin = dataBaseHelper.isAdmin(loadEmailFromLocal());
        if (isAdmin.moveToFirst()) {
            System.out.println(isAdmin.getInt(0));
            return( isAdmin.getInt(0) == 1 );
        }
        return false ; // unreachable
    }
}
