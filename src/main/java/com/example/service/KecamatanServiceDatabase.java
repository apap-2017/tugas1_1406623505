package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService
{

	@Autowired
	private PendudukMapper pendudukMapper;



	@Override
	public KecamatanModel selectKecamatan(int id_kecamatan) {
		// TODO Auto-generated method stub
		KecamatanModel asd = pendudukMapper.selectKecamatan(id_kecamatan);
        //System.out.println(asd.getNama());
		return asd;
	}

}
