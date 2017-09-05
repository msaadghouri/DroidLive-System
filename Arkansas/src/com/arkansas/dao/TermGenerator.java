package com.arkansas.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class TermGenerator {

	public static void main(String[] args) throws Exception{
	}

	public LinkedHashMap<String,Integer> generator(String post) throws Exception {
		try{
			post = post.toLowerCase();
			post = cleanPost(post);
			post = removeStopwords(post);
			LinkedHashMap<String,Integer> terms = getTerms(post);
			return terms;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LinkedHashMap<String,Integer> getTerms(String post) {
		LinkedHashMap<String,Integer> terms = new LinkedHashMap<String,Integer>();

		StringTokenizer st = new StringTokenizer(post," ");
		while(st.hasMoreElements())
		{
			String token = st.nextToken();
			int value = 0;
			if(token.length() > 2)
			{
				if(terms.containsKey(token))
				{
					value = terms.get(token);
				}
				value++;
				terms.put(token, value);
			}
		}
		return terms;
	}
	public static String cleanPost(String post)
	{
		char[] unwantedCharacters = {'\b','\n','\n','\f','\t','\r','\'','0','1','2','3','4','5','6','7','8','9','!','@','#','$','%','^','&','*','(',')','_','-','"','+','=',',','.','/',':',';','[',']','{','}','<','>','?','|','~','�','�','�','�','�','�','�','`','�','�','�','�','\\','"','\'','�','�','�','�'};
		for(char c: unwantedCharacters)
		{
			post = post.replace(c, ' ');
		}
		return post;
	}

	public static String removeStopwords(String post)
	{
		ArrayList<String> stopwords = getStopwords();
		for(String word:stopwords)
		{
			word = " " + word + " ";
			post = post.replace(word, " ");
		}
		while(post.contains("  "))
		{
			post = post.replace("  ", " ");
		}
		return post;
	}

	public static ArrayList<String> getStopwords()
	{
		ArrayList<String> stopwords = new ArrayList<String>();
		BufferedReader br = null;
		try
		{
			String line;
			br = new BufferedReader(new FileReader("stopwords_en.txt"));
			while((line = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line,",");
				while(st.hasMoreElements())
				{
					String word = st.nextToken();
					if(!stopwords.contains(word))
					{
						stopwords.add(word);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return stopwords;
	}
	public static int wordCount(String post){
		String trim = post.trim();
		if (trim.isEmpty())
			return 0;
		return trim.split("\\s+").length;
	}
}