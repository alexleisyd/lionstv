package au.com.lionslogistics.lionstv.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import au.com.lionslogistics.lionstv.model.Category;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;

    private int resource;

    private List<Category> data;

    public CategoryAdapter(Context context,int resource){
        super(context,resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v==null) {
            LayoutInflater vi = LayoutInflater.from(context);
            v = vi.inflate(resource,null);
        }

        Category category=data.get(position);

        if (category!=null){
            TextView textView= (TextView) v.findViewById(android.R.id.text1);
            textView.setText(category.getName());
        }
        return v;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    public void setData(List<Category> data){
        this.data=data;
    }
}
