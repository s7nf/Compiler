package Proteus.src.compiler.lexanal;

import java.io.*;
import Proteus.src.compiler.*;

/** Izvede prevajanje do faze leksikalne analize. */
public class Main {

	public static void main(String programName) {
		try {
			PrintStream xml = XML.open("lexanal");
			{
				LexAnal lexer = new LexAnal();
				lexer.openSourceFile(programName);
				
				Symbol symbol;
				while ((symbol = lexer.getNextSymbol()) != null) {
					symbol.toXML(xml);
				}

				lexer.closeSourceFile();
			}
			XML.close("lexanal", xml);
		} catch (IOException exception) {
			Report.error("Cannot perform lexical analysis.", 1);
		}
	}

}
