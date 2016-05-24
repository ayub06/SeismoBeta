package com.pkm.seismosense.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Shalahudin Al Ayyub on 24/05/2016.
 */

@Entity
public class Bantuan {
    @Id
    private Long id;
    private String nama;
    private String alamat;
    private String jenis;
    private String kuantitas;
    private String sumber;
    private String keterangan;

    public Bantuan(Long id, String nama, String alamat, String jenis, String kuantitas, String sumber, String keterangan) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis = jenis;
        this.kuantitas = kuantitas;
        this.sumber = sumber;
        this.keterangan = keterangan;
    }

    public Bantuan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(String kuantitas) {
        this.kuantitas = kuantitas;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
