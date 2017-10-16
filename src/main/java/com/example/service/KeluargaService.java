package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.KeluargaModel;

@Service
public interface KeluargaService {

	KeluargaModel selectKeluarga (String id_keluarga);
	
	List<KeluargaModel> selectKeluargaAll();

	String getLastUrutanKeluarga(String minNkk, String maxNkk);

	void insertKeluarga(String nkk, String alamat, String rt, String rw, String id_kelurahan, int i);
}
