package com.gw.das.common.email;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EmailUtil {

	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * 根据参数，使用framemark替换邮件内容
	 * 
	 * @return
	 */
	public static String formatContent(String content, Map<String, Object> args) {
		if (StringUtils.isBlank(content)) {
			return "";
		}
		String result = "";
		try {
			Template fmTemplate = new Template("Template", new StringReader(content),
					new Configuration(Configuration.VERSION_2_3_22));
			Writer out = new StringWriter();
			try {
				fmTemplate.process(args, out);
				out.flush();
				result = out.toString();
			} catch (TemplateException e) {
				logger.error("使用framemark替换邮件内容异常：" + e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			logger.error("使用framemark替换邮件内容异常：" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 获取并校验邮箱
	 * 
	 * @return
	 */
	public static String getEmail(List<String> emails) {
		Preconditions.checkArgument(null != emails && emails.size() > 0, "邮箱为空");
		Preconditions.checkArgument(emails.size() <= 100, "邮箱过多(最多支持100个)");
		String mobile = StringUtil.list2String(emails);
		return mobile;
	}

	/**
	 * 判断是否为邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * 方法用途: 将bigList转换成多个smallList,每个smallList里面最多存放smallListSize个数据<br>
	 * 实现步骤: <br>
	 * 
	 * @param bigList：原始的bigList,需要发送的手机号码
	 * @param smallListSize：smallList的容量大小,每一批的数量
	 * @return
	 */
	public static List<List<String>> bigList2MultiSmallList(List<String> bigList, int smallListSize) {
		Preconditions.checkArgument(bigList != null && bigList.size() > 0, "邮箱不能为空");
		Preconditions.checkArgument(smallListSize > 0, "每一批的数量不能小于0");
		List<List<String>> retList = new ArrayList<List<String>>();
		if (bigList.size() <= smallListSize) {
			retList.add(bigList);
			return retList;
		} else {
			List<String> tempSmallList = null;
			if (bigList.size() % smallListSize == 0) {
				for (int i = 0; i < bigList.size() / smallListSize; i++) {
					if (i == bigList.size() / smallListSize) {
						tempSmallList = new ArrayList<String>();
						tempSmallList = bigList.subList(i * smallListSize, bigList.size());
						retList.add(tempSmallList);
					} else {
						tempSmallList = new ArrayList<String>();
						tempSmallList = bigList.subList(i * smallListSize, (i + 1) * smallListSize);
						retList.add(tempSmallList);
					}
				}
			} else {
				for (int i = 0; i <= bigList.size() / smallListSize; i++) {
					if (i == bigList.size() / smallListSize) {
						tempSmallList = new ArrayList<String>();
						tempSmallList = bigList.subList(i * smallListSize, bigList.size());
						retList.add(tempSmallList);
					} else {
						tempSmallList = new ArrayList<String>();
						tempSmallList = bigList.subList(i * smallListSize, (i + 1) * smallListSize);
						retList.add(tempSmallList);
					}
				}
			}

		}
		return retList;
	}
	
	public static void main(String[] args) {
		// 普通参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", "小雪");
		String content = EmailUtil.formatContent("${name}您好！", paramMap);
		logger.info(content);
		
		// 迭代list参数
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("teacherName", "小雪");
		paramMap2.put("policyContent", "谈金论道");
		List<Map<String, String>> varietyList = new ArrayList<Map<String, String>>();
		Map<String, String> paramMap3 = new HashMap<String, String>();
		paramMap3.put("variety", "黄金");
		paramMap3.put("support", "1290.50");
		Map<String, String> paramMap4 = new HashMap<String, String>();
		paramMap4.put("variety", "白银");
		paramMap4.put("support", "17.90");
		varietyList.add(paramMap3);
		varietyList.add(paramMap4);
		paramMap2.put("varietyList", varietyList);
		String templateContent = "";
		templateContent += "老师${teacherName}的${policyContent}策略！";
		templateContent += "<#foreach vo in varietyList>";
		templateContent += "品种:${vo.variety}";
		templateContent += "支撑位:${vo.support}";
		templateContent += "</#foreach>";
		String result = EmailUtil.formatContent(templateContent, paramMap2);
		logger.info(result);
		logger.info(JacksonUtil.toJSon(varietyList));
		
	}
}
