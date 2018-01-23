package com.gw.das.common.sms;

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
import com.gw.das.common.enums.SmsSignEnum;
import com.gw.das.common.utils.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SmsUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	public static String suffixContent_1 = "退订回T";
	public static String suffixContent_2 = " 退订回T";// 加空格，是防止末尾链接是网址，隔开链接，防止链接失效

	/**
	 * 根据参数，使用framemark替换短信内容
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
				logger.error("使用framemark替换短信内容异常：" + e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			logger.error("使用framemark替换短信内容异常：" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 添加短信签名-如果内容包含签名，则不在添加
	 * @return
	 */
	public static String addSign(String content) throws Exception {
		Preconditions.checkArgument(!StringUtils.isBlank(content), "短信内容为空");
		if (!content.contains(SmsSignEnum.jd.getValue()) 
				&& !content.contains(SmsSignEnum.jdgwfx.getValue())
				&& !content.contains(SmsSignEnum.jdgjs.getValue())
				&& !content.contains(SmsSignEnum.hxgjs.getValue())
				&& !content.contains(SmsSignEnum.cf.getValue())) {
			content = SmsSignEnum.jd.getValue() + content;
		}
		return content;
	}

	/**
	 * 添加短信后缀【退订回T】
	 * 
	 * @return
	 */
	public static String addSuffix(String content) throws Exception {
		if (!content.contains(suffixContent_1)) {
			content = content + suffixContent_2;
		}
		return content;
	}
	
	/**
	 * 获取并校验手机号
	 * 
	 * @return
	 */
	public static String getMobile(List<String> mobileNums) {
		Preconditions.checkArgument(null != mobileNums && mobileNums.size() > 0, "手机号码为空");
		Preconditions.checkArgument(mobileNums.size() <= 100, "手机号码过多(最多支持100个)");
		String mobile = StringUtil.list2String(mobileNums);
		return mobile;
	}

	/**
	 * 
	 * 方法用途: 判断是否为手机号码<br>
	 * 实现步骤: <br>
	 * 
	 * @param mobileInput
	 * @return
	 */
	public static boolean checkMobileNum(String mobileInput) {
		Pattern p = Pattern.compile("^[1][1-9][0-9]{9}$|^[8][6][-][1][1-9][0-9]{9}$|^[8][6][-][-][1][1-9][0-9]{9}$");
		Matcher m = p.matcher(mobileInput);
		return m.matches();
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
		Preconditions.checkArgument(bigList != null && bigList.size() > 0, "手机号码号码不能为空");
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
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", "小雪");
		String content = SmsUtil.formatContent("${name}您好！", paramMap);
		logger.info(content);
	}
}
