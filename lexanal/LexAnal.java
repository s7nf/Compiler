package Proteus.src.compiler.lexanal;


import java.awt.List;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;

import Proteus.src.compiler.Report;

//** Leksikalni analizator.  */
public class LexAnal {

	 int lastCharP;
	File input;
	RandomAccessFile raf;
	//int lastCharP;
	String lexeme;
	StringBuffer sb = new StringBuffer(20);
	int token;
	int begLine,  begColumn, endLine, endColumn, tempLine, tempColumn;
	/** Ustvari nov leksikalni analizator.  */
	public LexAnal() {
		this.input = null;
		raf = null; 
		lastCharP = 0;
		begLine = begColumn = endLine = endColumn  = tempLine = tempColumn = 1;
		
	}

	/** Odpre datoteko z izvorno kodo programa.
	 * 
	 * @param programName ime datoteke z izvorno kodo programa.
	 * @throws IOException Ce datoteke z izvorno kodo programa ni mogoce odpreti.
	 */
	public void openSourceFile(String programName) throws IOException {
	//	Charset encoding = Charset.defaultCharset();
		this.input = new File(programName);
		raf = new RandomAccessFile(input, "rw");
	}
	
	/** Zapre datoteko z izvorno kodo programa.
	 * 
	 * @throws IOException Ce datoteke z izvorno kodo programa ni mogoce zapreti.
	 */
	public void closeSourceFile() throws IOException {
		this.raf.close();
	}
	
