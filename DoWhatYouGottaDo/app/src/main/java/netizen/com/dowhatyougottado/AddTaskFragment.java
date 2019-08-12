package netizen.com.dowhatyougottado;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import netizen.com.dowhatyougottado.model.Task;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTaskFragment.OnAddTaskFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public EditText deadlineEditText, contentTaskEditText;
    public CheckBox ifPrivateCheckBox;

    ImageButton confirmAddingTask;
    private SharedPreferences preferences;
    //task
    Task createdTask;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAddTaskFragmentInteractionListener mAddTaskFragmentInteractionListener;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_task, container, false);


        // get button
        confirmAddingTask =  v.findViewById(R.id.confirmAddBtn);
        confirmAddingTask.setOnClickListener(this);
        ifPrivateCheckBox = v.findViewById(R.id.ifprivate);

        //get deadling and task
        deadlineEditText = v.findViewById(R.id.deadline);
        deadlineEditText.setOnTouchListener(this);
        contentTaskEditText = v.findViewById(R.id.taskcontent);
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddTaskFragmentInteractionListener) {
            mAddTaskFragmentInteractionListener = (OnAddTaskFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAddTaskFragmentInteractionListener = null;
        Log.e("XD", "detaching");
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddTaskFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddTaskFragmentInteraction();
    }

    @Override
    public void onClick(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        switch (v.getId()) {
            case R.id.confirmAddBtn:
                mAddTaskFragmentInteractionListener.onAddTaskFragmentInteraction();
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        DialogFragment datePicker = new DatePickerFragment();
        final String DEBUG_TAG = "XD";
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.e(DEBUG_TAG,"Action was DOWN");
                switch (v.getId()) {
                    case R.id.deadline:
                        Log.e("Xd", "adding task....");
                        datePicker.show(getActivity().getSupportFragmentManager(),"datepicker");
                        break;

                }
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.e(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.e(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.e(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.e(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return true;
            //TimePickerFragment
        }
    }







}
