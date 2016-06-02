package com.pkm.proyek.seismoalpha.laporan.tim;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.pkm.proyek.seismoalpha.main.Gempa;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Shalahudin Al Ayyub on 26/03/2016.
 */
public class Laporan {
    public static ArrayList<Laporan> laporanArrayList;
    private Long idLaporan;

    private Gempa gempa;
    private Pelapor pelapor;
    private Calendar waktu;
    private LatLng lokasi;
    private String alamat;

    private int jumlah_korban;
    private int luka_berat;
    private int luka_ringan;

    private int rusak_berat;
    private int rusak_ringan;

    private Bitmap foto;

    public Laporan(Long idLaporan, Gempa gempa, Pelapor pelapor, Calendar waktu, LatLng lokasi, String alamat, int jumlah_korban, int luka_berat, int luka_ringan, int rusak_berat, int rusak_ringan,Bitmap foto) {
        this.idLaporan = idLaporan;
        this.gempa = gempa;
        this.pelapor = pelapor;
        this.waktu = waktu;
        this.lokasi = lokasi;
        this.alamat = alamat;
        this.jumlah_korban = jumlah_korban;
        this.luka_berat = luka_berat;
        this.luka_ringan = luka_ringan;
        this.rusak_berat = rusak_berat;
        this.rusak_ringan = rusak_ringan;
        this.foto=foto;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Long getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(Long idLaporan) {
        this.idLaporan = idLaporan;
    }

    public Gempa getGempa() {
        return gempa;
    }

    public void setGempa(Gempa gempa) {
        this.gempa = gempa;
    }

    public Pelapor getPelapor() {
        return pelapor;
    }

    public void setPelapor(Pelapor pelapor) {
        this.pelapor = pelapor;
    }

    public Calendar getWaktu() {
        return waktu;
    }

    public void setWaktu(Calendar waktu) {
        this.waktu = waktu;
    }

    public LatLng getLokasi() {
        return lokasi;
    }

    public void setLokasi(LatLng lokasi) {
        this.lokasi = lokasi;
    }

    public int getJumlah_korban() {
        return jumlah_korban;
    }

    public void setJumlah_korban(int jumlah_korban) {
        this.jumlah_korban = jumlah_korban;
    }

    public int getLuka_berat() {
        return luka_berat;
    }

    public void setLuka_berat(int luka_berat) {
        this.luka_berat = luka_berat;
    }

    public int getLuka_ringan() {
        return luka_ringan;
    }

    public void setLuka_ringan(int luka_ringan) {
        this.luka_ringan = luka_ringan;
    }

    public int getRusak_berat() {
        return rusak_berat;
    }

    public void setRusak_berat(int rusak_berat) {
        this.rusak_berat = rusak_berat;
    }

    public int getRusak_ringan() {
        return rusak_ringan;
    }

    public void setRusak_ringan(int rusak_ringan) {
        this.rusak_ringan = rusak_ringan;
    }

    public String getJam(){
        return this.waktu.get(Calendar.HOUR_OF_DAY)+"."+ this.waktu.get(Calendar.MINUTE);
    }

    public String getTanggal(){
        return this.waktu.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.getDefault())
                +", "+ this.waktu.get(Calendar.DAY_OF_MONTH)
                +" "+ this.waktu.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
                +" "+ this.waktu.get(Calendar.YEAR);
    }

    public String getTanggalSingkat(){
        return this.waktu.get(Calendar.DAY_OF_MONTH)
                +"/"+ this.waktu.get(Calendar.MONTH)
                +"/"+ this.waktu.get(Calendar.YEAR);
    }
}
