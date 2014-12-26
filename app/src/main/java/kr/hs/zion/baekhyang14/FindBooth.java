package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FindBooth extends ActionBarActivity {
    private DrawerLayout NavigationDrawer;
    private ActionBarDrawerToggle DrawerToggle;
    private ListView DrawerList;
    private ArrayList<String> DrawerArray;
    private ArrayList<Drawable> IconArray;
    private DrawerListAdapter Adapter;
    private Boolean isNavDrawerOpen = false;
    private JSONObject JsonObj;
    SwipeRefreshLayout SRL;

    LinearLayout first;
    LinearLayout second;
    LinearLayout third;
    LinearLayout fourth;
    LinearLayout fifth;

    String Title;
    String Member;
    String Location;
    String Desc;
    String Email;

    String TAG = "FindBooth";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_booth);
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
        first = (LinearLayout) findViewById(R.id.first);
        second = (LinearLayout)findViewById(R.id.second);
        third = (LinearLayout)findViewById(R.id.third);
        fourth = (LinearLayout)findViewById(R.id.fourth);
        fifth = (LinearLayout)findViewById(R.id.fifth);
        SRL = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

//        first.addView(new TextView(FindBooth.this));
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
                isNavDrawerOpen = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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
                        NavUtils.navigateUpFromSameTask(FindBooth.this);
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(new Intent(FindBooth.this, PerformanceSchedule.class));
                        break;
                    case 3:
                        startActivity(new Intent(FindBooth.this, Help.class));
                        break;
                    case 4:
                        startActivity(new Intent(FindBooth.this, About.class));
                        break;
                }
            }
        });

