package cup.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java_cup.runtime.*;
import toolmanutenzione.TracciaDati;
import yaspl2.Programma;

import javax.xml.stream.*;
import analizzatoresemantico.Env;

public class Driver {
	
	public static void main(String[] args)  {
		try {
			File f=new File("sorgentiYASPL2/yasplIGES.txt");
			if(f.exists()){
				/*
				 * parte progetto Compilatori
				 */
				//sorgente
				FileInputStream in=new FileInputStream(f);
				ComplexSymbolFactory cs=new ComplexSymbolFactory();
				Lexer l=new Lexer(in,cs);
				ScannerBuffer lexer = new ScannerBuffer(l);
				Parser parser=new Parser(lexer,cs);
				//analizzatore Lessicale
				/*
				Symbol token=parser.scan();
				while(token.sym!=sym.EOF){
					if(token.value==null){
						System.out.println(token.toString());
					}
					else{
						System.out.println(token.toString() + " value: " + token.value);
					}
					token=parser.scan();
				}
				*/
				
				//analizzatore Sintattico
				Symbol parse_tree=parser.parse();
				System.out.println(parse_tree.value);
				
				//stampiamo l'albero sintattico su xml
				XMLOutputFactory outFactory=XMLOutputFactory.newInstance();
				XMLStreamWriter sw = outFactory.createXMLStreamWriter(new FileOutputStream("fileXML/AST.xml"),"utf-8");
				sw.writeStartDocument("utf-8","1.0");
				Programma prog=(Programma)parse_tree.value;
				prog.drawComponent(sw);
				sw.writeEndDocument();
			
				//analizzatore Semantico
				Env e=new Env(null);
				prog.startScoping(e);
			
				//disegniamo l'albero sintattico dopo l'analisi semantica
				XMLOutputFactory outFactory2=XMLOutputFactory.newInstance();
				XMLStreamWriter sw2 = outFactory2.createXMLStreamWriter(new FileOutputStream("fileXML/ASTsemantic.xml"),"utf-8");
				sw2.writeStartDocument("utf-8", "1.0");
				prog.drawComponent(sw2);
				sw2.writeEndDocument();
				
				//generazione Codice
				File source=new File("sourceC.c");
				FileWriter file=new FileWriter(source);
				PrintWriter out=new PrintWriter(file,true);
				prog.scriviCodice(out);
				
				/*
				 * parte progetto IGES (tool manutenzione)
				 */
				//control flow Graph
				TracciaDati tracciatura=new TracciaDati(null);
				XMLOutputFactory outFactory3=XMLOutputFactory.newInstance();
				XMLStreamWriter sw3 = outFactory3.createXMLStreamWriter(new FileOutputStream("fileXML/controlGraph.xml"),"utf-8");
				sw3.writeStartDocument("utf-8", "1.0");
				prog.drawNode(sw3, tracciatura);
				sw3.writeEndDocument();
				
				//analisi flusso dati
				File reportFile=new File("analisiReport.txt");
				FileWriter out2=new FileWriter(reportFile);
				PrintWriter stampa=new PrintWriter(out2,true);
				prog.controlFlowDati(tracciatura);
				Hashtable<String,String> tabella=tracciatura.getTabella(); 
				Enumeration<String> variabili= tabella.keys();
				while(variabili.hasMoreElements()) {
					String variabile=variabili.nextElement();
					String espressione=tabella.get(variabile);
					if(tracciatura.analisiAnomalie(espressione)) {
						System.out.println("Variabile: "+ variabile + " Espressione: " + espressione + " Anomalie: non presenti!");
					}
					else {
						System.out.println("Variabile: "+ variabile + " Espressione: " + espressione + " Anomalie: presenti!");
					}
					tracciatura.eseguiReachingDefinition(stampa,variabile,espressione);
				}
				ArrayList<TracciaDati> tracciatureFunzioni=tracciatura.getTracciatureFunzioni();
				if(tracciatureFunzioni.size()>0){
					System.out.println("\nAnalisi del flusso effettuate dentro le funzioni:\n");
					for(TracciaDati t:tracciatureFunzioni) {
						Hashtable<String,String> tabellaFunzione=t.getTabella(); 
						Enumeration<String> variabili1= tabellaFunzione.keys();
						while(variabili1.hasMoreElements()) {
							String variabile=variabili1.nextElement();
							String espressione=tabellaFunzione.get(variabile);
							if(tracciatura.analisiAnomalie(espressione)) {
								System.out.println("Variabile: "+ variabile + " Espressione: " + espressione + " Anomalie: non presenti!");
							}
							else {
								System.out.println("Variabile: "+ variabile + " Espressione: " + espressione + " Anomalie: presenti!");
							}
							tracciatura.eseguiReachingDefinition(stampa,variabile,espressione);
						}
					}
				}
			}
			else{
				System.out.println("File not Found");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}