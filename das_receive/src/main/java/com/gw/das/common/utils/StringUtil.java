package com.gw.das.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

public class StringUtil {	
	private static final Logger logger = Logger.getLogger(StringUtil.class);
	
	public static String htmlToTextByRE(String  htmlStr){
		 return htmlStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", ""); 
	}
	public static String htmlToText(String  htmlStr){
			htmlStr = htmlStr.replace("\"", ""); //去掉引号
			htmlStr = htmlStr.replace("\n", ""); //去掉引号
			htmlStr = htmlStr.replace("\r", ""); //去掉引号
			 //System.out.println(htmlStr);
	        Pattern p = Pattern.compile("<.+?>", Pattern.DOTALL); //去HTML
	        Pattern p1 = Pattern.compile("<javascript[^>]*?>.*?</javascript>");//去javascript
	        Pattern p1a = Pattern.compile("<javascript[^>]*?>.*?</javascript>");//去javascript
	        Pattern p2 = Pattern.compile("<style[^>]*?>.*?</style>");//去style
	        Pattern p2a = Pattern.compile("<STYLE[^>]*?>.*?</STYLE>");//去style
	        //Pattern p2 = Pattern.compile("[\\s]"); //去空格
	        Pattern p3 = Pattern.compile("<script[^>]*?>.*?</script>"); //去JS
	        Pattern p3a = Pattern.compile("<SCRIPT[^>]*?>.*?</SCRIPT>"); //去JS

	        htmlStr =p3a.matcher(htmlStr).replaceAll(""); //在这要先去和 style,再去html代码,不然js理的代码就去掉了
	        htmlStr =p3.matcher(htmlStr).replaceAll("");
	        htmlStr =p1.matcher(htmlStr).replaceAll("");
	        htmlStr =p1a.matcher(htmlStr).replaceAll("");
	        htmlStr =p2.matcher(htmlStr).replaceAll("");
	        htmlStr =p2a.matcher(htmlStr).replaceAll("");
	        htmlStr =p.matcher(htmlStr).replaceAll("");
	        //htmlStr =p1.matcher(htmlStr).replaceAll("");
	       // System.out.println(htmlStr);
	       // htmlStr=p2.matcher(htmlStr).replaceAll("");
	        return htmlStr;   
		 } 

	public static boolean isRightFormatEmail(String content) {
		if(content.indexOf("'")>0){
			if(content.indexOf("'")!=content.lastIndexOf("'")){
				return true;
			}
		}
		return false;		
	}
	
	public static String fixString(String data,int maxlength,boolean left,String tchar){   
		String tdata=new String(data);   
        while(tdata.length()<maxlength){   
            if(left){   
                tdata=tchar+tdata;
            }else{   
                tdata=tdata+tchar;   
            }   
        }   
        return tdata;   
    }
	public static boolean matchs(String dateStr,String pattern){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(dateStr);
		boolean b = false;
		while(b = m.find()) {
			b=true;
		}
		return b;
	}
	public static String changeNullToEmpty (String str){
		if (str== null){
			str="";	
		}	
		return str;
	}
	
