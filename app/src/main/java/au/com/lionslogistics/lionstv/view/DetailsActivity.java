package au.com.lionslogistics.lionstv.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;

/**
 * Created by alex-daphne on 18/08/2017.
 * All rights reserved
 */

public class DetailsActivity extends LeanbackActivity {
    private static final String TAG="DetailsActivity";
    public static final String SHARED_ELEMENT_NAME="Imageview";
    public static final String CHANNEL="Channel";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

    }
}
