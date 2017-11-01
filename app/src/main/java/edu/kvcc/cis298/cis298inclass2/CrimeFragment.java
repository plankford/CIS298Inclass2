package edu.kvcc.cis298.cis298inclass2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";

    //This string will be used as a key for the fragment manager
    //when we create the date dialog.
    //We send it a fragmentManager, and what key we want it to use
    //when it makes the new fragment.
    private static final String DIALOG_DATE = "DialogDate";

    public static CrimeFragment newInstance(UUID crimeId) {
        //Create a new Bundle to store the args for our fragment
        Bundle args = new Bundle();
        //Put the crimeId that was passed in into the bundle object args
        args.putSerializable(ARG_CRIME_ID, crimeId);
        //Create a new CrimeFragment
        CrimeFragment fragment = new CrimeFragment();
        //Set the arguments on the fragment to the args Bundle we created above
        fragment.setArguments(args);
        //Return the newly created fragment
        return fragment;
    }

    //Define a single crime to use in our fragment
    private Crime mCrime;
    //Define a EditText so we can interact with the one in the layout
    private EditText mTitleField;
    //Define a Button and Checkbox to interact with
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the UUID for the crime from the intent used to start the hosting activity
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        //Get a handle to the crimeLab, and once we have it, call the getCrime method
        //sending in the UUID to get the specific crime we need.
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the view and store it a variable so we can setup listeners
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //We need the view to be able to call findViewById. This is different than with an activity
        //Activities have only one layout, so it can be assumed when we call findViewById that
        //we are referring to the only view it has.
        //Here, we need to specify the view that we are pulling widgets out of.
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Not doing anything here. I don't think we can get rid of it because
                //it is declared abstract in the parent
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Set the title on the crime each time a character is entered
                //into the edittext.
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Not doing anything here. I don't think we can get rid of it because
                //it is declared abstract in the parent
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                //The show method on the DialogFragment we made an instance
                //of right above takes in a fragmentManager, and a key that
                //will be used by the fragment manager to hold on to the
                //instance of the fragment.
                //This way on screen rotation, the fragment can be retrieved
                //from the fragmentManager's list without any extra work.
                //We did not need to do this when making the other fragments
                //because we did the transaction and committing ourselves.
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSolvedCheckbox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
