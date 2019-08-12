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

import netizen.com.dowhatyougottado.model.Task;
import netizen.com.dowhatyougottado.model.TaskList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SharedListFragment.OnSharedFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SharedListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharedListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TaskList taskList;


    TaskAdapter sharedTaskAdapter;
    RecyclerView sharedRecyclerView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private OnSharedFragmentInteractionListener mListener;

    public SharedListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharedListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SharedListFragment newInstance(TaskList taskList) {
        SharedListFragment fragment = new SharedListFragment();
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
        View v = inflater.inflate(R.layout.fragment_shared_list, container, false);
        sharedRecyclerView = v.findViewById(R.id.sharedTaskRecycler);

        taskList = (TaskList) getArguments().getSerializable(ARG_PARAM1);
        Log.e("XD", "Task list in shared fragment: " + taskList.getPrivateTasks().size());

        sharedTaskAdapter = new TaskAdapter(taskList.getSharedTasks(), new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClick(Task item) {
                Log.e("XD", "wyswietlenie framentu z dodne i remove");
                mListener.onSharedFragmentInteraction(item.getId());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sharedRecyclerView.setLayoutManager(mLayoutManager);
        sharedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sharedRecyclerView.setAdapter(sharedTaskAdapter);
        sharedTaskAdapter.notifyDataSetChanged();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSharedFragmentInteractionListener) {
            mListener = (OnSharedFragmentInteractionListener) context;
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
    public interface OnSharedFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSharedFragmentInteraction(Long taskId);
    }

}
