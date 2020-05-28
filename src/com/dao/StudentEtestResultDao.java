package com.dao;

import java.util.List;

import com.entities.StudentEtestResult;

public interface StudentEtestResultDao {
	void createStudentEtestResultTable(String tableName);
	void insertStudentEtestResult(StudentEtestResult ser,String tableName);
	StudentEtestResult selectStudentEtestResult(int id);
	List<StudentEtestResult> selectAllStudentEtestResult();
	void deleteStudentEtestResult(int id);
	void updateStudentEtestResult(StudentEtestResult ser,int id);
}
