package esercitazione5_COMP;

import java.io.PrintWriter;
import yapl2.Programma;

public class GeneratoreCodice {
	private Programma programma;
	private PrintWriter sourceC;
	
	public GeneratoreCodice(Programma programma,PrintWriter sourceC){
		this.programma=programma;
		this.sourceC=sourceC;
	}
	public void makeSource()throws Exception{
		programma.scriviCodice(sourceC);
	}
}
