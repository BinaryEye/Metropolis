package com.metropolis.requestClasses;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public class PostRequest extends HttpRequest {
	HttpPost httpPost;
	
	public PostRequest(String uri){
		httpPost = new HttpPost(uri);
		super.setMethod(httpPost);
	}
	
	public void setBody(JSONObject body){
		this.setHasBody(true);
		StringEntity data = null;
		try {
			data = new StringEntity(body.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.httpPost.setEntity(data);
	}
	

}