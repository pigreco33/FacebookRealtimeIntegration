package it.camera.hackathon.cddhackathon2014.model.reader;

import it.camera.hackathon.cddhackathon2014.model.Votazione;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoReader implements Reader {
	private static final Logger LOGGER = Logger.getLogger(MongoReader.class.getCanonicalName());


	private String host = "localhost";
	private int port = 27017;

	public MongoReader() {
		super();
	}

	public MongoReader(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public Votazione[] readVotazioni() throws UnknownHostException,
			JSONException {
		Votazione[] ppVotazione = null;
		MongoClient pMongoClient = null;
		DB pDB = null;
		DBCollection pDBCollection = null;
		DBCursor pDBCursor = null;
		try {
			List<Votazione> pListVotazione = new ArrayList<Votazione>();

			pMongoClient = new MongoClient(this.host, this.port);
			pDB = pMongoClient.getDB("hackathon");
			pDBCollection = pDB.getCollection("votazioni");
			BasicDBObject query = new BasicDBObject();
			query.put("approved", true);
			query.put("published", false);
			pDBCursor = pDBCollection.find(query);

			List<DBObject> ppDBObject = pDBCursor.toArray();
			for (DBObject pDBObject : ppDBObject) {
				String sJsonString = com.mongodb.util.JSON.serialize(pDBObject);
				JSONObject pJsonObject = new JSONObject(sJsonString);
				JSONObject pVotazioneData = pJsonObject.getJSONObject("data");
				Votazione pVotazione = new Votazione();
				pVotazione.setVotazione(pVotazioneData.getString("votazione"));
				pVotazione.setData(pVotazioneData.getString("date"));
				pVotazione.setDenominazione(pVotazioneData
						.getString("title"));
				pVotazione.setDescrizione(pVotazioneData.getString("description"));
				pVotazione.setVotanti(pVotazioneData.getInt("votanti"));
				pVotazione.setApprovato((pVotazioneData.getInt("approvato") == 1));
				pVotazione.setFavorevoli(pVotazioneData.getInt("favorevoli"));
				pVotazione.setContrari(pVotazioneData.getInt("contrari"));
				pVotazione.setAstenuti(pVotazioneData.getInt("astenuti"));
				pVotazione.setLeg(pVotazioneData.getString("rif_leg"));
				pListVotazione.add(pVotazione);
				String id = (String)pDBObject.get( "_id" );
				DBObject updateQuery = new BasicDBObject("_id", id);
				BasicDBObject updateDocument = new BasicDBObject();
				updateDocument.put("published", true);
				pDBCollection.update(updateQuery, new BasicDBObject("$set", updateDocument));
				LOGGER.info(pVotazione.toString());
			}
			ppVotazione = pListVotazione.toArray(new Votazione[pListVotazione
					.size()]);
		} finally {
			try {
				pDBCursor.close();
			} catch (Exception pException) {
			}

			try {
				pMongoClient.close();
			} catch (Exception pException) {
			}
		}
		return ppVotazione;
	}

}
