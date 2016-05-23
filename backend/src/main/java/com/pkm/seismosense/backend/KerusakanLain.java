package com.pkm.seismosense.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Shalahudin Al Ayyub on 09/05/2016.
 */

@Entity
public class KerusakanLain {
    @Id
    private Long id;        //Time

    @Index private Long idGempa;
    @Index private String kategori;
    @Index private String username;
    private Double lokasi_Lat;
    private Double lokasi_Lng;
    private String alamat;

    private int jmlRusakKios;
    private int jmlRusakBangunan;
    private int jmlRusakJembatan ;
    private int jmlRusakJalan ;
    private int jmlRusakSawah ;
    private int jmlRusakTebing;
    private int jmlRusakTalud ;
    private int jmlRusakKebun ;
    private int jmlRusakKolam ;
    private int jmlRusakIrigasi;


    public KerusakanLain() {
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

    public int getJmlRusakKios() {
        return jmlRusakKios;
    }

    public void setJmlRusakKios(int jmlRusakKios) {
        this.jmlRusakKios = jmlRusakKios;
    }

    public int getJmlRusakBangunan() {
        return jmlRusakBangunan;
    }

    public void setJmlRusakBangunan(int jmlRusakBangunan) {
        this.jmlRusakBangunan = jmlRusakBangunan;
    }

    public int getJmlRusakJembatan() {
        return jmlRusakJembatan;
    }

    public void setJmlRusakJembatan(int jmlRusakJembatan) {
        this.jmlRusakJembatan = jmlRusakJembatan;
    }

    public int getJmlRusakJalan() {
        return jmlRusakJalan;
    }

    public void setJmlRusakJalan(int jmlRusakJalan) {
        this.jmlRusakJalan = jmlRusakJalan;
    }

    public int getJmlRusakSawah() {
        return jmlRusakSawah;
    }

    public void setJmlRusakSawah(int jmlRusakSawah) {
        this.jmlRusakSawah = jmlRusakSawah;
    }

    public int getJmlRusakTebing() {
        return jmlRusakTebing;
    }

    public void setJmlRusakTebing(int jmlRusakTebing) {
        this.jmlRusakTebing = jmlRusakTebing;
    }

    public int getJmlRusakTalud() {
        return jmlRusakTalud;
    }

    public void setJmlRusakTalud(int jmlRusakTalud) {
        this.jmlRusakTalud = jmlRusakTalud;
    }

    public int getJmlRusakKebun() {
        return jmlRusakKebun;
    }

    public void setJmlRusakKebun(int jmlRusakKebun) {
        this.jmlRusakKebun = jmlRusakKebun;
    }

    public int getJmlRusakKolam() {
        return jmlRusakKolam;
    }

    public void setJmlRusakKolam(int jmlRusakKolam) {
        this.jmlRusakKolam = jmlRusakKolam;
    }

    public int getJmlRusakIrigasi() {
        return jmlRusakIrigasi;
    }

    public void setJmlRusakIrigasi(int jmlRusakIrigasi) {
        this.jmlRusakIrigasi = jmlRusakIrigasi;
    }
}
