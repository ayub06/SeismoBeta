package com.pkm.proyek.seismoalpha.laporan.tim;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkm.proyek.seismoalpha.laporan.FotoLaporan;
import com.pkm.proyek.seismoalpha.laporan.LaporanActivity;
import com.pkm.proyek.seismoalpha.maps.MapsActivity;
import com.pkm.proyek.seismoalpha.R;

import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.PersonViewHolder>{

    private ArrayList<Laporan> laporan;
    private Activity activity;

    public LaporanAdapter(ArrayList<Laporan> laporan,Activity activity){
        this.activity=activity;
        this.laporan = laporan;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        //CardView cv;
        LinearLayout container;
        TextView  waktu;
        TextView jam;
        ImageView foto;
        TextView  nama;
        TextView  alamat;

        TextView jumlah_korban;
        TextView luka_berat;
        TextView luka_ringan;

        TextView rusak_berat;
        TextView rusak_sedang;
        TextView rusak_ringan;

        ImageView foto_laporan;

        PersonViewHolder(View itemView) {
            super(itemView);
            //cv          = (CardView) itemView.findViewById(R.id.card_view);
            container   = (LinearLayout) itemView.findViewById(R.id.item_container);
            waktu       = (TextView)itemView.findViewById(R.id.tanggal_lapor);
            jam         = (TextView)itemView.findViewById(R.id.jam_lapor);
            foto        = (ImageView)itemView.findViewById(R.id.foto_pelapor);
            nama        = (TextView) itemView.findViewById(R.id.nama_pelapor);
            alamat      = (TextView) itemView.findViewById(R.id.lokasi_laporan);

            jumlah_korban = (TextView) itemView.findViewById(R.id.lapor_korban_jiwa);
            luka_berat = (TextView) itemView.findViewById(R.id.lapor_luka_berat);
            luka_ringan = (TextView) itemView.findViewById(R.id.lapor_luka_ringan);

            rusak_berat = (TextView) itemView.findViewById(R.id.lapor_rusak_berat);
            rusak_sedang = (TextView) itemView.findViewById(R.id.lapor_rusak_sedang);
            rusak_ringan = (TextView) itemView.findViewById(R.id.lapor_rusak_ringan);

            foto_laporan =(ImageView)itemView.findViewById(R.id.foto_laporan);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.foto_laporan.setImageBitmap(laporan.get(i).getFoto());
        personViewHolder.foto_laporan.setTag(i);
        personViewHolder.foto_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK ID", String.valueOf(v.getTag()));
                FragmentManager fragmentManager = LaporanActivity.activity.getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                FotoLaporan fotolaporan=new FotoLaporan();

                //Build communication
                Bundle bundle1=new Bundle();
                bundle1.putInt("index", (Integer) v.getTag());
                bundle1.putBoolean("tim",true);
                fotolaporan.setArguments(bundle1);

                //add and run
                fragmentTransaction.add(fotolaporan,"buy");
                fragmentTransaction.commit();
            }
        });
        personViewHolder.container.setTag(i);
        personViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", String.valueOf(v.getTag()));

                Bundle bundle=new Bundle();
                bundle.putInt(MapsActivity.MODE,MapsActivity.DISPLAY_LAPORAN);
                bundle.putInt(MapsActivity.ID_GEMPA_OR_LAPORAN,
                        Integer.parseInt(String.valueOf(v.getTag())));

                Intent intent=new Intent(new Intent(activity.getApplicationContext(), MapsActivity.class));
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        personViewHolder.waktu.setText(laporan.get(i).getTanggal());
        personViewHolder.jam.setText(laporan.get(i).getJam());

        personViewHolder.foto.setImageBitmap(laporan.get(i).getPelapor().getFoto());
        personViewHolder.nama.setText(laporan.get(i).getPelapor().getNama());
        personViewHolder.alamat.setText(laporan.get(i).getAlamat());

        personViewHolder.jumlah_korban.setText(String.valueOf(laporan.get(i).getJumlah_korban()));
        personViewHolder.luka_berat.setText(String.valueOf(laporan.get(i).getLuka_berat()));
        personViewHolder.luka_ringan.setText(String.valueOf(laporan.get(i).getLuka_ringan()));

        personViewHolder.rusak_ringan.setText(String.valueOf(laporan.get(i).getRusak_ringan()));
        //personViewHolder.rusak_sedang.setText(String.valueOf(laporan.get(i).getRusak_sedang()));
        personViewHolder.rusak_berat.setText(String.valueOf(laporan.get(i).getRusak_berat()));
    }

    @Override
    public int getItemCount() {
        return laporan.size();
    }
}

