package com.pkm.seismosense.backend;

import java.util.Calendar;

public class Gempa {
    private Long idGempa;

    private String nama;
    private Double pusat_Lat;
    private Double pusat_Lng;
    private Calendar waktu;
    private double sr;

    private int total_korban;
    private int total_luka_ringan;
    private int total_luka_berat;

    private int total_rusak_berat;
    private int total_rusak_ringan;
}
