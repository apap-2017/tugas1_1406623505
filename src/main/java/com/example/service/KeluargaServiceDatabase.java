package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.service.KeluargaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{

	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String id_keluarga) {
		System.out.println("ini selectkeluarga");
		KeluargaModel asd = pendudukMapper.selectKeluarga(id_keluarga);
        
		return asd;
	}
	
	public KelurahanModel selectKelurahanByName(String namaKelurahan)
	{
		return pendudukMapper.selectKelurahanByName(namaKelurahan);
	}

	@Override
	public List<KeluargaModel> selectKeluargaAll() {
		
		List <KeluargaModel> asd = pendudukMapper.selectKeluargaAll();
		return asd;
	}

	@Override
	public String getLastUrutanKeluarga(String minNkk, String maxNkk) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectLastUrutanKeluarga(minNkk, maxNkk);
		
	}

	@Override
	public void insertKeluarga(String nkk, String alamat, String rt, String rw, String id_kelurahan, int i) {
		System.out.println("cccc" + nkk + "cccc" );	
		pendudukMapper.insertKeluarga(Long.parseLong(nkk), alamat, Integer.parseInt(rt), Integer.parseInt(rw), Long.parseLong(id_kelurahan), i);
		
		
	}

	@Override
	public void updateKematianKeluarga(int is_tidak_berlaku, int id) {
		// TODO Auto-generated method stub
		System.out.println("nnnn " + id);
		pendudukMapper.updateKematiankeluarga(is_tidak_berlaku, id);
	}

	@Override
	public List<KeluargaModel> selecKeluargaByKelurahan(String id_kelurahan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKeluargaByKelurahan(Integer.parseInt(id_kelurahan));
	}

	@Override
	public KeluargaModel selectKeluargaByNkk(String nkk) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKeluargaByNKK(nkk);
	}

	@Override
	public void updateKeluarga(String alamat, int rt, int rw, String id_kelurahan, String newnkk, String nkk) {
		// TODO Auto-generated method stub
		pendudukMapper.updateKeluarga(alamat, rt, rw, Integer.parseInt(id_kelurahan), newnkk, nkk);
	}
}
