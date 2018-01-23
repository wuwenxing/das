package com.gw.das.common.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 亿美流量服务实现类
 * 
 * @author wayne
 */
public class YmFlowServer {
	private static final Logger logger = LoggerFactory.getLogger(YmFlowServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 开发者账号信息
	private static String flowYmUrl = "";
	private static String flowYmAppid = "";
	private static String flowYmToken = "";
	private static String systemActive = "";

	/**
	 * 流量监听器
	 */
	private List<FlowListener> flowListeners = new ArrayList<FlowListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		flowYmUrl = SystemConfigUtil.getProperty(SystemConfigEnum.flowYmUrl);
		flowYmAppid = SystemConfigUtil.getProperty(SystemConfigEnum.flowYmAppid);
		flowYmToken = SystemConfigUtil.getProperty(SystemConfigEnum.flowYmToken);
		systemActive = SystemConfigUtil.getProperty(SystemConfigEnum.systemActive);
		logger.info(flowYmUrl + "," + flowYmAppid + "," + flowYmToken + "," + systemActive);
	}

	/**
	 * 充值多条流量
	 */
	public void send(List<FlowInfo> flowInfos) {
		for (FlowInfo flowInfo : flowInfos) {
			send(flowInfo);
		}
	}

	/**
	 * 充值单条流量; ||mobiles 手机号列表，最多支持50个。 ||taskNo 订单唯一号，贵方提交必须保证唯一
	 * (与返回值batchNo)对应。 ||ctcc 电信套餐编号，根据提供套餐列表选择。 ||cucc 联通套餐编号，根据提供套餐列表选择。
	 * ||cmcc 移动套餐编号，根据提供套餐列表选择。 ||etype 生效类型，0-立即生效。 ||token 分配给您的准入TOKEN，用于加密。
	 * ||appId 分配给您的准入APPID，用于识别用户。 ||version 版本号(不填默认为1，2表示当前版本) 否（传2）。
	 * (注：如果该运营商要充值多个套装请以半角逗号分隔（每个运营商最多选5个套餐)。
	 */
	public void send(final FlowInfo flowInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				FlowContext flowContext = new FlowContext();
				flowContext.setFlowInfo(flowInfo);
				doBefore(flowContext);
				try {
					// 电话号码，最多50个，必选
					String mobiles = flowInfo.getPhones();
					// 批次唯一标识
					String taskNo = flowInfo.getFlowLogId() + "";
					// 电信套餐编号，根据提供套餐列表选择，必选
					String ctcc = flowInfo.getFlowPackage().getCtcc();
					// 联通套餐编号，根据提供套餐列表选择，必选
					String cucc = flowInfo.getFlowPackage().getCucc();
					// 移动套餐编号，根据提供套餐列表选择，必选
					String cmcc = flowInfo.getFlowPackage().getCmcc();
					// 生效类型：0-立即生效，1-下月生效，选填(默认立即生效)【我们会发送给运营商，但是不保证运营商受理】
					String etype = "0";

					StringBuffer buffer = new StringBuffer();
					buffer.append("mobiles=" + mobiles)
						.append("&taskNo=" + taskNo)
						.append("&ctcc=" + ctcc)
						.append("&cucc=" + cucc)
						.append("&cmcc=" + cmcc)
						.append("&etype=" + etype);

					String code = buffer.toString();
					String key = EMayEncryption.md5(code);
					String value = EMayEncryptionUtils
							.parseByte2HexStr(EMayEncryptionUtils.aesEncrypt(code, YmFlowServer.flowYmToken));

					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("appId", YmFlowServer.flowYmAppid);
					parameters.put("key", key);
					parameters.put("value", value);
					// 不传这个参数默认为1
					parameters.put("version", "2");
					// 提交请求
					String result = HttpUtil.getInstance().doPost(YmFlowServer.flowYmUrl, parameters);
					/**
					 * result: { "code":"E0001", //状态吗 "msg":"拒绝访问",
					 * //错误信息，成功的话，此处为空 "batchNo":"3028402934283",
					 * //批次号，我方放回的批次号，回调以此为准 "errorMobiles":["123","234"],
					 * //错误号码，检测到提供的错误不能处理的号码 "mobileNumber":10 //能够处理的号码总数
					 * }
					 * result:{"code":"M0006","msg":"解密校验失败","batchNo":null,
					 * "mobileNumber":null,"errorMobiles":null}
					 */
					logger.info("result:" + result);
					YmFlowResponse response = JacksonUtil.readValue(result, YmFlowResponse.class);
					flowContext.setYmFlowResponse(response);
					doAfter(flowContext);
				} catch (Exception e) {
					logger.error("ym-充值流量出现异常", e);
					flowContext.setThrowable(e);
					doAfterThrowable(flowContext);
				}
			}
		});
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param flowListener
	 */
	public void addFlowListener(FlowListener flowListener) {
		this.flowListeners.add(flowListener);
	}

	private void doBefore(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateBefore(flowContext);
		}
	}

	private void doAfter(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfter(flowContext);
		}
	}

	private void doAfterThrowable(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfterThrowable(flowContext);
		}
	}
}
