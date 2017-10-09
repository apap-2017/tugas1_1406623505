package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.KecamatanModel;

@Service
public interface KecamatanService {
	KecamatanModel selectKecamatan(int id_kecamatan);
}
