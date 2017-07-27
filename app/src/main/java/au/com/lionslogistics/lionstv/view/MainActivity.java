package au.com.lionslogistics.lionstv.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;
import au.com.lionslogistics.lionstv.util.ThumbnailAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String[] categories;
    String[] filenames;
    List<Channel> dummyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        //TODO Dummy list data
        categories =getResources().getStringArray(R.array.categories);
        filenames =getResources().getStringArray(R.array.filename);

        //Initialise List view
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(MainActivity.this,PlayerActivity.class);
//                intent.setData(Uri.parse(dummyData.get(position).getSource()));
//                intent.setAction(PlayerActivity.ACTION_VIEW);
//                startActivity(intent);
                try {
                    createMockData(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Initialise Recycler View
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void createMockData(int position) throws IOException{
        dummyData=new ArrayList<>();
        Gson gson=new Gson();
        JsonReader reader=new JsonReader(new BufferedReader(new InputStreamReader(getAssets().open(filenames[position]+".json"))));
        Channel[] channels=gson.fromJson(reader,Channel[].class);
        dummyData= Arrays.asList(channels);
        //TODO Dummy recycler view data
        RecyclerView.Adapter adapter = new ThumbnailAdapter(this,dummyData);
        recyclerView.setAdapter(adapter);
    }


}
