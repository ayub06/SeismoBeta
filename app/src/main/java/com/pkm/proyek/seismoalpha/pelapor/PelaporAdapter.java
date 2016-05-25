package com.pkm.proyek.seismoalpha.pelapor;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkm.proyek.seismoalpha.R;

import java.util.List;

public class PelaporAdapter extends RecyclerView.Adapter<PelaporAdapter.PersonViewHolder>{

    private List<Pelapor> pelaporList;
    public static int posisi;

    public PelaporAdapter(List<Pelapor> users){
        this.pelaporList = users;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView foto;
        TextView nama;
        TextView id;
        TextView alamat;
        TextView langganan;

        PersonViewHolder(View itemView) {
            super(itemView);
           /* cv          = (CardView) itemView.findViewById(R.jumlah_korban.card_view);
            foto        = (ImageView)itemView.findViewById(R.jumlah_korban.foto_user);
            nama        = (TextView) itemView.findViewById(R.jumlah_korban.nama_user);
            jumlah_korban          = (TextView) itemView.findViewById(R.jumlah_korban.id_user);
            luka_berat        = (TextView) itemView.findViewById(R.jumlah_korban.phone_user);
            status_img  = (ImageView)itemView.findViewById(R.jumlah_korban.aktif);
            alamat      = (TextView) itemView.findViewById(R.jumlah_korban.rumah_user);
            luka_ringan   = (TextView) itemView.findViewById(R.jumlah_korban.langganan_user);
*/
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
/*
        int j=0;
        String id_user="ID | " + pelaporList.get(i).getId();
        personViewHolder.foto.setImageBitmap(pelaporList.get(i).getFoto());
        personViewHolder.nama.setText(pelaporList.get(i).getNama());
        personViewHolder.jumlah_korban.setText(id_user);
        personViewHolder.luka_berat.setText(pelaporList.get(i).getPhone());

        if(pelaporList.get(i).isAktif()){
            personViewHolder.status_img.setImageResource(R.drawable.ic_check_circle_green_24dp);
        }else{
            personViewHolder.status_img.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }
        personViewHolder.alamat.setText(pelaporList.get(i).getAlamat());
        personViewHolder.luka_ringan.setText(String.valueOf(pelaporList.get(i).getLangganan()));*/
    }

    @Override
    public int getItemCount() {
        return pelaporList.size();
    }
}