//        Contents
        SRL.setRefreshing(true);
        NetWorkTask();
        SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetWorkTask();
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


    private void NetWorkTask(){
//
        Log.d("FindBooth","NetWorkTadk Started");
        AsyncHttpClient AsyncJsonClient = new AsyncHttpClient();
//        SRL.setRefreshing(true);
        AsyncJsonClient.get("http://www.youngbin-han.kr.pe/baekhyang14/booth/booth.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
            Log.d("FindBooth","Got JSON!");
//                ContentsRoot.removeAllViews();
                //바이트 배열을 문자열로 변환
                //Convert Byte Array to String
                String ConvertedResponse = null;
                try {
                    ConvertedResponse = new String(bytes, "UTF-8");
                    Log.d("JsonResponse", ConvertedResponse);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                JSONObject FullObj = null;
                JSONArray FirstF = null;
                JSONArray SecondF = null;
                JSONArray ThirdF = null;
                JSONArray FourthF = null;
                JSONArray FifthF = null;
                try {
                    FullObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                    JsonObj = FullObj;
                    FirstF = JsonObj.getJSONArray("first_floor");
                    SecondF = JsonObj.getJSONArray("second_floor");
                    ThirdF = JsonObj.getJSONArray("third_floor");
                    FourthF = JsonObj.getJSONArray("fourth_floor");
                    FifthF = JsonObj.getJSONArray("fifth_floor");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                1층 부스
                first.removeAllViews();
                for(int n=0; n < FirstF.length(); n++){
                    try {
                        String CODE = FirstF.getString(n);
                        first.addView(createCard(CODE, JsonObj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                2층 부스
                second.removeAllViews();
                for(int n=0; n < SecondF.length(); n++){
                    try {
                        String CODE = SecondF.getString(n);
                        second.addView(createCard(CODE, JsonObj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                3층 부스
                third.removeAllViews();
                for(int n=0; n < ThirdF.length(); n++){
                    try {
                        String CODE = ThirdF.getString(n);
                        third.addView(createCard(CODE, JsonObj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                4층 부스
                fourth.removeAllViews();
                for(int n=0; n < FourthF.length(); n++){
                    try {
                        String CODE = FourthF.getString(n);
                        fourth.addView(createCard(CODE, JsonObj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                5층 부스
                fifth.removeAllViews();
                for(int n=0; n < FifthF.length(); n++){
                    try {
                        String CODE = FifthF.getString(n);
                        fifth.addView(createCard(CODE, JsonObj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                SRL.setRefreshing(false);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                SharedPreferences OfflineData = getSharedPreferences("booth_data",MODE_PRIVATE);
                Log.d("FindBooth","Getting JSON Failed");
                //Load Offline Data
                Toast toast = Toast.makeText(FindBooth.this,
                        getString(R.string.offline),Toast.LENGTH_LONG);
                toast.show();
                int FirstCount = OfflineData.getInt("first_count",0);
                int SecondCount = OfflineData.getInt("second_count",0);
                int ThirdCount = OfflineData.getInt("third_count",0);
                int FourthCount = OfflineData.getInt("fourth_count",0);
                int FifthCount = OfflineData.getInt("fifth_count",0);

                Log.d(TAG,String.valueOf(FirstCount));
                //                1층 부스
                first.removeAllViews();
                for(int n=0; n < FirstCount; n++){
                    String CODE = getFloorDataOffline("first",n);
                    first.addView(createCardFromOfflineData(CODE));
                }

//                2층 부스
                second.removeAllViews();
                for(int n=0; n < SecondCount; n++){
                    second.addView(createCardFromOfflineData(getFloorDataOffline("second",n)));
                }


//                3층 부스
                third.removeAllViews();
                for(int n=0; n < ThirdCount; n++){
                    third.addView(createCardFromOfflineData(getFloorDataOffline("third",n)));
                }

//                4층 부스
                fourth.removeAllViews();
                for(int n=0; n < FourthCount; n++){
                    fourth.addView(createCardFromOfflineData(getFloorDataOffline("fourth",n)));
                }

//                5층 부스
                fifth.removeAllViews();
                for(int n=0; n < FifthCount; n++){
                    fifth.addView(createCardFromOfflineData(getFloorDataOffline("fifth",n)));
                }

                SRL.setRefreshing(false);
            }
        });
    }


    private View createCard(String Id, JSONObject Obj){
        if(Obj==null){
            Log.d("FindBooth","JsonObj is null");
        }else{
            Log.d("FindBooth","JsonObj is NOT null");
        }

        LayoutInflater LI = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        RelativeLayout RL = (RelativeLayout) LI.inflate(R.layout.item_booth, null);
        TextView title = (TextView)RL.findViewById(R.id.title);
        TextView member = (TextView)RL.findViewById(R.id.member);
        TextView location = (TextView)RL.findViewById(R.id.location);

        try {
            JSONObject SingleObj = Obj.getJSONObject(Id);
            title.setText(SingleObj.get("title").toString());
            member.setText(SingleObj.get("members").toString());
            location.setText(SingleObj.get("location").toString());

            Title = SingleObj.get("title").toString();
            Member = SingleObj.get("members").toString();
            Location = SingleObj.get("location").toString();
            Desc = SingleObj.get("desc").toString();
            Email = SingleObj.get("email").toString();
            Log.d("FindBooth","Creating Card:"+Title+Member+Location+Desc+Email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String titlestring = Title;
        final String memberstring = Member;
        final String locationstring = Location;
        final String descstring = Desc;
        final String emailstring = Email;

        RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindBooth.this, BoothDetails.class);
                intent.putExtra("title", titlestring);
                intent.putExtra("members", memberstring);
                intent.putExtra("location", locationstring);
                intent.putExtra("desc", descstring);
                intent.putExtra("email", emailstring);
                startActivity(intent);
            }
        });
        if(RL==null){
            Log.d("FindBooth","RL is NULL");
        }else{Log.d("FindBooth","RL is NOT NULL");}
        return RL;
    }

    private String getFloorDataOffline(String floor, int num){
        SharedPreferences OfflineData = getSharedPreferences("booth_data",MODE_PRIVATE);
        String LocationCode = OfflineData.getString(floor+String.valueOf(num),"0");
        Log.d("LocationCode",LocationCode);
        return LocationCode;
    }
    private View createCardFromOfflineData(String Id){
        SharedPreferences OfflineData = getSharedPreferences("booth_data",MODE_PRIVATE);

        LayoutInflater LI = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        RelativeLayout RL = (RelativeLayout) LI.inflate(R.layout.item_booth, null);
        TextView title = (TextView)RL.findViewById(R.id.title);
        TextView member = (TextView)RL.findViewById(R.id.member);
        TextView location = (TextView)RL.findViewById(R.id.location);

        final String titlestring = OfflineData.getString(Id+"_title","No Data");
        final String memberstring = OfflineData.getString(Id+"_members","No Data");
        final String locationstring = OfflineData.getString(Id+"_location",Id);
        final String descstring = OfflineData.getString(Id+"_desc","Updated Needed");
        final String emailstring = OfflineData.getString(Id+"_email","example@example.com");

        title.setText(titlestring);
        member.setText(memberstring);
        location.setText(locationstring);

        RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindBooth.this, BoothDetails.class);
                intent.putExtra("title", titlestring);
                intent.putExtra("members", memberstring);
                intent.putExtra("location", locationstring);
                intent.putExtra("desc", descstring);
                intent.putExtra("email", emailstring);
                startActivity(intent);
            }
        });
        return  RL;
    }
    @Override
    public void onBackPressed(){
        if(isNavDrawerOpen){
            NavigationDrawer.closeDrawer(Gravity.LEFT);
        }else{
            NavUtils.navigateUpFromSameTask(FindBooth.this);
        }
    }
}
