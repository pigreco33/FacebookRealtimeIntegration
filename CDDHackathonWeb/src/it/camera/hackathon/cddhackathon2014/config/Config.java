package it.camera.hackathon.cddhackathon2014.config;

public interface Config {
	public interface Facebook {
		public String PAGE_ID = "";
		
		public String APP_ID = "";
		public String APP_SECRET = "";
		public String REDERECT_URI = "http://tomcat.area19.net/CDDHackathonWeb/loginSuccessful";

		public String GRAPH_URL = "https://graph.facebook.com/v2.0";
		public String OAUTH_URL = GRAPH_URL+"/oauth/access_token";
		public String ACCOUNTS_URL = GRAPH_URL+"/me/accounts";
		public String PAGE_URL = GRAPH_URL+"/CDDHackathon2014";
		public String FEED_URL = PAGE_URL+"/feed";

		
	}
	
	public interface Mongo {
		public String URL = "localhost";
		public int PORT = 27017;
	}
}
