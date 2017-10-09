package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
	@Autowired
	private PendudukMapper pendudukMapper;
	

	@Override
	public PendudukModel selectPenduduk(String nik) {
		System.out.println(nik);
		PendudukModel asd = pendudukMapper.selectPenduduk(nik);
        //System.out.println(asd.getNama());
		return asd;
	}
	
	public List<PendudukModel> selectPendudukByNKK(String nkk)
	{
		List<PendudukModel> asd = pendudukMapper.selectPendudukByNKK(nkk);
		return asd;
	}
}
