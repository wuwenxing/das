package com.gw.das.api;

import java.util.HashMap;
import java.util.Map;

public class test2 {

	private static Map<String,String> pmMap = new HashMap<String,String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pmMap.put("pmf101-1", "pmf101-1");
		
		String str = "pmf101-1";
		
		System.out.println(pmMap.containsKey(str));
	}

}
