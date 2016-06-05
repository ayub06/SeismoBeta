package com.pkm.proyek.seismoalpha.laporan;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.tim.Laporan;
import com.pkm.proyek.seismoalpha.laporan.umum.LaporanUmum;

public class FotoLaporan extends DialogFragment {

    public FotoLaporan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.foto_fragment, container,false);

        int index = getArguments().getInt("index");
        boolean daritim = getArguments().getBoolean("tim");

        //Log.d("MASUK", "YA "+index);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.foto_full);
        if (daritim) {
            //Log.d("MASUK", "YA "+daritim);
            imageView.setImageBitmap(Laporan.laporanArrayList.get(index).getFoto());
        }else {
            //Log.d("MASUK", "YA "+daritim);
            imageView.setImageBitmap(LaporanUmum.laporanArrayList.get(index).getFoto_laporan());
        }
        return itemView;
    }
}
