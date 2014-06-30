package com.coma.client;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.classes.Benefit;
import com.coma.client.classes.ProblemImpact;
import com.coma.client.classes.User;
import com.coma.v2.ModelInfo;
import com.coma.v2.ProposalAvgVote;
import com.coma.v2.WorkGroupInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseConnectionAsync {
	void createNewGroup(int userID, String groupName, AsyncCallback<Integer> callback);
	void createNewUser(String name, String password,
			AsyncCallback<Void> callback);
	void getUserID(String email, AsyncCallback<Integer> asyncCallback);
	void loadGroupModel(int groupID, AsyncCallback<ModelInfo> asyncCallback);
	void loadModel(int modelID, AsyncCallback<ModelInfo> asyncCallback);
	void addVoteToModel(int userID, int modelID, int index, AsyncCallback<Void> asyncCallback);
	void getAllModelsFromSpecificGroupThatIsProposed(int activeGroup, AsyncCallback<List<ModelInfo>> callback);
	void addCommentToModel(int userID, int modelID, String comment,
			AsyncCallback<Void> callback);
	void getCommentsOnModel(int modelID,
			AsyncCallback<List<String>> asyncCallback);	
	void inviteToGroup(int groupID, int userID, AsyncCallback<Void> callback);
	void getGroupInfo(int activeGroupID,
			AsyncCallback<WorkGroupInfo> asyncCallback);
	void getGroupInvites(int userId,
			AsyncCallback<List<WorkGroupInfo>> asyncCallback);
	void setInviteToInactive(int inviteID, AsyncCallback<Void> asyncCallback);
	void addUserToGroup(int groupID, int userId,
			AsyncCallback<Void> asyncCallback);
	void addUserProfileToUser(int userID, String firstName, String surName,
			String birthday, String phoneNumber, AsyncCallback<Void> callback);
	void getUserProfile(int userID, AsyncCallback<List<String>> callback);
	void getUsersGroups(int userID, AsyncCallback<List<WorkGroupInfo>> callback);
	void getAllUsersModels(int userID, AsyncCallback<List<ModelInfo>> callback);
	void updateActiveGroupModel(int activeGroupID, int modelID, int version,
			AsyncCallback<Void> callback);
	void getAllGroupMembers(int activeGroupID,
			AsyncCallback<List<String>> asyncCallback);
	void getModelCreatorName(int modelID, AsyncCallback<String> callback);
	void getModelIDs(int groupID, AsyncCallback<List<Integer>> asyncCallback);
	void getVotes(List<Integer> modelIDs,
			AsyncCallback<List<ProposalAvgVote>> callback);

	void saveModel(int groupID, int userID, String modelName, int modelType, String modelString, int isProposal, AsyncCallback<Void> asyncCallback);
	void saveModelToActiveGroup(int activeGroupID, int modelID, String modelString, int version, AsyncCallback<Void> asyncCallback);
	void getUser(String email, AsyncCallback<User> asyncCallback);
	void getUser(int ID, AsyncCallback<User> asyncCallback);
	void getAllBenefits(AsyncCallback<List<Benefit>> asyncCallback);
	void createNewBenefit(String description, AsyncCallback<Void> callback);	
	void updateBenefitSelection(int groupID,int modelID, List<Integer> benefits, AsyncCallback<Void> callback);
	void getBenefitSelection(int groupID, int modelID, AsyncCallback<List<Integer>> callback); 
	void getProblemImpacts(int problemId, AsyncCallback<List<ProblemImpact>> callback);
}
