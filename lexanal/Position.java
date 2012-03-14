package Proteus.src.compiler.lexanal;

import java.io.*;

import Proteus.src.compiler.*;

public class Position implements XMLable{

	private String filename;
	
	private int begLine;
	
	private int begColumn;
	
	private int endLine;
	
	private int endColumn;
	
	public Position(String filename, int begLine, int begColumn, int endLine, int endColumn) {
		this.filename = filename;
		this.begLine = begLine; this.begColumn = begColumn;
		this.endLine = endLine; this.endColumn = endColumn;
	}
	
	@Override
	public void toXML(PrintStream xml) {
		xml.println("<position filename=\"" + filename + "\" begLine=\"" + begLine + "\" begColumn=\"" + begColumn + "\" endLine=\"" + endLine + "\" endColumn=\"" + endColumn + "\"/>");
	}
	
}
