package netizen.com.dowhatyougottado;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import netizen.com.dowhatyougottado.model.Founder;

public class HelloActivity extends FragmentActivity implements CreateAccountFragment.OnFragmentInteractionListener {
    CreateAccountFragment createAccountFragment;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        preferences = getSharedPreferences("doWhatYouGottaDoApp", Activity.MODE_PRIVATE);

       final Button createAccountButton = findViewById(R.id.createAccBtn);
        final Button existingAccountButton = findViewById(R.id.useExistingAccBtn);
        createAccountFragment = new CreateAccountFragment();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                existingAccountButton.setVisibility(View.GONE);
                fragmentTransaction.add(R.id.fragment_container, createAccountFragment);
                fragmentTransaction.commit();
                v.setVisibility(View.GONE);
            }
        });

        existingAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.remove(createAccountFragment);
                fragmentTransaction.commit();

                Intent intent = new Intent(getApplicationContext(), CreatedAccountActivity.class);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onFragmentInteraction(Founder newFounder) {
        Toast.makeText(getApplicationContext(), "CALLBACKS createAccount: " + newFounder.getListKey(), Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().remove(createAccountFragment).commit();

        Intent intent = new Intent(this, CreatedAccountActivity.class);
        saveAccountDataToSharedPreferences(newFounder);
        startActivity(intent);

    }

    private void saveAccountDataToSharedPreferences(Founder founder){
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("account_name", founder.getAccountName());
        preferencesEditor.putString("account_list_key", founder.getListKey());
        preferencesEditor.commit();

    }


}
