/* The following code was generated by JFlex 1.3.5 on 18/01/18 0.00 */

package cup.example;

import java_cup.runtime.*;
import java.util.ArrayList;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.3.5
 * on 18/01/18 0.00 from the specification file
 * <tt>file:/C:/Users/Desktop/workspace/Esercitazione4_COMP/lexer.jflex</tt>
 */
public class Lexer implements sym, java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  final public static int YYEOF = -1;

  /** initial size of the lookahead buffer */
  final private static int YY_BUFFERSIZE = 16384;

  /** lexical states */
  final public static int STRING = 1;
  final public static int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  final private static String yycmap_packed = 
    "\11\0\1\1\1\2\2\0\1\2\22\0\1\1\1\0\1\45\3\0"+
    "\1\43\1\0\1\33\1\32\1\4\1\42\1\31\1\37\1\7\1\3"+
    "\12\6\1\34\1\30\1\41\1\27\1\40\2\0\32\5\1\0\1\46"+
    "\2\0\1\5\1\0\1\15\1\21\1\5\1\23\1\13\1\14\1\5"+
    "\1\25\1\20\2\5\1\16\1\5\1\24\1\22\2\5\1\11\1\17"+
    "\1\10\1\12\1\5\1\26\3\5\1\35\1\44\1\36\uff82\0";

  /** 
   * Translates characters to character classes
   */
  final private static char [] yycmap = yy_unpack_cmap(yycmap_packed);

  /** 
   * Translates a state to a row index in the transition table
   */
  final private static int yy_rowMap [] = { 
        0,    39,    78,    78,   117,    78,   156,   195,   234,   273, 
      312,   351,   390,   429,   468,   507,   546,   585,   624,   663, 
       78,    78,    78,    78,    78,    78,    78,   702,   741,   780, 
       78,   819,   858,    78,   897,    78,   936,   975,  1014,  1053, 
     1092,  1131,  1170,  1209,   156,  1248,  1287,  1326,  1365,  1404, 
     1443,  1482,    78,    78,    78,    78,    78,    78,    78,    78, 
       78,    78,  1521,  1560,  1599,  1638,  1677,  1716,   156,  1755, 
      156,  1794,   156,  1833,  1872,   156,   156,   156,  1911,  1950, 
      156,  1989,   156,  2028,   156,   156,  2067,   156,   156
  };

  /** 
   * The packed transition table of the DFA (part 0)
   */
  final private static String yy_packed0 = 
    "\1\3\2\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\2\7\1\13\1\14\2\7\1\15\1\16\1\17\1\7"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\3\2\43\1\3\42\43\1\44"+
    "\1\45\52\0\1\46\1\47\47\0\2\7\1\0\17\7"+
    "\25\0\1\7\1\10\1\11\17\7\26\0\2\11\44\0"+
    "\2\7\1\0\1\7\1\50\13\7\1\51\1\7\25\0"+
    "\2\7\1\0\6\7\1\52\10\7\25\0\2\7\1\0"+
    "\5\7\1\53\11\7\25\0\2\7\1\0\1\54\16\7"+
    "\25\0\2\7\1\0\4\7\1\55\7\7\1\56\2\7"+
    "\25\0\2\7\1\0\12\7\1\57\4\7\25\0\2\7"+
    "\1\0\3\7\1\60\6\7\1\61\4\7\25\0\2\7"+
    "\1\0\12\7\1\62\4\7\25\0\2\7\1\0\3\7"+
    "\1\63\13\7\25\0\2\7\1\0\15\7\1\64\1\7"+
    "\47\0\1\65\57\0\1\66\35\0\1\67\46\0\1\70"+
    "\7\0\1\71\52\0\1\72\47\0\1\73\2\0\2\43"+
    "\1\0\42\43\12\0\1\74\1\75\12\0\1\76\22\0"+
    "\2\46\1\4\44\46\4\47\1\77\42\47\5\0\2\7"+
    "\1\0\2\7\1\100\14\7\25\0\2\7\1\0\3\7"+
    "\1\101\13\7\25\0\2\7\1\0\7\7\1\102\7\7"+
    "\25\0\2\7\1\0\6\7\1\103\10\7\25\0\2\7"+
    "\1\0\5\7\1\104\11\7\25\0\2\7\1\0\1\105"+
    "\16\7\25\0\2\7\1\0\12\7\1\106\4\7\25\0"+
    "\2\7\1\0\4\7\1\107\12\7\25\0\2\7\1\0"+
    "\2\7\1\110\14\7\25\0\2\7\1\0\1\111\16\7"+
    "\25\0\2\7\1\0\5\7\1\112\11\7\25\0\2\7"+
    "\1\0\10\7\1\113\6\7\20\0\3\47\1\4\1\77"+
    "\42\47\5\0\2\7\1\0\3\7\1\114\13\7\25\0"+
    "\2\7\1\0\14\7\1\115\2\7\25\0\2\7\1\0"+
    "\3\7\1\116\13\7\25\0\2\7\1\0\7\7\1\117"+
    "\7\7\25\0\2\7\1\0\1\7\1\120\15\7\25\0"+
    "\2\7\1\0\6\7\1\121\10\7\25\0\2\7\1\0"+
    "\11\7\1\122\5\7\25\0\2\7\1\0\13\7\1\123"+
    "\3\7\25\0\2\7\1\0\6\7\1\124\10\7\25\0"+
    "\2\7\1\0\3\7\1\125\13\7\25\0\2\7\1\0"+
    "\1\126\16\7\25\0\2\7\1\0\6\7\1\127\10\7"+
    "\25\0\2\7\1\0\3\7\1\130\13\7\25\0\2\7"+
    "\1\0\3\7\1\131\13\7\20\0";

  /** 
   * The transition table of the DFA
   */
  final private static int yytrans [] = yy_unpack();


  /* error codes */
  final private static int YY_UNKNOWN_ERROR = 0;
  final private static int YY_ILLEGAL_STATE = 1;
  final private static int YY_NO_MATCH = 2;
  final private static int YY_PUSHBACK_2BIG = 3;

  /* error messages for the codes above */
  final private static String YY_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Internal error: unknown state",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * YY_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private final static byte YY_ATTRIBUTE[] = {
     1,  0,  9,  9,  1,  9,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 
     1,  1,  1,  1,  9,  9,  9,  9,  9,  9,  9,  1,  1,  1,  9,  1, 
     1,  9,  1,  9,  1,  0,  0,  1,  1,  1,  1,  1,  1,  1,  1,  1, 
     1,  1,  1,  1,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  1, 
     1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 
     1,  1,  1,  1,  1,  1,  1,  1,  1
  };

  /** the input device */
  private java.io.Reader yy_reader;

  /** the current state of the DFA */
  private int yy_state;

  /** the current lexical state */
  private int yy_lexical_state = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char yy_buffer[] = new char[YY_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int yy_markedPos;

  /** the textposition at the last state to be included in yytext */
  private int yy_pushbackPos;

  /** the current text position in the buffer */
  private int yy_currentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int yy_startRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int yy_endRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn; 

  /** 
   * yy_atBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean yy_atBOL = true;

  /** yy_atEOF == true <=> the scanner is at the EOF */
  private boolean yy_atEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean yy_eof_done;

  /* user code: */
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.yy_reader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the split, compressed DFA transition table.
   *
   * @return the unpacked transition table
   */
  private static int [] yy_unpack() {
    int [] trans = new int[2106];
    int offset = 0;
    offset = yy_unpack(yy_packed0, offset, trans);
    return trans;
  }

  /** 
   * Unpacks the compressed DFA transition table.
   *
   * @param packed   the packed transition table
   * @return         the index of the last entry
   */
  private static int yy_unpack(String packed, int offset, int [] trans) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do trans[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] yy_unpack_cmap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 118) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   IOException  if any I/O-Error occurs
   */
  private boolean yy_refill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (yy_startRead > 0) {
      System.arraycopy(yy_buffer, yy_startRead, 
                       yy_buffer, 0, 
                       yy_endRead-yy_startRead);

      /* translate stored positions */
      yy_endRead-= yy_startRead;
      yy_currentPos-= yy_startRead;
      yy_markedPos-= yy_startRead;
      yy_pushbackPos-= yy_startRead;
      yy_startRead = 0;
    }

    /* is the buffer big enough? */
    if (yy_currentPos >= yy_buffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[yy_currentPos*2];
      System.arraycopy(yy_buffer, 0, newBuffer, 0, yy_buffer.length);
      yy_buffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = yy_reader.read(yy_buffer, yy_endRead, 
                                            yy_buffer.length-yy_endRead);

    if (numRead < 0) {
      return true;
    }
    else {
      yy_endRead+= numRead;  
      return false;
    }
  }


  /**
   * Closes the input stream.
   */
  final public void yyclose() throws java.io.IOException {
    yy_atEOF = true;            /* indicate end of file */
    yy_endRead = yy_startRead;  /* invalidate buffer    */

    if (yy_reader != null)
      yy_reader.close();
  }


  /**
   * Closes the current stream, and resets the
   * scanner to read from a new input stream.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>YY_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  final public void yyreset(java.io.Reader reader) throws java.io.IOException {
    yyclose();
    yy_reader = reader;
    yy_atBOL  = true;
    yy_atEOF  = false;
    yy_endRead = yy_startRead = 0;
    yy_currentPos = yy_markedPos = yy_pushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    yy_lexical_state = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  final public int yystate() {
    return yy_lexical_state;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  final public void yybegin(int newState) {
    yy_lexical_state = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  final public String yytext() {
    return new String( yy_buffer, yy_startRead, yy_markedPos-yy_startRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  final public char yycharat(int pos) {
    return yy_buffer[yy_startRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  final public int yylength() {
    return yy_markedPos-yy_startRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void yy_ScanError(int errorCode) {
    String message;
    try {
      message = YY_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = YY_ERROR_MSG[YY_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  private void yypushback(int number)  {
    if ( number > yylength() )
      yy_ScanError(YY_PUSHBACK_2BIG);

    yy_markedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void yy_do_eof() throws java.io.IOException {
    if (!yy_eof_done) {
      yy_eof_done = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int yy_input;
    int yy_action;

    // cached fields:
    int yy_currentPos_l;
    int yy_startRead_l;
    int yy_markedPos_l;
    int yy_endRead_l = yy_endRead;
    char [] yy_buffer_l = yy_buffer;
    char [] yycmap_l = yycmap;

    int [] yytrans_l = yytrans;
    int [] yy_rowMap_l = yy_rowMap;
    byte [] yy_attr_l = YY_ATTRIBUTE;

    while (true) {
      yy_markedPos_l = yy_markedPos;

      yy_action = -1;

      yy_startRead_l = yy_currentPos_l = yy_currentPos = 
                       yy_startRead = yy_markedPos_l;

      yy_state = yy_lexical_state;


      yy_forAction: {
        while (true) {

          if (yy_currentPos_l < yy_endRead_l)
            yy_input = yy_buffer_l[yy_currentPos_l++];
          else if (yy_atEOF) {
            yy_input = YYEOF;
            break yy_forAction;
          }
          else {
            // store back cached positions
            yy_currentPos  = yy_currentPos_l;
            yy_markedPos   = yy_markedPos_l;
            boolean eof = yy_refill();
            // get translated positions and possibly new buffer
            yy_currentPos_l  = yy_currentPos;
            yy_markedPos_l   = yy_markedPos;
            yy_buffer_l      = yy_buffer;
            yy_endRead_l     = yy_endRead;
            if (eof) {
              yy_input = YYEOF;
              break yy_forAction;
            }
            else {
              yy_input = yy_buffer_l[yy_currentPos_l++];
            }
          }
          int yy_next = yytrans_l[ yy_rowMap_l[yy_state] + yycmap_l[yy_input] ];
          if (yy_next == -1) break yy_forAction;
          yy_state = yy_next;

          int yy_attributes = yy_attr_l[yy_state];
          if ( (yy_attributes & 1) == 1 ) {
            yy_action = yy_state; 
            yy_markedPos_l = yy_currentPos_l; 
            if ( (yy_attributes & 8) == 8 ) break yy_forAction;
          }

        }
      }

      // store back cached position
      yy_markedPos = yy_markedPos_l;

      switch (yy_action) {

        case 33: 
          {  stringBuf.setLength(0); yybegin(STRING);  }
        case 90: break;
        case 80: 
          {  Symbol s=symbolFactory.newSymbol("BOOL",BOOL,new String("bool")); installID(s); return s;  }
        case 91: break;
        case 4: 
          {  Symbol s=symbolFactory.newSymbol("DIV",DIV,new Integer(DIV)); installID(s); return s;  }
        case 92: break;
        case 30: 
          {  Symbol s=symbolFactory.newSymbol("PLUS",PLUS,new Integer(PLUS)); installID(s); return s;  }
        case 93: break;
        case 57: 
          {  Symbol s=symbolFactory.newSymbol("AND",AND,new Integer(AND)); installID(s); return s;  }
        case 94: break;
        case 44: 
          {  Symbol s=symbolFactory.newSymbol("IF",IF); installID(s); return s;  }
        case 95: break;
        case 48: 
          {  Symbol s=symbolFactory.newSymbol("DO",DO); installID(s); return s;  }
        case 96: break;
        case 8: 
          {  Symbol s=symbolFactory.newSymbol("DOUBLE_CONST",DOUBLE_CONST, new Double(Double.parseDouble(yytext()))); installID(s); return s;  }
        case 97: break;
        case 2: 
        case 31: 
        case 32: 
        case 36: 
          {  emit_warning("Unrecognized character '" +yytext()+"' -- ignored");  }
        case 98: break;
        case 59: 
          {  stringBuf.append("\t");  }
        case 99: break;
        case 60: 
          {  stringBuf.append("\r");  }
        case 100: break;
        case 61: 
          {  stringBuf.append("\n");  }
        case 101: break;
        case 84: 
          {  Symbol s=symbolFactory.newSymbol("FALSE",FALSE, new Boolean(Boolean.parseBoolean(yytext()))); installID(s); return s;  }
        case 102: break;
        case 28: 
          {  Symbol s=symbolFactory.newSymbol("GT",GT,new Integer(GT)); installID(s); return s;  }
        case 103: break;
        case 0: 
        case 7: 
          {  Symbol s=symbolFactory.newSymbol("INT_CONST",INT_CONST,new Integer(Integer.parseInt(yytext()))); installID(s); return s;  }
        case 104: break;
        case 6: 
        case 9: 
        case 10: 
        case 11: 
        case 12: 
        case 13: 
        case 14: 
        case 15: 
        case 16: 
        case 17: 
        case 18: 
        case 39: 
        case 40: 
        case 41: 
        case 42: 
        case 43: 
        case 45: 
        case 46: 
        case 47: 
        case 49: 
        case 50: 
        case 51: 
        case 63: 
        case 64: 
        case 65: 
        case 66: 
        case 67: 
        case 69: 
        case 71: 
        case 73: 
        case 74: 
        case 78: 
        case 79: 
        case 81: 
        case 83: 
        case 86: 
          {  Symbol s=symbolFactory.newSymbol("NAME",NAME,new String(yytext())); installID(s); return s;  }
        case 105: break;
        case 29: 
          {  Symbol s=symbolFactory.newSymbol("LT",LT,new Integer(LT)); installID(s); return s;  }
        case 106: break;
        case 52: 
          {  Symbol s=symbolFactory.newSymbol("EQ",EQ,new Integer(EQ)); installID(s); return s;  }
        case 107: break;
        case 54: 
          {  Symbol s=symbolFactory.newSymbol("GE",GE,new Integer(GE)); installID(s); return s;  }
        case 108: break;
        case 55: 
          {  Symbol s=symbolFactory.newSymbol("LE",LE,new Integer(LE)); installID(s); return s;  }
        case 109: break;
        case 58: 
          {  Symbol s=symbolFactory.newSymbol("OR",OR,new Integer(OR)); installID(s); return s;  }
        case 110: break;
        case 68: 
          {  Symbol s=symbolFactory.newSymbol("INT",INT,new String("int")); installID(s); return s;  }
        case 111: break;
        case 34: 
          {  stringBuf.append(yytext());  }
        case 112: break;
        case 27: 
          {  Symbol s=symbolFactory.newSymbol("MINUS",MINUS,new Integer(MINUS)); installID(s); return s;  }
        case 113: break;
        case 5: 
          {  Symbol s=symbolFactory.newSymbol("TIMES",TIMES,new Integer(TIMES)); installID(s); return s;  }
        case 114: break;
        case 26: 
          {  Symbol s=symbolFactory.newSymbol("RGPAR",RGPAR); installID(s); return s;  }
        case 115: break;
        case 25: 
          {  Symbol s=symbolFactory.newSymbol("LGPAR",LGPAR); installID(s); return s;  }
        case 116: break;
        case 23: 
          {  Symbol s=symbolFactory.newSymbol("LPAR",LPAR); installID(s); return s;  }
        case 117: break;
        case 22: 
          {  Symbol s=symbolFactory.newSymbol("RPAR",RPAR); installID(s); return s;  }
        case 118: break;
        case 72: 
          {  Symbol s=symbolFactory.newSymbol("NOT",NOT); installID(s); return s;  }
        case 119: break;
        case 35: 
          {  yybegin(YYINITIAL); Symbol s=symbolFactory.newSymbol("STRING_CONST",STRING_CONST,stringBuf.toString()); installID(s); return s;  }
        case 120: break;
        case 21: 
          {  Symbol s=symbolFactory.newSymbol("COMMA",COMMA); installID(s); return s;  }
        case 121: break;
        case 77: 
          {  Symbol s=symbolFactory.newSymbol("ELSE",ELSE); installID(s); return s;  }
        case 122: break;
        case 24: 
          {  Symbol s=symbolFactory.newSymbol("COLON",COLON); installID(s); return s;  }
        case 123: break;
        case 87: 
          {  Symbol s=symbolFactory.newSymbol("WHILE",WHILE); installID(s); return s;  }
        case 124: break;
        case 19: 
          {  Symbol s=symbolFactory.newSymbol("ASSIGN",ASSIGN); installID(s); return s;  }
        case 125: break;
        case 53: 
          {  Symbol s=symbolFactory.newSymbol("WRITE",WRITE); installID(s); return s;  }
        case 126: break;
        case 76: 
          {  Symbol s=symbolFactory.newSymbol("THEN",THEN); installID(s); return s;  }
        case 127: break;
        case 88: 
          {  Symbol s=symbolFactory.newSymbol("DOUBLE",DOUBLE,new String("double")); installID(s); return s;  }
        case 128: break;
        case 82: 
          {  Symbol s=symbolFactory.newSymbol("HEAD",HEAD); installID(s); return s;  }
        case 129: break;
        case 20: 
          {  Symbol s=symbolFactory.newSymbol("SEMI",SEMI); installID(s); return s;  }
        case 130: break;
        case 56: 
          {  Symbol s=symbolFactory.newSymbol("READ",READ); installID(s); return s;  }
        case 131: break;
        case 70: 
          {  Symbol s=symbolFactory.newSymbol("DEF",DEF); installID(s); return s;  }
        case 132: break;
        case 75: 
          {  Symbol s=symbolFactory.newSymbol("TRUE",TRUE,new Boolean(Boolean.parseBoolean(yytext()))); installID(s); return s;  }
        case 133: break;
        case 85: 
          {  Symbol s=symbolFactory.newSymbol("START",START); installID(s); return s;  }
        case 134: break;
        case 3: 
          {  }
        case 135: break;
        default: 
          if (yy_input == YYEOF && yy_startRead == yy_currentPos) {
            yy_atEOF = true;
            yy_do_eof();
              {     return symbolFactory.newSymbol("EOF",EOF);
 }
          } 
          else {
            yy_ScanError(YY_NO_MATCH);
          }
      }
    }
  }


}
