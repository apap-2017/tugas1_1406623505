package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
	@Autowired
	private PendudukMapper pendudukMapper;
	

	@Override
	public PendudukModel selectPenduduk(String nik) {
		//System.out.println(nik);
		PendudukModel asd = pendudukMapper.selectPenduduk(nik);
        //System.out.println(asd.getNama());
		return asd;
	}
	
	@Override
	public List<PendudukModel> selectPendudukByNKK(String nkk)
	{
		List<PendudukModel> asd = pendudukMapper.selectPendudukByNKK(nkk);
		return asd;
	}
	
	@Override
	public void addPenduduk(String nik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, String golongan_darah, String agama, String status_perkawinan, String pekerjaan, String is_wni, String is_wafat, String id_keluarga, String status_dalam_keluarga)
	{
		pendudukMapper.addPenduduk(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, golongan_darah, agama, status_perkawinan, pekerjaan, Integer.parseInt(is_wni), Integer.parseInt(is_wafat), Integer.parseInt(id_keluarga), status_dalam_keluarga);
		
	}
	
	@Override
	public String getLastUrutanPenduduk(String minNik, String maxNik) {
		log.info ("get max nik between {} and {}", minNik, maxNik);
        return pendudukMapper.selectLastUrutanPenduduk(minNik, maxNik);
	}

	@Override
	public void updatePenduduk(String nik, String newnik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin,
			String golongan_darah, String agama, String status_perkawinan, String pekerjaan, String is_wni,
			String is_wafat, String id_keluarga, String status_dalam_keluarga) {
		
		pendudukMapper.updatePenduduk(nik, newnik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, golongan_darah, agama, status_perkawinan, pekerjaan, Integer.parseInt(is_wni), Integer.parseInt(is_wafat), Integer.parseInt(id_keluarga), status_dalam_keluarga);
		//System.out.println("WWW " + nik );
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKematianPenduduk(int status_kematian, String nik) {
		pendudukMapper.updateKematianPenduduk(status_kematian, nik);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PendudukModel> selectPendudukNIKNamaJenisKelaminByNKK(String id) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectPendudukNIKNamaJenisKelaminByNKK(id);
	}
}
