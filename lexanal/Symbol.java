package Proteus.src.compiler.lexanal;

import java.io.*;

import Proteus.src.compiler.*;

public class Symbol implements XMLable {

	private int token;

	private String lexeme;

	private Position position;

	public Symbol(int token, String lexeme, Position position) {
		if ((token < 0) || (token > 44)) Report.error("Internal error.", 1);
		this.token = token;
		this.lexeme = lexeme;
		this.position = position;
	}

	public int getToken() {
		return token;
	}

	public String getLexeme() {
		return lexeme;
	}

	public Position getPosition() {
		return position;
	}
	
	@Override
	public void toXML(PrintStream xml) {
		String lex = toXML(lexeme);
		
		switch (token) {

		case IDENTIFIER : xml.println("<symbol token=\"IDENTIFIER\"  lexeme=\"" + lex + "\">"); position.toXML(xml); xml.println("</symbol>"); break;

		case INTCONST   : xml.println("<symbol token=\"INTCONST\"    lexeme=\"" + lex + "\">"); position.toXML(xml); xml.println("</symbol>"); break;
		case REALCONST  : xml.println("<symbol token=\"REALCONST\"   lexeme=\"" + lex + "\">"); position.toXML(xml); xml.println("</symbol>"); break;
		case BOOLCONST  : xml.println("<symbol token=\"BOOLCONST\"   lexeme=\"" + lex + "\">"); position.toXML(xml); xml.println("</symbol>"); break;
		case STRINGCONST: xml.println("<symbol token=\"STRINGCONST\" lexeme=\"" + lex + "\">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case INT        : xml.println("<symbol token=\"INT\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case REAL       : xml.println("<symbol token=\"REAL\""                        +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case BOOL       : xml.println("<symbol token=\"BOOL\""                        +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case STRING     : xml.println("<symbol token=\"STRING\""                      +   ">"); position.toXML(xml); xml.println("</symbol>"); break;

		case ADD        : xml.println("<symbol token=\"ADD\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case SUB        : xml.println("<symbol token=\"SUB\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case MUL        : xml.println("<symbol token=\"MUL\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case DIV        : xml.println("<symbol token=\"DIV\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case MOD        : xml.println("<symbol token=\"MOD\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case NOT        : xml.println("<symbol token=\"NOT\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case AND        : xml.println("<symbol token=\"AND\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case OR         : xml.println("<symbol token=\"OR\""                          +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case EQU        : xml.println("<symbol token=\"EQU\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case NEQ        : xml.println("<symbol token=\"NEQ\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case LTH        : xml.println("<symbol token=\"LTH\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case GTH        : xml.println("<symbol token=\"GTH\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case LEQ        : xml.println("<symbol token=\"LEQ\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case GEQ        : xml.println("<symbol token=\"GEQ\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case ASSIGN     : xml.println("<symbol token=\"ASSIGN\""                      +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case LPARENT    : xml.println("<symbol token=\"LPARENT\""                     +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case RPARENT    : xml.println("<symbol token=\"RPARENT\""                     +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case LBRACKET   : xml.println("<symbol token=\"LBRACKET\""                    +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case RBRACKET   : xml.println("<symbol token=\"RBRACKET\""                    +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case LBRACE     : xml.println("<symbol token=\"LBRACE\""                      +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case RBRACE     : xml.println("<symbol token=\"RBRACE\""                      +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		case DOT        : xml.println("<symbol token=\"DOT\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case COMMA      : xml.println("<symbol token=\"COMMA\""                       +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case COLON      : xml.println("<symbol token=\"COLON\""                       +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case SEMIC      : xml.println("<symbol token=\"SEMIC\""                       +   ">"); position.toXML(xml); xml.println("</symbol>"); break;

		case ARR        : xml.println("<symbol token=\"ARR\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case ELSE       : xml.println("<symbol token=\"ELSE\""                        +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case FOR        : xml.println("<symbol token=\"FOR\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case FUN        : xml.println("<symbol token=\"FUN\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case IF         : xml.println("<symbol token=\"IF\""                          +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case REC        : xml.println("<symbol token=\"REC\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case THEN       : xml.println("<symbol token=\"THEN\""                        +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case TYP        : xml.println("<symbol token=\"TYP\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case VAR        : xml.println("<symbol token=\"VAR\""                         +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case WHILE      : xml.println("<symbol token=\"WHILE\""                       +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		case WHERE      : xml.println("<symbol token=\"WHERE\""                       +   ">"); position.toXML(xml); xml.println("</symbol>"); break;
		
		}
	}
	
	/** Pripravi predstavitev osnovnega simbola za izpis v XML datoteki.
	 * 
	 * @param lexeme Znakovna predstavitev osnovnega simbola.
	 * @return Predstavitev osnovnega simbola za izpis v XML datoteki.
	 */
	private String toXML(String lexeme) {
		StringBuffer lex = new StringBuffer();
		for (int i = 0; i < lexeme.length(); i++)
			switch (lexeme.charAt(i)) {
			case '\'': lex.append("&#39;"); break;
			case '\"': lex.append("&#34;"); break;
			case '&' : lex.append("&#38;"); break;
			case '<' : lex.append("&#60;"); break;
			case '>' : lex.append("&#62;"); break;
			default  : lex.append(lexeme.charAt(i)); break;
			}
		return lex.toString();
	}
	
	public static final int IDENTIFIER  = 0;
	
	public static final int INTCONST    = 1;
	public static final int REALCONST   = 2;
	public static final int BOOLCONST   = 3;
	public static final int STRINGCONST = 4;
	
	public static final int INT         = 5;
	public static final int REAL        = 6;
	public static final int BOOL        = 7;
	public static final int STRING      = 8;
	
	public static final int ADD         = 9;  // +
	public static final int SUB         = 10; // -
	public static final int MUL         = 11; // *
	public static final int DIV         = 12; // /
	public static final int MOD         = 13; // %
	
	public static final int NOT         = 14; // !
	public static final int AND         = 15; // &
	public static final int OR          = 16; // |
	
	public static final int EQU         = 17; // ==
	public static final int NEQ         = 18; // <>
	public static final int LTH         = 19; // <
	public static final int GTH         = 20; // >
	public static final int LEQ         = 21; // <=
	public static final int GEQ         = 22; // >=
	
	public static final int ASSIGN      = 23; // =
	
	public static final int LPARENT     = 24; // (
	public static final int RPARENT     = 25; // )
	public static final int LBRACKET    = 26; // [
	public static final int RBRACKET    = 27; // ]
	public static final int LBRACE      = 28; // {
	public static final int RBRACE      = 29; // }
	
	public static final int DOT         = 30; // .
	public static final int COMMA       = 31; // ,
	public static final int COLON       = 32; // :
	public static final int SEMIC       = 33; // ;
	
	public static final int ARR         = 34;
	public static final int ELSE        = 35;
	public static final int FOR         = 36;
	public static final int FUN         = 37;
	public static final int IF          = 38;
	public static final int REC         = 39;
	public static final int THEN        = 40;
	public static final int TYP         = 41;
	public static final int VAR         = 42;
	public static final int WHERE       = 43;
	public static final int WHILE       = 44;

}
