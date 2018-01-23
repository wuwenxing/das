package com.gw.das.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Map> list = new ArrayList();
          Map<String,String> map1 = new HashMap();
          map1.put("2", "2");
          list.add(map1);
          
          Map<String,String> map2 = new HashMap();
          map2.put("4", "4");
          list.add(map2);
          
          Map<String,String> map3 = new HashMap();
          map3.put("6", "6");
          list.add(map3);
          
          Map<String,String> map11 = new HashMap();
          map11.put("1", "1");
          list.add(0, map11);
          
          Map<String,String> map22 = new HashMap();
          map22.put("3", "3");
          list.add(2, map22);
          
          for (int i = 0; i < list.size(); i++) {
        	  Map<String,String> map = list.get(i);
        	  System.out.println(map);
		}
          
          
	}

}
