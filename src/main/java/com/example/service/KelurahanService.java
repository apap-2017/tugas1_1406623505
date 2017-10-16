package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.KelurahanModel;

@Service
public interface KelurahanService {

	KelurahanModel selectKelurahan(String id_kelurahan);
	
	KelurahanModel selectKelurahanByName(String name);
	
	List <KelurahanModel> selectKelurahanAll();
}
