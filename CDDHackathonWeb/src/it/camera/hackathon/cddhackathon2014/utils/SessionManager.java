package it.camera.hackathon.cddhackathon2014.utils;

public class SessionManager {
	private static SessionManager sessionManager = new SessionManager();
	
	private String accessToken;
	
	private SessionManager() {
		
	}
	
	public static SessionManager getInstance() {
		return sessionManager;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
}
