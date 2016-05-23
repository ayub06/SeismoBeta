package com.pkm.seismosense.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Shalahudin Al Ayyub on 09/05/2016.
 */

@Entity
public class Kerusakan {
    @Id
    private Long id;        //Time

    @Index private Long idGempa;
    @Index private String kategori;
    @Index private String username;
    private Double lokasi_Lat;
    private Double lokasi_Lng;
    private String alamat;

    private int rusak_berat;
    private int rusak_sedang;
    private int rusak_ringan;


    public Kerusakan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGempa() {
        return idGempa;
    }

    public void setIdGempa(Long idGempa) {
        this.idGempa = idGempa;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getRusak_berat() {
        return rusak_berat;
    }

    public void setRusak_berat(int rusak_berat) {
        this.rusak_berat = rusak_berat;
    }

    public int getRusak_sedang() {
        return rusak_sedang;
    }

    public void setRusak_sedang(int rusak_sedang) {
        this.rusak_sedang = rusak_sedang;
    }

    public int getRusak_ringan() {
        return rusak_ringan;
    }

    public void setRusak_ringan(int rusak_ringan) {
        this.rusak_ringan = rusak_ringan;
    }
}
