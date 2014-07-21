package cn.ac.iscas.web.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class TestGitHubSubmitUnit {

	public static void main(String[] args) throws ParseException, IOException {
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		    HttpGet httpget = new HttpGet("https://github.com/apache/hadoop-common/commit/7e215a5e0e401fe7f8271a195f9d00bcad93b026");
		    CloseableHttpResponse response = httpclient.execute(httpget);
		    try {
		    	 HttpEntity entity = response.getEntity();
		    	 System.out.println(EntityUtils.toString(entity));
		    } finally {
		        response.close();
		    }
	}
}
