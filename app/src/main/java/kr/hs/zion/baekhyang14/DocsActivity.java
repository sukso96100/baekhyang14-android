package kr.hs.zion.baekhyang14;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DocsActivity extends ActionBarActivity {
    private int docs;
    private ActionBar ActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar = getSupportActionBar();
        TextView helloTxt = (TextView)findViewById(R.id.doc);
        docs = getIntent().getIntExtra("doc", 0);
        helloTxt.setText(readTxt());
    }
    private String readTxt(){
        InputStream inputStream = null;
        switch (docs){
            case 0:
                inputStream = getResources().openRawResource(R.raw.copying);
                ActionBar.setTitle(getString(R.string.src_license));
                break;
            case 1:
                inputStream = getResources().openRawResource(R.raw.contributors);
                ActionBar.setTitle(getString(R.string.contrib));
                break;
            case 2:
                inputStream = getResources().openRawResource(R.raw.notices);
                ActionBar.setTitle(getString(R.string.thirdparty));
                break;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }
}
