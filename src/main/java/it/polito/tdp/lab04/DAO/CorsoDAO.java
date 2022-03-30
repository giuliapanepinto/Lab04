package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	//Ottengo tutti i corsi salvati nel Db
	public List<Corso> getTuttiICorsi() {
		final String sql = "SELECT * FROM corso";
		List<Corso> corsi = new LinkedList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}
			st.close();
			rs.close();
			conn.close();
			return corsi;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	//Dato un codice insegnamento, ottengo il corso
	public Corso getCorso(Corso corso) {
		for(Corso c: getTuttiICorsi())
			if(corso.getCodins().equals(c.getCodins()))
				return c;
		return null;
	}
	
	//Ottengo tutti gli studenti iscritti al Corso
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins=?";
		List<Studente> studenti = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				studenti.add(new Studente(matricola, nome, cognome, cds));
			}
			st.close();
			rs.close();
			conn.close();
			return studenti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	 //Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// ritorna true se l'iscrizione e' avvenuta con successo
		final String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES (?, ?)";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			int rs = st.executeUpdate();
			st.close();
			conn.close();
			return (rs==1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

}
