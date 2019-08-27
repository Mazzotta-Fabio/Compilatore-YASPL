package yaspl2;

import java.io.PrintWriter;

import java.util.List;
import javax.xml.stream.XMLStreamWriter;

import analizzatoresemantico.Env;
import toolmanutenzione.*;

//controllata
public abstract class Body implements AzioniCompilatore{
	//nodo body
	public static class BodyOP extends Body{
		private List<Var_decl> varD;
		private List<Stat> stat;
		public BodyOP(List<Var_decl> varD,List<Stat> stat) {
			this.varD = varD;
			this.stat = stat;
		}
		public String toString(){
			String frase="";
			for(Var_decl v:varD){
				frase=v+frase;
			}
			for(Stat b:stat){
				frase=b+frase;
			}
			return frase + " " + "\n";
		}
		@Override
		public void drawComponent(XMLStreamWriter x) throws Exception{
			x.writeStartElement("BODYOP");
			for(int i=varD.size()-1;i>=0;i--){
				Var_decl g=varD.get(i);
				g.drawComponent(x);
			}
			for(int i=stat.size()-1;i>=0;i--){
				Stat g=stat.get(i);
				g.drawComponent(x);
			}
			x.writeEndElement();
		}
		@Override
		public void scriviCodice(PrintWriter c) throws Exception {
			for(int i=varD.size()-1;i>=0;i--){
				Var_decl g=varD.get(i);
				c.write("     ");
				g.scriviCodice(c);
				c.write(";");
				c.println();
			}
			for(int i=stat.size()-1;i>=0;i--){
				Stat g=stat.get(i);
				g.scriviCodice(c);
			}
		}
		@Override
		public void startScoping(Env e) {
			for(int i=varD.size()-1;i>=0;i--){
				varD.get(i).startScoping(e);
			}
			for(int i=stat.size()-1;i>=0;i--){
				stat.get(i).startScoping(e);
			}
		}
		
		/**
		 * manutenzione
		 */
		
		@Override
		public void drawNode(XMLStreamWriter x,TracciaDati t) throws Exception {
			for(int i=varD.size()-1;i>=0;i--){
				Var_decl var=varD.get(i);
				var.drawNode(x, t);
			}
			for(int i=stat.size()-1;i>=0;i--){
				Stat stat1=stat.get(i);
				stat1.drawNode(x, t);
			}
			x.writeStartElement("NODOFINALEFUNZIONE"+t.incrementaNodi());
			for(int i=stat.size();i>=0;i--){
				x.writeEndElement();
			}
		}
		@Override
		public void controlFlowDati(TracciaDati t) throws Exception {
			if(t.getPrec()!=null) {
				for(int i=varD.size()-1;i>=0;i--){
					Var_decl var=varD.get(i);
					var.controlFlowDati(t);
				}
			}
			for(int i=stat.size()-1;i>=0;i--){
				Stat stat1=stat.get(i);
				stat1.controlFlowDati(t);
			}
		}
	}
	
	public static BodyOP bodyOP(List<Var_decl> varD,List<Stat> stat){
		return new BodyOP(varD,stat);
	}
}
