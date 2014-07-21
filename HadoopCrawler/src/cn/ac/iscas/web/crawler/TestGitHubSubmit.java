package cn.ac.iscas.web.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestGitHubSubmit {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		ArrayList<String> fileList = getAllFiles();
		ArrayList<String> url = new ArrayList<String>();
		for(int i=0; i<fileList.size(); i++){
		
			HashMap<String,String> resultHtml = new HashMap<String,String>();
			
			url = readFile(fileList.get(i));
			System.out.println(fileList.get(i));
			
			String fileLoc = fileList.get(i)
					.substring(fileList.get(i).lastIndexOf("\\")+1).replace(".txt","_");
			
			for(int j=0; j<url.size(); j++){
				String urlLoc = url.get(j).substring(url.get(j).lastIndexOf("/")+1);
				String fname = fileLoc + urlLoc;
				
				String result = null;
				result = sendGet(url.get(j));
				resultHtml.put(fname, result);
				System.out.println(fname +"added in memory");
			}
			
			writeFile(resultHtml);
		}
	}
	
	public static void writeFile(HashMap resultHtml) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Iterator iter = resultHtml.entrySet().iterator();
	
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			
			FileOutputStream dest = new FileOutputStream("F:/Hadoop Common/GitCommitHtml/" + key +".html");
		
//			System.out.println("File "+ key + "\n" + val);
		
			try{
				Writer out = new OutputStreamWriter(dest,"UTF-8");
				out.write(val);
				System.out.println("File Writer Done");
				out.flush();
				out.close();
			} catch( FileNotFoundException e ){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<String> getAllFiles() {
		File file=new File("F:\\Hadoop Common\\GitUrl");
		String[] fileName = file.list();
		ArrayList<String> fileList = new ArrayList<String>();
		
		for( int i=0; i<fileName.length; i++){
		   fileList.add(file.getAbsolutePath()+"\\"+fileName[i]);
		}
		
//		for(int i=0; i<fileList.size(); i++){
//			System.out.println(fileList.get(i));
//		}
		return fileList;
	}
	
	public static String sendGet(String urlStr) throws ClientProtocolException, IOException{
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpget = new HttpGet(urlStr);
	    CloseableHttpResponse response = httpclient.execute(httpget);
	    try {
	    	 HttpEntity entity = response.getEntity();
	    	 result = EntityUtils.toString(entity);
	    } finally {
	        response.close();
	    }
		return result;
	}
	
	private static ArrayList<String> readFile(String fileLoc) {
		// TODO Auto-generated method stub
		ArrayList<String> url = new ArrayList<String>();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileLoc));
			String tempString = null;
			while( null != (tempString =in.readLine())){
				url.add(tempString);
			}
			in.close();
			
		} catch( FileNotFoundException e ){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return url;
	}
}
