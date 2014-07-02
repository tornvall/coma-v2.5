package com.coma.client;

import java.util.ArrayList;
import java.util.List;

import java_cup.internal_error;

import com.coma.v2.*;
import com.coma.client.*;
import com.coma.client.classes.Benefit;
import com.coma.client.classes.ProblemClass;
import com.coma.client.classes.ProblemImpact;
import com.coma.client.classes.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseConnection extends RemoteService {
	void createNewUser(String name, String password) throws IllegalArgumentException;
	int createNewGroup(int userID, String groupName);
	int getUserID(String email);
	void addVoteToModel(int userID, int modelID, int index);
	List<ModelInfo> getAllModelsFromSpecificGroupThatIsProposed(int activeGroup)
			throws IllegalArgumentException;
	ModelInfo loadModel(int modelID);
	ModelInfo loadGroupModel(int groupID);
	void addCommentToModel(int userID, int modelID, String comment);
	List<String> getCommentsOnModel(int modelID);
	void inviteToGroup(int groupID, int userID);
	WorkGroupInfo getGroupInfo(int activeGroupID);
	List<WorkGroupInfo> getGroupInvites(int userId);
	void setInviteToInactive(int inviteID);
	void addUserToGroup(int groupID, int userId);
	void addUserProfileToUser(int userID, String firstName, String surName, String birthday, String phoneNumber);
	List<String> getUserProfile(int userID) throws IllegalArgumentException;
	List<WorkGroupInfo> getUsersGroups(int userID);
	List<ModelInfo> getAllUsersModels(int userID);
	void updateActiveGroupModel(int activeGroupID, int modelID, int version);
	List<String> getAllGroupMembers(int activeGroupID);
	String getModelCreatorName(int modelID);
	List<Integer> getModelIDs(int groupID);
	List<ProposalAvgVote> getVotes(List<Integer> modelIDs);
	
	void saveModel(int groupID, int userID, String modelName, int modelType, String modelString, int isProposal);
	void saveModelToActiveGroup(int activeGroupID, int modelID, String modelString, int version);
	User getUser(String email);
	User getUser(int ID);
	List<Benefit> getAllBenefits() throws IllegalArgumentException;
	void createNewBenefit(String description);
	void updateBenefitSelection(int groupID,int modelID, List<Integer> benefits);
	List<Integer> getBenefitSelection(int groupID, int modelID);
	List<ProblemImpact> getProblemImpacts(int problemId);
	void createNewProblemImpact(int problemID, int benefitID, String impact);
	void createNewProblem(int userID, int groupID, int activegroupModelID, ProblemClass problem);
	ProblemClass loadProblem(int problemID);
	List<ProblemClass> loadProblemsFromUser(int userID, int groupID, int activegroupModelID);
}
