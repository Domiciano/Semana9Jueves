package appmoviles.com.semana9jueves;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {

    private MapController controller;
    private TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        outputTV = findViewById(R.id.outputTV);
        controller = new MapController(this);

    }

    public TextView getOutputTV() {
        return outputTV;
    }
}
