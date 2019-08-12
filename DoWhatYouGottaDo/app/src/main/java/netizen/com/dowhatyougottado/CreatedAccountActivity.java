package netizen.com.dowhatyougottado;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import netizen.com.dowhatyougottado.model.Founder;
import netizen.com.dowhatyougottado.model.TaskList;
import netizen.com.dowhatyougottado.server.DoWhatYouGottaDoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatedAccountActivity extends FragmentActivity implements TypeKeyFragment.OnTypeKeyFragmentInteractionListener {

    private SharedPreferences preferences;
    TextView accountNameTextView;
    private Button createListButton;
    private Button pinToListButton;
    final String BASE_URL = "http://10.0.2.2:8080/";
    FragmentManager fragmentManager;
    String accountNameFromPreferences;
    TextView keyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_account);

        preferences = getSharedPreferences("doWhatYouGottaDoApp", Activity.MODE_PRIVATE);
        accountNameTextView = findViewById(R.id.nameTextView);
        createListButton = findViewById(R.id.createListBtn);
        pinToListButton = findViewById(R.id.pinToListBtn);
        keyText = findViewById(R.id.listKeyTextView);

        accountNameFromPreferences = preferences.getString("account_name", "");
        String listKeyFromPreferences = preferences.getString("account_list_key", "");

        keyText.setText(listKeyFromPreferences);
        fragmentManager = getSupportFragmentManager();

        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);

            }
        });


        pinToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createListButton.setVisibility(View.GONE);
                pinToListButton.setVisibility(View.GONE);
                keyText.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TypeKeyFragment typeKeyFragment = new TypeKeyFragment();


                fragmentTransaction.add(R.id.keycontainer, typeKeyFragment);
                fragmentTransaction.commit();



            }
        });
    }

    @Override
    public void onTypeKeyFragmentInteraction(String newKey) {

        Log.e("XD", "get new key: " + newKey);
        setListKey(newKey);
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);

    }

    private void setListKey(String newKey){

        Log.e("XD", "account name from preferences: " + accountNameFromPreferences);
        Founder founder = new Founder();
        founder.setAccountName(accountNameFromPreferences);
        founder.setListKey(newKey);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<Founder> call = request.pinToBudList(founder);
        call.enqueue(new Callback<Founder>(){
            @Override
            public void onResponse(Call<Founder> call, Response<Founder> response) {

                Founder newF = response.body();
                Log.e("XD", "Tyle: " + newF.getListKey());
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putString("account_name", newF.getAccountName());
                preferencesEditor.putString("account_list_key", newF.getListKey());
                preferencesEditor.commit();

            }

            @Override
            public void onFailure(Call<Founder> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API");
                t.printStackTrace();
            }
        });
    }
}
