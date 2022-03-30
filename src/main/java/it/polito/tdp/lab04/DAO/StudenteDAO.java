package it.polito.tdp.lab04.DAO;

import java.sql.*;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	public String getNomeCogmomeDaMatricola(int matricolaDaTrovare) {
		final String sql = "SELECT matricola, nome, cognome FROM studente WHERE matricola=?";
		String nome_cognome = "";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricolaDaTrovare);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				nome_cognome = rs.getString("nome")+" "+rs.getString("cognome");
			}
			st.close();
			rs.close();
			conn.close();
			return nome_cognome;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
		
	//Dato uno studente, restituisce i corsi a cui è iscritto
	public List<Corso> getCorsiDaMatricola(Integer matricola) {
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND i.matricola=?";
		List<Corso> corsi = new ArrayList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
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
	
	//Dato studente e corso, dice se esso è iscitto al corso o no
	public boolean getStudenteCorso(Integer matricola, Corso c) {
		final String sql = "SELECT * "
				+ "FROM iscrizione "
				+ "WHERE codins=? AND matricola=?";
		boolean iscritto = false;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodins());
			st.setInt(2, matricola);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				iscritto = true;
			}
			st.close();
			rs.close();
			conn.close();
			return iscritto;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	//Data una matricola, ottengo lo studente
	public Studente getStudente(Integer matricola) {
		final String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";
		Studente s = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				s = new Studente(matricola, nome, cognome, cds);
			}
			st.close();
			rs.close();
			conn.close();
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
}
