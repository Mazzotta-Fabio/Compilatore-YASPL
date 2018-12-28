package cup.example;

import java.io.*;

import java_cup.runtime.*;
import javax.xml.stream.*;
import yapl2.Programma;
import drawTree.DrawTree;
import esercitazione5_COMP.*;

class Driver {
	
	public static void main(String[] args) throws Exception {
		File f=new File("input.txt");
		if(f.exists()){
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
			XMLStreamWriter sw = outFactory.createXMLStreamWriter(new FileOutputStream("mioFile.xml"),"utf-8");
			sw.writeStartDocument("utf-8","1.0");
			Programma prog=(Programma)parse_tree.value;
			DrawTree syntax_tree=new DrawTree(sw,prog);
			syntax_tree.drawTree();
			sw.writeEndDocument();
			
			//analizzatore Semantico
			AnalizzatoreSemantico seman=new AnalizzatoreSemantico(prog);
			prog=seman.doScoping();
			
			//disegniamo l'albero sintattico dopo l'analisi semantica
			XMLOutputFactory outFactory2=XMLOutputFactory.newInstance();
			XMLStreamWriter sw2 = outFactory2.createXMLStreamWriter(new FileOutputStream("ASTsemantic.xml"),"utf-8");
			sw2.writeStartDocument("utf-8", "1.0");
			DrawTree semantic_syntax_tree=new DrawTree(sw2,prog);
			semantic_syntax_tree.drawTree();
			sw2.writeEndDocument();
			
			//generazione Codice
			File source=new File("sourceC.c");
			FileWriter file=new FileWriter(source);
			PrintWriter out=new PrintWriter(file,true);
			GeneratoreCodice gen=new GeneratoreCodice(prog,out);
			gen.makeSource();
			
		}
		else{
			System.out.println("File not Found");
		}
	}
}