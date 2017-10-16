package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;


@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	KelurahanService kelurahanDAO;
	@Autowired
	KecamatanService kecamatanDAO;
	@Autowired
	KotaService kotaDAO;
	/*
	@RequestMapping("/penduduk")
    public String penduduk ()
    {
        PendudukModel penduduk = new PendudukModel();
		return "get-penduduk-by-nik";
    }
	*/
	@RequestMapping("/penduduk")
    public String getPenduduk (Model model, @RequestParam(value = "nik") String nik )
    {
        //System.out.println(nik);
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		System.out.println(penduduk.getId_keluarga());
		
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
		
		if (penduduk.getIs_wni().equals("0"))
		{
			penduduk.setIs_wni("Bukan WNI");
			
		}
		else
		{
			penduduk.setIs_wni("WNI");
		}
		
		if (penduduk.getIs_wafat().equals("0"))
		{
			penduduk.setIs_wafat("Hidup");
		}
		else
		{
			penduduk.setIs_wafat("Wafat");
		}
		
		model.addAttribute ("penduduk", penduduk);
		model.addAttribute ("keluarga", keluarga);
		model.addAttribute ("kelurahan", kelurahan);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kota", kota);
		
        return "detil-penduduk";
    }
	
	@RequestMapping("/keluarga")
	public String getPendudukByKeluarga(Model model, @RequestParam(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		List <PendudukModel> penduduks = pendudukDAO.selectPendudukByNKK(nkk);
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute ("kelurahan", kelurahan);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kota", kota);
		model.addAttribute("penduduks", penduduks);
		model.addAttribute("keluarga", keluarga);
		return "select-penduduk-by-nkk";
	}
	
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga (Model model)
    {
		List <KelurahanModel> kelurahans = kelurahanDAO.selectKelurahanAll();
		
		model.addAttribute("kelurahans", kelurahans);
		System.out.println(kelurahans.get(0).getNama_kelurahan());
		return "add-keluarga";
    }
	
	@RequestMapping(value="/keluarga/tambah/submit" , method = RequestMethod.POST)
    public String addKeluargaSubmit (
    		Model model, @RequestParam
    			(value = "alamat") String alamat,
    			@RequestParam (value = "rt") String rt,
    			@RequestParam (value = "rw") String rw,
    			@RequestParam (value = "kelurahan") String kelurahan
    			//,@RequestParam (value = "kecamatan") String kecamatan,
    			//@RequestParam (value = "kota") String kota
    			) 
    {
		
		
		KelurahanModel dataKelurahan = kelurahanDAO.selectKelurahanByName(kelurahan);
		KecamatanModel dataKecamatan = kecamatanDAO.selectKecamatan(dataKelurahan.getId_kecamatan());
		
		
		Date currentDate = new Date();
		//System.out.println("bb " + currentDate);
		String stringDate = constructTanggal(currentDate);
		String id_kelurahan = dataKelurahan.getKode_kelurahan();
		//System.out.println("cc" + stringDate);
		String nkk = constructNkk(id_kelurahan, stringDate);
		//System.out.println("bb" + id_kelurahan);
		keluargaDAO.insertKeluarga(nkk, alamat, rt, rw, dataKelurahan.getId(), 0);
		//System.out.println("debug");
		model.addAttribute("title", "Success Add Keluarga");
		model.addAttribute("message", "Keluarga dengan NKK " + nkk + " berhasil ditambahkan");
		
		return "add-keluarga-success";
    }
	
	private String constructTanggal(Date tanggal)
	{
		return new SimpleDateFormat("dd-MM-yyyy").format(tanggal).replace("-", "");
	}
	/**
	 * Controller Tambah Penduduk 
	 */
	@RequestMapping("/penduduk/tambah")
    public String add (Model model)
    {
        List <KeluargaModel> keluargas = keluargaDAO.selectKeluargaAll();
		
        model.addAttribute("keluargas", keluargas);
        return "add-penduduk";
    }
	
	@RequestMapping("/penduduk/update")
	public String updatePenduduk (Model model, @RequestParam(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		
        model.addAttribute("penduduk", penduduk);
        if (penduduk.getIs_wni().equals("1"))
        {
        	String kwn = "indonesia";
        	model.addAttribute("kewarganegaraan", kwn);
            
        }
        else
        {
        	String kwn = "asing";
        	model.addAttribute("kewarganegaraan", kwn);
        }
        
        if (penduduk.getIs_wafat().equals("1"))
        {
        	model.addAttribute("is_wafat", "Wafat");
            
        }
        else
        {
        	model.addAttribute("is_wafat", "Hidup");
            
        }
        return "update-penduduk";
    }
	
	@RequestMapping("/penduduk/tambah/submit")
    public String addSubmit (
    		Model model, @RequestParam
    			(value = "nama") String nama,
    			@RequestParam (value = "tempat_lahir") String tempat_lahir,
    			@RequestParam (value = "tanggal_lahir") String tanggal_lahir,
    			@RequestParam (value = "golongan_darah") String golongan_darah,
    			@RequestParam (value = "agama") String agama,
    			@RequestParam (value = "status_perkawinan") String status_perkawinan,
    			@RequestParam (value = "pekerjaan") String pekerjaan,
    			@RequestParam (value = "kewarganegaraan") String kewarganegaraan,
    			@RequestParam (value = "status_kematian") String status_kematian,
    			@RequestParam (value = "nkk") String nkk,
    			@RequestParam (value = "status_dalam_keluarga") String status_dalam_keluarga
    		)
    
    
    {
		String is_wafat;
		String is_wni;
		String id_keluarga = nkk;
		
		if (status_kematian.equalsIgnoreCase("hidup"))
		{
			is_wafat = "0";
		}
		else
		{
			is_wafat = "1";
		}
		
		if (kewarganegaraan.equalsIgnoreCase("indonesia"))
		{
			is_wni = "1";
			
		}
		else
		{
			is_wni = "0";
		}
		
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		
		KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
		
		int jenis_kelamin = 1;
		//int kode1 = kecamatan.getId_kota();
		int kode2 = kelurahan.getId_kecamatan();
		
		String nik = constructNik(kecamatan.getKode_kecamatan(),tanggal_lahir, jenis_kelamin);
		model.addAttribute("nik", nik);
		System.out.println(nik);
		pendudukDAO.addPenduduk(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin,  golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga);
		return "add-penduduk-success";
    }
	
	/**
	 * 
	 * Constructor NKK dan NIK
	 */
	private String constructNkk(String kodeDaerah, String tanggal) {
		String minNkk = kodeDaerah.substring(0, 6) + tanggal.substring(0, 4) + tanggal.substring(tanggal.length()-2, tanggal.length()) + "0001";
		String maxNkk = String.valueOf(Long.parseLong(minNkk)+1000);
		String lastNoUrutNkk = keluargaDAO.getLastUrutanKeluarga(minNkk, maxNkk);
		System.out.println("asd " + lastNoUrutNkk);
		if(lastNoUrutNkk == null) {
			return minNkk;
		} else {
			return String.valueOf(Long.parseLong(lastNoUrutNkk)+1);
		}
	}
	
	private String constructNik(String kodeDaerah, String tanggal, int jenis_kelamin) {
		String newTanggal = "";
		if(jenis_kelamin == 1) {
			newTanggal = String.valueOf(Integer.parseInt(tanggal.substring(0,2))+40) + tanggal.substring(2, tanggal.length());			
		} else {
			newTanggal = tanggal;
		}
		String minNik = kodeDaerah.substring(0, 6) + newTanggal.substring(0, 4) + newTanggal.substring(newTanggal.length()-2, newTanggal.length()) + "0001";
		String maxNik = String.valueOf(Long.parseLong(minNik)+999);
		String lastNoUrutNik = pendudukDAO.getLastUrutanPenduduk(minNik, maxNik);
		if(lastNoUrutNik == null) {
			return minNik;
		} else {
			return String.valueOf(Long.parseLong(lastNoUrutNik)+1);
		}
	}
}
