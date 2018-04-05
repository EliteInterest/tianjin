package com.zx.tjmarketmobile.manager;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParserXml {

	public static int parser(InputStream is, BaseParser parser) throws Exception {
		if (is != null) {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(parser);
			xr.parse(new InputSource(is));
			return parser.getRespObject();
		}
		return 0;
	}
}