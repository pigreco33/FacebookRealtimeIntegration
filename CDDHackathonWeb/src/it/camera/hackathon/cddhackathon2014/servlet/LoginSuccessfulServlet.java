package it.camera.hackathon.cddhackathon2014.servlet;

import it.camera.hackathon.cddhackathon2014.model.Facebook;
import it.camera.hackathon.cddhackathon2014.utils.SessionManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSuccessfulServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		
		Facebook facebook = new Facebook();
		String shortLiveAccessToken = facebook.getShortLiveAccessToken(code);
		String pageAccessToken = facebook.getPageAccessToken(shortLiveAccessToken);
		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.setAccessToken(pageAccessToken);
		
		ServletOutputStream out = resp.getOutputStream();
		out.write("Login successful!".getBytes());
		out.flush();
        out.close();
    }
    
}
