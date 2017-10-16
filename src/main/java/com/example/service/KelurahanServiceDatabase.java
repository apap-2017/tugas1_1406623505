package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {

	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public KelurahanModel selectKelurahan(String id_kelurahan) {
		KelurahanModel asd = pendudukMapper.selectKelurahan(id_kelurahan);
        //System.out.println(asd.getNama());
		return asd;
	}

	@Override
	public KelurahanModel selectKelurahanByName(String name) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKelurahanByName(name);
	}

	@Override
	public List<KelurahanModel> selectKelurahanAll() {
		// TODO Auto-generated method stub
		return pendudukMapper.selectKelurahanAll();
	}
}
