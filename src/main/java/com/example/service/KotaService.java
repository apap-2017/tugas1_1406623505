package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.KotaModel;

@Service
public interface KotaService {

	KotaModel selectKota(String id_kota);
}
