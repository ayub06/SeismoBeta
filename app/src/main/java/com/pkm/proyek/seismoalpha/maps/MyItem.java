package com.pkm.proyek.seismoalpha.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by User3887 on 5/11/2016.
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private int  id;


    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
