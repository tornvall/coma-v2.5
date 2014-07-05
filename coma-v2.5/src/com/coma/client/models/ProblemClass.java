package com.coma.client.models;

import java.io.Serializable;
import java.util.List;

import java_cup.internal_error;

public class ProblemClass  implements Serializable {
	private static final long serialVersionUID = 8427357966262343169L;
	
	private int problemID = -1;
	private String name = "";
	private String description = "";
	private ProblemSeverity severity;
	private ProblemEvolution evolution;
	private ProblemUrgency urgency;
	private ProblemOccurence occurence;
	private String explanation;
	private List<ProblemImpact> problemImpactList;
	private String modelString = "";


	public ProblemClass(){}
	
	public ProblemClass(String name, String description, ProblemSeverity severity, ProblemEvolution evolution, ProblemUrgency urgency, ProblemOccurence occurence, String explanation, List<ProblemImpact> problemImpactList, String modelString){
		this(name, description, severity, evolution, urgency, occurence, explanation, problemImpactList);
		this.modelString = modelString;
	}
	public ProblemClass(String name, String description, ProblemSeverity severity, ProblemEvolution evolution, ProblemUrgency urgency, ProblemOccurence occurence, String explanation, List<ProblemImpact> problemImpactList){
		this.name = name;
		this.description = description;
		this.severity = severity;
		this.evolution = evolution;
		this.urgency = urgency;
		this.occurence = occurence;
		this.explanation = explanation;
		this.problemImpactList = problemImpactList;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProblemSeverity getSeverity() {
		return severity;
	}
	public void setSeverity(ProblemSeverity severity) {
		this.severity = severity;
	}
	public ProblemEvolution getEvolution() {
		return evolution;
	}
	public void setEvolution(ProblemEvolution evolution) {
		this.evolution = evolution;
	}
	public ProblemUrgency getUrgency() {
		return urgency;
	}
	public void setUrgency(ProblemUrgency urgency) {
		this.urgency = urgency;
	}
	public ProblemOccurence getOccurence() {
		return occurence;
	}
	public void setOccurence(ProblemOccurence occurence) {
		this.occurence = occurence;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public List<ProblemImpact> getProblemImpactList() {
		return problemImpactList;
	}
	public void setProblemImpactList(List<ProblemImpact> problemImpactList) {
		this.problemImpactList = problemImpactList;
	}
	public String getModelString() {
		return modelString;
	}
	public void setModelString(String modelString) {
		this.modelString = modelString;
	}
	public int getProblemID() {
		return problemID;
	}
	public void setProblemID(int problemID) {
		this.problemID = problemID;
	}
}
