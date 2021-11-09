package yasplcompiler;

import java.io.*;
import java_cup.runtime.*;
import scopehandler.Env;
import javax.xml.stream.*;
import astcomponent.Programma;

public class Driver {
	
	public static void main(String[] args)  {
		try {
			File f=new File("sorgentiYASPL2/areacerchio.txt");
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
				GraphGenerator graphGenerator=new GraphGenerator();
				graphGenerator.getControlFlowGraph(prog);
				//stampiamo il grafo con graphViz
				File filegrafo=new File("grafo.dot");
				FileWriter filewriter=new FileWriter(filegrafo);
				PrintWriter printer=new PrintWriter(filewriter,true);
				graphGenerator.disegnaCFG(printer);
				//facciamo reaching definition
				if(!(graphGenerator.eseguiReachingDefinition("c"))) {
					System.out.println("La variabile non esiste");
				}
				else {
					graphGenerator.tracciaCammini();
					//disegniamo i cammini
					File filegrafo1=new File("cammini.dot");
					FileWriter filewriter1=new FileWriter(filegrafo1);
					PrintWriter printer1=new PrintWriter(filewriter1,true);
					graphGenerator.disegnaCFG(printer1);
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