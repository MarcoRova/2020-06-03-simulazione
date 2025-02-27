/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Sconfitto;
import it.polito.tdp.PremierLeague.model.TopPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	
    	double x = 0.0;
    	try {
    		x = Double.parseDouble(this.txtGoals.getText());
    		
    		if(x<0.0){
    			this.txtResult.setText("Numeri negativi non ammessi.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Formato non corretto per i goal fatti.");
    		return;
    	}
    	
    	this.model.creaGrafo(x);
    	
    	this.txtResult.appendText(this.model.infoGrafo());
    	
    	
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	int k = 0;
    	try {
    		k = Integer.parseInt(this.txtK.getText());
    		
    		if(k<0.0){
    			this.txtResult.setText("Numeri negativi non ammessi.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Formato non corretto per il numero di giocatori.");
    		return;
    	}
    	
    	if(this.model.getGrafo() == null) {
    		txtResult.appendText("Crea il grafo!");
    		return;
    	}
    	
    	List<Player> dreamTeam = this.model.getDreamTeam(k);
    	int degree = this.model.getBestDegree();
    	
    	txtResult.appendText("DREAM TEAM - grado di titolarità: " + degree + "\n\n");
    	for(Player p : dreamTeam)
    		txtResult.appendText(p.toString() + "\n");

    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	double x = 0.0;
    	try {
    		x = Double.parseDouble(this.txtGoals.getText());
    		
    		if(x<0.0){
    			this.txtResult.setText("Numeri negativi non ammessi.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Formato non corretto per i goal fatti.");
    		return;
    	}
    	
    	if(this.model.getGrafo() == null) {
    		txtResult.appendText("Crea il grafo!");
    		return;
    	}
    	
    	TopPlayer tp = this.model.getTopPlayer(x);
    	
    	this.txtResult.appendText("TOP PLAYER: "+tp.getTopPlayer()+"\n");
    	
    	for(Sconfitto s : tp.getSconfitti())
    		this.txtResult.appendText(s.toString()+"\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
