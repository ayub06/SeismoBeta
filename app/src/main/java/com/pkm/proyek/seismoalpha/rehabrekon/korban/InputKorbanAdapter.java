package com.pkm.proyek.seismoalpha.rehabrekon.korban;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shalahudin Al Ayyub on 07/05/2016.
 */
public class InputKorbanAdapter extends FragmentPagerAdapter{
    private List<Korban> korbans=new ArrayList<>();

    public InputKorbanAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setKorbans(List<Korban> korbans) {
        this.korbans = korbans;
    }

    @Override
    public Fragment getItem(int position) {
        return korbans.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return korbans.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return korbans.get(position).getKategori();
    }
}