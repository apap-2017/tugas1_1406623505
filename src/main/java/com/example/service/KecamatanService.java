package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.KecamatanModel;

@Service
public interface KecamatanService {
	KecamatanModel selectKecamatan(int id_kecamatan);

	List<KecamatanModel> selectKecamatanByKota(String id_kota);
}
