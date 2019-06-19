package com.example.acer.jm_pos.home_sales;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.jm_pos.R;
import com.example.acer.jm_pos.dashboard.dashboard_fragment;
import com.example.acer.jm_pos.home_sales.home_sales_fragment.home_sale_fragment;
import com.example.acer.jm_pos.inventory.inventory_fragment;
import com.example.acer.jm_pos.reports.reports_fragment;




public class home_sales extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, home_sale_fragment.OnFragmentInteractionListener, home_sales_contract.home_sales_view,
        inventory_fragment.OnFragmentInteractionListener,dashboard_fragment.OnFragmentInteractionListener,reports_fragment.OnFragmentInteractionListener{

    home_sales_presenter presenter;
    Menu menu;

    //Object declaration
    View header;
    TextView user_name;
    TextView user_contact_number;
    ImageView user_image;

    //sharedLocation
    SharedPreferences inventory_state;
    SharedPreferences getInventory_state;
    SharedPreferences.Editor inventory_stateEditor;

    //Object Declaration
    String home_sales_state = "";

    //Instances class
    static home_sales instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new home_sales_presenter(this);
        instance = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.inflateHeaderView(R.layout.nav_header_home_sales);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));

        //Object Declaration
        user_name               = header.findViewById(R.id.admin_name);
        user_contact_number     = header.findViewById(R.id.admin_contact);
        user_image              = header.findViewById(R.id.admin_image);


        //LocalStorage Declaration For Inventory State
        inventory_state         = getSharedPreferences("home_sales_state", Context.MODE_PRIVATE);
        getInventory_state      = getApplicationContext().getSharedPreferences("home_sales_state", Context.MODE_PRIVATE);


        //Getting String of home_sales_state
        home_sales_state        = getInventory_state.getString("home_sales_state","");

        mainFragment(home_sales_state);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            presenter.onLogout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_sales, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_sales) {
            //Disabled Setting Icon
            MenuItem discount_setting = menu.findItem(R.id.discount_setting);
            discount_setting.setVisible(true);

            //Store inventory State
            inventory_stateEditor = inventory_state.edit();
            inventory_stateEditor.putString("home_sales_state","home_sales");
            inventory_stateEditor.commit();

            //Intent to other fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, new home_sale_fragment());
            ft.commit();
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Sales</font>"));

        } else if (id == R.id.nav_inventory) {
            //Disabled Setting Icon
            MenuItem discount_setting = menu.findItem(R.id.discount_setting);
            discount_setting.setVisible(false);

            //Store inventory State
            inventory_stateEditor = inventory_state.edit();
            inventory_stateEditor.putString("home_sales_state","inventory");
            inventory_stateEditor.commit();

            //Intent to other fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, new inventory_fragment());
            ft.commit();
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Inventory</font>"));

        } else if (id == R.id.nav_dashboard) {
            //Disabled Setting Icon
            MenuItem discount_setting = menu.findItem(R.id.discount_setting);
            discount_setting.setVisible(false);

            //Store inventory State
            inventory_stateEditor = inventory_state.edit();
            inventory_stateEditor.putString("home_sales_state","dashboard");
            inventory_stateEditor.commit();

            //Intent to other fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, new dashboard_fragment());
            ft.commit();
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Dashboard</font>"));

        } else if (id == R.id.nav_reports) {
            //Disabled Setting Icon
            MenuItem discount_setting = menu.findItem(R.id.discount_setting);
            discount_setting.setVisible(false);

            //Store inventory State
            inventory_stateEditor = inventory_state.edit();
            inventory_stateEditor.putString("home_sales_state","reports");
            inventory_stateEditor.commit();

            //Intent to other fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, new reports_fragment());
            ft.commit();
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Reports</font>"));

        } else if (id == R.id.nav_logout) {
            presenter.onLogout();
        }else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExit() {
        finish();
    }

    @Override
    public void onPopulateNavData(String first_name, String last_name, String contact_number) {
        user_name.setText("Welcome, "+first_name+" "+last_name);
        user_contact_number.setText(contact_number);
    }


    public void mainFragment(String state){
        Log.d("HOME_STATE",state);

        switch(state){
            case "home_sales":
                presenter.onSalesFragment();
            break;

            case "inventory":
                presenter.onInventoryFragment();
            break;

            case "dashboard":

            break;


            case "reports":

            break;

        }
    }

    @Override
    public void changeFragmentToSales() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        presenter.onUserData(username);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, new home_sale_fragment());
        ft.commit();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Sales</font>"));
    }

    @Override
    public void changeFragmentToInventory() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, new inventory_fragment());
        ft.commit();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF' align='center'>Inventory</font>"));
    }



}
