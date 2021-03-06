package com.johnyhawkdesigns.a56_tailorapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.fragment.AddEditPersonFragment;
import com.johnyhawkdesigns.a56_tailorapp.fragment.OrderFragment;
import com.johnyhawkdesigns.a56_tailorapp.fragment.SettingsFragment;
import com.johnyhawkdesigns.a56_tailorapp.fragment.SizeDetailFragment;
import com.johnyhawkdesigns.a56_tailorapp.fragment.SizeListFragment;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;


public class MainActivity extends AppCompatActivity implements
        AddEditPersonFragment.AddEditFragmentListener,
        SizeListFragment.SizeListFragmentListener,
        SizeDetailFragment.SizeDetailFragmentListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static int SPLASH_TIMEOUT = 2000; //This is 2 seconds
    Handler splashHandler = new Handler();

    //https://www.androidhive.info/2013/11/android-sliding-menu-using-navigation-drawer/
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader; // Navigation drawer Header with image and name and website
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_ORDERS = "orders";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    
    private PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // To add appropriate delay for screen.
        try{
            Thread.sleep(SPLASH_TIMEOUT);
        } catch (Exception e){
            Log.d(TAG, "onCreate: e = " + e);
        }

        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, SPLASH_TIMEOUT);


        toolbar = (Toolbar) findViewById(R.id.toolbar); // Note: Inside styles.xml, we defined AppTheme.NoActionBar and used inside AndroidMANIFEST for MainActivity's theme @style/AppTheme.NoActionBar
        setSupportActionBar(toolbar); // I had error at this line after using splshscreen, so I used Theme.AppCompat.Light.NoActionBar in styles for AppTheme



        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // android.support.v4.widget.DrawerLayout inside activity_main.xml
        navigationView = (NavigationView) findViewById(R.id.nav_view);  // android.support.design.widget.NavigationView inside activity_main.xml

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        // Means if we are opening app for the first time
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: savedInstanceState == null");
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


        // Get ViewModel
        personViewModel = new PersonViewModel(getApplication());
    }




    /***
     * Load navigation menu header information like background image, profile image name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        //txtName.setText("Tailor App");
        //txtWebsite.setText("An App to facilitate tailors");

        // loading header background image
        Glide.with(this).load(R.drawable.nav_menu_header)
                .transition(new DrawableTransitionOptions()
                .crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(R.mipmap.ic_launcher_round)
                .transition(new DrawableTransitionOptions()
                .crossFade())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }







    /***
     * Returns respected fragment that user selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        /*
        //*****I skipped this code below helps solve the "stuck fragment" problem when I visit detail view of user and return back
        // if user select the current navigation menu again, don't do anything just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            toggleFab();
            return;
        }
        */


        // Sometimes, when fragment has huge data, screen seems hanging when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG); // FrameLayout inside app_bar_main.xml
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable); // Post Runnable to Handler
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // Size fragment
                SizeListFragment sizeListFragment = new SizeListFragment();
                return sizeListFragment;
            case 1:
                // order fragment
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;
            case 2:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;

            default:
                return new SizeListFragment();
        }
    }



    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }



    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        Log.d(TAG, "onNavigationItemSelected: switching to sizes fragment");
                        break;
                    case R.id.nav_orders:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ORDERS;
                        Log.d(TAG, "onNavigationItemSelected: switching to orders fragment");
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SETTINGS;
                        Log.d(TAG, "onNavigationItemSelected: switching to settings fragment");
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        Log.d(TAG, "onNavigationItemSelected: Launching About us activity");
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteAllRecords) {

            Log.d(TAG, "onOptionsItemSelected: deleteAllRecords");
            
            // Build alert dialog for confirmation
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Do you want to delete all persons/sizes data??");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppUtils.showMessage(MainActivity.this, "Delete all persons success" );
                    personViewModel.deleteAllPersons();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog ad = builder.create();
            ad.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAddEditCompleted(int personID) {
        Log.d(TAG, "onAddEditCompleted: personID = " + personID);
    }

    @Override
    public void onPersonSelected(int personID) {
        Log.d(TAG, "onPersonSelected: personID = " + personID);

        // Create new SizeDetailFragment and pass personID in bundle arguments
        SizeDetailFragment sizeDetailFragment = new SizeDetailFragment();
        Bundle args = new Bundle();
        args.putInt("personID", personID);
        sizeDetailFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, sizeDetailFragment);
        transaction.addToBackStack(null); // By using this line, we will be able to return back
        transaction.commit();
    }

    @Override
    public void onPersonDeleted() {
        Log.d(TAG, "onPersonDeleted: ");
    }

    @Override
    public void onEditPerson(int personID) {
        Log.d(TAG, "onEditPerson: personID = " + personID);

        // Create new AddEditPersonFragment and pass personID in bundle arguments
        AddEditPersonFragment addEditPersonFragment = new AddEditPersonFragment();
        Bundle args = new Bundle();
        args.putInt("personID", personID);
        addEditPersonFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, addEditPersonFragment); //
        transaction.addToBackStack(null);
        transaction.commit();

    }
}



