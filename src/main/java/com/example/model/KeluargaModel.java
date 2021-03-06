package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {

	private String id;
	private String nomor_kk;
	private String alamat;
	private int rt;
	private int rw;
	private String id_kelurahan;
	private int is_tidak_berlaku;
	
}
