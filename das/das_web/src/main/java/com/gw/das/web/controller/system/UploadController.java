package com.gw.das.web.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UploadContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.email.EmailUtil;
import com.gw.das.common.enums.AccountAnalyzeStatusEnum;
import com.gw.das.common.enums.BusTagContentEnum;
import com.gw.das.common.enums.BusTagTypeEnum;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.PlatformtypeEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.RiskTypeEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.enums.UploadFileTypeEnum;
import com.gw.das.common.utils.AesUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.ExcelUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.dao.market.entity.BusinessTagEntity;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.dao.system.entity.UploadLogEntity;
import com.gw.das.dao.trade.entity.RiskBlacklistEntity;
import com.gw.das.service.custConsultation.CustConsultationService;
import com.gw.das.service.market.AccountAnalyzeService;
import com.gw.das.service.market.BusinessTagService;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.system.UploadLogService;
import com.gw.das.service.trade.RiskBlacklistService;

@Controller
@RequestMapping("/UploadController")
public class UploadController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private UploadLogService uploadLogService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private CustConsultationService custConsultationService;
	@Autowired
	private AccountAnalyzeService accountAnalyzeService;
	@Autowired
	private RiskBlacklistService riskBlacklistService;
	
	@Autowired
	private BusinessTagService tagService;

	private final static String fx = "fx";
	private final static String pm = "pm";
	private final static String hx = "hx";
	private final static String cf = "cf";
	
	/**
	 * 上传日志-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("uploadFileTypeEnum", UploadFileTypeEnum.getList());
			return "/system/uploadLog/uploadLog";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 上传日志-分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<UploadLogEntity> pageList(HttpServletRequest request,
			@ModelAttribute UploadLogEntity uploadLogEntity) {
		try {
			PageGrid<UploadLogEntity> pageGrid = uploadLogService
					.findPageList(super.createPageGrid(request, uploadLogEntity));
			for (Object obj : pageGrid.getRows()) {
				UploadLogEntity record = (UploadLogEntity) obj;
				if(UploadFileTypeEnum.textFile.getLabelKey().equals(record.getFileType())){
					// 文本文件隐藏路径
					record.setFileName(StringUtil.formatPhone(record.getFileName()));
					record.setFilePath(StringUtil.formatPhone(record.getFilePath()));
					record.setFileUrl(StringUtil.formatPhone(record.getFileUrl()));
				}
				record.setFileType(UploadFileTypeEnum.format(record.getFileType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<UploadLogEntity>();
		}
	}

	/**
	 * 读取本地图片文件,并输出
	 */
	@RequestMapping(value = "/readImg/{encryptionPath}")
	public void readImg(HttpServletRequest request, HttpServletResponse response, @PathVariable String encryptionPath) {
		try {
			String realPath = new String(AesUtil.decrypt(AesUtil.parseHexStr2Byte(encryptionPath)));
			logger.debug(realPath);

		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 风控导入-上传excel文件
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/riskBlacklistUploadExcel/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void riskBlacklistUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		String errorMsg = "";
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					List<RiskBlacklistEntity> listParam = new ArrayList<RiskBlacklistEntity>();
					if (null != file) {
						// 解析excel并转化为账号诊断对象
						List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 13);
						for(int i=1; i<list.size(); i++){//前1行是标题，
							RiskBlacklistEntity param = new RiskBlacklistEntity();
							List<Object> objList = list.get(i);
							for(int j=0; j<objList.size(); j++){
								Object obj = objList.get(j);
								String objStr = obj == null?"":obj.toString();
								if(j == 0){//第一列下标为0,对应事业部
									if(StringUtils.isNotBlank(objStr)){
										if(objStr.equalsIgnoreCase(UploadController.fx)){
											param.setCompanyId(CompanyEnum.fx.getLabelKeyLong());
										}else if(objStr.equalsIgnoreCase(UploadController.pm)){
											param.setCompanyId(CompanyEnum.pm.getLabelKeyLong());
										}else  if(objStr.equalsIgnoreCase(UploadController.hx)){
											param.setCompanyId(CompanyEnum.hx.getLabelKeyLong());
										}else if(objStr.equalsIgnoreCase(UploadController.cf)){
											param.setCompanyId(CompanyEnum.cf.getLabelKeyLong());
										}else{
											errorMsg += "第"+(i+1)+"行，事业部输入有误!;";
											continue;
										}
									}
								}else if(j == 1){//对应风险类型;;}}};}}}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
									if(StringUtils.isNotBlank(objStr)){
										if(RiskTypeEnum.bmd.getLabelKey().equals(objStr)){
											param.setRiskType(objStr);
										}else if(RiskTypeEnum.fj.getLabelKey().equals(objStr)){
											param.setRiskType(objStr);
										}else  if(RiskTypeEnum.fx.getLabelKey().equals(objStr)){
											param.setRiskType(objStr);
										}else if(RiskTypeEnum.cxwxx.getLabelKey().equals(objStr)){
											param.setRiskType(objStr);
										}else{
											errorMsg += "第"+(i+1)+"行，风险类型输入有误!;";
											continue;
										}
										if(objStr.indexOf(".") != -1){
											errorMsg += "第"+(i+1)+"行，风险类型需为文本类型，请检查!;";
											continue;
										}
									}else{
										errorMsg += "第"+(i+1)+"行，风险类型为空!;";
										continue;
									}
								}else if(j == 2){//风险原因
									if(StringUtils.isNotBlank(objStr)){
										param.setRiskReason(objStr);
									}else{
										errorMsg += "第"+(i+1)+"行，风险原因为空!;";
										continue;
									}
								}else if(j == 3){//风险备注
									if(StringUtils.isNotBlank(objStr)){
										param.setRiskRemark(objStr);
									}else{
										errorMsg += "第"+(i+1)+"行，风险备注为空!;";
										continue;
									}
								}else if(j == 4){//风险时间(一种是yyyy-MM-dd;另一种是yyyy-MM-dd HH:mm:ss)
									if(StringUtils.isNotBlank(objStr)){
										if(objStr.length() == 10){
											param.setRiskTime(DateUtil.formatDate(DateUtil.stringToDate(objStr)));
										}else if(objStr.length() == 19){
											param.setRiskTime(DateUtil.formatDate(DateUtil.stringToDate(objStr, "yyyy-MM-dd HH:mm:ss")));
										}else{
											errorMsg += "第"+(i+1)+"行，风险时间格式有误!;";
											continue;
										}
									}else{
										//  对于没有填写风险时间的，根据Excel 文档上传的时间补全
										param.setRiskTime(DateUtil.formatDate(new Date()));
									}
								}else if(j == 5){//对应交易平台
									if(StringUtils.isNotBlank(objStr)){
										if(PlatformtypeEnum.MT4.getValue().equalsIgnoreCase(objStr)){
											param.setPlatform(PlatformtypeEnum.MT4.getLabelKey());
										}else if(PlatformtypeEnum.MT5.getValue().equalsIgnoreCase(objStr)){
											param.setPlatform(PlatformtypeEnum.MT5.getLabelKey());
										}else  if(PlatformtypeEnum.GTS.getValue().equalsIgnoreCase(objStr)){
											param.setPlatform(PlatformtypeEnum.GTS.getLabelKey());
										}else if(PlatformtypeEnum.GTS2.getLabelKey().equalsIgnoreCase(objStr)){
											param.setPlatform(PlatformtypeEnum.GTS2.getLabelKey());
										}else{
											errorMsg += "第"+(i+1)+"行，交易平台输入有误!;";
										}
									}
								}else if(j == 6){//对应账号
									if(StringUtils.isNotBlank(objStr)){
										param.setAccountNo(objStr);
										if(objStr.indexOf(".") != -1){
											errorMsg += "第"+(i+1)+"行，账号需为文本类型，请检查!;";
										}
									}
								}else if(j == 7){//对应电话
									if(StringUtils.isNotBlank(objStr)){
										param.setMobile(objStr);
										if(objStr.indexOf(".") != -1){
											errorMsg += "第"+(i+1)+"行，电话需为文本类型，请检查!;";
										}
									}
								}else if(j == 8){//对应邮箱
									if(StringUtils.isNotBlank(objStr)){
										param.setEmail(objStr);
									}
								}else if(j == 9){//对应证件号码
									if(StringUtils.isNotBlank(objStr)){
										param.setIdCard(objStr);
										if(objStr.indexOf(".") != -1){
											errorMsg += "第"+(i+1)+"行，证件号码需为文本类型，请检查!;";
										}
									}
								}else if(j == 10){//对应IP
									if(StringUtils.isNotBlank(objStr)){
										param.setIp(objStr);
									}
								}else if(j == 11){//对应设备类型
									if(StringUtils.isNotBlank(objStr)){
										if(DeviceTypeEnum.PC.getValue().equalsIgnoreCase(objStr)){
											param.setDeviceType(DeviceTypeEnum.PC.getLabelKey());
										}else if(DeviceTypeEnum.ANDROID.getValue().equalsIgnoreCase(objStr)){
											param.setDeviceType(DeviceTypeEnum.ANDROID.getLabelKey());
										}else  if(DeviceTypeEnum.IOS.getValue().equalsIgnoreCase(objStr)){
											param.setDeviceType(DeviceTypeEnum.IOS.getLabelKey());
										}else{
											errorMsg += "第"+(i+1)+"行，设备类型输入有误!;";
										}
									}
								}else if(j == 12){//对应设备信息
									if(StringUtils.isNotBlank(objStr)){
										param.setDeviceInfo(objStr);
									}
								}
							}
							listParam.add(param);
						}
					}
					
					if(StringUtils.isNotBlank(errorMsg)){
						errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
					}else{
						//再次验证数据正确性
						// 1、账户非空时，事业部及平台项必填;
						// 2、账户非空时，要素（账号\电话\邮箱\证件号码\IP\设备类型 设备信息） 这些项输入的值忽略;
						// 3、风险类型+风险原因+风险备注是必填项(上一步已经判断过，不在验证);
						// 4、设备类型 -(有设备信息时必须有)
						for(int i=0; i<listParam.size(); i++){
							RiskBlacklistEntity param = listParam.get(i);
							if(StringUtils.isNotBlank(param.getAccountNo())){
								if(StringUtils.isBlank(param.getCompanyId()+"") || StringUtils.isBlank(param.getPlatform())){
									errorMsg += "第"+(i+1)+"行，账户非空时，事业部及平台项必填!;";
									continue;
								}
								param.setMobile("");
								param.setEmail("");
								param.setIdCard("");
								param.setIp("");
								param.setDeviceType("");
								param.setDeviceInfo("");
							}
							if(StringUtils.isNotBlank(param.getDeviceInfo())){
								if(StringUtils.isBlank(param.getDeviceType())){
									errorMsg += "第"+(i+1)+"行，设备信息非空时，设备类型必填!;";
									continue;
								}
							}
						}
						
						if(StringUtils.isNotBlank(errorMsg)){
							errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
						}else{
							// 插入Excel数据
							for(RiskBlacklistEntity param:listParam){
								riskBlacklistService.saveOrUpdate2(param);
							}
							errorMsg = "导入全部成功";
						}
					}
				} else {
					logger.error(context.getResMsg());
					errorMsg = context.getResMsg();
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
				errorMsg = "上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName;
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
			errorMsg = "上传文件异常:" + e.getMessage();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			String returnStr = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
					+ errorMsg + "');</script>";
			out.print(returnStr);
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件IO异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 账号诊断导入-上传excel文件-导入账号诊断并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/accountAnalyzeUploadExcel/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void accountAnalyzeUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		String errorMsg = "";
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					List<AccountAnalyzeEntity> listParam = new ArrayList<AccountAnalyzeEntity>();
					if (null != file) {
						// 解析excel并转化为账号诊断对象
						List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 5);
						for(int i=1; i<list.size(); i++){//前1行是标题，
							AccountAnalyzeEntity param = new AccountAnalyzeEntity();
							List<Object> objList = list.get(i);
							for(int j=0; j<objList.size(); j++){
								Object obj = objList.get(j);
								String objStr = obj == null?"":obj.toString();
								if(j == 0){//第一列下标为0,对应账号
									if(StringUtils.isNotBlank(objStr)){
										param.setAccountNo(objStr);
									}else{
										errorMsg += "第"+(i+1)+"行，账号为空!;";
										continue;
									}
								}else if(j == 1){//对应平台
									if(StringUtils.isNotBlank(objStr)){
										if(PlatformtypeEnum.MT4.getLabelKey().equals(objStr)){
											param.setPlatform(objStr);
										}else if(PlatformtypeEnum.MT5.getLabelKey().equals(objStr)){
											param.setPlatform(objStr);
										}else if(PlatformtypeEnum.GTS.getLabelKey().equals(objStr)){
											param.setPlatform(objStr);
										}else if(PlatformtypeEnum.GTS2.getLabelKey().equals(objStr)){
											param.setPlatform(objStr);
										}else{
											errorMsg += "第"+(i+1)+"行，平台类型有误!;";
											continue;
										}
									}
								}else if(j == 2){//对应邮箱
									if(StringUtils.isNotBlank(objStr)){
										if(EmailUtil.checkEmail(objStr)){
											param.setEmail(objStr);
										}else{
											errorMsg += "第"+(i+1)+"行，邮箱格式有误!;";
											continue;
										}
									}else{
										errorMsg += "第"+(i+1)+"行，邮箱为空!;";
										continue;
									}
								}else if(j == 3){//对应诊断开始时间
									if(StringUtils.isNotBlank(objStr)){
										try{
											DateUtil.stringToDate(objStr);
											param.setStartDate(objStr);
										}catch(Exception e){
											errorMsg += "第"+(i+1)+"行，诊断开始时间格式应为(yyyy-MM-dd)!;";
											continue;
										}
									}else{
										errorMsg += "第"+(i+1)+"行，诊断开始时间为空!;";
										continue;
									}
								}else if(j == 4){//对应诊断结束时间
									if(StringUtils.isNotBlank(objStr)){
										try{
											DateUtil.stringToDate(objStr);
											param.setEndDate(objStr);
										}catch(Exception e){
											errorMsg += "第"+(i+1)+"行，诊断结束时间格式应为(yyyy-MM-dd)!;";
											continue;
										}
									}else{
										errorMsg += "第"+(i+1)+"行，诊断结束时间为空!;";
										continue;
									}
								}
							}
							listParam.add(param);
						}
					}
					
					if(StringUtils.isNotBlank(errorMsg)){
						errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
					}else{
						// 插入Excel的账号诊断数据
						Random r = new Random();
						String batchNo = Constants.batchNoPrefix + DateUtil.formatDateToString(new Date(), "yyyyMMdd_HHmmss_SSS") + "_" + r.nextInt(99);
						for(AccountAnalyzeEntity param:listParam){
							param.setBatchNo(batchNo);
							param.setGenerateStatus(AccountAnalyzeStatusEnum.N.getLabelKey());
							accountAnalyzeService.saveOrUpdate(param);
						}
						errorMsg = "导入全部成功";
					}
				} else {
					logger.error(context.getResMsg());
					errorMsg = context.getResMsg();
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
				errorMsg = "上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName;
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
			errorMsg = "上传文件异常:" + e.getMessage();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			String returnStr = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
					+ errorMsg + "');</script>";
			out.print(returnStr);
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件IO异常:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 渠道导入-上传excel文件-导入渠道并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/channelUploadExcel/{channelType}/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void channelUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String channelType, @PathVariable String jsName, @PathVariable String jsFunName) {
		
		String errorMsg = "";
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					List<ChannelEntity> listParam = new ArrayList<ChannelEntity>();
					if (null != file) {
						// 解析excel并转化为渠道对象
						List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 6);
						for(int i=1; i<list.size(); i++){//前1行是标题，
							ChannelEntity param = new ChannelEntity();
							List<Object> objList = list.get(i);
							for(int j=0; j<objList.size(); j++){
								Object obj = objList.get(j);
								String objStr = obj == null?"":obj.toString();
								if(j == 0){//第一列下标为0,对应来源字段
									param.setUtmcsr(objStr);
								}else if(j == 1){//对应媒介字段
									param.setUtmcmd(objStr);
								}else if(j == 2){//对应渠道名称字段
									if(StringUtils.isNotBlank(objStr)){
										param.setChannelName(objStr);
									}else{
										errorMsg += "第"+(i+1)+"行，渠道名称为空!;";
										continue;
									}
								}else if(j == 3){//对应是否付费字段
									if("免费".equals(objStr)){
										param.setIsPay(1L);
									}else if("付费".equals(objStr)){
										param.setIsPay(2L);
									}else if("其他".equals(objStr)){
										param.setIsPay(3L);
									}else{
										errorMsg += "第"+(i+1)+"行，是否付费字段为空或者错误!;";
										continue;
									}
								}else if(j == 4){//对应渠道分组
									if(StringUtils.isNotBlank(objStr)){
										param.setChannelGroup(objStr);
									}else{
										// 不填写则默认为：Direct
										param.setChannelGroup("Direct");
									}
								}else if(j == 5){//对应渠道级别
									if(StringUtils.isNotBlank(objStr)){
										param.setChannelLevel(objStr);
									}else{
										// 不填写则默认为：Direct
										param.setChannelLevel("Direct");
									}
								}
							}
							listParam.add(param);
						}
					}
					
					if(StringUtils.isNotBlank(errorMsg)){
						errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
					}else{
						ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.format(channelType);
						if(null == channelType){
							errorMsg = "所选择的渠道类型不存在，请检查后再次导入";
						}
						// 先删除以前渠道数据
						channelService.deleteByCompanyId(channelTypeEnum);
						// 插入Excel的渠道数据
						for(ChannelEntity param:listParam){
							param.setChannelType(channelTypeEnum.getLabelKey());
							channelService.saveOrUpdate(param);
						}
						errorMsg = "导入全部成功";
					}
					
				} else {
					logger.error(context.getResMsg());
					errorMsg = context.getResMsg();
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
				errorMsg = "上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName;
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
			errorMsg = "上传文件异常:" + e.getMessage();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			String returnStr = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
					+ errorMsg + "');</script>";
			out.print(returnStr);
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件IO异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 流量充值-上传Excel文件-读取内容并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/flowUploadExcel/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void flowUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					// 解析excel
					String phones = "";
					if (null != file) {
						List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 1);
						for(int i=1; i<list.size(); i++){//前1行是标题，
							List<Object> objList = list.get(i);
							for(int j=0; j<objList.size(); j++){
								Object obj = objList.get(j);
								String objStr = obj == null?"":obj.toString();
								if(j == 0){//第一列下标为0,对应手机号
									phones += objStr + ",";
								}
							}
						}
					}
					if(StringUtils.isNotBlank(phones)){
						phones = phones.substring(0, phones.length()-1);
					}
					String str = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
							+ phones + "');</script>";
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					logger.error(context.getResMsg());
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 短信发送-上传text文件-读取内容并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/smsUploadText/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void smsUploadText(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.textFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					// 解析txt文件
					String phones = null;
					if (null != file) {
						String content = new String(file.getBytes(), "UTF-8");// 字符编码
						String[] strArr = content.split("\n|\r\n|\r|\\s+");// 回车换行和空格
						phones = StringUtil.array2String(strArr);
					}
					String str = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
							+ phones + "');</script>";
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					logger.error(context.getResMsg());
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
			}
		} catch (IOException e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 邮件发送-上传text文件-读取内容并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/emailUploadText/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void emailUploadText(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.textFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					// 解析txt文件
					String emails = null;
					if (null != file) {
						String content = new String(file.getBytes(), "UTF-8");// 字符编码
						String[] strArr = content.split("\n|\r\n|\r|\\s+");// 回车换行和空格
						emails = StringUtil.array2String(strArr);
					}
					String str = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
							+ emails + "');</script>";
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					logger.error(context.getResMsg());
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
			}
		} catch (IOException e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 上传text文件
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/uploadText/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void uploadText(DefaultMultipartHttpServletRequest request, HttpServletResponse response, @PathVariable String jsName, @PathVariable String jsFunName) {
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.textFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					String str = "<script type=\"text/javascript\">window.parent." + jsName + "." + jsFunName + "('"
							+ context.getResUrl() + "');</script>";
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					logger.error(context.getResMsg());
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
			}
		} catch (IOException e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 上传图片文件
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/uploadImg/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void uploadImg(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String jsName, @PathVariable String jsFunName) {
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.imageFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					String str = "<script type=\"text/javascript\">window.parent." + jsName + "." + jsFunName + "('" + context.getResUrl() + "');</script>";
					PrintWriter out = response.getWriter();
					out.print(str);
					out.flush();
				} else {
					logger.error(context.getResMsg());
				}
			} else {
				logger.error("上传图片参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
			}
		} catch (IOException e) {
			logger.error("上传图片文件异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 单个文件上传通用方法
	 * 上传至fastfds服务器上
	 */
	private UploadContext uploadFastfds(DefaultMultipartHttpServletRequest request, HttpServletResponse response, UploadFileTypeEnum fileTypeEnum) {
		// 上传文件上下文对象
		UploadContext uploadContext = new UploadContext();
		uploadContext.setFileName("");
		uploadContext.setFileType(fileTypeEnum);
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			// 1、循环上传文件列表， 只取第一个文件
			Iterator<String> iter = request.getFileNames();
			MultipartFile file = null;
			while (iter.hasNext()) {
				String fileName = iter.next();
				file = request.getFile(fileName);
				break;
			}
			// 2、判断文件是否为null
			if (file == null) {
				logger.error("未找到上传的文件！请检查;");
				uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
				uploadContext.setResMsg("未找到上传的文件！请检查;");
				return uploadContext;
			}
			// 3、文件大小及判断文件类型是否正确
			String orgFileName = file.getOriginalFilename();
			Long fileSize = file.getSize();
			if (fileSize > Constants.MAX_UPLOAD_FILE_SIZE) {
				logger.error("最大上传文件大小为10M，请检查;");
				uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
				uploadContext.setResMsg("最大上传文件大小为10M，请检查;");
				return uploadContext;
			}
			String fileType = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
			if (UploadFileTypeEnum.imageFile.getLabelKey().equals(fileTypeEnum.getLabelKey())) {
				if (!(fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg")
						|| fileType.equalsIgnoreCase("bmp") || fileType.equalsIgnoreCase("gif")
						|| fileType.equalsIgnoreCase("png"))) {
					logger.error("图片类型不正确，支持(jpg/jpeg/bmp/gif/png)，请检查;");
					uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
					uploadContext.setResMsg("图片类型不正确，支持(jpg/jpeg/bmp/gif/png)，请检查;");
					return uploadContext;
				}
			} else if (UploadFileTypeEnum.excelFile.getLabelKey().equals(fileTypeEnum.getLabelKey())) {
				if (!(fileType.equalsIgnoreCase("xls") || fileType.equalsIgnoreCase("xlsx"))) {
					logger.error("excel文件类型不正确，支持(xls/xlsx)，请检查;");
					uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
					uploadContext.setResMsg("excel文件类型不正确，支持(xls/xlsx)，请检查;");
					return uploadContext;
				}
			} else if (UploadFileTypeEnum.textFile.getLabelKey().equals(fileTypeEnum.getLabelKey())) {
				if (!(fileType.equalsIgnoreCase("txt") || fileType.equalsIgnoreCase("text"))) {
					logger.error("text文件类型不正确，支持(txt/text)，请检查;");
					uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
					uploadContext.setResMsg("text文件类型不正确，支持(txt/text)，请检查;");
					return uploadContext;
				}
			} else {
				logger.error("目前只支持上传图片及excel文件及text文件类型！请检查;");
				uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
				uploadContext.setResMsg("目前只支持上传图片及excel文件及text文件类型！请检查;");
				return uploadContext;
			}

			// 4、上传文件
			// 建立连接
		    TrackerClient tracker = new TrackerClient();
		    TrackerServer trackerServer = tracker.getConnection();
		    StorageServer storageServer = null;
		    StorageClient1 client = new StorageClient1(trackerServer, storageServer);
		    
		    // 设置元信息
		    NameValuePair[] metaList = new NameValuePair[3];
		    metaList[0] = new NameValuePair("fileName", orgFileName);
		    metaList[1] = new NameValuePair("fileExtName", fileType);
		    metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
		    
		    // 上传文件, 设置返回文件的url
		    byte[] fileBuff = file.getBytes();
		    String fileId = client.upload_file1(fileBuff, fileType, metaList);
		    String fileName = fileId;
		    if(StringUtils.isNotBlank(fileId)){
		    	int lastIndex = fileId.lastIndexOf("/");
			    if(lastIndex != -1){
			    	fileName = fileId.substring(lastIndex + 1, fileId.length());
			    }
		    }
			String uploadFileAccessurl = SystemConfigUtil.getProperty(SystemConfigEnum.UploadFileAccessurl);
		    uploadContext.setResUrl(uploadFileAccessurl + "/" + fileId);
		    
			// 6 、保存上传日志
			UploadLogEntity logEntity = new UploadLogEntity();
			logEntity.setUserId(this.getLoginUser(request).getUserId());
			logEntity.setFileType(fileTypeEnum.getLabelKey());
			logEntity.setFileName(fileName);
			logEntity.setFilePath(fileId);
			logEntity.setFileUrl(uploadFileAccessurl + "/" + fileId);
			uploadLogService.saveOrUpdate(logEntity);

			// 设置上传的文件
			Map<String, Object> resParameters = uploadContext.getResParameters();
			resParameters.put("file", file);
		} catch (Exception e) {
			logger.error("上传出现异常！" + e.getMessage(), e);
			uploadContext.setResCode(ResultCodeEnum.fail.getLabelKey());
			uploadContext.setResMsg("上传出现异常！" + e.getMessage());
			return uploadContext;
		}

		// 成功返回
		uploadContext.setResCode(ResultCodeEnum.success.getLabelKey());
		uploadContext.setResMsg("上传成功！");
		return uploadContext;
	}
	
	/**
	 * 新客咨询导入-上传excel文件-导入新客咨询并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/consulttationNumUploadExcel/{reportType}/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void consulttationNumUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String reportType, @PathVariable String jsName, @PathVariable String jsFunName) {
		
		String errorMsg = "";
		try {
			if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
				// 上传
				UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
				if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
					MultipartFile file = (MultipartFile)context.getResParameters().get("file");
					List<CustConsultationEntity> listParam = new ArrayList<CustConsultationEntity>();
					if (null != file) {
						// 解析excel并转化为渠道对象
						List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 5);
						for(int i=1; i<list.size(); i++){//前1行是标题，
							CustConsultationEntity param = new CustConsultationEntity();
							List<Object> objList = list.get(i);
							for(int j=0; j<objList.size(); j++){
								Object obj = objList.get(j);
								String objStr = obj == null?"":obj.toString();
								if(j == 1){//第一列下标为0,对应咨询类型
									if(StringUtils.isNotBlank(objStr)){
										if(ReportTypeEnum.months.getValue().equals(objStr)){
											param.setReportType(ReportTypeEnum.months.getLabelKey());
										}
										if(ReportTypeEnum.days.getValue().equals(objStr)){
											param.setReportType(ReportTypeEnum.days.getLabelKey());
										}
									}else{
										errorMsg += "第"+(i+1)+"行，咨询类型为空!;";
										continue;
									}
								}else if(j == 2){//对应新客咨询数
									param.setNewConsulttationNum(Double.valueOf(objStr).intValue());
								}else if(j == 3){//对应老客咨询数
									param.setOldConsulttationNum(Double.valueOf(objStr).intValue());
								}else if(j == 4){//对应咨询时间
									DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
									Date d = dateFormat.parse(objStr);
									dateFormat = new SimpleDateFormat("yyyy-MM");
									String dateStr = dateFormat.format(d);
									param.setConsulttationTime(dateStr);
								}
							}
							listParam.add(param);
						}
					}
					
					if(StringUtils.isNotBlank(errorMsg)){
						errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
					}else{
						for (CustConsultationEntity custConsultationEntity : listParam) {
							CustConsultationEntity queryCustConsultationEntity = custConsultationService.findCustConsultation(custConsultationEntity);
							if(null != queryCustConsultationEntity ){
								queryCustConsultationEntity.setNewConsulttationNum(custConsultationEntity.getNewConsulttationNum());
								queryCustConsultationEntity.setOldConsulttationNum(custConsultationEntity.getOldConsulttationNum());
								custConsultationService.update(queryCustConsultationEntity);
							}else{
								custConsultationService.saveOrUpdate(custConsultationEntity);
							}
							errorMsg = "导入全部成功";
						}
					}
				} else {
					logger.error(context.getResMsg());
					errorMsg = context.getResMsg();
				}
			} else {
				logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
				errorMsg = "上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName;
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
			errorMsg = "上传文件异常:" + e.getMessage();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			String returnStr = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
					+ errorMsg + "');</script>";
			out.print(returnStr);
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件IO异常:" + e.getMessage(), e);
		}
	}
	
	
	/**
	 * 业务标签导入-上传excel文件-导入标签并返回
	 * 
	 * @param jsName
	 *            String 回调js文件的变量名称
	 * @param jsFunName
	 *            String 回调js文件的函数名称
	 */
	@RequestMapping(value = "/businessTagUploadExcel/{tagContent}/{tagType}/{jsName}/{jsFunName}", method = { RequestMethod.POST })
	public void businessTagUploadExcel(DefaultMultipartHttpServletRequest request, HttpServletResponse response
			, @PathVariable String tagContent,@PathVariable String tagType, @PathVariable String jsName, @PathVariable String jsFunName) {
		
		String errorMsg = "";
		try {
			String busTagContent = BusTagContentEnum.format(tagContent);
			String busTagType = BusTagTypeEnum.format(tagType);
			if(null == busTagContent){
				errorMsg = "所选择的标签内容不存在，请检查后再次导入";
			}else if(null == busTagType){
				errorMsg = "所选择的标签类型不存在，请检查后再次导入";
			}else{
				if (StringUtils.isNotBlank(jsName) && StringUtils.isNotBlank(jsFunName)) {
					// 上传
					UploadContext context = uploadFastfds(request, response, UploadFileTypeEnum.excelFile);
					if (ResultCodeEnum.success.getLabelKey().equals(context.getResCode())) {
						MultipartFile file = (MultipartFile)context.getResParameters().get("file");
						List<BusinessTagEntity> listParam = new ArrayList<BusinessTagEntity>();
						if (null != file) {
							// 解析excel并转化为业务标签对象
							List<List<Object>> list = ExcelUtil.read2007ExcelByInputStream(file.getInputStream(), 6);
							for(int i=1; i<list.size(); i++){//前1行是标题，
								BusinessTagEntity param = new BusinessTagEntity();
								List<Object> objList = list.get(i);
								for(int j=0; j<objList.size(); j++){
									Object obj = objList.get(j);
									String objStr = obj == null?"":obj.toString();																	
									if(BusTagTypeEnum.PAGE.getLabelKey().equals(tagType)){
										if(j ==0){//对应tagUrl
											param.setTagUrl(objStr);
										}
									}else{
										if(j == 0){//对应EventCategory
											param.setEventCategory(objStr);
										}else if(j == 1){//对应EventAction
											param.setEventAction(objStr);
										}else if(j == 2){//对应EventLabel
											param.setEventLabel(objStr);
										}else if(j == 3){//对应EventValue
											param.setEventValue(objStr);
										}
									}								
								}
								listParam.add(param);
							}
						}
						if(StringUtils.isNotBlank(errorMsg)){
							errorMsg = "导入数据有误，请检查后再次导入("+errorMsg+")";
						}else{
							// 先删除以前渠道数据
							tagService.deleteByTagType(Integer.valueOf(tagContent),Integer.valueOf(tagType));
							// 插入Excel的业务标签数据
							for(BusinessTagEntity param:listParam){
								param.setTagContent(Integer.valueOf(tagContent));
								param.setTagType(Integer.valueOf(tagType));
								tagService.saveOrUpdate(param);
							}
							errorMsg = "导入全部成功";
						}
					} else {
						logger.error(context.getResMsg());
						errorMsg = context.getResMsg();
					}
				} else {
					logger.error("上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName);
					errorMsg = "上传参数错误，jsName=" + jsName + "，jsFunName=" + jsFunName;
				}
			}
		} catch (Exception e) {
			logger.error("上传文件异常:" + e.getMessage(), e);
			errorMsg = "上传文件异常:" + e.getMessage();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			String returnStr = "<script type=\"text/javascript\">window.parent." + jsName + "."+ jsFunName +"('"
					+ errorMsg + "');</script>";
			out.print(returnStr);
			out.flush();
		} catch (IOException e) {
			logger.error("上传文件IO异常:" + e.getMessage(), e);
		}
	}
	
}
