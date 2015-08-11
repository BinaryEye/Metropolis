package com.metropolis.requestClasses;

import org.apache.http.client.methods.HttpDelete;

public class DeleteRequest extends HttpRequest {
	HttpDelete httpDelete;
	
	public DeleteRequest(String uri){
		httpDelete = new HttpDelete(uri);
		super.setMethod(httpDelete);
	}
	
}