	/**
     * 功能：将obj转换成String
     */
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				return null;
			} else {
				return obj.toString();
			}
		}
	}
	
	public static boolean isNullOrEmpty (String str){
		if (str== null || "".equals(str))
			return true;
		else {
			if(str.trim().equals("")) {
				return true;
			}
			if("null".equals(str)) {
				return true;
			}
			return false;
		}
	}

	public static boolean isNullOrEmptyOrAll (String str){
		if (str== null || "".equals(str.trim()))
			return true;
		else {
			if(str.trim().equals("") || "所有".equals(str)) {
				return true;
			}
			return false;
		}
	}
	public static String nullToEmpty(String str){
		if (str== null)
			return "";
			return str;
		
	}
    public static String[] separate(String word,String word2){
    	try{
    		if(word==null){
    			return null;
    		}
    		//System.out.println("word"+word+"word2"+word2);
    		return word.split(word2);
    	}catch(Exception e){
    		logger.error("", e);
    		return null;
    	}
    }
    public static String removeWordFromList(String word, String removeWord, String word2){
    	try{
    		if(word!=null && removeWord != null){    		
    			String[] wordArray = separate(word,word2);   		
    			String result = "";
    			for(String origin : wordArray){
    				boolean match = false; 
    			
    					if(origin.equals(removeWord)){
    						match = true;	
    					}
    				
    				if(!match){
    					if(result.equals("")){
    						result += origin;
    					}else{
    						result += word2 + origin;
    					}
    				}
    			}
    			return result;
    		}
    		return null;
    	}catch(Exception e){
    		logger.error("", e);
    		return null;
    	}
    }
    
    /**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：helloWorld->hello_world
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		if(StringUtils.isBlank(name)){
			return name;
		}
	    StringBuilder result = new StringBuilder();
	    if (name != null && name.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(name.substring(0, 1));
	        // 循环处理其余字符
	        for (int i = 1; i < name.length(); i++) {
	            String s = name.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s);
	        }
	    }
	    return result.toString().toLowerCase();
	}
    
	public static String formatDecimal(BigDecimal decimal){
		DecimalFormat format  = new DecimalFormat("#,##0.000");
		return format.format(decimal.doubleValue());
	}
	
	public static String coalesce(String... args){
		for(String arg : args){
			if(arg != null){
				return arg;
			}
		}
		return null;
	}
	
	public static boolean isNumber(String number){
		try{
			Double.parseDouble(number);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	public static boolean isInteger(String number){
		try{
			Integer.parseInt(number);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	public static String padLeadingZero(String str, int maxNoOfChar){
		try{
			int strLength = str.length();
			if(maxNoOfChar > strLength){
				int noOfZeroShouldBeAdded = maxNoOfChar - strLength;
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<noOfZeroShouldBeAdded; i++){
					sb.append("0");
				}
				sb.append(str);
				return sb.toString();
			}
		}catch(Exception e){
			logger.error("", e);
		}
		return str;
	}
	
	public static StringBuffer padSpace(int number){
		if(number > 0){
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<number; i++){
				sb.append(' ');
			}
			return sb;
		}
		return null;
	}
	
    /**  
     * 根據傳入的數組轉換成以逗號分割的字符串  
     * @author jiangxc<br>  
     * <b>create Date:</b>Jul 7, 2009 10:45:50 AM<br>  
     * <b>last modify Date:</b>Jul 7, 2009 10:45:50 AM<br>  
     * @param arrays  
     * @return String 例如"1,2,3,4,5,6"  
     */  
       
    public static String array2String(String[] arrays){   
        String resultString="";   
        if(arrays!=null && arrays.length!=0){   
            StringBuffer tmpstring=new StringBuffer();   
            boolean flag=false;   
            for(String tmps:arrays){   
                if(flag)tmpstring.append(",");   
                tmpstring.append(tmps.trim());   
                flag=true;   
            }   
            resultString=tmpstring.toString();   
        }   
        return resultString;   
    }   
       
    /**  
     * 根據傳入的List<String>轉換成以逗號分割的字符串  
     * @author jiangxc<br>  
     * <b>create Date:</b>Jul 7, 2009 10:45:53 AM<br>  
     * <b>last modify Date:</b>Jul 7, 2009 10:45:53 AM<br>  
     * @param stringlist  
     * @return String 例如"1,2,3,4,5,6"  
     */  
       
    public static String list2String(List<String> stringlist){   
        String resultString="";   
        if(stringlist!=null && stringlist.size()!=0){   
            StringBuffer tmpstring=new StringBuffer();   
            boolean flag=false;   
            for(String tmps:stringlist){   
                if(flag)tmpstring.append(",");   
                tmpstring.append(tmps.trim());   
                flag=true;   
            }   
            resultString=tmpstring.toString();   
        }   
        return resultString;   
    }   
       
    /**  
     * 根據以逗號分割的字符串參數轉換為去除逗號的數組  
     * @author jiangxc<br>  
     * <b>create Date:</b>Jul 7, 2009 10:45:56 AM<br>  
     * <b>last modify Date:</b>Jul 7, 2009 10:45:56 AM<br>  
     * @param string 例如"1,2,3,4,5,6"  
     * @return String[]  
     */  
       
    public static String[] string2Array(String string){   
        String[] tmpArray=null;   
        if(string!=null && !"".equals(string.trim())){   
            tmpArray=string.split(",");   
        }   
        return tmpArray;   
    }   
       
    /**  
     * 根據以逗號分割的字符串參數轉換為去除逗號的List<String>  
     * @author jiangxc<br>  
     * <b>create Date:</b>Jul 7, 2009 10:45:59 AM<br>  
     * <b>last modify Date:</b>Jul 7, 2009 10:45:59 AM<br>  
     * @param string 例如"1,2,3,4,5,6"  
     * @return List<String>  
     */  
       
    public static List<String> string2List(String string){    
        List<String> tmpList=null;   
        if(string!=null && !"".equals(string.trim())){   
            tmpList=Arrays.asList(string.split(","));   
        }   
        return tmpList;   
    } 
    
    public static boolean isEqual(String str1, String str2){
    	return StringUtils.defaultString(str1).equals(StringUtils.defaultString(str2));
    }
    
    /**
	 * 是否为空
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value){
		if (value == null || value.equals("")){
			return true;
		}else{
			if(value.toString().trim().equals(""))	return true;
		}
				
		return false;
	}
    /**
     * 格式化数字
     * @param number
     * @return
     */
    public static String formArith(double number) {
        DecimalFormat format = new DecimalFormat("###0.000");
        BigDecimal bd = new BigDecimal(number);
        //      NumberFormat format = NumberFormat.getNumberInstance();
        //      format.setMinimumFractionDigits(2);
        //      String temNum =  format.format(number);
        //      if(temNum.indexOf(",")>=0){
        //          temNum = temNum.replace(",","");
        //      }
        return format.format(bd);
    }
    
    public static String getPercentFormat(double d, int IntegerDigits,
        int FractionDigits) {
	    NumberFormat nf = java.text.NumberFormat.getPercentInstance();
	   // nf.setMaximumIntegerDigits(IntegerDigits);//小数点前保留几位
	    nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
	    String str = nf.format(d);
	    return str;
    }
    
    /**
	 * 转成模糊匹配形式的字符串
	 * @param value
	 */
	public static String toFuzzyMatch(String value){
		return ".*?"+value+".*";
	}
	
	/**
	 * 格式手机号码，中间使用*号代替
	 * @param value
	 * @return
	 */
	public static String formatPhone(String value){
		if(StringUtils.isNotBlank(value) && value.length() > 8){
			value = value.substring(0, value.length()-8)
					+ "****"
					+ value.substring(value.length()-4, value.length());
		}
		return value;
	}
	
}
