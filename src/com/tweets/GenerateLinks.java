package com.tweets;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class GenerateLinks {
	   public static void main(String args[])  throws ParserConfigurationException, SAXException, 
			IOException, XPathExpressionException{
			//System.out.println(getHTML("http://search.twitter.com/search.atom?q=%23radiohead%20filter%3Alinks&rpp=100"));
			if(args.length!=1){
			System.out.println("USAGE:\nRun the script by passing a single alphanumeric hashtag as the argument.");
			System.exit(1);
			}
			TopTweets.generateLinks(args[0]);
	   }	
}
