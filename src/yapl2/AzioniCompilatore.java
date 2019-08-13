package yapl2;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;

import analizzatoreSemantico.Env;
import toolManutenzione.TracciaDati;

public interface AzioniCompilatore {
	public void startScoping(Env e);
	public void scriviCodice(PrintWriter c)throws Exception;
	public void drawComponent(XMLStreamWriter s)throws Exception;
	public void controlFlowDati(TracciaDati t) throws Exception;
}
