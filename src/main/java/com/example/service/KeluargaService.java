package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.KeluargaModel;

@Service
public interface KeluargaService {

	KeluargaModel selectKeluarga (String id_keluarga);
}
