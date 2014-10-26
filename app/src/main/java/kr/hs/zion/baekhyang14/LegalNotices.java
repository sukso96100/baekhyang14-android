package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class LegalNotices extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] LegalStringArray = getResources().getStringArray(R.array.legal_array);

        ListView LV = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, LegalStringArray);
        LV.setAdapter(Adapter);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LegalNotices.this, DocsActivity.class);
                i.putExtra("doc", position);
                startActivity(i);
            }
        });
    }

}
