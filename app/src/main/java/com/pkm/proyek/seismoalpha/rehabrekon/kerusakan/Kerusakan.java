package com.pkm.proyek.seismoalpha.rehabrekon.kerusakan;

import com.pkm.proyek.seismoalpha.rehabrekon.korban.InputKorban;

/**
 * Created by Shalahudin Al Ayyub on 07/05/2016.
 */
public class Kerusakan {
    private String kategori;
    private InputKerusakan fragment;

    private int rusak_berat;
    private int rusak_sedang;
    private int rusak_ringan;

    public Kerusakan(String kategori, InputKerusakan fragment) {
        this.kategori = kategori;
        this.fragment = fragment;
    }

    public void insertData(int rusak_berat, int rusak_sedang, int rusak_ringan) {
        this.rusak_berat = rusak_berat;
        this.rusak_sedang = rusak_sedang;
        this.rusak_ringan = rusak_ringan;
    }

    public String getKategori() {
        return kategori;
    }

    public InputKerusakan getFragment() {
        return fragment;
    }

    public int getRusak_berat() {
        return rusak_berat;
    }

    public int getRusak_sedang() {
        return rusak_sedang;
    }

    public int getRusak_ringan() {
        return rusak_ringan;
    }
}
