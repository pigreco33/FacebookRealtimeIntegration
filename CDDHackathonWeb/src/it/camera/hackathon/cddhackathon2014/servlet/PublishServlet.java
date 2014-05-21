package it.camera.hackathon.cddhackathon2014.servlet;

import it.camera.hackathon.cddhackathon2014.model.Publisher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PublishServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Publisher publisher = new Publisher();
			String[] ids = publisher.publish();
			
			
			ServletOutputStream out = resp.getOutputStream();
	        
			for(String id : ids) {
				out.write(id.getBytes());
				out.write(" - ".getBytes());
			}
			out.flush();
	        out.close();
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
    }
    
}
