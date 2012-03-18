package com.tweets;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.*;

import javax.xml.xpath.*;
import javax.xml.parsers.*;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class TopTweets {


   public static void generateLinks(String tag)throws ParserConfigurationException, SAXException, 
						IOException, XPathExpressionException{
		  DocumentBuilderFactory domFactory = 
				  DocumentBuilderFactory.newInstance();
		  domFactory.setNamespaceAware(true); 
		  DocumentBuilder builder = domFactory.newDocumentBuilder();

		  
		  String xmlMessage =  getAtomFeed(getURL(tag));
		  
		  Document doc = builder.parse(new InputSource(new StringReader(xmlMessage)));
		  Element docEle = doc.getDocumentElement();
		  
		  NodeList nl = docEle.getElementsByTagName("content");	  
		  if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {
			  	  //for(Element el:nl){	
					Element el = (Element)nl.item(i);
					String str = el.getTextContent();
					//System.out.println(str);
					System.out.println(getLink(str));//el.getTextContent());
				}
		  }    		

   }
   private static String getURL(String tag){
		  StringBuilder url = new StringBuilder();
		  url.append("http://search.twitter.com/search.atom?q=%23");
		  url.append(tag);
		  url.append("%20filter%3Alinks&rpp=100");
		  
		  return url.toString();
   }  
   private static String getAtomFeed(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (Exception e) {
	    	  System.err.println("Error while retriving Twitter feed"+e.getMessage());
	      }
	      return result;
   }
   private static String getLink(String text) {
	   String urlStr="";
	   String regex = "\\(?\\b(http|[/s]://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
	   Pattern p = Pattern.compile(regex);
	   Matcher m = p.matcher(text);
	   String prev="";
	   while(m.find()) {
		   urlStr = m.group();
		   if(prev.equalsIgnoreCase(urlStr) && !urlStr.contains("search.twitter.com")){
			   return urlStr;
		   }   
		   prev= urlStr;
	   }
	   return "";
   }   
}
