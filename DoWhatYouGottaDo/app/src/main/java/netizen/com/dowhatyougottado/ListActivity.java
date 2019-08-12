package netizen.com.dowhatyougottado;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import netizen.com.dowhatyougottado.model.Founder;
import netizen.com.dowhatyougottado.model.Task;
import netizen.com.dowhatyougottado.model.TaskList;
import netizen.com.dowhatyougottado.server.DoWhatYouGottaDoAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity implements PrivateListFragment.OnPrivateFragmentInteractionListener,
        SharedListFragment.OnSharedFragmentInteractionListener,
        AddTaskFragment.OnAddTaskFragmentInteractionListener,
        DatePickerFragment.OnDatePickerDialogFragmentInteractionListener,
        TimePickerFragment.OnTimePickerDialogFragmentInteractionListener,
        RemoveDoneFragment.OnRemoveFragmentInteractionListener {


    private SharedPreferences preferences;
    String accountNameFromPreferences;
    String listKeyFromPreferences;
    final String BASE_URL = "http://10.0.2.2:8080/";
    private TextView mTextMessage;
    PrivateListFragment privateListFragment;
    SharedListFragment sharedListFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TaskList tasks;
    ImageButton addTaskButton, getKeyButton;
    AddTaskFragment addTaskFragment;
    Task toAdd;
    Calendar deadlineDateOfCreatedTask;
    Calendar timeDate;
    RemoveDoneFragment removeDoneFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    getTasksByListKey();
                    privateListFragment = PrivateListFragment.newInstance(tasks);
                    findViewById(R.id.navigation_home).setVisibility(View.VISIBLE);
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.add(R.id.list_fragment_container, privateListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    getTasksByListKey();
                    findViewById(R.id.navigation_dashboard).setVisibility(View.VISIBLE);
                    sharedListFragment = SharedListFragment.newInstance(tasks);
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.add(R.id.list_fragment_container, sharedListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    MapsActivity map = new MapsActivity();
                    fragmentTransaction.add(R.id.list_fragment_container, map);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // set custom tolbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().getCustomView().getParent();
        Toolbar parent = (Toolbar) getSupportActionBar().getCustomView().getParent();
        parent.setContentInsetsAbsolute(0, 0);

        // get last created account
        preferences = getSharedPreferences("doWhatYouGottaDoApp", Activity.MODE_PRIVATE);
        accountNameFromPreferences = preferences.getString("account_name", "");
        listKeyFromPreferences = preferences.getString("account_list_key", "");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setCoordinates();
        //getting task by list key
        getTasksByListKey();

        // get buttons
        addTaskButton = findViewById(R.id.addBtn);
        getKeyButton = findViewById(R.id.keyBtn);
        addTaskFragment = new AddTaskFragment();
        fragmentManager = getSupportFragmentManager(); // fragment manager


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.add(R.id.add_task_container, addTaskFragment);
                fragmentTransaction.commit();

            }
        });

        getKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getKey = getString(R.string.yourkeylistis);
                Toast.makeText(getApplicationContext(), getKey + " " + listKeyFromPreferences, Toast.LENGTH_LONG).show();
                //android.app.FragmentManager fragmentManager = getFragmentManager();
                // android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //fragmentTransaction.add(R.id.fragment_container, createAccountFragment);
                //fragmentTransaction.commit();

            }
        });


    }


    @Override
    public void onAddTaskFragmentInteraction() {

        // getting added task fields
        String addingTaskContent = addTaskFragment.contentTaskEditText.getText().toString();
        boolean onlyMe = addTaskFragment.ifPrivateCheckBox.isChecked();
        // collect all task field
        Task taskToAdd = new Task();
        taskToAdd.setFounderName(preferences.getString("account_name", ""));
        taskToAdd.setContent(addingTaskContent);
        taskToAdd.setShared(!onlyMe);
        taskToAdd.setDeadline(this.timeDate.getTimeInMillis());
        taskToAdd.setDone(false);

        addNewTask(taskToAdd);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(addTaskFragment);
        fragmentTransaction.commit();




    }

    private void getTasksByListKey() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<LinkedList<Task>> call = request.getTaskByListKey(listKeyFromPreferences);
        call.enqueue(new Callback<LinkedList<Task>>() {
            @Override
            public void onResponse(Call<LinkedList<Task>> call, Response<LinkedList<Task>> response) {

                tasks = new TaskList();
                LinkedList<Task> taskList = new LinkedList<>();
                taskList = response.body();

                tasks.setTasksList(taskList);


                Log.e("XD", "Tyle: " + tasks.getTasks().size());

            }

            @Override
            public void onFailure(Call<LinkedList<Task>> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API");
                t.printStackTrace();
            }
        });
    }

    private Task addNewTask(Task newTask) {/////////////////////////////////////////


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<Task> call = request.addTask(newTask);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {

                toAdd = new Task();
                LinkedList<Task> taskList = new LinkedList<>();
                Log.e("XD", "siema = " + response.body());
                toAdd = response.body();
                tasks.getTasks().add(toAdd);
                Log.e("XD", "Tyle: " + toAdd.getFounderName());
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API");
            }
        });
        return toAdd;
    }


    @Override
    public void onDatePickerDialogFragmentInteraction(Calendar date) {
        addTaskFragment.deadlineEditText.setText(date.toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", date);

        DialogFragment timePicker = new TimePickerFragment();
        timePicker.setArguments(bundle);
        timePicker.show(getSupportFragmentManager(), "timepicker");
    }

    @Override
    public void onTimePickerDialogFragmentInteraction(Calendar timeDate) {
        Log.e("XD", "Passed time date: " + timeDate.getTime());
        this.timeDate = timeDate;
        addTaskFragment.deadlineEditText.setText(this.timeDate.getTime().toString());
    }


    @Override
    public void onPrivateFragmentInteraction(Long itemId) {
        removeDoneFragment = RemoveDoneFragment.newInstance(itemId);
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.add_task_container, removeDoneFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSharedFragmentInteraction(Long itemId) {
        removeDoneFragment = RemoveDoneFragment.newInstance(itemId);
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.add_task_container, removeDoneFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRemoveFragmentInteraction(Long itemId, boolean remove) {

        if (remove) {
            removeTask(itemId);
        } else doneTask(itemId);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(removeDoneFragment);
        fragmentTransaction.commit();
    }

    public void removeTask(long id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<ResponseBody> call = request.removeTask(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("XD", "siema = " + response.body());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API - removeTask");
            }
        });

    }

    public void doneTask(long id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<ResponseBody> call = request.makeTaskDone(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("XD", "siema = " + response.body());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API - make task done");
            }
        });

    }

    private void setCoordinates() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        Founder founderCoordinates = new Founder();
        founderCoordinates.setAccountName(accountNameFromPreferences);
        founderCoordinates.setLongtitude(longitude);
        founderCoordinates.setLatitude(latitude);

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<ResponseBody> call = request.setCoordinates(founderCoordinates);
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.e("XD", "Coordinates setted");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API - sending coordinates");
                t.printStackTrace();
            }
        });
    }
}
