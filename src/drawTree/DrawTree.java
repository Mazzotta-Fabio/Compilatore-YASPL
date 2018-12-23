package drawTree;

import javax.xml.stream.XMLStreamWriter;
import yapl2.Programma;

public class DrawTree {
	private XMLStreamWriter fileXml;
	private Programma programma;
	
	public DrawTree(XMLStreamWriter fileXml,Programma programma){
		this.fileXml=fileXml;
		this.programma=programma;
	}
	
	public void drawTree()throws Exception{
		programma.drawComponent(fileXml);
	}
}
