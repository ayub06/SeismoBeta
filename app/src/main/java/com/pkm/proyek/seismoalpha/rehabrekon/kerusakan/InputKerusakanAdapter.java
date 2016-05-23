package com.pkm.proyek.seismoalpha.rehabrekon.kerusakan;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shalahudin Al Ayyub on 07/05/2016.
 */
public class InputKerusakanAdapter extends FragmentPagerAdapter{
    private List<Kerusakan> kerusakans =new ArrayList<>();

    public InputKerusakanAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setKerusakans(List<Kerusakan> kerusakans) {
        this.kerusakans = kerusakans;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==5){
            return InputKerusakanActivity.kerusakanLain;
        }
        return kerusakans.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return kerusakans.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return kerusakans.get(position).getKategori();
    }
}