	/** Vrne naslednji osnovni simbol.
	 * 
	 * @return Naslednji osnovni simbol ali <code>null</code> ob koncu datoteke.
	 * @throws IOException Ce je prislo do napake pri branju vhodne datoteke.
	 */
	public Symbol getNextSymbol() throws IOException 
	{	
		Symbol s, prev;
		int c;
		while( (c = getNextChar()) != -1 )
		{
			
			if (c == SPACE) 
			{
				begColumn++;
			}
			else if (c == NEWLINE )
			{
				endLine++; begLine++; begColumn = 1; endColumn = 1;
			}
			else if (c == TILDA || c == BSQ || c == BSL || c == STREHA || c == USC || c == AFNA || c == VPRASAJ || c == DOLLAR ||
					 c == SQ)
			{
				tempColumn = begColumn;
				begColumn = endColumn;
				//Report.error("neveljaven vhod: ", new Position("test.txt", begLine, tempColumn, endLine, endColumn), -1);
				return new Symbol(-1, "hopla", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			}
				
			else if (isLetter(c))
			{

				sb.append((char) c);
				String remaining = readRemainingLetters();
				if (remaining.length() == 0)
				{
					continue;
				}
				else
				{
					sb.append(remaining);
					tempColumn = begColumn;
					begColumn = endColumn;
					s = new Symbol(getTypeOfToken2(sb.toString()), sb.toString(), new Position("test.txt", begLine, tempColumn, endLine, endColumn));
					System.out.println(sb.toString());
					sb.setLength(0);
					return s;
				}
				
			}

			else if (isDigit(c))
			{
				StringBuffer sb = new StringBuffer(20);
				sb.append((char) c);
				sb.append(readRemainingDigits());
				System.out.println(sb.toString());
				tempColumn = begColumn;
				begColumn = endColumn;
				s = new Symbol(1, sb.toString(), new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				sb.setLength(0);
				return s;
			}
			else
			{
				tempColumn = begColumn;
				begColumn = endColumn;
				if (c == 35)
				{
					ommitComment();
				}
				else if (c == 34)
				{
					String str = getStringConst();
					return new Symbol(4, str, new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				}
				else if (c == 43)
					return new Symbol(9, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 45)
					return new Symbol(10, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 42)
					return new Symbol(11, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 47)
					return new Symbol(12, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 37)
					return new Symbol(13, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 33)
					return new Symbol(14, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 38)
					return new Symbol(15, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 124)
					return new Symbol(16, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			/* return EQU (17) || ASSIGN (23) */
			else if (c == 61)
			{
				if (getNextChar() == 61)
					return new Symbol(17, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else
				{
					raf.seek(raf.getFilePointer()-1);
					return new Symbol(23, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				}
			}
			/* return NEQ (18) || LEQ (21) || LTH (19)   */
			else if (c == 60)
			{
				if ((c =getNextChar()) == 62)
					return new Symbol(18, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else if (c == 61)
				{
					return new Symbol(21, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				}
				else
				{
					raf.seek(raf.getFilePointer()-1);
					return new Symbol(19, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				}
			}
			/* return GTH (20) || GEQ (22) */
			else if (c == 62)
			{
				if ((c = getNextChar()) == 61)
					return new Symbol(22, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				else
				{	
					raf.seek(raf.getFilePointer()-1);
					return new Symbol(20, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
				}
				
			}
			else if (c == 40)
				return new Symbol(24, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 41)
				return new Symbol(25, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 91)
				return new Symbol(26, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 93)
				return new Symbol(27, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 123)
				return new Symbol(28, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 125)
				return new Symbol(29, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 46)
				return new Symbol(30, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 44)
				return new Symbol(31, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 58)
				return new Symbol(32, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			else if (c == 59)
				return new Symbol(33, "", new Position("test.txt", begLine, tempColumn, endLine, endColumn));
			}
		}
		return null;
	}
	
	private boolean isWhiteSpace(int i)
	{
		return (i == SPACE) || (i == NEWLINE); 
	}
	
	private void eatWord() throws IOException
	{
		while (! isWhiteSpace(getNextChar()))
			;
	}

	private int getTypeOfToken2(String token)
	{
		Integer type;
		if ((type = TOKENS2.get(token)) != null)
		{
			return (int) type;
		}
		else
			return 0;
	}
	
	private boolean isLetter(int c)
	{
		return ( (c >= 65 && c <= 90 ) || (c >= 97 && c <= 122) );
	}
	
	private boolean isDigit(int c)
	{
			return (c >= 48 && c <= 57);
	}
	
	private String readRemainingDigits() throws IOException
	{
		StringBuffer sb = new StringBuffer();
		int c;
		while (true)
		{
			c = getNextChar();
			if (isDigit(c))
			{
				sb.append((char) c);
			}
			else if (c == DOT)
			{
				sb.append((char) c);
				String remaining = readRealNum();
				if (remaining.length() == 0)
					return "";
				else
					return sb.append(remaining).toString();
			}
			else
			{
				raf.seek(raf.getFilePointer()-1);
				begColumn--;
				break;
			}
		}
		return sb.toString();
	}
	
	private String readRealNum() throws IOException
	{
		StringBuffer sb = new StringBuffer();
		int c;
		
		while (true)
		{
			if (isDigit(c =getNextChar()))
			{
				sb.append((char) c);
			}
			else
				break;
		}
		
		if (c == 101 || c == 69) //e or E
		{
			return "";
		}
		else if (! isWhiteSpace(c))
		{
			System.out.println("error");
			return "";
		}
		else if( c == DOT)
		{
			System.out.println("error");
			return "";
		}
		else
		{
			return sb.toString();
		}
		
	}
	private String readRemainingLetters() throws IOException
	{
		int c;
		StringBuffer sb = new StringBuffer(20);
		while (true)
		{
			c = getNextChar();
			if (isLetter(c) || isDigit(c))
			{
				sb.append((char) c);
			}
			else
			{
				raf.seek(raf.getFilePointer()-1);
				endColumn--;
				break;
			}
		}
		return sb.toString();
	}
	private int readRemainingLetters2() throws IOException
	{
		int c;
		
		while (true)
		{
			c = getNextChar();
			if (isLetter(c) || isDigit(c))
			{
				sb.append((char) c);
			}
			else if (c == TILDA || c == BSQ || c == BSL || c == STREHA || c == USC || c == AFNA || c == VPRASAJ || c == DOLLAR ||
					 c == SQ)
			{
				//raf.seek(raf.getFilePointer()-1);
				return -1;
			}
			else
			{
				raf.seek(raf.getFilePointer()-1);
				return 0;
			}
		}

		//return sb.toString();
	}
	private void ommitComment() throws IOException
	{
		int c;
		while ((c = getNextChar()) != 10)
			;
	}
	private String getStringConst() throws IOException
	{
		int c;
		char ch;
		StringBuilder sb = new StringBuilder(20);
		while ((c = getNextChar()) != 34)
		{
			ch = (char) c;
			sb.append(ch);
		}
		return sb.toString();
	}
	private int getNextChar() throws IOException
	{
		char c;
		int i;
		if ( (i = raf.read()) != -1)
		{
			endColumn++;
			return i;
		}
		else
		{
			return -1;
		}
	}
	
	public static void main(String[] args) throws IOException {
		LexAnal lex = new LexAnal();
		lex.openSourceFile("/home/blaz/test.txt");
		lex.getNextSymbol();
		//System.out.println(lex.lastCharP);
	
		
	}
	
	/*white space IN veljavni znaki*/
	public static final int SPACE = 32;
	public static final int NEWLINE = 10;
	public static final int DOT = 46;
	public static final int COMMA = 44;
	public static final int SEMIC = 59;
	public static final int COLON = 58;
	public static final int ADD = 43;
	public static final int SUB = 45;
	public static final int MUL = 42;
	public static final int DIV = 47;
	public static final int MOD = 37;
	
	/* neveljavni */
	public static final int TILDA = 126;
	public static final int BSQ = 96; /* `*/
	public static final int USC = 95; // _
	public static final int  STREHA = 94; // ^
	public static final int BSL = 92; // \
	public static final int AFNA = 64;
	public static final int VPRASAJ = 63;
	public static final int DOLLAR = 36;
	public static final int SQ = 39; // '
	
	
	
	/*comment*/
	public static final int COMMENT = 35;
	
	//Imena tipov
	public static final int INT  = 5;
	public static final int REAL  = 6;
	public static final int BOOL  = 7;
	public static final int STRING  = 8;
	
//	private static final String[] TOKENS = new String[8];
	
	
	public static final HashMap<String, Integer> TOKENS2 = new HashMap<String, Integer>() {{
		put("intconst", 1);
		put("realconst", 2);
		put("boolconst", 3);
		put("stringconst", 4);
		put("int", 5);
		put("real", 6);
		put("bool", 7);
		put("string", 8);
		put("arr", 34);
		put("else", 35);
		put("for", 36);
		put("fun", 37);
		put("if", 38);
		put("rec", 39);
		put("then", 40);
		put("typ", 41);
		put("var", 42);
		put("where", 43);
		put("while", 44);
	}};

}