package com.entities;

public class StudentEtestResult {
	
	private int id; 
	private String mailAdresse;
	private Double[] eTestResults;
	
	public StudentEtestResult(){
		
	}

	public StudentEtestResult(String mailAdresse,Double[] eTestResults){
		this.mailAdresse = mailAdresse;
		this.eTestResults = eTestResults;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailAdresse() {
		return mailAdresse;
	}

	public void setMailAdresse(String mailAdresse) {
		this.mailAdresse = mailAdresse;
	}

	public Double[] geteTestResults() {
		return eTestResults;
	}

	public void seteTestResults(Double[] eTestResults) {
		this.eTestResults = eTestResults;
	}
}
