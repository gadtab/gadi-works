package il.co.gadiworks.googlemaps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;

public class Main extends MapActivity {
	MapController mController;
	GeoPoint geoP;
	MapView mapV;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapV = (MapView) findViewById(R.id.mapView);
        mapV.displayZoomControls(true);
        mapV.setBuiltInZoomControls(true);
        
        double lat = 40.8;
        double longi = -96.666;
        
        geoP = new GeoPoint((int)(lat * 1E6), (int)(longi * 1E6));
        
        mController = mapV.getController();
        mController.animateTo(geoP);
        mController.setZoom(13);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}