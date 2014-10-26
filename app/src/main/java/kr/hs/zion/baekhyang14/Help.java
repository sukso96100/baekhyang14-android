package kr.hs.zion.baekhyang14;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;


public class Help extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expendable_list_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpandableListView ELV = (ExpandableListView) findViewById(R.id.expandableListView);


        ArrayList<ExpandableListData> AL = new ArrayList<ExpandableListData>();

        //"Finding the Booth You Want" Item
        ArrayList<String> finding_booth_child = new ArrayList<String>();
        for(String s : getResources().getStringArray(R.array.finding_booth_child)) {
            finding_booth_child.add(s);
        }
        AL.add(new ExpandableListData(getString(R.string.finding_booth), finding_booth_child));

        //Checking Performance Schedule" Item
        ArrayList<String> checking_performance_schedule = new ArrayList<String>();
        for(String s : getResources().getStringArray(R.array.checking_performance_schedule_child)) {
            checking_performance_schedule.add(s);
        }
        AL.add(new ExpandableListData(getString(R.string.checking_performance_schedule), checking_performance_schedule));

        //"Sending Feedback" Item
        ArrayList<String> sending_feedback = new ArrayList<String>();
        for(String s : getResources().getStringArray(R.array.sending_feedback_child)) {
            sending_feedback.add(s);
        }
        AL.add(new ExpandableListData(getString(R.string.sending_feedback), sending_feedback));

        //"Downloading Data for Offline" Item
        ArrayList<String> downloading_data = new ArrayList<String>();
        for(String s : getResources().getStringArray(R.array.downloading_data_child)) {
            downloading_data.add(s);
        }
        AL.add(new ExpandableListData(getString(R.string.downloading_data), downloading_data));

        //Set Adapter
                ELV.setAdapter(new ExpandableAdapter(this, AL));
    }

}
