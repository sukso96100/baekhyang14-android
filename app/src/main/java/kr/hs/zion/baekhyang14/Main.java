package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ScrollView SV = (ScrollView) findViewById(R.id.scrollview);
        final View header = (View) findViewById(R.id.header);
        final ColorDrawable Transparent = new ColorDrawable(Color.TRANSPARENT);
        final ColorDrawable Darkblue = new ColorDrawable(Color.parseColor("#ff373166"));

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



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
