package com.gw.das.common.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配类
 * @author alan.wu
 *
 */
abstract public class RegexReplace{
	private String _regex,_source;
	private int _flags=0;//Pattern.CASE_INSENSITIVE|Pattern.DOTALL;
	public RegexReplace(String source,String regex){
		this._regex=regex;this._source=source;
	}
	public RegexReplace(String source,String regex,int flags) {
		this(source,regex);
		this._flags=flags;
	}
	
	abstract public String getReplacement(Matcher matcher) throws Exception;
	
	public String replaceAll() throws Exception{
		Pattern p=Pattern.compile(_regex,_flags);
		Matcher m=p.matcher(_source);
		StringBuffer result=new StringBuffer();
		while(m.find()){
			// System.out.println(content.substring(m.start(),m.end()));
			// System.out.println(m.group(2));
			String replacement=getReplacement(m);
			if(replacement!=null){
				if(replacement.indexOf('$')>=0)
					replacement=replacement.replace("$","\\$");
					//replacement=StringUtil.ReplaceAll(replacement,"$","\\$",false);
				m.appendReplacement(result,replacement);
			}
		}
		m.appendTail(result);
		return result.toString();
	}
	
	public String replaceFirst() throws Exception{
		Pattern p=Pattern.compile(_regex,_flags);
		Matcher m=p.matcher(_source);
		StringBuffer result=new StringBuffer();
		if(m.find()){
			// System.out.println(content.substring(m.start(),m.end()));
			// System.out.println(m.group(2));
			String replacement=getReplacement(m);
			if(replacement!=null){
				if(replacement.indexOf('$')>=0)
					replacement=replacement.replace("$","\\$");
				//System.out.println(replacement);
				m.appendReplacement(result,replacement);
			}
		}
		m.appendTail(result);
		return result.toString();
	}
}
