package com.pkm.proyek.seismoalpha.pelapor;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Shalahudin Al Ayyub on 26/03/2016.
 */
public class Pelapor {
    public static ArrayList<Pelapor> pelaporArrayList;
    public static Pelapor akunIni;

    private int id;
    private Bitmap foto;
    private String urlFoto;
    private String username;
    private String password;
    private String nama;
    private String alamat;
    private LatLng lokasi;


    public Pelapor(String urlFoto, int id, String nama, String username) {
        this.urlFoto = urlFoto;
        this.id = id;
        this.nama = nama;
        this.username = username;
    }

    public Pelapor(int id, Bitmap foto, String username, String password, String nama, String alamat, LatLng lokasi) {
        this.id = id;
        this.foto = foto;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.alamat = alamat;
        this.lokasi = lokasi;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public LatLng getLokasi() {
        return lokasi;
    }

    public void setLokasi(LatLng lokasi) {
        this.lokasi = lokasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
