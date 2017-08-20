package au.com.lionslogistics.lionstv.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import au.com.lionslogistics.lionstv.R;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class MainActivity extends LeanbackActivity {
    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //    @BindView(R.id.listView)
//    ListView listView;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//
//    private List<Category> categories=new ArrayList<>();
//
//
//    CategoryAdapter categoryAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        ButterKnife.setDebug(true);
//
//        //Initialise List view
//        categoryAdapter=new CategoryAdapter(this,android.R.layout.simple_list_item_1);
//        categoryAdapter.setData(categories);
//
//
//        listView.setAdapter(categoryAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    getChannels(position);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        //Initialise Recycler View
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,3);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e(TAG,"On resume...");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(TAG,"On pause...");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e(TAG,"On stop...");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.e(TAG,"On restart...");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e(TAG,"On start...");
//        CategoryService.getInstance(this).getAllCategories();
//    }
//
//    private void getChannels(int position) throws IOException{
//        Category category=categories.get(position);
//        if (category!=null){
//            ChannelService.getInstance(this).getChannelsByCategoryId(category.getId());
//        }
//
//    }
//
//    public void onCategoryListReceived(List<Category> data){
//        categories=data;
//        categoryAdapter.setData(data);
//        categoryAdapter.notifyDataSetChanged();
//        listView.setSelection(0);
//    }
//
//    public void onChannelListReceived(List<Channel> data){
//        RecyclerView.Adapter adapter = new ThumbnailAdapter(this,data);
//        recyclerView.setAdapter(adapter);
//    }
//
//    public void onConnectionError(String errorCode){
//        AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.Theme_Leanback));
//        String msg=getString(R.string.network_error)+errorCode;
//        builder.setMessage(msg);
//        builder.setTitle(R.string.error);
//        AlertDialog dialog=builder.create();
//        dialog.show();
//    }
}
