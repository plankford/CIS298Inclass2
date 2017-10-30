package edu.kvcc.cis298.cis298inclass2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

    //Method will get called when we return to this activity from
    //the CrimeActivity that shows the details
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        //This will call the static method on the CrimeLab to get
        //the singleton instance of the CrimeLab.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        //Get the list of crimes from the CrimeLab
        List<Crime> crimes = crimeLab.getCrimes();

        //Check to see if the adapter is already created. If so, no need
        //to make it again. Just notify that the data set has changed.
        if (mAdapter == null) {
            //Make a new adapter for the Recycler View and send over
            //the crime List
            mAdapter = new CrimeAdapter(crimes);

            //Set the adapter on the Recycler View
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            //Tell the adapter that the data set has changed, which will
            //force a reload of all the data.
            mAdapter.notifyDataSetChanged();
        }
    }


    //*****************************
    //Inner classes
    //*****************************

    //This class is used to make a new CrimeHolder that the adapter below
    //will need to do it's job.
    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        //Make a var to hold the crime
        private Crime mCrime;

        //Setup variables for each widget on the new list item layout
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckbox;

        public CrimeHolder(View itemView) {
            super(itemView);

            //Set the onclicklistener to be this class
            //since it implements OnClickListener, it will be
            //required to have a onClick method to handle clicking
            itemView.setOnClickListener(this);

            //Get a handle to each of the list item widgets
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckbox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        //Method to bind a crime to the holder and set each
        //widget control with the data from the crime.
        public void bindCrime(Crime crime)
        {
            //Assign the passed in crime to the class level one
            //Then assign the values from crime to each widget
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckbox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View view) {
            //This was the old activity that we were creating before ViewPager
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());

            //This is the new one with the ViewPager
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
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
                    .inflate(R.layout.list_item_crime, parent, false);

            //Return a new crime holder with this new view
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            //Get the crime at a specific index from the mCrimes List
            Crime crime = mCrimes.get(position);
            //Set all the values of the widgets to the
            //properties of the crime
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


}
