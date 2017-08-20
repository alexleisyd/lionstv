package au.com.lionslogistics.lionstv.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Created by alex-daphne on 19/08/2017.
 * All rights reserved
 */

public class LeanbackActivity extends FragmentActivity {
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }
}
