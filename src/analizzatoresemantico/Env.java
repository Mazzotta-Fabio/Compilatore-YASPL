package analizzatoresemantico;

import java.util.*;
/*
 * Rappresenta un ambiente di visibilità(scope)
 */
public class Env {
	private Hashtable<String,String> table;
	private Env prec;//rappresenta la tabella con cui è legato.
	private String function;
	private int contaArg;
	private ArrayList<String> numArgomenti;
	/**
	 * Crea una nuova tabella ogni volta trova un nuovo scope
	 * @param p:indica l'elemento con cui è puntato la nuova tabella
	 */
	public Env(Env p){
		table=new Hashtable<String,String>();
		prec=p;
		function=null;
		contaArg=0;
		numArgomenti=new ArrayList<String>();
	}
	/**
	 * metodo utilizzato per aggiungere un nuovo elemento alla tabella corrente.
	 * @param type indica il tipo di dato
	 * @param name indica il nome della varibile.
	 */
	public void addVariabile(String type,String name){
		if(function!=null){
			contaArg++;
			name=name+contaArg;
		}
		/*
		 * key(K): name
		 * value(V): type
		 */
		table.put(name, type);
	}
	/**
	 * metodo utilizzato per ricercare un elemento 
	 * @param l'identificatore da ricercare
	 * @return il tipo dell'elemento
	 */
	public String getElementTables(String s){
		for(Env e=this;e!=null;e=e.prec){
			String found=(e.table.get(s));//ritorna il tipo di dato(value)
			if(found!=null){
				return found;
			}
		}
		return null;
	}
	/**
	 * metodo utilizzato per verificare l'esistenza di un identificatore locale all'interno della tabella locale
	 * @param s:l'identificatore da ricercare
	 * @return 
	 */
	public boolean getElementLocal(String s){
		if(table.containsKey(s)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * metodo che verifica se già esiste una funzione all'interno di uno scope
	 * @param s il nome della funzione
	 * @return l'esito booleano del confronto
	 */
	public boolean existFunction(String s){
		if(table.containsValue(s)){
			return false;
		}
		else{
			if(prec==null){
				return true;
			}
			else {
				if(prec.table.containsValue(s)) {
					return false;
				}
				else {
					return true;
				}
			}
		}
	}
	/**
	 * Restituisce il nome della funzione a cui appartiene lo scope.
	 * @return il nome della funzione
	 */
	public String getFunction(){
		return function;
	}
	
	public void setFunction(String function){
		if(function==null){
			numArgomenti.add(this.function+contaArg);
			contaArg=0;
			if(prec!=null) {
				prec.numArgomenti.add(this.function+contaArg);
			}
		}
		this.function=function;
	}
	
	public boolean getNumArgomenti(String s){
		for(String arg:numArgomenti){
			if(arg.equals(s)){
				return true;
			}
		}
		if(prec!=null) {
			for(String arg2:prec.numArgomenti){
				if(arg2.equals(s)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean controllaNomeFunzione(String nome) {
		if(table.containsKey(nome)) {
			return true;
		}
		if(prec==null) {
			return false;
		}
		else {
			if(prec.table.containsValue(nome)) {
				return true;
			}
		}
		return false;
	}
	public Env getEnv(){
		return prec;
	}
}