package it.camera.hackathon.cddhackathon2014.model.reader;

import it.camera.hackathon.cddhackathon2014.model.Votazione;

public interface Reader {
	
	public Votazione[] readVotazioni() throws Exception;

}
