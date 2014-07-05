package com.coma.client.models;

import java.io.Serializable;

public class ProblemImpact implements Serializable {
	private static final long serialVersionUID = 1765950051101731143L;
	
	private int problemImpactId = -1;
	private int problemId = -1;
	private int benefitId = -1;
	private String benefitName = "";
	private String impact = "";
	private Boolean isActive = true;
	private int uniqueId = -1;

	public ProblemImpact(){}
	
	public ProblemImpact(int problemImpactId, int problemId, int benefitId, String benefitName, String impact, Boolean isActive){
		this(problemId, benefitId, benefitName, impact, isActive);
		this.problemImpactId = problemImpactId;
	}

	public ProblemImpact(int problemId, int benefitId, String benefitName, String impact, Boolean isActive){
		this(benefitId, benefitName, impact, isActive);
		this.problemId = problemId;
	}
	public ProblemImpact(int benefitId, String benefitName, String impact, Boolean isActive){
		this.benefitId = benefitId;
		this.benefitName = benefitName;
		this.impact = impact;
		this.isActive = isActive;		
	}

	public int getProblemImpactId() {
		return problemImpactId;
	}
	public void setProblemImpactId(int problemImpactId) {
		this.problemImpactId = problemImpactId;
	}
	public int getProblemId() {
		return problemId;
	}
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	public int getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(int benefitId) {
		this.benefitId = benefitId;
	}
	public String getBenefitName() {
		return benefitName;
	}
	public void setBenefitName(String benefitName) {
		this.benefitName = benefitName;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	
}
