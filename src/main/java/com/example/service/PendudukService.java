package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.PendudukModel;


@Service
public interface PendudukService {

	PendudukModel selectPenduduk (String nik);

	List<PendudukModel> selectPendudukByNKK(String nkk);
}
