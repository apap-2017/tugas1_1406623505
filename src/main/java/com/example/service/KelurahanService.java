package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.KelurahanModel;

@Service
public interface KelurahanService {

	KelurahanModel selectKelurahan(String id_kelurahan);
}
