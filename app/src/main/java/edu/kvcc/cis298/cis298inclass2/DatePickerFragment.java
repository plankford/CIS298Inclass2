package edu.kvcc.cis298.cis298inclass2;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by cisco on 11/1/2017.
 */

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Get the view that we want to use for the dialog from R
        //In the onCreateView method of our other fragments, we have
        //a LayoutInflater passed in to us.
        //In this method, we do not. We can still get access to the
        //inflater by calling a static method n the LayoutInflater
        //called from and passing it the hosting activity.
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //Build the AlertDialog we want the Fragment to show.
        //There are a lot of methods on the Builder object
        //to help create an alert dialog.
        //Notice that it uses method chaining to create the dialog.
        //Each method returns a Builder, so the next method can be
        //called on it.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
