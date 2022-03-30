package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.*;

public class Model {
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;

	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	 
	public List<Corso> getTuttiICorsi() {
		return this.corsoDao.getTuttiICorsi();
	}
	
	public String getNomeCognomeDaMatricola(int matricola) {
		return this.studenteDao.getNomeCogmomeDaMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return this.corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiDaMatricola(Integer matricola) {
		return this.studenteDao.getCorsiDaMatricola(matricola);
	}
	
	public boolean getStudenteCorso(Integer matricola, Corso c) {
		return this.studenteDao.getStudenteCorso(matricola, c);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return this.corsoDao.inscriviStudenteACorso(studente, corso);
	}
	
	public Studente getStudente(Integer matricola) {
		return this.studenteDao.getStudente(matricola);
	}
}
