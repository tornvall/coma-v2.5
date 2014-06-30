package com.coma.client.classes;

import java.io.Serializable;

public class ProblemImpact implements Serializable {
	private static final long serialVersionUID = 1765950051101731143L;
	
	private static ProblemImpact instance = null;
	private int problemImpactId;
	private int problemId;
	private int benefitId;
	private String benefitName;
	private String impact;
	private Boolean isActive;
	
	public static ProblemImpact getInstance(){		
		if(instance == null){
			instance = new ProblemImpact();
			return instance;
		}else{
			return instance;
		}		
	}
	
	public static void setInstance(ProblemImpact problemImpact){
		instance = problemImpact;		
	}

	public ProblemImpact(){}
	
	public ProblemImpact(int problemImpactId, int problemId, int benefitId, String benefitName, String impact, Boolean isActive){
		this.problemImpactId = problemImpactId;
		this.problemId = problemId;
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
	
}
