package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {

	@Select("select * from penduduk where nik = #{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select * from penduduk where id_keluarga = #{nkk}")
	List <PendudukModel> selectPendudukByNKK (@Param("nkk") String nkk);
	
	@Update("update penduduk SET nik = #{newnik}, nama =#{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, golongan_darah = #{golongan_darah}, agama = #{agama}, status_perkawinan = #{status_perkawinan}, pekerjaan = #{pekerjaan}, is_wni = #{is_wni}, is_wafat = #{is_wafat}, id_keluarga = #{id_keluarga}, status_dalam_keluarga = #{status_dalam_keluarga} WHERE nik = #{nik}")
    void updatePenduduk(@Param("nik") String nik, @Param("newnik") String newnik, @Param("nama") String nama, @Param("tempat_lahir") String tempat_lahir, @Param("tanggal_lahir")String tanggal_lahir, @Param("jenis_kelamin") int jenis_kelamin, @Param("golongan_darah")String golongan_darah, @Param("agama") String agama, @Param("status_perkawinan")String status_perkawinan, @Param("pekerjaan")String pekerjaan, @Param("is_wni")int is_wni, @Param("is_wafat")int is_wafat, @Param("id_keluarga") int id_keluarga, @Param("status_dalam_keluarga") String status_dalam_keluarga);
	
	@Update("update penduduk SET is_wafat = #{status_kematian} where nik = #{nik}")
	void updateKematianPenduduk(@Param("status_kematian")int status_kematian, @Param("nik") String nik);
	
	@Select("select * from keluarga where id = #{id_keluarga}")
	KeluargaModel selectKeluarga (@Param("id_keluarga") String id_keluarga);
	
	@Select("select * from keluarga")
	List <KeluargaModel> selectKeluargaAll();
	
	@Select("select * from kecamatan where id = #{id_kecamatan}")
	KecamatanModel selectKecamatan (@Param("id_kecamatan") int id_kecamaatan);

	@Select("select * from kelurahan where id = #{id_kelurahan}")
	KelurahanModel selectKelurahan (@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select * from kelurahan")
	List <KelurahanModel> selectKelurahanAll();
	
	
	
	@Select("select * from kota where id = #{id_kota}")
	KotaModel selectKota (@Param("id_kota") String id_kota);
	
	@Select("select * from kota where id = #{id_kota}")
	KotaModel selectKotaByName (@Param("id_kota") String id_kota);
	
	
	@Select("SELECT nomor_kk FROM keluarga WHERE nomor_kk BETWEEN #{minNkk} AND #{maxNkk}"
			+ "ORDER BY nomor_kk DESC LIMIT 1")
	String selectLastUrutanKeluarga(@Param("minNkk") String minNkk, @Param("maxNkk") String maxNkk);

	@Select("SELECT nik FROM penduduk WHERE nik BETWEEN #{minNik} AND #{maxNik}"
			+ "ORDER BY nik DESC LIMIT 1")
	String selectLastUrutanPenduduk(@Param("minNik") String minNik, @Param("maxNik") String maxNik);

	@Select("SELECT * from kelurahan where nama_kelurahan = #{namaKelurahan}")
	KelurahanModel selectKelurahanByName(@Param("namaKelurahan")String namaKelurahan);

	
	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga) "
			+ "VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{golongan_darah}, #{agama}, #{status_perkawinan}, #{pekerjaan}, #{is_wni}, #{is_wafat}, #{id_keluarga}, #{status_dalam_keluarga})")
    void addPenduduk (@Param("nik") String nik, @Param("nama") String nama, @Param("tempat_lahir") String tempat_lahir, @Param("tanggal_lahir")String tanggal_lahir, @Param("jenis_kelamin") int jenis_kelamin, @Param("golongan_darah")String golongan_darah, @Param("agama") String agama, @Param("status_perkawinan")String status_perkawinan, @Param("pekerjaan")String pekerjaan, @Param("is_wni")int is_wni, @Param("is_wafat")int is_wafat, @Param("id_keluarga") int id_keluarga, @Param("status_dalam_keluarga") String status_dalam_keluarga);
	
	@Insert("INSERT INTO keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES (#{nkk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{i})")
	void insertKeluarga(@Param("nkk")long nkk, @Param("alamat")String alamat, @Param("rt")int rt, @Param("rw")int rw, @Param("id_kelurahan") long id_kelurahan, @Param("i")int i);

	
}
