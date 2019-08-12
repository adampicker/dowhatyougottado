package netizen.com.dowhatyougottado;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


        public int hour,minute,second;
        public Calendar date;

        OnTimePickerDialogFragmentInteractionListener mListener;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;


        public static TimePickerFragment newInstance(String param1, String param2) {
            TimePickerFragment fragment = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnTimePickerDialogFragmentInteractionListener) {
                mListener = (OnTimePickerDialogFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            if (getArguments() != null) {
                date = (Calendar)getArguments().getSerializable("date");
                Log.e("xD", "passed date: " + date.getTime().toString());
            }

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            this.hour = hourOfDay;
            this.minute = minute;
            this.second = 0;
            date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);
            mListener.onTimePickerDialogFragmentInteraction(date);

        }

        public interface OnTimePickerDialogFragmentInteractionListener {
            // TODO: Update argument type and name
            void onTimePickerDialogFragmentInteraction(Calendar timeDate);
        }
    }
