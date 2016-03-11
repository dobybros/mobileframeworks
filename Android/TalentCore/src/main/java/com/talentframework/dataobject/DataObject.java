package com.talentframework.dataobject;

public abstract class DataObject {
	
	public static final String FIELD_ID = "id";
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public void fromXXX(JSONObject jsonObj) {
//		id = jsonObj.get(FIELD_ID);
//	}
//	
//	public JSONObject toXXX() {
//		jsonOBJ.put(FIELD_ID, id);
//	}
//	
//	public class User extends DataObject {
//		public static final String FIELD_NAME = "username";
//		public static final String FIELD_PASSWORD = "password";
//	
//		private String name;
//		private String account;
//		private String password;
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public String getAccount() {
//			return account;
//		}
//		public void setAccount(String account) {
//			this.account = account;
//		}
//		public String getPassword() {
//			return password;
//		}
//		public void setPassword(String password) {
//			this.password = password;
//		}
//		
//		public JSONObject toXXX() {
//			jsonObj = super.toXXX();
//			jsonO
//		}
//	}
}

//User user = new User();
//user.fromXXX(jsonObj);
//
//user.toXXX()