/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trainticketing;
import java.sql.Date;
/**
 *
 * @author Nandavahindra
 */
public class trainData {

    private Integer id_kereta;
    private String nama_kereta;
    private String asal;
    private String tujuan;
    private String jam_berangkat;
    private String jam_tiba;
    private Integer harga;
    private Date tanggal;
    
    public trainData(Integer id, String nama, String asal, String tujuan, String jamBerangkat, String jamTiba, Integer Harga, Date tanggal)
    {
        this.id_kereta = id;
        this.nama_kereta = nama;
        this.asal = asal;
        this.tujuan = tujuan;
        this.jam_berangkat = jamBerangkat;
        this.jam_tiba = jamTiba;
        this.harga = Harga;
        this.tanggal = tanggal;
    }

    public Integer getId_kereta() {
        return id_kereta;
    }


    public String getNama_kereta() {
        return nama_kereta;
    }

    public String getAsal() {
        return asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getJam_berangkat() {
        return jam_berangkat;
    }

    public String getJam_tiba() {
        return jam_tiba;
    }

    public Integer getHarga() {
        return harga;
    }

    public Date getTanggal() {
        return tanggal;
    }

}
