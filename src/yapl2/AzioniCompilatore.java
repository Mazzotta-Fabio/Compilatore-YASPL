package yapl2;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;

import analizzatoresemantico.Env;
import toolmanutenzione.TracciaDati;

public interface AzioniCompilatore {
	public void startScoping(Env e);
	public void scriviCodice(PrintWriter c)throws Exception;
	public void drawComponent(XMLStreamWriter s)throws Exception;
	public void controlFlowDati(TracciaDati t) throws Exception;
	public void drawNode(XMLStreamWriter x,TracciaDati c)throws Exception;
}
