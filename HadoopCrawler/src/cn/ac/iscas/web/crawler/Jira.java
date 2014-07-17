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
//import java.util.List;
//import java.util.Map;

public class Jira {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		
		for(i=1; i<10850; i++){
			String result = sendGet("https://issues.apache.org/jira/browse/HADOOP-"+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    writeFile(i,result);	
			System.out.println(result);
		}
	}
	
	public static void writeFile(int i, String result) {
		// TODO Auto-generated method stub
		File dest = new File("F:/Hadoop Common/Jira/" + i +".html");
		
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

	public static String sendGet(String urlStr){
		String result = "";
		String line;
		BufferedReader in = null;
		
		try{
			URL urlAddr = new URL(urlStr);
			URLConnection conn = urlAddr.openConnection();
			
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", 
										"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.connect();
//			Map<String, List<String>> map = conn.getHeaderFields();
//			for(String key: map.keySet()){
//				System.out.println(key +"------>" + map.get(key));
//			}
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
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
