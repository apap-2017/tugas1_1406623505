package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
