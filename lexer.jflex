package cup.example;

import java_cup.runtime.*;
import java.util.ArrayList;

%%

%class Lexer
%implements sym
%public
%unicode
%cup


%{
    private StringBuffer stringBuf=new StringBuffer();
	private ArrayList<Symbol> symbolTable=new ArrayList<Symbol>();
    private ComplexSymbolFactory symbolFactory;
    
    public Lexer(java.io.InputStream is,ComplexSymbolFactory sf){
		this(is);
        symbolFactory = sf;
    }
    
	public Lexer(java.io.Reader reader,ComplexSymbolFactory sf){
		this(reader);
        symbolFactory = sf;
    }
    
    protected void emit_warning(String message){
        System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    //invocato quando deve essere inserito un token dentro la tabella dei simboli
	private void installID(Symbol s){
	    boolean flag=true;
		for(Symbol t: symbolTable){
			if(((t.toString()).equals(s.toString()))&&(t.value==s.value)){
			    flag=false;
			    break;
			}
		}
		if(flag){
		    symbolTable.add(s);
		}
	}
%}
WhiteSpace = " "
Tab = "\t"
NewLine = "\n"|"\r"

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" {CommentContent} \*+ "/"
EndOfLineComment = "//" [^\r\n]* {NewLine}
CommentContent = ( [^*] | \*+[^*/] )* //scorre uno o più caratteri eccetto un * o più * seguito da caratteri che non sia un (/)

//definizione macro id
Name = [a-zA-Z0-9_]*
Int_Const = [0-9]*
Double_Const = [0-9.]*


%eofval{
    return symbolFactory.newSymbol("EOF",EOF);
%eofval}

%state STRING

%%  
<YYINITIAL> "true" { Symbol s=symbolFactory.newSymbol("TRUE",TRUE,new Boolean(Boolean.parseBoolean(yytext()))); installID(s); return s; }
<YYINITIAL> "false" { Symbol s=symbolFactory.newSymbol("FALSE",FALSE, new Boolean(Boolean.parseBoolean(yytext()))); installID(s); return s; }
<YYINITIAL> "if" { Symbol s=symbolFactory.newSymbol("IF",IF); installID(s); return s; }
<YYINITIAL> "bool" { Symbol s=symbolFactory.newSymbol("BOOL",BOOL,new String("bool")); installID(s); return s; }
<YYINITIAL> "double" { Symbol s=symbolFactory.newSymbol("DOUBLE",DOUBLE,new String("double")); installID(s); return s; }
<YYINITIAL> "int" { Symbol s=symbolFactory.newSymbol("INT",INT,new String("int")); installID(s); return s; }
<YYINITIAL> "then" { Symbol s=symbolFactory.newSymbol("THEN",THEN); installID(s); return s; }
<YYINITIAL> "while" { Symbol s=symbolFactory.newSymbol("WHILE",WHILE); installID(s); return s; }
<YYINITIAL> "do" { Symbol s=symbolFactory.newSymbol("DO",DO); installID(s); return s; }
<YYINITIAL> "else" { Symbol s=symbolFactory.newSymbol("ELSE",ELSE); installID(s); return s; }
<YYINITIAL> "not" { Symbol s=symbolFactory.newSymbol("NOT",NOT); installID(s); return s; }
<YYINITIAL> "head" { Symbol s=symbolFactory.newSymbol("HEAD",HEAD); installID(s); return s; }
<YYINITIAL> "start" { Symbol s=symbolFactory.newSymbol("START",START); installID(s); return s; }
<YYINITIAL> "def" { Symbol s=symbolFactory.newSymbol("DEF",DEF); installID(s); return s; }

<YYINITIAL>{
	"=" { Symbol s=symbolFactory.newSymbol("ASSIGN",ASSIGN); installID(s); return s; }
	";" { Symbol s=symbolFactory.newSymbol("SEMI",SEMI); installID(s); return s; }
	"," { Symbol s=symbolFactory.newSymbol("COMMA",COMMA); installID(s); return s; }
	")" { Symbol s=symbolFactory.newSymbol("RPAR",RPAR); installID(s); return s; }
	"(" { Symbol s=symbolFactory.newSymbol("LPAR",LPAR); installID(s); return s; }
	":" { Symbol s=symbolFactory.newSymbol("COLON",COLON); installID(s); return s; }
	"{" { Symbol s=symbolFactory.newSymbol("LGPAR",LGPAR); installID(s); return s; }
	"}" { Symbol s=symbolFactory.newSymbol("RGPAR",RGPAR); installID(s); return s; }
	"->" { Symbol s=symbolFactory.newSymbol("WRITE",WRITE); installID(s); return s; }
	"<-" { Symbol s=symbolFactory.newSymbol("READ",READ); installID(s); return s; }
	"+" { Symbol s=symbolFactory.newSymbol("PLUS",PLUS,new Integer(PLUS)); installID(s); return s; }
	"-" { Symbol s=symbolFactory.newSymbol("MINUS",MINUS,new Integer(MINUS)); installID(s); return s; }
	"*" { Symbol s=symbolFactory.newSymbol("TIMES",TIMES,new Integer(TIMES)); installID(s); return s; }
	"/" { Symbol s=symbolFactory.newSymbol("DIV",DIV,new Integer(DIV)); installID(s); return s; }
	">" { Symbol s=symbolFactory.newSymbol("GT",GT,new Integer(GT)); installID(s); return s; }
	">=" { Symbol s=symbolFactory.newSymbol("GE",GE,new Integer(GE)); installID(s); return s; }
	"<" { Symbol s=symbolFactory.newSymbol("LT",LT,new Integer(LT)); installID(s); return s; }
	"<=" { Symbol s=symbolFactory.newSymbol("LE",LE,new Integer(LE)); installID(s); return s; }
	"==" { Symbol s=symbolFactory.newSymbol("EQ",EQ,new Integer(EQ)); installID(s); return s; }
	"&&" { Symbol s=symbolFactory.newSymbol("AND",AND,new Integer(AND)); installID(s); return s; }
	"||" { Symbol s=symbolFactory.newSymbol("OR",OR,new Integer(OR)); installID(s); return s; }
	{WhiteSpace} {}
	{NewLine} {}
	{Tab} {}
	{Comment} {}
	{Int_Const} { Symbol s=symbolFactory.newSymbol("INT_CONST",INT_CONST,new Integer(Integer.parseInt(yytext()))); installID(s); return s; }
	{Name} { Symbol s=symbolFactory.newSymbol("NAME",NAME,new String(yytext())); installID(s); return s; } 
	{Double_Const} { Symbol s=symbolFactory.newSymbol("DOUBLE_CONST",DOUBLE_CONST, new Double(Double.parseDouble(yytext()))); installID(s); return s; }
	"\"" { stringBuf.setLength(0); yybegin(STRING); }
	"-" { Symbol s=symbolFactory.newSymbol("UMINUS",UMINUS); installID(s); return s;}
}
<STRING>{

   [^\n\r\"\\]+ { stringBuf.append(yytext()); } //scorre tutti i caratteri dal prossimo backslach (\n) , doppi apici o ritorno a capo
   {WhiteSpace} { stringBuf.append(" "); }
   \\t { stringBuf.append("\t"); }
   \\n { stringBuf.append("\n"); }
   \\r { stringBuf.append("\r"); }
   {WhiteSpace} { stringBuf.append(" "); }
   "\"" { yybegin(YYINITIAL); Symbol s=symbolFactory.newSymbol("STRING_CONST",STRING_CONST,stringBuf.toString()); installID(s); return s; }
   
}

// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
