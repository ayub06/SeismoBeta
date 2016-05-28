package com.pkm.proyek.seismoalpha.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.tim.Laporan;
import com.pkm.proyek.seismoalpha.laporan.LaporanActivity;
import com.pkm.proyek.seismoalpha.util.loadFromAPI;

import java.util.ArrayList;
import java.util.List;

public class GempaAdapter extends RecyclerView.Adapter<GempaAdapter.PersonViewHolder>{

    public static int indexGempaMain;
    private List<Gempa> gempa;
    private static Activity activity;

    public GempaAdapter(List<Gempa> gempa, Activity activity) {
        this.gempa = gempa;
        this.activity = activity;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView waktu;
        TextView jam;
        TextView nama;
        TextView sr;

        TextView jumlah_korban;
        TextView luka_berat;
        TextView luka_ringan;

        TextView rusak_berat;
        TextView rusak_ringan;

        Button korban;
        Button peta;
        PersonViewHolder(View itemView) {
            super(itemView);
            nama           = (TextView) itemView.findViewById(R.id.lokasi_gempa);
            waktu        = (TextView) itemView.findViewById(R.id.tanggal_gempa);
            jam         = (TextView) itemView.findViewById(R.id.jam_gempa);
            sr        = (TextView) itemView.findViewById(R.id.skala_gempa);

            jumlah_korban = (TextView) itemView.findViewById(R.id.total_korban_jiwa);
            luka_berat = (TextView) itemView.findViewById(R.id.total_luka_berat);
            luka_ringan = (TextView) itemView.findViewById(R.id.total_luka_ringan);

            rusak_berat = (TextView) itemView.findViewById(R.id.total_rusak_berat);
            rusak_ringan = (TextView) itemView.findViewById(R.id.total_rusak_ringan);

            korban      = (Button) itemView.findViewById(R.id.button_korban_timeline);
            peta = (Button) itemView.findViewById(R.id.button_pemetaan);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.waktu.setText(gempa.get(i).getTanggalSingkat());
        personViewHolder.jam.setText(gempa.get(i).getJam());
        personViewHolder.nama.setText(gempa.get(i).getNama());
        personViewHolder.sr.setText(String.valueOf(gempa.get(i).getSr()));

        personViewHolder.jumlah_korban.setText(String.valueOf(gempa.get(i).getTotal_korban()));
        personViewHolder.luka_berat.setText(String.valueOf(gempa.get(i).getTotal_luka_berat()));
        personViewHolder.luka_ringan.setText(String.valueOf(gempa.get(i).getTotal_luka_ringan()));

        personViewHolder.rusak_ringan.setText(String.valueOf(gempa.get(i).getTotal_rusak_ringan()));
        personViewHolder.rusak_berat.setText(String.valueOf(gempa.get(i).getTotal_rusak_berat()));

        personViewHolder.korban.setTag(String.valueOf(i));
        personViewHolder.korban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle bundle=new Bundle();
                //bundle.putInt("gempaid", Integer.parseInt(String.valueOf(v.getTag())));
                Log.d("BUNDLE", String.valueOf(Integer.parseInt(String.valueOf(v.getTag()))));
                Intent intent=new Intent(activity.getApplicationContext(), LaporanActivity.class);
                //intent.putExtras(bundle);
                LaporanActivity.indexGempa=Integer.parseInt(String.valueOf(v.getTag()));
                activity.startActivity(intent);
            }
        });
        personViewHolder.peta.setTag(String.valueOf(i));
        personViewHolder.peta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexGempaMain=Integer.parseInt(String.valueOf(v.getTag()));
                loadLaporanByIDGempa();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gempa.size();
    }


    public static void loadLaporanByIDGempa() {
        //Get Laporan from Server

        MainActivity.startLoading();

        Laporan.laporanArrayList=new ArrayList<>();
        loadFromAPI.from=loadFromAPI.MAIN_ACTIVITY;
        loadFromAPI.sync_mode = loadFromAPI.SYNC_MODE_GET_LAPORAN;
        new loadFromAPI().execute(
                new Pair<Context, String>(activity, "Load Laporan")
        );
    }
}

