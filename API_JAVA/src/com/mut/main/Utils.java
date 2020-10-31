package com.mut.main;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

public class Utils {

	private Utils() {
		throw new IllegalAccessError("Utility class");
	}

	public static String getLocalIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	// Retorna a URI base da API
	public static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/API_JAVA").build();
	}
}
