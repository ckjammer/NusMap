package com.maps;

import java.util.List;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NUSMapsActivity extends Activity implements LocationListener, android.view.View.OnClickListener {
	/** Called when the activity is first created. */

	MapView map;
	long start, stop;
	MyLocationOverlay compass;
	MapController controller;
	int x,y;
	GeoPoint touchPoint;
	Drawable d;
	List<Overlay> overlayList;
	LocationManager lm;
	String towers;
	int lat = 0, longi = 0;
	Button floorPlan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		map = (MapView) findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);

		
		overlayList = map.getOverlays();
		
		
		
		compass = new MyLocationOverlay(NUSMapsActivity.this, map);
		overlayList.add(compass);
		
		controller = map.getController();
		GeoPoint point = new GeoPoint(5164324, 7848593);
		controller.animateTo(point);
		controller.setZoom(6);
		
		d = getResources().getDrawable(R.drawable.ic_launcher);
		
		//placing pinpoint at location
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		
		towers = lm.getBestProvider(crit, false);
		Location location = lm.getLastKnownLocation(towers);
		if(location != null){
			lat = (int) (location.getLatitude()* 1E6);
			longi = (int) (location.getLongitude()* 1E6);
			GeoPoint ourLocation = new GeoPoint(lat, longi);
			OverlayItem  overlayItem = new OverlayItem("2nd string", "what's up", ourLocation);

		}else{
			Toast.makeText(NUSMapsActivity.this, "Couldn't get provider", Toast.LENGTH_SHORT).show();
		}
		
		floorPlan = (Button) findViewById(R.id.bFloor);
		floorPlan.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		compass.disableCompass();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		compass.enableCompass();
		lm.requestLocationUpdates(towers, 5000, 1, this);
	}


	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	

	public void onLocationChanged(Location l) {
		// TODO Auto-generated method stub
		lat = (int) (l.getLatitude()* 1E6);
		longi = (int) (l.getLongitude()* 1E6);
		GeoPoint ourLocation = new GeoPoint(lat, longi);
		OverlayItem  overlayItem = new OverlayItem("2nd string", "what's up", ourLocation);
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		/*--------------------------------------------Shows Floor Plan When Clicked -------------------------------------*/
		case R.id.bFloor:
		Intent  a = new Intent(NUSMapsActivity.this,FloorPlan.class);
		startActivity(a);
		break;
		}
	}



	
	
	
		
		
	
	
	
}