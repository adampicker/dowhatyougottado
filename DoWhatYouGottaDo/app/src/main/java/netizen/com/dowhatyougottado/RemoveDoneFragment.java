package netizen.com.dowhatyougottado;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RemoveDoneFragment.OnRemoveFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RemoveDoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoveDoneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long taskId;

    private OnRemoveFragmentInteractionListener mListener;
    ImageButton doneButton, removeButton;

    public RemoveDoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemoveDoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RemoveDoneFragment newInstance(long param1) {
        RemoveDoneFragment fragment = new RemoveDoneFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
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

        taskId = getArguments().getLong(ARG_PARAM1);
        View v  = inflater.inflate(R.layout.fragment_remove_done, container, false);
        doneButton = v.findViewById(R.id.doneBtn);
        removeButton = v.findViewById(R.id.removeBtn);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRemoveFragmentInteraction(taskId, false);


            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onRemoveFragmentInteraction(taskId, true);
            }
        });




        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRemoveFragmentInteractionListener) {
            mListener = (OnRemoveFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnRemoveFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRemoveFragmentInteraction(Long itemId, boolean done);
    }
}
