package cn.ac.iscas.web.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GitHubUrl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String baseUrl ="https://github.com/apache/hadoop-common/commits/trunk?page=";
		
		for(int i=1; i<=290; i++ )
		{
			try {
				Thread.sleep(1000);
				String queryUrl = baseUrl + i;
				String httpResult = sendGet(queryUrl);
				String commitUrl = getGitCommitUrl(httpResult);
				writeFile(i, commitUrl); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public static void writeFile(int i, String result) {
		// TODO Auto-generated method stub
		File dest = new File("F:/Hadoop Common/GitUrl/" + i +".txt");
		
		System.out.println("File "+ i + "\n" + result);
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(dest));
			out.write(result);
			out.flush();
			out.close();
		} catch( FileNotFoundException e ){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static String getGitCommitUrl(String result) {

		String baseGitUrl ="https://github.com";
		String resultUrl=""; 
		Document doc = Jsoup.parse(result);
		Elements links = doc.getElementsByAttribute("href");
		HashSet<String> commitUrl = new HashSet<String>();
		
		for (int i =0; i<links.size(); i++){
			//Get all the hyperlink address in the file.
			String commitTemp = links.get(i).attr("href"); 
			
			//Get all the commit link
			if(commitTemp.contains("/apache/hadoop-common/commit/")){
				String resultLink = baseGitUrl + commitTemp;
				commitUrl.add(resultLink);
			}
		}
		
		Iterator<String> it=commitUrl.iterator();
		
		while(it.hasNext()){
			resultUrl += it.next()+"\r\n";
		}
		return resultUrl;
	}
	
	
	
	
	public static String sendGet(String urlStr){
		String result = "";
		String line;
		BufferedReader in = null;
		
		try{
			URL urlAddr = new URL(urlStr);
			URLConnection conn = urlAddr.openConnection();
			
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.connect();
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			while(null != (line = in.readLine())){
				result += line;
			}

		}
		catch(Exception e){
			System.out.println("Error in send get request:" + e);
			e.printStackTrace();
		}
		finally{
			try{
				if(null != in){
					in.close();
				}
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return result;
		
	}
	
}
