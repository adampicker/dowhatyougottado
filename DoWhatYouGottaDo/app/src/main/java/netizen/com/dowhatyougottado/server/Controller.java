package netizen.com.dowhatyougottado.server;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import netizen.com.dowhatyougottado.model.Founder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller  {

    final static String BASE_URL = "http://10.0.2.2:8080/";
    public Founder newFounder;

  //  public void start(Founder founder) throws IOException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

  /*      DoWhatYouGottaDoAPI doWhatYouGottaDoAPI = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<Founder> call = doWhatYouGottaDoAPI.createFounder(founder);
        //call.enqueue(this);
        newFounder = call.execute().body();
    }

    @Override
    public void onResponse(Call<Founder> call, Response<Founder> response) {

        if(response.isSuccessful()) {
            newFounder = response.body();
            Log.e("XD", response.body().toString() );
        } else Log.e("XD", response.body().toString());
    }

    @Override
    public void onFailure(Call<Founder> call, Throwable t) {
        t.printStackTrace();
    } */
}
