package cn.ac.iscas.web.crawler;

import java.io.File;
import java.util.ArrayList;

public class GitHubSubmit {

	public static void main(String[] args) {
		
		getAllFiles();

	}

	public static ArrayList<String> getAllFiles() {
		File file=new File("F:\\Hadoop Common\\GitUrl");
		String[] fileName = file.list();
		ArrayList<String> fileList = new ArrayList<String>();
		
		for( int i=0; i<fileName.length; i++){
		   System.out.println(file.getAbsolutePath()+"\\"+fileName[i]);
		  }
		return null;
	}

}
