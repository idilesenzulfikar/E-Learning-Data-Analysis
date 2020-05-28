package com.dao;

import java.util.List;

import com.entities.StudentEtestResult;
import com.entities.TMG;

public interface TMGDao {
	void createTMGTable(String tableName);
	void insertTMG(TMG tmg,String tableName);
	TMG selectTMG(int id);
	List<TMG> selectAllTMG(String tableName);
	void deleteTMG(int id);
	void updateTMG(TMG tmg,int id);
}
