package esercitazione5_COMP;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;

import toolManutenzione.TracciaDati;

public interface AzioniCompilatore {
	public void startScoping(Env e);
	public void scriviCodice(PrintWriter c)throws Exception;
	public void drawComponent(XMLStreamWriter s)throws Exception;
	public void controlFlowDati(TracciaDati t) throws Exception;
}
