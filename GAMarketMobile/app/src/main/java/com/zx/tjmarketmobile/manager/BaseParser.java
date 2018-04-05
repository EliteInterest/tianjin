package com.zx.tjmarketmobile.manager;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.zx.tjmarketmobile.util.DigitUtil;

public class BaseParser extends DefaultHandler {

	public StringBuffer mBuf;
	public int mRespObj = 0;

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		mBuf.append(ch, start, length);
	}

	@Override
	public void startDocument() throws SAXException {
		mBuf = new StringBuffer();
		mRespObj = 0;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("version")) {
			mRespObj = DigitUtil.StringToInt(mBuf.toString().trim());
			mBuf.setLength(0);
		} 
	}

	public int getRespObject() {
		return mRespObj;
	}
}