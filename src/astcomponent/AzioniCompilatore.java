package astcomponent;

import java.io.PrintWriter;
import javax.xml.stream.XMLStreamWriter;
import graphcomponent.Graph;
import scopehandler.Env;

public interface AzioniCompilatore {
	public void startScoping(Env e);
	public void scriviCodice(PrintWriter c)throws Exception;
	public void drawComponent(XMLStreamWriter s)throws Exception;
	public void buildControlFlow(Graph<String> g);
}
