package com.pkm.seismosense.backend;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class Laporan {
    @Id
    Long id;

    private String gempaId;
    private String usernamePelapor;
    private Double lokasi_Lat;
    private Double lokasi_Lng;
    private String alamat;


    private int jumlah_korban;
    private int luka_berat;
    private int luka_ringan;

    private int rusak_berat;
    private int rusak_ringan;

    private Blob foto;

    public Laporan() {
    }

    public Laporan(String gempaId, String usernamePelapor, Double lokasi_Lat, Double lokasi_Lng, String alamat, int jumlah_korban, int luka_berat, int luka_ringan, int rusak_berat, int rusak_ringan) {
        this.gempaId = gempaId;
        this.usernamePelapor = usernamePelapor;
        this.lokasi_Lat = lokasi_Lat;
        this.lokasi_Lng = lokasi_Lng;
        this.alamat=alamat;
        this.jumlah_korban = jumlah_korban;
        this.luka_berat = luka_berat;
        this.luka_ringan = luka_ringan;
        this.rusak_berat = rusak_berat;
        this.rusak_ringan = rusak_ringan;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGempaId() {
        return gempaId;
    }

    public void setGempaId(String gempaId) {
        this.gempaId = gempaId;
    }

    public String getUsernamePelapor() {
        return usernamePelapor;
    }

    public void setUsernamePelapor(String usernamePelapor) {
        this.usernamePelapor = usernamePelapor;
    }
    public Double getLokasi_Lat() {
        return lokasi_Lat;
    }

    public void setLokasi_Lat(Double lokasi_Lat) {
        this.lokasi_Lat = lokasi_Lat;
    }

    public Double getLokasi_Lng() {
        return lokasi_Lng;
    }

    public void setLokasi_Lng(Double lokasi_Lng) {
        this.lokasi_Lng = lokasi_Lng;
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
}
