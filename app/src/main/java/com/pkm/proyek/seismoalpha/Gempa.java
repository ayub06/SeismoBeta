package com.pkm.proyek.seismoalpha;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Shalahudin Al Ayyub on 26/03/2016.
 */
public class Gempa {
    public static ArrayList<Gempa> gempaArrayList;

    private int idGempa;

    private String nama;
    private LatLng pusat;
    private Calendar waktu;
    private double sr;

    private int total_korban;
    private int total_luka_ringan;
    private int total_luka_berat;

    private int total_rusak_berat;
    private int total_rusak_ringan;

    public Calendar getWaktu() {
        return waktu;
    }

    public void setWaktu(Calendar waktu) {
        this.waktu = waktu;
    }

    public Gempa(int idGempa, String nama, LatLng pusat, Calendar waktu, double sr, int total_korban, int total_luka_ringan, int total_luka_berat, int total_rusak_berat, int total_rusak_ringan) {
        this.idGempa = idGempa;
        this.nama = nama;
        this.pusat = pusat;
        this.waktu = waktu;
        this.sr = sr;
        this.total_korban = total_korban;
        this.total_luka_ringan = total_luka_ringan;
        this.total_luka_berat = total_luka_berat;
        this.total_rusak_berat = total_rusak_berat;
        this.total_rusak_ringan = total_rusak_ringan;
    }

    public int getIdGempa() {
        return idGempa;
    }

    public void setIdGempa(int idGempa) {
        this.idGempa = idGempa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LatLng getPusat() {
        return pusat;
    }

    public void setPusat(LatLng pusat) {
        this.pusat = pusat;
    }

    public double getSr() {
        return sr;
    }

    public void setSr(double sr) {
        this.sr = sr;
    }

    public int getTotal_korban() {
        return total_korban;
    }

    public void setTotal_korban(int total_korban) {
        this.total_korban = total_korban;
    }

    public int getTotal_luka_ringan() {
        return total_luka_ringan;
    }

    public void setTotal_luka_ringan(int total_luka_ringan) {
        this.total_luka_ringan = total_luka_ringan;
    }

    public int getTotal_luka_berat() {
        return total_luka_berat;
    }

    public void setTotal_luka_berat(int total_luka_berat) {
        this.total_luka_berat = total_luka_berat;
    }

    public int getTotal_rusak_berat() {
        return total_rusak_berat;
    }

    public void setTotal_rusak_berat(int total_rusak_berat) {
        this.total_rusak_berat = total_rusak_berat;
    }

    public int getTotal_rusak_ringan() {
        return total_rusak_ringan;
    }

    public void setTotal_rusak_ringan(int total_rusak_ringan) {
        this.total_rusak_ringan = total_rusak_ringan;
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
