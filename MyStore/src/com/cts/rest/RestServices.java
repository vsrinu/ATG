package com.cts.rest;

import atg.nucleus.GenericService;

public class RestServices extends GenericService {

	public String doLogin(String name) {
		System.out.println("dologim method");
		return name;
	}
}
