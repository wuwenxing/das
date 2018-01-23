package com.gw.das.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 摘要：IP处理类
 */
public class IPUtil {

	private static final Logger logger = Logger.getLogger(IPUtil.class);
	
	/**
	 * 功能：获取客户端IP
	 * @param request  客户端request
	 * @return   客户端IP
	 */
	public static String getClientIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}else{
			ip=request.getRemoteAddr();
		}
		return ip;
    }
	
	/**
	 * 功能：获取真实本地的IP
	 * @return 本机IP
	 */
	public static String getRealIp(){
		String localip = "";												// 本地IP，如果没有配置外网IP则返回它
		String netip = "";													// 外网IP
		Enumeration<NetworkInterface> netInterfaces = null;
		try{
			 netInterfaces = NetworkInterface.getNetworkInterfaces();
			 InetAddress ip = null;
				boolean finded = false;										// 是否找到外网IP
				while (netInterfaces.hasMoreElements() && !finded) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = address.nextElement();
						if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {		// 外网IP
							netip = ip.getHostAddress();
							finded = true;
							break;
						} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {	// 内网IP
							localip = ip.getHostAddress();
						}
					}
				}
				if (netip != null && !"".equals(netip)) {
					return netip;
				} else {
					return localip;
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static List<String> getLocalIp(){
		List<String> addrList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> e1 = NetworkInterface.getNetworkInterfaces();
			while (e1 != null && e1.hasMoreElements()) {
				NetworkInterface ifc = e1.nextElement();
				if (ifc.isUp()) {
					Enumeration<InetAddress> e2 = ifc.getInetAddresses();
					while (e2 != null && e2.hasMoreElements()) {
						InetAddress addr = e2.nextElement();
						if (!addr.getHostAddress().equals("127.0.0.1")) {
							String address = addr.getHostAddress();
							if(address.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
								addrList.add(address);	
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			logger.error("", e);
		}
		return addrList;
	}
}
