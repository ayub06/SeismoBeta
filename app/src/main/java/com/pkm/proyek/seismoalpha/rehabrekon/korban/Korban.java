package com.pkm.proyek.seismoalpha.rehabrekon.korban;

/**
 * Created by Shalahudin Al Ayyub on 07/05/2016.
 */
public class Korban {
    private String kategori;
    private InputKorban fragment;
    private int anak;
    private int dewasa;
    private int lansia;

    private int anakp;
    private int dewasap;
    private int lansiap;
    private int hamil;

    public Korban(String kategori, InputKorban fragment) {
        this.kategori = kategori;
        this.fragment = fragment;
    }

    public void insertData(int anak, int dewasa, int lansia, int anakp, int dewasap, int lansiap, int hamil) {
        this.anak = anak;
        this.dewasa = dewasa;
        this.lansia = lansia;
        this.anakp = anakp;
        this.dewasap = dewasap;
        this.lansiap = lansiap;
        this.hamil = hamil;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public InputKorban getFragment() {
        return fragment;
    }

    public void setFragment(InputKorban fragment) {
        this.fragment = fragment;
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
