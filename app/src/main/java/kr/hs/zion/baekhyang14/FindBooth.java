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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
    LinearLayout forth;
    LinearLayout fifth;

    String Title;
    String Member;
    String Location;
    String Desc;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_booth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundResource(R.drawable.polygon);
        setSupportActionBar(toolbar);
        first = (LinearLayout) findViewById(R.id.first);
        second = (LinearLayout)findViewById(R.id.second);
        third = (LinearLayout)findViewById(R.id.third);
        forth = (LinearLayout)findViewById(R.id.forth);
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
                try {
                    FullObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                    JsonObj = FullObj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                1층 부스
                first.removeAllViews();
                first.addView(createCard("household",JsonObj));
                first.addView(createCard("health",JsonObj));
                first.addView(createCard("pc2",JsonObj));
                first.addView(createCard("pc1",JsonObj));
                first.addView(createCard("c110",JsonObj));
                first.addView(createCard("c110",JsonObj));
                first.addView(createCard("c111",JsonObj));
                first.addView(createCard("c112",JsonObj));
                first.addView(createCard("c113",JsonObj));
                first.addView(createCard("c114",JsonObj));
                first.addView(createCard("c115",JsonObj));
                first.addView(createCard("weeclass",JsonObj)); //ERROR




//                2층 부스
                second.removeAllViews();
                second.addView(createCard("biolab",JsonObj));
                second.addView(createCard("c101",JsonObj));
                second.addView(createCard("c102",JsonObj));
                second.addView(createCard("c103",JsonObj));
                second.addView(createCard("c104",JsonObj));
                second.addView(createCard("zbs",JsonObj));
                second.addView(createCard("c105",JsonObj));
                second.addView(createCard("c106",JsonObj));
                second.addView(createCard("c107",JsonObj));
                second.addView(createCard("c108",JsonObj));
                second.addView(createCard("c109",JsonObj));


//                3층 부스
                third.removeAllViews();
                third.addView(createCard("chemistry",JsonObj));
                third.addView(createCard("c210",JsonObj));
                third.addView(createCard("c211",JsonObj));
                third.addView(createCard("c212",JsonObj));
                third.addView(createCard("c213",JsonObj));
                third.addView(createCard("c214",JsonObj));
                third.addView(createCard("language",JsonObj));

//                4층 부스
                forth.removeAllViews();
                forth.addView(createCard("physics",JsonObj));
                forth.addView(createCard("c301",JsonObj));
                forth.addView(createCard("c302",JsonObj));
                forth.addView(createCard("c201",JsonObj));
                forth.addView(createCard("c202",JsonObj));
                forth.addView(createCard("c203",JsonObj));
                forth.addView(createCard("c204",JsonObj));
                forth.addView(createCard("c205",JsonObj));
                forth.addView(createCard("c206",JsonObj));
                forth.addView(createCard("c207",JsonObj));
                forth.addView(createCard("c208",JsonObj));
                forth.addView(createCard("c209",JsonObj));
                forth.addView(createCard("c215",JsonObj));

//                5층 부스
                fifth.removeAllViews();
                fifth.addView(createCard("earth",JsonObj));
                fifth.addView(createCard("c303",JsonObj));
                fifth.addView(createCard("c304",JsonObj));
                fifth.addView(createCard("c305",JsonObj));
                fifth.addView(createCard("c306",JsonObj));
                fifth.addView(createCard("c307",JsonObj));
                fifth.addView(createCard("c308",JsonObj));
                fifth.addView(createCard("c305",JsonObj));
                fifth.addView(createCard("c310",JsonObj));
                fifth.addView(createCard("c311",JsonObj));
                fifth.addView(createCard("c312",JsonObj));
                fifth.addView(createCard("c313",JsonObj));
                forth.addView(createCard("c214",JsonObj));
                SRL.setRefreshing(false);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("FindBooth","Getting JSON Failed");
//Load Offline Data
                Toast toast = Toast.makeText(FindBooth.this,
                        getString(R.string.offline),Toast.LENGTH_LONG);
                toast.show();
                //                1층 부스
                first.removeAllViews();
                first.addView(createCardFromOfflineData("household"));
                first.addView(createCardFromOfflineData("health"));
                first.addView(createCardFromOfflineData("pc2"));
                first.addView(createCardFromOfflineData("pc1"));
                first.addView(createCardFromOfflineData("c110"));
                first.addView(createCardFromOfflineData("c110"));
                first.addView(createCardFromOfflineData("c111"));
                first.addView(createCardFromOfflineData("c112"));
                first.addView(createCardFromOfflineData("c113"));
                first.addView(createCardFromOfflineData("c114"));
                first.addView(createCardFromOfflineData("c115"));
                first.addView(createCardFromOfflineData("weeclass")); //ERROR




//                2층 부스
                second.removeAllViews();
                second.addView(createCardFromOfflineData("biolab"));
                second.addView(createCardFromOfflineData("c101"));
                second.addView(createCardFromOfflineData("c102"));
                second.addView(createCardFromOfflineData("c103"));
                second.addView(createCardFromOfflineData("c104"));
                second.addView(createCardFromOfflineData("zbs"));
                second.addView(createCardFromOfflineData("c105"));
                second.addView(createCardFromOfflineData("c106"));
                second.addView(createCardFromOfflineData("c107"));
                second.addView(createCardFromOfflineData("c108"));
                second.addView(createCardFromOfflineData("c109"));


//                3층 부스
                third.removeAllViews();
                third.addView(createCardFromOfflineData("chemistry"));
                third.addView(createCardFromOfflineData("c210"));
                third.addView(createCardFromOfflineData("c211"));
                third.addView(createCardFromOfflineData("c212"));
                third.addView(createCardFromOfflineData("c213"));
                third.addView(createCardFromOfflineData("c214"));
                third.addView(createCardFromOfflineData("language"));

//                4층 부스
                forth.removeAllViews();
                forth.addView(createCardFromOfflineData("physics"));
                forth.addView(createCardFromOfflineData("c301"));
                forth.addView(createCardFromOfflineData("c302"));
                forth.addView(createCardFromOfflineData("c201"));
                forth.addView(createCardFromOfflineData("c202"));
                forth.addView(createCardFromOfflineData("c203"));
                forth.addView(createCardFromOfflineData("c204"));
                forth.addView(createCardFromOfflineData("c205"));
                forth.addView(createCardFromOfflineData("c206"));
                forth.addView(createCardFromOfflineData("c207"));
                forth.addView(createCardFromOfflineData("c208"));
                forth.addView(createCardFromOfflineData("c209"));
                forth.addView(createCardFromOfflineData("c215"));

//                5층 부스
                fifth.removeAllViews();
                fifth.addView(createCardFromOfflineData("earth"));
                fifth.addView(createCardFromOfflineData("c303"));
                fifth.addView(createCardFromOfflineData("c304"));
                fifth.addView(createCardFromOfflineData("c305"));
                fifth.addView(createCardFromOfflineData("c306"));
                fifth.addView(createCardFromOfflineData("c307"));
                fifth.addView(createCardFromOfflineData("c308"));
                fifth.addView(createCardFromOfflineData("c305"));
                fifth.addView(createCardFromOfflineData("c310"));
                fifth.addView(createCardFromOfflineData("c311"));
                fifth.addView(createCardFromOfflineData("c312"));
                fifth.addView(createCardFromOfflineData("c313"));
                forth.addView(createCardFromOfflineData("c214"));
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
