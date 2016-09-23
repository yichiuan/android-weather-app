package com.yichiuan.weatherapp;

import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.PermissionEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.weatherapi.WeatherService;


public class ContentFragment extends Fragment {

    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private View constraintLayout;
    private TextView temperatureView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_frag, container, false);
        constraintLayout = view.findViewById(R.id.constraint_layout);
        temperatureView = (TextView) view.findViewById(R.id.temperature_view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void receiveLocation() {

        if (ContextCompat.checkSelfPermission(getContext(), LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager =
                    (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            List<String> allprovides = locationManager.getAllProviders();
            for (String allprovide : allprovides) {
                Log.i("WeatherActivity", allprovide);
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                WeatherService.getInstance().requestWeather(location.getLatitude(), location.getLongitude());
            } else {
                Log.e("WeatherActivity", "location is null.");
            }
        } else {
            Snackbar.make(constraintLayout, "沒權限", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Subscribe
    public void onWeatherInfoEvent(WeatherInfoEvent weatherInfoEvent) {
        temperatureView.setText(String.valueOf(weatherInfoEvent.weatherInfo.getTemperature()));
    }

    @Subscribe
    public void onErrorResponseEvent(ErrorResponseEvent errorEvnet) {
        Toast.makeText(getActivity().getApplicationContext(), errorEvnet.message, Toast.LENGTH_LONG).show();
    }

    @Subscribe(sticky = true)
    public void onPermissionEvent(PermissionEvent permissionEvent) {
        receiveLocation();
    }
}