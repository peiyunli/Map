package com.example.map;

import java.util.ArrayList;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.FlickrPOIProvider;
import org.osmdroid.bonuspack.location.GeoNamesPOIProvider;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends Activity {

	private MapController myMapController;
	
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(48.87372, 2.29363);
        IMapController mapController = map.getController();
        mapController.setZoom(13);
        mapController.setCenter(startPoint);//constructo
        
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        
        
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        startMarker.setTitle("Start point");
        
        BoundingBoxE6 bb = new BoundingBoxE6(48.87372, 2.368291,
        		48.852647, 2.334817);
        
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(this, map);
        GeoPoint loc=myLocationOverlay.getMyLocation();
        
        NominatimPOIProvider poiProvider = new NominatimPOIProvider();
        ArrayList<POI> pois = poiProvider.getPOICloseTo(startPoint, "cinema", 50, 0.1);
        

        FolderOverlay poiMarkers = new FolderOverlay(this);
        map.getOverlays().add(poiMarkers);
        
        
        Drawable poiIcon = getResources().getDrawable(android.R.drawable.star_big_on);
        for (POI poi:pois){
                Marker poiMarker = new Marker(map);
                poiMarker.setTitle(poi.mType);
                poiMarker.setSnippet(poi.mDescription);
                poiMarker.setPosition(poi.mLocation);
                poiMarker.setInfoWindow(new CustomInfoWindow(map));
                poiMarker.setIcon(poiIcon);
                poiMarker.setRelatedObject(poi);
                if (poi.mThumbnail != null){
                        poiMarker.setImage(new BitmapDrawable(poi.mThumbnail));
                }
                poiMarkers.add(poiMarker);
        }
    }
}