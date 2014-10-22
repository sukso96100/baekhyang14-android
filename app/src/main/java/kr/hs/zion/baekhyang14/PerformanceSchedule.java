package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class PerformanceSchedule extends ActionBarActivity {
    private DrawerLayout NavigationDrawer;
    private ActionBarDrawerToggle DrawerToggle;
    private ListView DrawerList;
    private ArrayList<String> DrawerArray;
    private ArrayList<Drawable> IconArray;
    private DrawerListAdapter Adapter;
    private Boolean isNavDrawerOpen = false;

    private String[] TimeArray;
    private String[] TitleArray;
    private String[] PerformerArray;
    private String[] DescArray;
    private String[] TurnArray;

    LinearLayout ContentsRoot;
    SwipeRefreshLayout SRL;
    ScrollView SV;
    View header;
    ColorDrawable Transparent;
    ColorDrawable Darkblue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_schedule);
        ContentsRoot = (LinearLayout) findViewById(R.id.contents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SRL = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        SV = (ScrollView) findViewById(R.id.scrollview);
        header = (View) findViewById(R.id.header);
        Transparent = new ColorDrawable(Color.TRANSPARENT);
        Darkblue = new ColorDrawable(Color.parseColor("#ff373166"));

        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SRL.setRefreshing(true);
                NetWorkTask();
            }
        });

       NetWorkTask();

        if(SV.getScrollY()<=header.getBottom()/2){
            getSupportActionBar().setBackgroundDrawable(Transparent);
        }else{
            getSupportActionBar().setBackgroundDrawable(Darkblue);
        }

        SV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(SV.getScrollY()<=header.getBottom()/2){
                    getSupportActionBar().setBackgroundDrawable(Transparent);
                }else{
                    getSupportActionBar().setBackgroundDrawable(Darkblue);
                }

                return false;
            }
        });

        ImageView HeaderIcon = (ImageView) header.findViewById(R.id.icon);
        ImageView HeaderBg = (ImageView) header.findViewById(R.id.background);

        HeaderIcon.setImageDrawable(getResources().getDrawable(R.drawable.stage));
        HeaderIcon.setBackgroundColor(Color.WHITE);

        //Navigation Drawer
        DrawerArray = new ArrayList<String>();
        DrawerArray.add(getString(R.string.home));
        DrawerArray.add(getString(R.string.title_activity_find_booth));
        DrawerArray.add(getString(R.string.title_activity_performance_schedule));
        DrawerArray.add(getString(R.string.title_activity_help));
        DrawerArray.add(getString(R.string.title_activity_about));

        IconArray = new ArrayList<Drawable>();
        IconArray.add(getResources().getDrawable(R.drawable.ic_home_black));
        IconArray.add(getResources().getDrawable(R.drawable.booth));
        IconArray.add(getResources().getDrawable(R.drawable.stage));
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
                //Change ToolBar Color by Scroll Degree
                if(SV.getScrollY()<=header.getBottom()/2){
                    getSupportActionBar().setBackgroundDrawable(Transparent);
                }else{
                    getSupportActionBar().setBackgroundDrawable(Darkblue);
                }
                isNavDrawerOpen = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setBackgroundDrawable(Darkblue);
                isNavDrawerOpen = true;
            }

        };
        NavigationDrawer.setDrawerListener(DrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(PerformanceSchedule.this, Main.class));
                        break;
                    case 1:
                        startActivity(new Intent(PerformanceSchedule.this, FindBooth.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        startActivity(new Intent(PerformanceSchedule.this, Help.class));
                        break;
                    case 4:
                        startActivity(new Intent(PerformanceSchedule.this, About.class));
                        break;
                }
            }
        });
    }


    private void NetWorkTask(){
        AsyncHttpClient AsyncJsonClient = new AsyncHttpClient();
        SRL.setRefreshing(true);
        AsyncJsonClient.get("http://www.youngbin-han.kr.pe/baekhyang14/performance/schedule.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                ContentsRoot.removeAllViews();
                //바이트 배열을 문자열로 변환
                //Convert Byte Array to String
                String ConvertedResponse = null;
                try {
                    ConvertedResponse = new String(bytes, "UTF-8");
                    Log.d("JsonResponse", ConvertedResponse)
                    ;                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                JSONObject FullSchedObj = null;
                JSONArray SchedArray = null;
                try {
                    FullSchedObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                    SchedArray = new JSONArray(FullSchedObj.getString("schedule")); //Json 객체에서 schedule 배열 얻기
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TimeArray = new String[SchedArray.length()];
                TitleArray = new String[SchedArray.length()];
                PerformerArray = new String[SchedArray.length()];
                DescArray = new String[SchedArray.length()];
                TurnArray = new String[SchedArray.length()];

                for (int n = 0; n < SchedArray.length(); n++) {
                    // SchedArray 에서 각 요소로 Json 객체 생성
                    JSONObject EachSchedObj = null;
                    final int fn = n;
                    try {
                        EachSchedObj = SchedArray.getJSONObject(n);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    LayoutInflater LI = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View Item = LI.inflate(R.layout.item_schedule, null);
                    TextView TimeTxt = (TextView) Item.findViewById(R.id.time);
                    TextView TitleTxt = (TextView) Item.findViewById(R.id.title);
                    TextView PerformerTxt = (TextView) Item.findViewById(R.id.performer);

                    try {
                        Log.d("Getting String Item",EachSchedObj.getString("time") + EachSchedObj.getString("name") +
                                EachSchedObj.getString("performers"));

                        TimeTxt.setText(EachSchedObj.getString("time"));
                        TitleTxt.setText(EachSchedObj.getString("name"));
                        PerformerTxt.setText(EachSchedObj.getString("performers"));

                        TimeArray[n] = EachSchedObj.getString("time");
                        TitleArray[n] = EachSchedObj.getString("name");
                        PerformerArray[n] = EachSchedObj.getString("performers");
                        DescArray[n] = EachSchedObj.getString("desc");
                        TurnArray[n] = EachSchedObj.getString("turn");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PerformanceSchedule.this, PerformanceDetail.class);
                            intent.putExtra("title", TitleArray[fn]);
                            intent.putExtra("time", TimeArray[fn]);
                            intent.putExtra("performers", PerformerArray[fn]);
                            intent.putExtra("desc", DescArray[fn]);
                            startActivity(intent);
                        }
                    });
                    ContentsRoot.addView(Item, n);
                }
                SRL.setRefreshing(false);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
}
