package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KotaModel;

@Service
public class KotaServiceDatabase implements KotaService {


	@Autowired
	private PendudukMapper pendudukMapper;
	@Override
	public KotaModel selectKota(String id_kota) {
		// TODO Auto-generated method stub
		KotaModel asd = pendudukMapper.selectKota(id_kota);
        //System.out.println(asd.getNama());
		return asd;
	}

	

}
