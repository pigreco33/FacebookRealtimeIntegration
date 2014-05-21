package it.camera.hackathon.cddhackathon2014.model;

import java.text.MessageFormat;

public class Votazione implements Publishable {

	//private static final String MESSAGE_FORMAT = "La \"{0}\" - {1} - {2} stata approvata con {3} voti favorevoli, {4} voti contrari e {5} astenuti.";
	private static final String MESSAGE_FORMAT = "La \"{0}\" - {1} - si � conclusa con {3} voti favorevoli, {4} voti contrari e {5} astenuti.";
	private String votazione = null;
	private String data = null;
	private String denominazione = null;
	private String descrizione = null;
	private int votanti = 0;
	private boolean approvato = false;
	private int favorevoli = 0;
	private int contrari = 0;
	private int astenuti = 0;
	private String leg = null;

	public String getVotazione() {
		return votazione;
	}

	public void setVotazione(String votazione) {
		this.votazione = votazione;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getVotanti() {
		return votanti;
	}

	public void setVotanti(int votanti) {
		this.votanti = votanti;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public int getFavorevoli() {
		return favorevoli;
	}

	public void setFavorevoli(int favorevoli) {
		this.favorevoli = favorevoli;
	}

	public int getContrari() {
		return contrari;
	}

	public void setContrari(int contrari) {
		this.contrari = contrari;
	}

	public int getAstenuti() {
		return astenuti;
	}

	public void setAstenuti(int astenuti) {
		this.astenuti = astenuti;
	}

	public String getLeg() {
		return leg;
	}

	public void setLeg(String leg) {
		this.leg = leg;
	}

	@Override
	public String toString() {
		return "Votazione [votazione=" + votazione + ", data=" + data
				+ ", denominazione=" + denominazione + ", descrizione="
				+ descrizione + ", votanti=" + votanti + ", approvato="
				+ approvato + ", favorevoli=" + favorevoli + ", contrari="
				+ contrari + ", astenuti=" + astenuti + ", leg=" + leg + "]";
	}

	public String getMessage() {
		MessageFormat pMessageFormat = new MessageFormat(MESSAGE_FORMAT);
		String sApprovato = null;
		if (this.approvato) {
			sApprovato = "�";
		} else {
			sApprovato = "non �";
		}
		String[] ssFormatArgs = { 
				this.denominazione, 
				this.descrizione.replace('\n', '\0').replace('\n', '\0').trim(),
				sApprovato, 
				String.valueOf(this.favorevoli),
				String.valueOf(this.contrari), 
				String.valueOf(this.astenuti) 
				};
		String sMessage = pMessageFormat.format(ssFormatArgs);
		
		return sMessage;
	}

	public String getLink() {
		return votazione;
	}

}
