package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FirstRun extends ActionBarActivity{

    private int page = 0;
    TextView Desc;
    Button NextBtn;
    Button EtcBtn;
    ActionBar AB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        AB = getSupportActionBar();

        Desc = (TextView)findViewById(R.id.desc);
        NextBtn = (Button)findViewById(R.id.next);
        EtcBtn = (Button)findViewById(R.id.etc);
        setPageContent();
        NextBtn.setText(getString(R.string.next));
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                setPageContent();
            }
        });



    }
    private void setPageContent(){
        switch (page){
            case 0:
                Desc.setText(getString(R.string.welcome));
                AB.setTitle(getString(R.string.welcome_title));
                EtcBtn.setVisibility(View.INVISIBLE);
                break;
            case 1:
                Desc.setText(getString(R.string.simple_guide));
                AB.setTitle(getString(R.string.simple_guide_title));
                EtcBtn.setVisibility(View.VISIBLE);
                EtcBtn.setText(getString(R.string.open_help));
                EtcBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(FirstRun.this, Help.class));
                    }
                });
                break;
            case 2:
                Desc.setText(getString(R.string.sending_feedback_desc));
                AB.setTitle(getString(R.string.sending_feedback));
                EtcBtn.setVisibility(View.INVISIBLE);
                break;
            case 3:
                Desc.setText(getString(R.string.downloading_data_desc));
                AB.setTitle(getString(R.string.downloading_data));
                EtcBtn.setVisibility(View.VISIBLE);
                EtcBtn.setText(getString(R.string.download_data_btn));
                EtcBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Data Download Action
                        startService(new Intent(FirstRun.this, DataDownloadService.class));
                    }
                });
                break;
            case 4:
                Desc.setText(getString(R.string.ready_to_use));
                AB.setTitle(getString(R.string.ready_to_use_title));
                NextBtn.setText(getString(R.string.finish));
                NextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                EtcBtn.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Log.d("FirstRun","Back Key Pressed");
    }

}