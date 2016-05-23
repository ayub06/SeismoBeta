package com.pkm.seismosense.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Shalahudin Al Ayyub on 09/05/2016.
 */

@Entity
public class Korban {
    @Id
    private Long id;        //Time

    @Index private Long idGempa;
    @Index private String kategori;
    @Index private String username;
    private Double lokasi_Lat;
    private Double lokasi_Lng;
    private String alamat;

    private int anak;
    private int dewasa;
    private int lansia;

    private int anakp;
    private int dewasap;
    private int lansiap;
    private int hamil;

    public Korban() {
    }

    public Long getId() {
        return id;
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

    public int getAnak() {
        return anak;
    }

    public void setAnak(int anak) {
        this.anak = anak;
    }

    public int getDewasa() {
        return dewasa;
    }

    public void setDewasa(int dewasa) {
        this.dewasa = dewasa;
    }

    public int getLansia() {
        return lansia;
    }

    public void setLansia(int lansia) {
        this.lansia = lansia;
    }

    public int getAnakp() {
        return anakp;
    }

    public void setAnakp(int anakp) {
        this.anakp = anakp;
    }

    public int getDewasap() {
        return dewasap;
    }

    public void setDewasap(int dewasap) {
        this.dewasap = dewasap;
    }

    public int getLansiap() {
        return lansiap;
    }

    public void setLansiap(int lansiap) {
        this.lansiap = lansiap;
    }

    public int getHamil() {
        return hamil;
    }

    public void setHamil(int hamil) {
        this.hamil = hamil;
    }
}
