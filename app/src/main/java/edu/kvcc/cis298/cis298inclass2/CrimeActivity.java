package edu.kvcc.cis298.cis298inclass2;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    //This is the method that we must implement from the parent
    //class that we created. It does the work of making the
    //fragment that needs to be hosted.
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

}
