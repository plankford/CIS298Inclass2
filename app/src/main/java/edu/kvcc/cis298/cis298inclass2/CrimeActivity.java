package edu.kvcc.cis298.cis298inclass2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        //Get a handle to the fragment manager that can store all of
        //our instanciated fragments
        FragmentManager fm = getSupportFragmentManager();

        //Get the fragment we want from the fragment manager.
        //The first time this is run, it will return null since it does not exist
        //We send into this method, the Id of the framelayout where we expect
        //the fragment to be.
        //Obviously when we start up, the fragement will not be in our framelayout.
        //There is nothing in there.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //Check to see if there was a fragment in the frame layout we used to get
        //out the fragment we want.
        if (fragment == null) {
            fragment = new CrimeFragment();
            //We then need to start and finish a transaction to get the
            //fragment added to the frame layout
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
