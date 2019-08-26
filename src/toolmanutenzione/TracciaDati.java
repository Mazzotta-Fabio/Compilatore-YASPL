package toolmanutenzione;

import java.io.PrintWriter;
import java.util.*;

public class TracciaDati {
	private Hashtable<String,String> tabellaVariabili;
	private int contaNodi;
	private int contaPassaggiVariabili;
	private ArrayList<TracciaDati> tracciatureFunzioni;
	private TracciaDati prec;

	public TracciaDati(TracciaDati p) {
		tabellaVariabili=new Hashtable<String,String>();
		contaNodi=0;
		contaPassaggiVariabili=1;
		if(p==null) {
			tracciatureFunzioni=new ArrayList<TracciaDati>();
		}
		else {
			tracciatureFunzioni=null;
		}
		prec=p;		
	}
	
	public ArrayList<TracciaDati> getTracciatureFunzioni(){
		return tracciatureFunzioni;
	}
	
	public void addNuovaFunzione(TracciaDati func) {
		if(tracciatureFunzioni!=null) {
			tracciatureFunzioni.add(func);
		}
	}
	
	public TracciaDati getPrec() {
		return prec;
	}
	public void aggiungiNuovaVariabile(String variabile,String espressione) {
		tabellaVariabili.put(variabile,espressione);
	}
	
	public void aggiornaEspressioneNonUsate() {
		Enumeration<String> variabili=tabellaVariabili.keys();
		while(variabili.hasMoreElements()) {
			String variabile=variabili.nextElement();
			String valore=tabellaVariabili.get(variabile);
			if(valore.length()<contaPassaggiVariabili) {
				valore=valore+"-";
				tabellaVariabili.replace(variabile,valore);
			}
		}
	}

	public void aggiornaEspressione(String variabileDaCambiare,String espr) {
		if(tabellaVariabili.containsKey(variabileDaCambiare)) {
			String valore=tabellaVariabili.get(variabileDaCambiare);
			valore=valore+espr;
			tabellaVariabili.replace(variabileDaCambiare,valore);
		}
	}

	public int incrementaNodi() {
		contaNodi++;
		return contaNodi;
	}
	
	public void incrementaPassaggi() {
		contaPassaggiVariabili++;
	}

	public Hashtable<String,String> getTabella(){
		return tabellaVariabili;
	}
	
	public boolean analisiAnomalie(String espressione) {
		char [] valori=espressione.toCharArray();
		char lastC=valori[0];
		for(int i=1;i<valori.length;i++) {
			switch(valori[i]){
			case 'd':
				if(lastC=='d') {
					return false;
				}
				lastC=valori[i];
				break;
			case 'u':
				if(lastC=='u'){
					return false;
				}
				if(lastC=='a') {
					return false;
				}
				lastC=valori[i];
				break;
			}
		}
		return true;
	}
	
	public void eseguiReachingDefinition(PrintWriter c,String variabile,String espressione) {
		c.println("La variabile " + variabile + " :");
		char [] valori=espressione.toCharArray();
		boolean flag=true;
		for(int i=0;i<valori.length;i++) {
			switch(valori[i]){
			case 'd':
				c.println("        Al nodo " +(i+1)+ " alla variabile viene fatta una definizione ");
				flag=false;
				break;
			case 'u':
				if(flag) {
					c.println("        Al nodo " + (i+1)+ " la variabile " + variabile + " viene usata prima di essere definita." );
				}
				else {
					c.println("        , raggiunge gli usi di " + variabile + " al nodo "+ (i+1));
				}
				break;
			case 'a':
				c.println("        Al nodo " +(i+1)+ " la variabile viene dichiarata." );
				break;
			}
		}
	}

	public int getContaPassaggi() {
		return contaPassaggiVariabili;
	}
}