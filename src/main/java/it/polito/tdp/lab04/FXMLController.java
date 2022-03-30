package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> cmbCorsi;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtRisultato.clear();
    	if(txtMatricola.getText()==null)
    		txtRisultato.setText("Inserire una matricola");
    	else if(!txtMatricola.getText().matches("[0-9]*"))
    		txtRisultato.setText("Inserire una matricola valida");
    	else {
    		Integer matricola = Integer.parseInt(txtMatricola.getText());
    		if(model.getNomeCognomeDaMatricola(matricola).split(" ").length<=1)
    			txtRisultato.setText("Studente non presente");
    		else if(model.getCorsiDaMatricola(matricola).size()==0)
    			txtRisultato.setText("Studente non iscritto ad alcun corso");
    		else
    			for(Corso c: model.getCorsiDaMatricola(matricola)) {
    				String stampa = c.getCodins() + "\t\t\t" + c.getCrediti() + "\t\t" + c.getNome() + "\t\t\t\t\t" + c.getPd() + "\n";
    				txtRisultato.appendText(stampa);
    			}
    	}
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	Corso corso = cmbCorsi.getValue();
    	if(corso==null || corso.getCodins().equals(""))
    		txtRisultato.setText("Selezionare un corso");
    	else if(model.getStudentiIscrittiAlCorso(corso).size()==0)
    		txtRisultato.setText("Corso senza iscritti");
    	else
    		for(Studente s: model.getStudentiIscrittiAlCorso(corso))
				txtRisultato.appendText(s.toString());
    }

    @FXML
    void doGreen(ActionEvent event) {
    	if(txtMatricola.getText().length()==0)
    		txtRisultato.setText("Inserire una matricola");
    	else if(!txtMatricola.getText().matches("[0-9]*"))
    		txtRisultato.setText("Inserire una matricola valida");
    	else {
	    	int matricola = Integer.parseInt(txtMatricola.getText());
	    	String[] nome_cognome = model.getNomeCognomeDaMatricola(matricola).split(" ");
	    	if(nome_cognome.length<=1)
				txtRisultato.setText("Studente non presente");
	    	else {
	    		txtNome.setText(nome_cognome[0]);
	    		txtCognome.setText(nome_cognome[1]);
	    	}
    	}
    }

    @FXML
    void doCerca(ActionEvent event) {
    	Corso corso = cmbCorsi.getValue();
    	if(corso==null || corso.getCodins().equals(""))
    		txtRisultato.setText("Selezionare un corso");
    	else if(txtMatricola.getText().length()==0)
    		txtRisultato.setText("Inserire una matricola");
    	else if(!txtMatricola.getText().matches("[0-9]*"))
    		txtRisultato.setText("Inserire una matricola valida");
    	else {
	    	Integer matricola = Integer.parseInt(txtMatricola.getText());
	    	if(model.getNomeCognomeDaMatricola(matricola).split(" ").length<=1)
				txtRisultato.setText("Studente non presente");
	    	else if(model.getStudenteCorso(matricola, corso))
	    		txtRisultato.setText("Lo studente è iscritto al corso");
	    	else
	    		txtRisultato.setText("Lo studente non è iscritto al corso");
    	}
    }
    
    @FXML
    void doIscrivi(ActionEvent event) {
    	Corso corso = cmbCorsi.getValue();
    	if(corso==null || corso.getCodins().equals(""))
    		txtRisultato.setText("Selezionare un corso");
    	else if(txtMatricola.getText().length()==0)
    		txtRisultato.setText("Inserire una matricola");
    	else if(!txtMatricola.getText().matches("[0-9]*"))
    		txtRisultato.setText("Inserire una matricola valida");
    	else {
	    	Integer matricola = Integer.parseInt(txtMatricola.getText());
	    	if(model.getNomeCognomeDaMatricola(matricola).split(" ").length<=1)
				txtRisultato.setText("Studente non presente");
	    	else if(model.getStudenteCorso(matricola, corso))
	    		txtRisultato.setText("Lo studente è già iscritto al corso");
	    	else if(model.inscriviStudenteACorso(model.getStudente(matricola), corso))
	    		txtRisultato.setText("Lo studente è stato iscritto al corso");
	    	else
	    		txtRisultato.setText("Lo studente non è stato iscritto al corso");

    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    	cmbCorsi.setValue(null);
    }
    
    public void setModel(Model model) {
		this.model = model;
		
		cmbCorsi.getItems().add(new Corso());
		for(Corso c: model.getTuttiICorsi())
			cmbCorsi.getItems().addAll(c);
	}

    @FXML
    void initialize() {
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";                
    }

}
