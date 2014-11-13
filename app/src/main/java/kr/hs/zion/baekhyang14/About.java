package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class About extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get app version name from Manifest
        String app_ver = null;
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String[] AboutStringArray = getResources().getStringArray(R.array.about_app);
        AboutStringArray[0] = "Version : " + app_ver;
        ListView LV = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, AboutStringArray);
        LV.setAdapter(Adapter);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                switch (position){
                    case 2:

                        i.setData(Uri.parse("mailto:sukso96100@gmail.com"));
                        startActivity(i);
                        break;
                    case 3:
                        i.setData(Uri.parse("http://youngbin-han.kr.pe"));
                        startActivity(i);
                        break;
                    case 4:
                        i.setData(Uri.parse("http://youngbin-han.kr.pe/baekhyang14"));
                        startActivity(i);
                        break;
                    case 5:
                        i.setData(Uri.parse("http://github.com/sukso96100/baekhyang14-android"));
                        startActivity(i);
                        break;
                    case 6:
                        startActivity(new Intent(About.this, LegalNotices.class));
                    case 7:
                        break;
                    case 8:
                        startService(new Intent(About.this, DataDownloadService.class));
                        break;
                }
            }
        });
    }



}
