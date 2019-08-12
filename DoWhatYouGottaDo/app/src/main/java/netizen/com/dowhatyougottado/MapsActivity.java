package netizen.com.dowhatyougottado;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

import netizen.com.dowhatyougottado.model.Founder;
import netizen.com.dowhatyougottado.server.DoWhatYouGottaDoAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends Fragment
        implements OnMapReadyCallback {

    private SharedPreferences preferences;
    LinkedList<Founder> founderMates;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getCoordinates();
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        MapView mapView = (MapView) view.findViewById(R.id.fragment_map_trip);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if(founderMates == null ) Log.e("XD", "Coordinates empty");
        else Log.e("XD", "Coordinates not empty, size: " + founderMates.size());

        int i =0;
        for (Founder founder : founderMates){
            if (i==0) {
                LatLng marker = new LatLng(founder.getLatitude(), founder.getLongtitude());

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 12));
            }
            googleMap.addMarker(new MarkerOptions().title(founder.getAccountName()).position(new LatLng(founder.getLatitude()+i*10, founder.getLongtitude())));
            i++;
        }
        //googleMap.addMarker(new MarkerOptions().title("Testing").position(new LatLng(53.92, 4.47)));
        //googleMap.addMarker(new MarkerOptions().title("Hello Google Maps!").position(marker));



        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true); // This is how I had implemented the setMyLocationEnabled method


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getCoordinates() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        preferences = getActivity().getSharedPreferences("doWhatYouGottaDoApp", Activity.MODE_PRIVATE);
        String accountNameFromPreferences = preferences.getString("account_name", "");
        Founder founderCoordinates = new Founder();
        founderCoordinates.setAccountName(accountNameFromPreferences);

        DoWhatYouGottaDoAPI request = retrofit.create(DoWhatYouGottaDoAPI.class);
        Call<LinkedList<Founder>> call = request.getCoordinates(founderCoordinates);
        call.enqueue(new Callback<LinkedList<Founder>>(){
            @Override
            public void onResponse(Call<LinkedList<Founder>> call, Response<LinkedList<Founder>> response) {
                Log.e("XD", "Coordinates get");
                founderMates = response.body();

                Log.e("XD", "Coordinates get: "+ founderMates.size());
            }

            @Override
            public void onFailure(Call<LinkedList<Founder>> call, Throwable t) {
                Log.e("XD", "ERROR while call to rest API - geting coordinates");
                t.printStackTrace();
            }
        });
    }

}