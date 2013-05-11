package fr.hartok.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LanguageExporter {

	protected final String			m_tag;
	protected List<LocalizedString>	m_strings = new ArrayList<>();

	protected final static String LANGUAGE_DIR = "/values%s/";
	protected final static String FILE_NAME = "strings.xml";
	protected final static String START_TAG = "<resources>\n";
	protected final static String END_TAG = "\n</resources>";
	
	protected class LocalizedString {
		
		public final String m_id;
		public final String m_value;
		
		protected static final String XML_BASE = "\t<string name=\"%s\">%s</string>";
		
		public LocalizedString(String id, String value) {
			m_id = id;
			m_value = value;
		}
		
		public String getXMLTag()
		{
			return String.format(XML_BASE, m_id, m_value);
		}
	}
	
	public LanguageExporter(String languageTag)
	{
		m_tag = languageTag;
	}
	
	/**
	 * Generate values-TAG/strings.xml file.
	 */
	public void export(String outputDir)
	{
		System.out.println("Exporting localization: " + ((m_tag.isEmpty()) ? "default" : m_tag) );
		String languageOutputDir = outputDir + String.format(LANGUAGE_DIR, (m_tag.isEmpty()) ? "" : "-"+m_tag );
		
		// create output dir
		new File( languageOutputDir ).mkdirs();
		
		PrintWriter writer = createWriter(languageOutputDir + FILE_NAME);
		
		if(writer != null)
		{
			writer.println( START_TAG );
			
			for (LocalizedString string : m_strings)
				writer.println( string.getXMLTag() );
			
			writer.println( END_TAG );
			
			writer.flush();
			writer.close();
		}
	}
	
	public void print()
	{
		System.out.println( "----\t" + m_tag + "\t----" );
		for (LocalizedString loc : m_strings) {
			System.out.println( "\t" + loc.m_id + "\t|\t" + loc.m_value );
		}
		System.out.println( "--------------------" );
	}
	
	
	public void appendLocalizedString(String id, String value)
	{
		m_strings.add( new LocalizedString(id, value) );
	}
	
	protected static PrintWriter createWriter(String filePath)
	{
		PrintWriter writer = null; 
		try {
			writer = new PrintWriter( new BufferedWriter( new FileWriter(filePath) ) );			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return writer;
	}
}
