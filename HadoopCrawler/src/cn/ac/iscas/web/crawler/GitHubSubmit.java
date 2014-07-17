package cn.ac.iscas.web.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GitHubSubmit {

	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<String> fileList = getAllFiles();
		ArrayList<String> url = new ArrayList<String>();
		for(int i=0; i<fileList.size(); i++){

			url = readFile(fileList.get(i));
			
			System.out.println(fileList.get(i));
			
			String fileLoc = fileList.get(i)
					.substring(fileList.get(i).lastIndexOf("\\")+1).replace(".txt","_");
			
			for(int j=0; j<url.size(); j++){
				String urlLoc = url.get(j).substring(url.get(j).lastIndexOf("/")+1);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String fname = fileLoc + urlLoc;
				
				String result = null;
				result = sendGet(url.get(j));
				writeFile(fname,result);
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
	
	
	public static void writeFile(String i, String result) throws FileNotFoundException {
		// TODO Auto-generated method stub
		FileOutputStream dest = new FileOutputStream("F:/Hadoop Common/GitCommitHtml/" + i +".html");
		
		System.out.println("File "+ i + "\n" + result);
		
		try{
			Writer out = new OutputStreamWriter(dest,"UTF-8");
			out.write(result);
			out.flush();
			out.close();
		} catch( FileNotFoundException e ){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
