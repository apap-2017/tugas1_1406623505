package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
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
}
