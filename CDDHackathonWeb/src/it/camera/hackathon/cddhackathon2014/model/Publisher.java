package it.camera.hackathon.cddhackathon2014.model;

import it.camera.hackathon.cddhackathon2014.config.Config;
import it.camera.hackathon.cddhackathon2014.model.Votazione;
import it.camera.hackathon.cddhackathon2014.model.reader.MongoReader;
import it.camera.hackathon.cddhackathon2014.model.reader.Reader;

public class Publisher {

	public static void main(String[] args) throws Exception {

		Publisher pPublisher = new Publisher();
		pPublisher.publish();

	}

	public String[] publish() throws Exception {
		Reader pReader = new MongoReader(Config.Mongo.URL, Config.Mongo.PORT);
		Votazione[] ppVotazione = pReader.readVotazioni();
		
		Facebook facebook = new Facebook();
		String[] ids = facebook.publish(ppVotazione);
		
		return ids;
	}
}
