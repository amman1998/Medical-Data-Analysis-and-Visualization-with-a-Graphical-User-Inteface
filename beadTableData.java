/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class beadTableData {
	private  SimpleStringProperty bead; 
	private   SimpleStringProperty analyte;
	
	public beadTableData(String bead, String analyte) {
		super();
		this.bead = new SimpleStringProperty(bead);
		this.analyte = new SimpleStringProperty (analyte);
	}
	public String getBead() {
		return bead.get();
	}
	public String getAnalyte() {
		return analyte.get();
	}
        public void setBead(String bead)
        {
           this.bead = new SimpleStringProperty(bead);
        }
        
        public void setAnalyte(String analyte)
        {
           this.analyte = new SimpleStringProperty(analyte);
        }
	
}
