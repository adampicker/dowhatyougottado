package netizen.com.dowhatyougottado;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import netizen.com.dowhatyougottado.model.Task;
import netizen.com.dowhatyougottado.model.TaskList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrivateListFragment.OnPrivateFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrivateListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivateListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TaskList taskList;


    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private OnPrivateFragmentInteractionListener mListener;

    public PrivateListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrivateListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrivateListFragment newInstance(TaskList taskList) {
        PrivateListFragment fragment = new PrivateListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, taskList);
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
        View v = inflater.inflate(R.layout.fragment_private_list, container, false);
        recyclerView = v.findViewById(R.id.taskRecycler);
        taskList = (TaskList) getArguments().getSerializable(ARG_PARAM1);
        Log.e("XD", "Task list in private fragment: " + taskList.getPrivateTasks().size());
        fragmentManager = getActivity().getSupportFragmentManager();

        taskAdapter = new TaskAdapter(taskList.getPrivateTasks(), new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClick(Task item) {
                mListener.onPrivateFragmentInteraction(item.getId());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        return v;



    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPrivateFragmentInteractionListener) {
            mListener = (OnPrivateFragmentInteractionListener) context;
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
    public interface OnPrivateFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPrivateFragmentInteraction(Long itemId);
    }






}
