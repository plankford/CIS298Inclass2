package edu.kvcc.cis298.cis298inclass2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import static android.icu.util.Calendar.*;

/**
 * Created by cisco on 11/1/2017.
 */

public class DatePickerFragment extends DialogFragment {

    /*
    Because this will be used in onActivityResult there is
    a possibility that there will be other activity keys used
    therefore this need to be the full package name
     */
    public static final String EXTRA_DATE =
            "edu.kvcc.cis298.cis298inclass2.date";

    private static final String ARG_DATE = "date";

    //Not using this yet. Will be soon
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {



        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Get the date out of arguments
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        //Get and instance of the calender. since ther is a static getInstance
        //method I would suspec that Calender is a singleton
        Calendar calendar = Calendar.getInstance();
        //Set the date
        calendar.setTime(date);
        //Pull out each part of the date separately
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        //Get the view that we want to use for the dialog from R
        //In the onCreateView method of our other fragments, we have
        //a LayoutInflater passed in to us.
        //In this method, we do not. We can still get access to the
        //inflater by calling a static method n the LayoutInflater
        //called from and passing it the hosting activity.
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //Get control of the widget and set the date
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        //Build the AlertDialog we want the Fragment to show.
        //There are a lot of methods on the Builder object
        //to help create an alert dialog.
        //Notice that it uses method chaining to create the dialog.
        //Each method returns a Builder, so the next method can be
        //called on it.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Get out the year month day of the year
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                /*
                                Create a new date using the Gregorian Calendar
                                object to convert the year, month, day to
                                a Date object
                                Then call the sendResult method we wrote below to do
                                the work of making an intent with the date
                                as data and calling the target fragments
                                onActivityResult method
                                 */
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                        .create();
    }

    /*
    get the target fragment and make and explicit call
    to its onActivityResult method. Normally this is called
    automatically by an activity but we need to do it explicitely
    since we are dealing with communication between 2 fragments
    and not activities
    the getTargetResultCode will fetch the request code that was used when the
    setTargetFragment method was called over in the CrimeFragment
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
