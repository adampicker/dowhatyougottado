package netizen.com.dowhatyougottado;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public int year, month, day;

    OnDatePickerDialogFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDatePickerDialogFragmentInteractionListener) {
            mListener = (OnDatePickerDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar date = Calendar.getInstance();
        date.set(year,month,day);

        Log.e("XD", "Date picker closed - date picked: " + date.getTime().toString());
        mListener.onDatePickerDialogFragmentInteraction(date);
    }

    public interface OnDatePickerDialogFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDatePickerDialogFragmentInteraction(Calendar date);
    }
}

