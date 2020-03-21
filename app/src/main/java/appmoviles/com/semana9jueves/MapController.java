package appmoviles.com.semana9jueves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

public class MapController implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private MapActivity view;
    private Marker me;
    private Marker icesiMarker;
    private Polygon icesiPolygon;

    public MapController(MapActivity view){
        this.view = view;
        SupportMapFragment mapFragment = (SupportMapFragment) view.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationManager manager = (LocationManager) view.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 2, this);

        LatLng icesi = new LatLng(3.341,-76.530);
        LatLng miPosicion = new LatLng(3.342, -76.531);
        me = mMap.addMarker(new MarkerOptions().position(miPosicion).title("Yo estoy aquí").snippet("Mi ubicación"));
        icesiMarker = mMap.addMarker(new MarkerOptions().position(icesi).title("Universidad Icesi").snippet("Ubicación de la universidad"));
        icesiPolygon = mMap.addPolygon(
                new PolygonOptions()
                        .add(new LatLng(3.343095,-76.530961))
                        .add(new LatLng(3.343395,-76.527251))
                        .add(new LatLng(3.338661,-76.527133))
                        .add(new LatLng(3.338522,-76.531369))
                        .fillColor(Color.argb(50,255,0,0) )
                        .strokeColor(Color.argb(50,255,0,0))
        );

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 18));

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        me.setPosition(pos);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,18));

        if(PolyUtil.containsLocation(pos, icesiPolygon.getPoints(), true)){
            //Estoy dentro de icesi
            view.getOutputTV().setText("Estás en Icesi");
        }else{
            //Estoy fuera de icesi
            view.getOutputTV().setText("NO estás en Icesi");
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //NO USAR, ESTÁ DEPRECATED
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(icesiMarker)){
            double distance = calculateDistance(icesiMarker, me);
            icesiMarker.setSnippet("Tu distancia a la universidad es "+distance+" metros");
            icesiMarker.showInfoWindow();
            return true;
        }
        return false;
    }

    public double calculateDistance(Marker A, Marker B){
        double metros = SphericalUtil.computeDistanceBetween(A.getPosition(), B.getPosition());
        return Math.abs(metros);
    }
}
