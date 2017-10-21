package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/")
	public String index()
	{
		
		return "index";
	}
	/**
	 * Fitur 7
	 */
	@RequestMapping(value = "/penduduk/{nik}", method = RequestMethod.GET)
    public String ubahKematianPenduduk (@PathVariable (value= "nik") String nik, Model model)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		
        model.addAttribute("penduduk", penduduk);
        return "mati-penduduk";
    }
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
    public String submitUbahKematianPenduduk (Model model,
    		@RequestParam(value = "nik") String nik,
    		@RequestParam(value = "status_kematian") String status_kematian_string
    		)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		String id_keluarga = penduduk.getId_keluarga();
		List <PendudukModel> penduduks = pendudukDAO.selectPendudukByNKK(id_keluarga);
        int is_tidak_berlaku = 0;
        
        
		int status_kematian = Integer.parseInt(status_kematian_string);
        model.addAttribute("penduduk", penduduk);
        pendudukDAO.updateKematianPenduduk(status_kematian, nik);
        
        int jumlah_wafat = 0;
        for (int i = 0; i < penduduks.size(); i++)
        {
        	System.out.println("yey = " + penduduks.size());
            
        	
        	if (penduduks.get(i).getIs_wafat().equalsIgnoreCase("1"))
        	{
        		
        		
        		jumlah_wafat++;
        		System.out.println(jumlah_wafat);
    			
        		if (jumlah_wafat == penduduks.size())
        		{
        			
        			is_tidak_berlaku = 1;
        		}
        	}
        }
		
        System.out.println("Tidak berlaku  = " + is_tidak_berlaku);
        if (is_tidak_berlaku == 1)
        {
        	keluargaDAO.updateKematianKeluarga(is_tidak_berlaku, Integer.parseInt(keluargaDAO.selectKeluarga(penduduk.getId_keluarga()).getId()));
        }
        else
        {
        	keluargaDAO.updateKematianKeluarga(0, Integer.parseInt(keluargaDAO.selectKeluarga(penduduk.getId_keluarga()).getId()));
        }
        return "mati-penduduk-success";
    }
	
	
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
	/**
	 *	Fitur 2 
	 */
	@RequestMapping("/keluarga")
	public String getPendudukByKeluarga(Model model, @RequestParam(value = "nkk") String nkk)
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nkk);
		List <PendudukModel> penduduks = pendudukDAO.selectPendudukByNKK(keluarga.getId());
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
	
	
	
	/**
	 * 
	 * Fitur 1
	 */
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
    			@RequestParam (value = "status_dalam_keluarga") String status_dalam_keluarga,
    			@RequestParam (value = "jenis_kelamin") String jenis_kelamin_string
    		)
    
    
    {
		String is_wafat;
		String is_wni;
		String id_keluarga = nkk;
		System.out.println("ini jenis kelamin" + jenis_kelamin_string);
		
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
		
		int jenis_kelamin = Integer.parseInt(jenis_kelamin_string);
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
	 * Fitur 5
	 */
	
	@RequestMapping("/penduduk/ubah")
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
	@RequestMapping(value="/penduduk/ubah/{nik}", method=RequestMethod.POST)
    public String updatePendudukSubmit (@PathVariable(value = "nik") String nik,
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
		String newnik;
		if (id_keluarga.equalsIgnoreCase(pendudukDAO.selectPenduduk(nik).getId_keluarga()) 
				&& tanggal_lahir.equalsIgnoreCase(pendudukDAO.selectPenduduk(nik).getTanggal_lahir())
				)
		{
			newnik = nik;
		}
		else
		{	
			newnik = constructNik(kecamatan.getKode_kecamatan(),tanggal_lahir, jenis_kelamin);
		}
		model.addAttribute("nik", newnik);
		System.out.println(newnik);
		pendudukDAO.updatePenduduk(nik, newnik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin,  golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga);
		return "update-penduduk-success";
    }
	
	/**
	 * 
	 * Fitur 6
	 */
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method=RequestMethod.GET)
	public String updateKeluarga (@PathVariable(value = "nkk") String nkk, 
			Model model)
    {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nkk);
		List <KelurahanModel> kelurahans = kelurahanDAO.selectKelurahanAll();
        
        
		model.addAttribute("keluarga", keluarga);
        model.addAttribute("kelurahans", kelurahans);
        
        return "update-keluarga";
    }
	@RequestMapping(value="/keluarga/ubah/{nkk}", method=RequestMethod.POST)
    public String updateKeluargaSubmit (@PathVariable(value = "nkk") String nkk,
    		Model model, 
    			@RequestParam (value = "alamat") String alamat,
    			@RequestParam (value = "rt") int rt,
    			@RequestParam (value = "rw") int rw,
    			@RequestParam (value = "kelurahan") String id_kelurahan
    		)
    
    
    {
		System.out.println("aaaa " + id_kelurahan);
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nkk);
		KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(id_kelurahan);
		KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
		
		KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
		//int kode1 = kecamatan.getId_kota();
		System.out.println("zz " + id_kelurahan);
		System.out.println("xx " + keluarga.getId_kelurahan());
		
		
		String newnkk = nkk;
		List <PendudukModel> penduduks = pendudukDAO.selectPendudukByNKK(keluarga.getId());
		if (id_kelurahan.equals(keluarga.getId_kelurahan()) ==false)
		{
			String stringDate = nkk.substring(6, 12);
			newnkk = constructNkk(kecamatan.getKode_kecamatan(), stringDate);
			System.out.println("nnnnn " + stringDate);
			
			System.out.println("pre bikin nik");
			for (int i = 0; i < penduduks.size(); i++)
			{
				System.out.println("bikin nik");
				String newnik = constructNik(kecamatan.getKode_kecamatan(),penduduks.get(i).getTanggal_lahir(), penduduks.get(i).getJenis_kelamin());
				pendudukDAO.updatePenduduk(penduduks.get(i).getNik(),
						newnik,
						penduduks.get(i).getNama(), 
						penduduks.get(i).getTempat_lahir(),
						penduduks.get(i).getTanggal_lahir(), 
						penduduks.get(i).getJenis_kelamin(), 
						penduduks.get(i).getGolongan_darah(), 
						penduduks.get(i).getAgama(), 
						penduduks.get(i).getStatus_perkawinan(), 
						penduduks.get(i).getPekerjaan(), 
						penduduks.get(i).getIs_wni(), 
						penduduks.get(i).getIs_wafat(), 
						penduduks.get(i).getId_keluarga(), 
						penduduks.get(i).getStatus_dalam_keluarga());
				
			}
		}
		
		keluargaDAO.updateKeluarga(alamat, rt, rw, id_kelurahan, newnkk, nkk);
		model.addAttribute("nkk", nkk);
		return "update-keluarga-success";
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
	
	/**
	 * 
	 * Fitur 8
	 */
	
	@RequestMapping("/penduduk/cari")
	private String searchPenduduk(Model model, 
			@RequestParam(value = "id_kota", required = false) String id_kota,
			@RequestParam(value = "id_kecamatan", required = false) String id_kecamatan,
			@RequestParam(value = "id_kelurahan", required = false) String id_kelurahan
			)
	{
			List <KotaModel> kotas = kotaDAO.selectKotaAll();
			model.addAttribute("kotas", kotas);
			if (id_kota == null)
			{
				model.addAttribute("id_kota", null);
			}
			else
			{
				List <KecamatanModel> kecamatans = kecamatanDAO.selectKecamatanByKota(id_kota);
				model.addAttribute("id_kota", id_kota);
				model.addAttribute("kecamatans", kecamatans);
			}
			
			if (id_kecamatan == null)
			{
				model.addAttribute("id_kecamatan", id_kecamatan);
			}
			else
			{
				List <KelurahanModel> kelurahans = kelurahanDAO.selectKelurahanAllByKecamatan(id_kecamatan);
				model.addAttribute("kelurahans", kelurahans);
				model.addAttribute("id_kecamatan", id_kecamatan);
			}
			
			if (id_kota != null && id_kecamatan != null && id_kelurahan != null)
			{
				//List <KeluargaModel> keluargas = keluargaDAO.selecKeluargaByKelurahan(id_kelurahan);
				
				List <PendudukModel> penduduks = pendudukDAO.selectPendudukByKelurahan(id_kelurahan);
				
				/**
				List <PendudukModel> penduduks = pendudukDAO.selectPendudukNIKNamaJenisKelaminByNKK(keluargas.get(0).getId());
				
				for(int i = 1; i < keluargas.size(); i++)
				{
					//List <PendudukModel> a = pendudukDAO.selectPendudukNIKNamaJenisKelaminByNKK(keluargas.get(i).getId());
					
					
					/**
					for (int j = 0; j < a.size(); j++)
					{
						//penduduks.add(a.get(j));
						//System.out.println(a.get(j).getNama());
					}
					
				}
				*/
				model.addAttribute("penduduks", penduduks);
				return "cari-penduduk-success";
			}
			
		/**
		if (id_kecamatan != null)
		{
			return "cari-penduduk-disabled-disabled";
		}
		if (id_kota != null)
		{
			return "cari-penduduk-disabled";
		}
		*/
		return "cari-penduduk";
	}
}
