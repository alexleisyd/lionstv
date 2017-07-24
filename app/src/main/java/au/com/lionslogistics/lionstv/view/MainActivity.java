package au.com.lionslogistics.lionstv.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import au.com.lionslogistics.lionstv.R;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class MainActivity extends AppCompatActivity {
    ListView listView;

    String[] names;
    String[] urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Dummy data
        names=getResources().getStringArray(R.array.name);
        urls=getResources().getStringArray(R.array.url);
        listView= (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,PlayerActivity.class);
                intent.setData(Uri.parse(urls[position]));
                intent.setAction(PlayerActivity.ACTION_VIEW);
                startActivity(intent);
            }
        });

    }


}
