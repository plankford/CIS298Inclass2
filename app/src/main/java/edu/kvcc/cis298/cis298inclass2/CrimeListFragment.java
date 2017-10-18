package edu.kvcc.cis298.cis298inclass2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cisco on 10/16/2017.
 */

public class CrimeListFragment extends Fragment {

    //Private variable for the recycler view
    private RecyclerView mCrimeRecyclerView;

    //Make a class level variable for the crime adapter so
    //it can be used with the recycler view
    private CrimeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Use the inflater that is passed into this method to inflate the view
        //for this fragment. This used to be handled automatically for us with
        //activities, but now we need to do it ourselves.
        //Notice that the R reference is referencing the name of the layout file
        //that will be used as the view.
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        //Get a reference to the recyclerview
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        //Set the LayoutManager. If this is skipped the app will crash. Recycler view
        //must have a layout manager set.
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Call the updateUI method to get the Recycler View
        //all setup and ready for displaying
        updateUI();

        //Return the view
        return view;
    }

    private void updateUI() {
        //This will call the static method on the CrimeLab to get
        //the singleton instance of the CrimeLab.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        //Get the list of crimes from the CrimeLab
        List<Crime> crimes = crimeLab.getCrimes();

        //Make a new adapter for the Recycler View and send over
        //the crime List
        mAdapter = new CrimeAdapter(crimes);

        //Set the adapter on the Recycler View
        mCrimeRecyclerView.setAdapter(mAdapter);
    }


    //*****************************
    //Inner classes
    //*****************************

    //This class is used to make a new CrimeHolder that the adapter below
    //will need to do it's job.
    private class CrimeHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public CrimeHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }
    }

    //This class is used to make a new adapter to map the data between our
    //CrimeLab list and what needs to be displayed on the recycler view
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Get a reference to the LayoutInflater so that we can inflate
            //the view that will go into the view holder
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            //Get the View for the view holder
            //Right now it is a generic built in one. Later we will make our own
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);

            //Return a new crime holder with this new view
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            //Get the crime at a specific index from the mCrimes List
            Crime crime = mCrimes.get(position);
            //Set the text for the view to the title of the
            //specific crime we pulled out above
            holder.mTitleTextView.setText(crime.getTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
