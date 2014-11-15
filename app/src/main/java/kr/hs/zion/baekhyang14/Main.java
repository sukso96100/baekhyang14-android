package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;


public class Main extends ActionBarActivity {
    private DrawerLayout NavigationDrawer;
    private ActionBarDrawerToggle DrawerToggle;
    private ListView DrawerList;
    private ArrayList<String> DrawerArray;
    private ArrayList<Drawable> IconArray;
    private DrawerListAdapter Adapter;
    private Boolean isNavDrawerOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        int random = (int)(Math.random()*4)+1;
        switch (random){
            case 1:
                toolbar.setBackgroundResource(R.drawable.polygon);
                break;
            case 2:
                toolbar.setBackgroundResource(R.drawable.performance);
                break;
            case 3:
                toolbar.setBackgroundResource(R.drawable.fireworks);
                break;
            case 4:
                toolbar.setBackgroundResource(R.drawable.code);
                break;
        }
        setSupportActionBar(toolbar);

        //Show Tutorial Screen if user run this app for the first time
        SharedPreferences Pref = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor PrefEdit = Pref.edit();
        boolean firstrun = Pref.getBoolean("firstrun",false);
        if(!firstrun){
            startActivity(new Intent(this, FirstRun.class));
            PrefEdit.putBoolean("firstrun",true);
            PrefEdit.commit();
        }else{}

        CardView BoothCard = (CardView) findViewById(R.id.booth);
        CardView PerformanceCard = (CardView) findViewById(R.id.performance);

        BoothCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, FindBooth.class));
            }
        });

        PerformanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, PerformanceSchedule.class));
            }
        });

        //Navigation Drawer
        DrawerArray = new ArrayList<String>();
        DrawerArray.add(getString(R.string.title_activity_help));
        DrawerArray.add(getString(R.string.title_activity_about));

        IconArray = new ArrayList<Drawable>();
        IconArray.add(getResources().getDrawable(R.drawable.ic_help_black));
        IconArray.add(getResources().getDrawable(R.drawable.ic_info_black));

        NavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerList = (ListView) findViewById(R.id.left_drawer);

        Adapter = new DrawerListAdapter(this, DrawerArray, IconArray);
        DrawerList.setAdapter(Adapter);

        //Listen for Navigation Drawer State
        DrawerToggle = new ActionBarDrawerToggle(this,
                NavigationDrawer, R.string.drawer_open, R.string.drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                isNavDrawerOpen = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setBackgroundDrawable(Darkblue);
                isNavDrawerOpen = true;
            }

        };
        NavigationDrawer.setDrawerListener(DrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Drawer Item Click action
        DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(Main.this, Help.class));
                        break;
                    case 1:
                        startActivity(new Intent(Main.this, About.class));
                        break;
                }
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(DrawerToggle.onOptionsItemSelected(item)){
            if(!isNavDrawerOpen){
                NavigationDrawer.openDrawer(Gravity.LEFT);
            }
            else{
                NavigationDrawer.closeDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        DrawerToggle.syncState();
    }

    @Override
    public void onBackPressed(){
        if(isNavDrawerOpen){
            NavigationDrawer.closeDrawer(Gravity.LEFT);
        }else{
            finish();
        }
    }
}
