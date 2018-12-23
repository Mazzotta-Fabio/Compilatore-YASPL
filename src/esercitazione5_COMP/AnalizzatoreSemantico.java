package esercitazione5_COMP;

import yapl2.Programma;

public class AnalizzatoreSemantico {
	//albero sintattico
	private Programma programma;
	public AnalizzatoreSemantico(Programma programma){
		this.programma=programma;
	}
	public Programma doScoping(){
		Env e=new Env(null);
		programma.startScoping(e);
		return programma;
	}
}
