package com.metropolis.requestClasses;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public abstract class HttpRequest extends AsyncTask<String, String, String> {

	private HttpClient httpClient;

	private HttpRequestBase request;

	private boolean hasError = false;

	private String errorMessage = null;

	private boolean hasBody = false;

	private int statusCode;

	public HttpRequest() {
		httpClient = new DefaultHttpClient();
	}

	void setMethod(HttpRequestBase request) {
		this.request = request;
	}

	public void addHeader(String header, String value) {
		this.request.addHeader(header, value);
	}

	public boolean hasError() {
		return hasError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	protected String doInBackground(String... args) {
		if (hasBody) {
			this.request.addHeader("content-type", "application/json");
		}
		HttpResponse x = null;
		HttpEntity entity = null;
		try {
			x = httpClient.execute(this.request);
			entity = x.getEntity();
			this.statusCode = x.getStatusLine().getStatusCode();
			if (statusCode / 100 == 2) {
				return entity == null ? "" : EntityUtils.toString(entity);
			} else if (statusCode / 100 == 4) {
				hasError = true;
				errorMessage = entity == null ? "" : EntityUtils
						.toString(entity);
				return null;
			} else {
				hasError = true;
				errorMessage = "Server Error";
				return null;
			}
		} catch (ClientProtocolException e) {
			hasError = true;
			errorMessage = e.getMessage();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	void setHasBody(boolean hasBody) {
		this.hasBody = hasBody;
	}
}