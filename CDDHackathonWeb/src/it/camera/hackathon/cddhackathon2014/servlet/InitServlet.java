package it.camera.hackathon.cddhackathon2014.servlet;

import it.camera.hackathon.cddhackathon2014.config.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("https://www.facebook.com/dialog/oauth?client_id="+Config.Facebook.APP_ID+"&redirect_uri="+Config.Facebook.REDERECT_URI);
    }
    
}
