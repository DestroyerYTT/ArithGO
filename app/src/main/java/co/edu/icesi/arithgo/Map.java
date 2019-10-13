package co.edu.icesi.arithgo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.zip.Inflater;

import co.edu.icesi.arithgo.ExchangeZone;
import co.edu.icesi.arithgo.model.data.CRUDPoints;
import co.edu.icesi.arithgo.model.entity.Point;

public class Map extends FragmentActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener {

    private static final int CALL_BACK_ID = 1;
    private GoogleMap mMap;
    private int zoon = 19;
    private float[] disResultado;
    private Circle zone2;
    private Circle zone1;
    private Circle library;
    private TextView reactiveZones;
    private Button actionButton;
    private static TextView points;
    private TextView points_label;
    private int n;

    /**
     * Marker of the current location of the user
     */
    private Marker myLocation;
    private Fragment exchangeZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragment);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        n = 0;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                n++;
                LatLng pos;
                if(n % 3 == 0){
                    pos = new LatLng(3.341835, -76.529982);
                }else if(n % 3 == 1){
                    pos = new LatLng(3.341753,-76.529998);
                }else{
                    pos = new LatLng(3.342567, -76.529752);
                }
                myLocation.setPosition(pos);
                reactiveZone(pos.latitude, pos.longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, zoon));
            }
        });

        reactiveZones = findViewById(R.id.tv_zones);
        actionButton = findViewById(R.id.action_btn);
        points = findViewById(R.id.tv_points);
        points_label = findViewById(R.id.points_label_tv);
     //   CRUDPoints.update(new Point("1", "points", 0));
        points.setText(""+CRUDPoints.retrieve("1").getPoints());
        actionButton.setOnClickListener(this);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setMapToolbarEnabled(false);

        disResultado = new float[2];

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CALL_BACK_ID);
            }

        }

        // System service to get the current location
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        LatLng pos = new LatLng(3.341441,-76.529544);
        // Inicialization of current location
        myLocation = mMap.addMarker(new MarkerOptions().position(pos).title("Current Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, zoon));
        myLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.player));

        LatLng libraryPos = new LatLng(3.341753,-76.529998);
        Marker libraryMarker = mMap.addMarker(new MarkerOptions().position(libraryPos));
        libraryMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.library));

        LatLng stage1Pos = new LatLng(3.342567, -76.529752);
        Marker stage1Marker = mMap.addMarker(new MarkerOptions().position(stage1Pos));
        stage1Marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.stage1));

        LatLng stage2Pos = new LatLng(3.341335, -76.529982);
        Marker stage2Marker = mMap.addMarker(new MarkerOptions().position(stage2Pos));
        stage2Marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.stage2));

        zone1 = mMap.addCircle(new CircleOptions()
                .center(new LatLng(3.342567, -76.529752))
                .radius(20)
                .strokeColor(Color.RED)
                .strokeWidth(4)
                .fillColor(Color.TRANSPARENT));


        library = mMap.addCircle(new CircleOptions()
                .center(new LatLng(3.341753,-76.529998))
                .radius(15)
                .strokeColor(Color.BLACK)
                .strokeWidth(4)
                .fillColor(Color.TRANSPARENT));

        zone2 = mMap.addCircle(new CircleOptions()
                .center(new LatLng(3.341335, -76.529982))
                .radius(20)
                .strokeColor(Color.BLUE)
                .strokeWidth(4)
                .fillColor(Color.TRANSPARENT));

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        myLocation.setPosition(pos);
        reactiveZone(pos.latitude, pos.longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, zoon));
    }


    public void reactiveZone(double lat, double lon) {
        Location.distanceBetween(lat, lon,
                zone1.getCenter().latitude,
                zone1.getCenter().longitude,
                disResultado);
        System.out.println("Distancia:" + disResultado[0]);

        if (disResultado[0] <= zone1.getRadius()) {
            reactiveZones.setText("¡You are in the reactive Zone #1!");
            actionButton.setText("Solve a Puzzle");
            actionButton.setVisibility(View.VISIBLE);
            reactiveZones.setTextColor(Color.RED);
            reactiveZones.setVisibility(View.VISIBLE);
            points.setVisibility(View.VISIBLE);
            points_label.setVisibility(View.VISIBLE);
        } else {
            Location.distanceBetween(lat, lon,
                    zone2.getCenter().latitude,
                    zone2.getCenter().longitude,
                    disResultado);
            if (disResultado[0] <= zone2.getRadius()) {
                reactiveZones.setText("¡You are in the reactive Zone #2!");
                actionButton.setText("Solve a Puzzle");
                actionButton.setVisibility(View.VISIBLE);
                reactiveZones.setTextColor(Color.RED);
                reactiveZones.setVisibility(View.VISIBLE);
                points.setVisibility(View.VISIBLE);
                points_label.setVisibility(View.VISIBLE);
            }else{
                Location.distanceBetween(lat, lon,
                        library.getCenter().latitude,
                        library.getCenter().longitude,
                        disResultado);
                if (disResultado[0] <= library.getRadius()) {
                    reactiveZones.setText("¡You are in the Library Zone!");
                    actionButton.setText("Exchange Points");
                    actionButton.setVisibility(View.VISIBLE);
                    reactiveZones.setTextColor(Color.RED);
                    reactiveZones.setVisibility(View.VISIBLE);
                    points.setVisibility(View.VISIBLE);
                    points_label.setVisibility(View.VISIBLE);
                }else{
                    reactiveZones.setVisibility(View.INVISIBLE);
                    actionButton.setVisibility(View.INVISIBLE);
                    points.setVisibility(View.INVISIBLE);
                    points_label.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public static void updatePoint(){
        points.setText(""+CRUDPoints.retrieve("1").getPoints());
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        if(actionButton.getId() == view.getId()){
            if(actionButton.getText().equals("Exchange Points")){
                i = new Intent(this, ExchangeZone.class);
                startActivity(i);
            }else if(actionButton.getText().equals("Solve a Puzzle")){
                i = new Intent(this, Puzzle.class);
                startActivity(i);
            }

        }
    }
}
