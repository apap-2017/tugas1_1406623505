package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.PendudukModel;


@Service
public interface PendudukService {

	PendudukModel selectPenduduk (String nik);

	List<PendudukModel> selectPendudukByNKK(String nkk);
	
	void addPenduduk(String nik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, String golongan_darah, String agama, String status_perkawinan, String pekerjaan, String is_wni, String is_wafat, String id_keluarga, String status_dalam_keluarga);

	String getLastUrutanPenduduk(String minNik, String maxNik);

	void updatePenduduk(String nik, String newnik, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin,
			String golongan_darah, String agama, String status_perkawinan, String pekerjaan, String is_wni,
			String is_wafat, String id_keluarga, String status_dalam_keluarga);

	void updateKematianPenduduk(int status_kematian, String nik);

}
