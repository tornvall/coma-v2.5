package com.coma.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java_cup.internal_error;

import com.coma.client.*;
import com.coma.client.helpers.*;
import com.coma.client.models.Benefit;
import com.coma.client.models.ProblemClass;
import com.coma.client.models.ProblemEvolution;
import com.coma.client.models.ProblemImpact;
import com.coma.client.models.ProblemOccurence;
import com.coma.client.models.ProblemSeverity;
import com.coma.client.models.ProblemUrgency;
import com.coma.client.models.User;
import com.coma.client.models.UserType;
import com.coma.v2.ModelInfo;
import com.coma.v2.ProposalAvgVote;
import com.coma.v2.WorkGroupInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.view.client.ListDataProvider;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseConnectionImpl extends RemoteServiceServlet implements
DatabaseConnection {
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	private Connection initializeDBConnection() throws SQLException {
		String dbURL = Settings.dbURL;
		String username = Settings.dbUserName;
		String password = Settings.dbPassword;

		Connection dbCon = null;

		return dbCon = DriverManager.getConnection(dbURL, username, password);
	}

	@Override
	public void createNewUser(String email, String password)
			throws IllegalArgumentException {
		Connection dbCon = null;

		String insertUserQuery = "INSERT INTO user (userEmail, userPassword) VALUES (?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(insertUserQuery);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, password);

			preparedStmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}

	@Override
	public int getUserID(String email) throws IllegalArgumentException {
		Connection dbCon = null;
		int userID = 0;

		String query = "SELECT userID FROM user WHERE userEmail = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userID = rs.getInt("userID");
			}
			return userID;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return 0;
	}

	@Override
	public List<ModelInfo> getAllModelsFromSpecificGroupThatIsProposed(int activeGroup) throws IllegalArgumentException {
		Connection dbCon = null;

		List<ModelInfo> modelInfoList = new ArrayList<ModelInfo>();

		int modelID;
		int modelGroupID;
		int modelCreator;
		int modelType;
		String modelString;
		String modelName;	
		int IsProposal;
		String modelCreationDate;

		String query = "SELECT * FROM model WHERE groupID = ? AND isProposal = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, activeGroup);
			preparedStatement.setInt(2, 1);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelID = rs.getInt("modelID");
				modelGroupID = rs.getInt("groupID");
				modelCreator = rs.getInt("modelCreator");
				modelType = rs.getInt("modelType");
				modelString = rs.getString("modelString");
				modelName	 = rs.getString("modelName");
				IsProposal = rs.getInt("isProposal");
				modelCreationDate = rs.getString("creationDate");

				ModelInfo mI = new ModelInfo(modelID, modelGroupID, modelCreator,modelType,modelString,modelName,IsProposal,modelCreationDate);
				modelInfoList.add(mI);
			}
			return  modelInfoList;
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return  null;

	}

	@Override
	public int createNewGroup(int userID, String groupName) {
		Connection dbCon = null;

		String query = "INSERT INTO workGroup (groupFacilitator, groupname) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, groupName);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

		query = "SELECT * FROM workGroup WHERE groupFacilitator = ? ORDER BY groupID DESC LIMIT 1";
		int groupID = 0;
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				groupID = rs.getInt("groupID");
			} 
		}
		catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
		return groupID;

	}

	public String getDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(calendar.getTime());
	}

	@Override
	public void addCommentToModel(int userID, int modelID, String comment) {

		Connection dbCon = null;

		String query = "insert into modelcomment (modelID, comment) VALUES (?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);   
			preparedStatement.setInt(1, modelID);
			preparedStatement.setString(2, comment);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 

	}

	@Override
	public void addVoteToModel(int userID, int modelID, int grade) {

		Connection dbCon = null;

		String query = "INSERT INTO voteonmodel (modelID, userID, grade) VALUES (?,?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, grade);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public List<String> getCommentsOnModel(int modelID) {
		Connection dbCon = null;

		List<String> commentList = new ArrayList<String>();

		String comment;

		String query = "SELECT comment FROM modelcomment WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				comment = rs.getString("comment");
				commentList.add(comment);
			}
			return  commentList;
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return  null;
	}

	public void inviteToGroup(int groupID, int userID) {

		Connection dbCon = null;

		String query = "INSERT INTO workgroupinvites (groupID, userID) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
	}

	@Override
	public WorkGroupInfo getGroupInfo(int activeGroupID) {
		Connection dbCon = null;
		WorkGroupInfo workGroupInfo = new WorkGroupInfo();

		String query = "SELECT * FROM workgroup WHERE groupID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, activeGroupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				workGroupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				workGroupInfo.setWorkGroupName(rs.getString("groupName"));
			}
			return workGroupInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public ModelInfo loadGroupModel(int groupID) {
		Connection dbCon = null;
		ModelInfo modelInfo = new ModelInfo();
		String query = "SELECT * FROM activegroupmodel as a LEFT JOIN model as m ON a.modelID = m.modelID WHERE a.groupID = ? ORDER BY a.groupModelID DESC LIMIT 1 ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));

			}			
			return modelInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public List<WorkGroupInfo> getGroupInvites(int userID) {
		Connection dbCon = null;
		List<WorkGroupInfo> invitesList = new ArrayList<WorkGroupInfo>();

		String query = "SELECT * FROM workgroupinvites WHERE userID = ? AND isActive = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, 1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				WorkGroupInfo workGroupInfo = new WorkGroupInfo();
				workGroupInfo.setWorkGroupInviteID(rs.getInt("inviteID"));
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				invitesList.add(workGroupInfo);
			}
			return getGroupInvitesInfo(invitesList);

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}


	private List<WorkGroupInfo> getGroupInvitesInfo(List<WorkGroupInfo> invitesList) {
		Connection dbCon = null;

		String query = "SELECT * FROM workgroup WHERE groupID = ?";
		for(WorkGroupInfo groupInfo : invitesList){
			try{
				dbCon = initializeDBConnection(); 
				PreparedStatement preparedStatement = dbCon.prepareStatement(query);
				preparedStatement.setInt(1, groupInfo.getWorkGroupID());
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					groupInfo.setWorkGroupName(rs.getString("groupName"));
					groupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				}

			} catch (SQLException ex) {
				Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
			}      
		}
		return invitesList;
	}


	@Override
	public void addUserProfileToUser(int userID, String firstName,
			String surName, String birthDay, String phoneNumber) {
		Connection dbCon = null;

		String query = "INSERT INTO userprofile (userID, firstName, surName, birthDate, phoneNumber) VALUES (?,?,?,?,?)" +
				"ON DUPLICATE KEY update firstName = ?,surName = ?, birthDate = ?, phoneNumber = ?";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, surName);
			preparedStatement.setString(4, birthDay);
			preparedStatement.setString(5, phoneNumber);

			preparedStatement.setString(6, firstName);
			preparedStatement.setString(7, surName);
			preparedStatement.setString(8, birthDay);
			preparedStatement.setString(9, phoneNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  
	}

	@Override
	public List<String> getUserProfile(int userID) throws IllegalArgumentException {
		Connection dbCon = null;

		List<String> userProfile = new ArrayList<String>();

		String query = "SELECT * FROM userprofile WHERE userID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String fName = rs.getString("firstName");
				String sName = rs.getString("surName");
				String bDay = rs.getString("birthDate");
				String phoneNr = rs.getString("phoneNumber");
				userProfile.add(fName);
				userProfile.add(sName);
				userProfile.add(bDay);
				userProfile.add(phoneNr);
			}

			return userProfile;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return userProfile;
	}

	@Override
	public void updateActiveGroupModel(int activeGroupID, int modelID, int version) {
		Connection dbCon = null;

		String modelString = "";
		String query = "SELECT modelString FROM model WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 

			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelString = rs.getString("modelString");
			}

			String queryTwo = "INSERT INTO activegroupmodel(groupID, modelID, modelString, version) VALUES (?,?,?,?)";

			PreparedStatement preparedStatementTwo = dbCon.prepareStatement(queryTwo);
			preparedStatementTwo.setInt(1, activeGroupID);
			preparedStatementTwo.setInt(2, modelID);
			preparedStatementTwo.setString(3, modelString);
			preparedStatementTwo.setInt(4, version);

			preparedStatementTwo.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}
		return;
	}

	@Override
	public List<WorkGroupInfo> getUsersGroups(int userID) {
		Connection dbCon = null;
		List<WorkGroupInfo> invitesList = new ArrayList<WorkGroupInfo>();

		String query = "SELECT * FROM workgroupmember WHERE userID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				WorkGroupInfo workGroupInfo = new WorkGroupInfo();
				workGroupInfo.setWorkGroupID(rs.getInt("groupID"));
				//workGroupInfo.setWorkGroupName(rs.getString("groupName"));
				//workGroupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				invitesList.add(workGroupInfo);
			}
			return getUsersGroupsInfo(invitesList);

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	public List<WorkGroupInfo> getUsersGroupsInfo(List<WorkGroupInfo> invitesList) {
		Connection dbCon = null;

		String query = "SELECT * FROM workgroup WHERE groupID = ?";

		for(WorkGroupInfo groupInfo: invitesList){
			try{
				dbCon = initializeDBConnection(); 
				PreparedStatement preparedStatement = dbCon.prepareStatement(query);
				preparedStatement.setInt(1, groupInfo.getWorkGroupID());
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					groupInfo.setWorkGroupName(rs.getString("groupName"));
					groupInfo.setWorkGroupFacilitator(rs.getInt("groupFacilitator"));
				}
			} catch (SQLException ex) {
				Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return invitesList;
	}

	@Override
	public List<ModelInfo> getAllUsersModels(int userID) {
		Connection dbCon = null;
		List<ModelInfo> modelList = new ArrayList<ModelInfo>();

		String query = "SELECT * FROM model WHERE modelCreator = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ModelInfo modelInfo = new ModelInfo();
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));
				modelList.add(modelInfo);
			}
			return modelList;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public void setInviteToInactive(int inviteID) {
		Connection dbCon = null;



		String query = "UPDATE workgroupinvites SET isActive = ? WHERE inviteID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, inviteID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public void addUserToGroup(int groupID, int userID) {
		Connection dbCon = null;

		System.out.println("GROUP : " + groupID + "USERID : " + userID);
		String query = "INSERT INTO workgroupmember (groupID, userID) VALUES (?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public List<String> getAllGroupMembers(int groupID) {
		Connection dbCon = null;
		List<String> members = new ArrayList<String>();

		String query = "SELECT * FROM workgroupmember as w LEFT JOIN user as u ON w.userID = u.userID WHERE w.groupID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				members.add(rs.getString("userEmail"));
			}
			return members;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}


	@Override
	public List<ProposalAvgVote> getVotes(List<Integer> modelIDs) {
		Connection dbCon = null;
		ProposalAvgVote proposalAvgVote;
		List<ProposalAvgVote> votes = new ArrayList<ProposalAvgVote>();

		String query = "SELECT AVG(v.grade) as average, u.firstName FROM model as m Left JOIN userprofile as u ON m.modelCreator = u.userID LEFT JOIN voteonmodel as v ON v.modelID = m.modelID WHERE m.modelID = ?";

		for (Iterator<Integer> i = modelIDs.iterator(); i.hasNext();) {

			if(i.hasNext()){

				proposalAvgVote = new ProposalAvgVote();

				try{		
					int modelID = i.next();
					dbCon = initializeDBConnection(); 
					PreparedStatement preparedStatement = dbCon.prepareStatement(query);
					preparedStatement.setInt(1, modelID);					
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						proposalAvgVote.setName(rs.getString("firstName"));
						proposalAvgVote.setAvgVote(rs.getFloat("average"));
					}
					votes.add(proposalAvgVote);

				} catch (SQLException ex) {
					Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
				}      
			}
		}
		return votes;
	}


	@Override
	public String getModelCreatorName(int modelID) {
		Connection dbCon = null;
		String modelCreator = "";

		String query = "SELECT * FROM userprofile as u LEFT JOIN model as m ON u.userID =  m.modelCreator WHERE m.modelID = ? ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelCreator = rs.getString("firstName");
			}
			return modelCreator;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}

	@Override
	public List<Integer> getModelIDs(int groupID) {
		Connection dbCon = null;
		List<Integer> modelIDs = new ArrayList<Integer>();

		String query = "SELECT * FROM model  WHERE groupID = ? AND isProposal = ? ";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, 1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelIDs.add(rs.getInt("modelID"));
			}
			return modelIDs;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}
	
	@Override
	public void saveModel(int groupID, int userID, String modelName,
			int modelType, String modelString, int isProposal) {

		String creationDate = getDate();

		Connection dbCon = null;

		String query = "INSERT INTO model (groupID, modelCreator, modelName, modelType, modelString, creationDate, isProposal) VALUES (?,?,?,?,?,?,?)";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setString(3, modelName);
			preparedStatement.setInt(4, modelType);
			preparedStatement.setString(5, modelString);
			preparedStatement.setString(6, creationDate);
			preparedStatement.setInt(7, isProposal);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public ModelInfo loadModel(int modelID) {
		Connection dbCon = null;
		ModelInfo modelInfo = new ModelInfo();

		String query = "SELECT * FROM model WHERE modelID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, modelID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				modelInfo.setModelID(rs.getInt("modelID"));
				modelInfo.setModelGroupID(rs.getInt("groupID"));
				modelInfo.setModelCreator(rs.getInt("modelCreator"));
				modelInfo.setModelType(rs.getInt("modelType"));
				modelInfo.setModelString(rs.getString("modelString"));
				modelInfo.setModelName(rs.getString("modelName"));
				modelInfo.setIsProposal(rs.getInt("isProposal"));
				modelInfo.setModelCreationDate(rs.getString("creationDate"));
			}
			return modelInfo;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		return null;
	}
	
	@Override
	public void saveModelToActiveGroup(int activeGroupID, int modelID, String modelString, int version) {
		Connection dbCon = null;
		String query = "INSERT INTO activegroupmodel(groupID, modelID, modelString, version) VALUES (?,?,?,?)";
		
		try{
			dbCon = initializeDBConnection(); 		

			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, activeGroupID);
			preparedStatement.setInt(2, modelID);
			preparedStatement.setString(3, modelString);
			preparedStatement.setInt(4, version);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}  

	}

	@Override
	public User getUser(String email) {
		Connection dbCon = null;
		User user = new User();

		String query = "SELECT userID, userType FROM user WHERE userEmail = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user.setUserId(rs.getInt("userID"));
				user.setUserType(UserType.fromString(rs.getString("userType")));				
			}
			user.setUserEmail(email);
			return user;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		
		return null;
	}

	@Override
	public User getUser(int ID) {
		Connection dbCon = null;
		User user = new User();

		String query = "SELECT userEmail FROM user WHERE userID = ?";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserType(UserType.valueOf(rs.getString("userType")));	
			}
			user.setUserId(ID);
			return user;

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		
		return null;
	}
	
	@Override
	public List<Benefit> getAllBenefits() throws IllegalArgumentException {
		Connection dbCon = null;
		List<Benefit> benefits = new ArrayList<Benefit>();
	
		String query = "SELECT * FROM benefit";
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				benefits.add(new Benefit(rs.getInt("benefitID"), rs.getString("benefitDescription")));
			}

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		
		return benefits;
	}
	
	@Override
	public void createNewBenefit(String description)throws IllegalArgumentException {
		Connection dbCon = null;
		String query = "INSERT INTO benefit (benefitDescription) VALUES (?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(query);
			preparedStmt.setString(1, description);			

			preparedStmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}

	@Override
	public void updateBenefitSelection(int groupID, int modelID, List<Integer> benefits) {
		Connection dbCon = null;
		int result = 0;

		String deleteQuery = "DELETE FROM workgroupModelBenefit WHERE groupID = ? AND modelID = ?";
		String insertQuery = "INSERT INTO workgroupModelBenefit (groupID, modelID, benefitID) VALUES (?,?,?)";
		
		try{
			dbCon = initializeDBConnection(); 
			
			PreparedStatement deleteStatement = dbCon.prepareStatement(deleteQuery);
			deleteStatement.setInt(1, groupID);
			deleteStatement.setInt(2, modelID);			
			deleteStatement.executeUpdate();

			//Add all selected benefits
			for(Integer benefitID : benefits){					
				PreparedStatement insertStatement = dbCon.prepareStatement(insertQuery);
				insertStatement.setInt(1, groupID);
				insertStatement.setInt(2, modelID);			
				insertStatement.setInt(3, benefitID);
				insertStatement.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}
	
	@Override
	public List<Integer> getBenefitSelection(int groupID, int modelID) throws IllegalArgumentException {
		Connection dbCon = null;
		List<Integer> benefits = new ArrayList<Integer>();
	
		String query = "SELECT * FROM workgroupModelBenefit WHERE groupID = ? AND modelID = ? ";
		
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);	
			preparedStatement.setInt(1, groupID);
			preparedStatement.setInt(2, modelID);		
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				benefits.add(rs.getInt("benefitID"));
			}

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		
		return benefits;
	}
	
	@Override
	public List<ProblemImpact> getProblemImpacts(int problemId) throws IllegalArgumentException {
		Connection dbCon = null;
		List<ProblemImpact> problemImpacts = new ArrayList<ProblemImpact>();
	
		String query = "SELECT * FROM problemImpact as pi LEFT JOIN benefit as b ON pi.benefitID = b.benefitID WHERE pi.problemID = ?";
		
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStatement = dbCon.prepareStatement(query);	
			preparedStatement.setInt(1, problemId);	
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				problemImpacts.add(new ProblemImpact(
						rs.getInt("problemImpactID"),
						rs.getInt("problemID"),
						rs.getInt("benefitID"),
						rs.getString("benefitDescription"),
						rs.getString("impact"),
						rs.getBoolean("isActive")));
			}

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		}      
		
		return problemImpacts;
	}
	
	@Override
	public void createNewProblemImpact(int problemID, int benefitID, String impact)throws IllegalArgumentException {
		Connection dbCon = null;
		String query = "INSERT INTO problemImpact (`problemID`, `benefitID`, `impact`, `isActive`) VALUES (?,?,?,?)";

		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(query);
			preparedStmt.setInt(1, problemID);
			preparedStmt.setInt(2, benefitID);
			preparedStmt.setString(3, impact);
			preparedStmt.setBoolean(4, true);

			preparedStmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}
	
	@Override
	public void createNewProblem(int userID, int groupID, int activegroupModelID, ProblemClass problem)throws IllegalArgumentException {
		Connection dbCon = null;
		String query = "INSERT INTO problem (`userID`, `groupID`, `activegroupModelID`, `name`, `description`, `severity`, `evolution`, `urgency`, `occurence`, `explanation`, `modelString`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		int newProblemID = -1;
		
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.setInt(2, groupID);
			preparedStmt.setInt(3, activegroupModelID);
			preparedStmt.setString(4, problem.getName());
			preparedStmt.setString(5, problem.getDescription());
			preparedStmt.setString(6, problem.getSeverity().toString());
			preparedStmt.setString(7, problem.getEvolution().toString());
			preparedStmt.setString(8, problem.getUrgency().toString());
			preparedStmt.setString(9, problem.getOccurence().toString());
			preparedStmt.setString(10, problem.getExplanation());
			preparedStmt.setString(11, problem.getModelString());
			preparedStmt.executeUpdate();
			
			//Get the new problemID
			String idQuery = "SELECT problemID FROM problem WHERE activegroupModelID = ? AND userID = ?";
			PreparedStatement preparedStmt2 = dbCon.prepareStatement(idQuery);
			preparedStmt2.setInt(1, activegroupModelID);
			preparedStmt2.setInt(2, userID);
			ResultSet rs2 = preparedStmt2.executeQuery();
			while (rs2.next()) {				
				newProblemID = rs2.getInt("problemID");						
			}
			
			//Add all impacts
			for(ProblemImpact impact:problem.getProblemImpactList()){
				String impactQuery = "INSERT INTO problemImpact (`problemID`, `benefitID`, `impact`, `isActive`) VALUES (?,?,?,?)";
				PreparedStatement preparedStmt3 = dbCon.prepareStatement(impactQuery);
				preparedStmt3.setInt(1, newProblemID);
				preparedStmt3.setInt(2, impact.getBenefitId());
				preparedStmt3.setString(3, impact.getImpact());
				preparedStmt3.setBoolean(4, impact.getIsActive());				
				preparedStmt3.executeUpdate();				
			}	

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}
	
	@Override
	public ProblemClass getProblem(int problemID)throws IllegalArgumentException {
		Connection dbCon = null;
		String query = "SELECT * FROM problem WHERE problemID = ?";
		ProblemClass problem = new ProblemClass();
				
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(query);
			preparedStmt.setInt(1, problemID);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {				
				problem.setProblemID(rs.getInt("problemID"));
				problem.setName(rs.getString("name"));
				problem.setDescription(rs.getString("description"));
				problem.setSeverity(ProblemSeverity.fromString(rs.getString("severity")));
				problem.setEvolution(ProblemEvolution.fromString(rs.getString("evolution")));
				problem.setUrgency(ProblemUrgency.fromString(rs.getString("urgency")));
				problem.setOccurence(ProblemOccurence.fromString(rs.getString("occurence")));
				problem.setExplanation(rs.getString("explanation"));
				problem.setModelString(rs.getString("modelString"));
			}			

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return problem;
	}
	
	@Override
	public List<ProblemClass> getProblemsFromUser(int userID, int groupID, int activegroupModelID)throws IllegalArgumentException {
		Connection dbCon = null;
		String query = "SELECT * FROM problem WHERE userID = ? AND groupID = ? AND activegroupModelID = ?";
		List<ProblemClass> problemList = new ArrayList<ProblemClass>();				
				
		try{
			dbCon = initializeDBConnection(); 
			PreparedStatement preparedStmt = dbCon.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.setInt(2, groupID);
			preparedStmt.setInt(3, activegroupModelID);			
			ResultSet rs = preparedStmt.executeQuery();
			
			//Create all problems
			while (rs.next()) {			
				ProblemClass problem = new ProblemClass();
				problem.setProblemID(rs.getInt("problemID"));
				problem.setName(rs.getString("name"));
				problem.setDescription(rs.getString("description"));
				problem.setSeverity(ProblemSeverity.fromString(rs.getString("severity")));
				problem.setEvolution(ProblemEvolution.fromString(rs.getString("evolution")));
				problem.setUrgency(ProblemUrgency.fromString(rs.getString("urgency")));
				problem.setOccurence(ProblemOccurence.fromString(rs.getString("occurence")));
				problem.setExplanation(rs.getString("explanation"));
				problem.setModelString(rs.getString("modelString"));
				problemList.add(problem);
			}			

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return problemList;
	}
	
	@Override
	public void updateProblem(ProblemClass problem)throws IllegalArgumentException {
		Connection dbCon = null;		
		
		try{
			dbCon = initializeDBConnection(); 
			
			//Delete previous impacts
			for(ProblemImpact impact:problem.getProblemImpactList()){
				String deleteQuery = "DELETE FROM problemImpact WHERE `problemImpactID` = ?";
				PreparedStatement preparedStmt = dbCon.prepareStatement(deleteQuery);
				preparedStmt.setInt(1, impact.getProblemImpactId());								
				preparedStmt.executeUpdate();	
			}
			
			String query = "UPDATE problem SET `name` = ?, `description` = ?, `severity` = ?, `evolution` = ?, `urgency` = ?, `occurence` = ?, `explanation` = ?, `modelString` = ? WHERE problemID = ?";			
			PreparedStatement preparedStmt2 = dbCon.prepareStatement(query);
			preparedStmt2.setString(1, problem.getName());
			preparedStmt2.setString(2, problem.getDescription());
			preparedStmt2.setString(3, problem.getSeverity().toString());
			preparedStmt2.setString(4, problem.getEvolution().toString());
			preparedStmt2.setString(5, problem.getUrgency().toString());
			preparedStmt2.setString(6, problem.getOccurence().toString());
			preparedStmt2.setString(7, problem.getExplanation());
			preparedStmt2.setString(8, problem.getModelString());
			preparedStmt2.setInt(9, problem.getProblemID());
			preparedStmt2.executeUpdate();					
			
			//Add all impacts
			for(ProblemImpact impact:problem.getProblemImpactList()){
				String impactQuery = "INSERT INTO problemImpact (`problemID`, `benefitID`, `impact`, `isActive`) VALUES (?,?,?,?)";
				PreparedStatement preparedStmt3 = dbCon.prepareStatement(impactQuery);
				preparedStmt3.setInt(1, problem.getProblemID());
				preparedStmt3.setInt(2, impact.getBenefitId());
				preparedStmt3.setString(3, impact.getImpact());
				preparedStmt3.setBoolean(4, impact.getIsActive());				
				preparedStmt3.executeUpdate();				
			}	

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}
	
	@Override
	public void deleteProblem(ProblemClass problem)throws IllegalArgumentException {
		Connection dbCon = null;		
		
		try{
			dbCon = initializeDBConnection(); 
			
			//Delete previous impacts
			String deleteQuery = "DELETE FROM problemImpact WHERE `problemID` = ?";
			PreparedStatement preparedStmt = dbCon.prepareStatement(deleteQuery);
			preparedStmt.setInt(1, problem.getProblemID());								
			preparedStmt.executeUpdate();	
			
			//Delete problem			
			String query = "DELETE FROM problem WHERE problemID = ?";			
			PreparedStatement preparedStmt2 = dbCon.prepareStatement(query);
			preparedStmt2.setInt(1, problem.getProblemID());
			preparedStmt2.executeUpdate();						

		} catch (SQLException ex) {
			Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		return;
	}
}


