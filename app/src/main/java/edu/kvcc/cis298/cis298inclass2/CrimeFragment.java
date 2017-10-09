package edu.kvcc.cis298.cis298inclass2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment {

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
        mCrime = new Crime();
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
        mDateButton.setEnabled(false);

        mSolvedCheckbox = (CheckBox)v.findViewById(R.id.crime_solved);
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
