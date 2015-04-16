package com.example.map;

import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CustomInfoWindow extends MarkerInfoWindow {
	
	POI mSelectedPoi;

	public CustomInfoWindow(MapView mapView) {
		super(R.layout.bonuspack_bubble, mapView);
		
		Button btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
		
		btn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	        	if (mSelectedPoi.mUrl != null){
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.mUrl));
                    view.getContext().startActivity(myIntent);
	        }}
	});
		// TODO Auto-generated constructor stub
	}
	
	@Override public void onOpen(Object item){
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        Marker marker = (Marker)item;
        mSelectedPoi = (POI)marker.getRelatedObject();
}    

}
