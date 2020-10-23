package com.kc.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: PingUtil
 * @Description: TODO ping命令工具类
 * @author jason
 * @date 2017-8-8
 */
public class PingUtil {

	private static Logger logger = Logger.getLogger(PingUtil.class);

	private PingUtil() {

	}

	/**
	 * @Title: ping
	 * @Description: TODO 执行ping命令
	 * @param address ip或者域名
	 * @param num ping的次数
	 * @param timeout 超时时间
	 * @return boolean
	 * @throws
	 */
	public static boolean ping(String address, int num, int timeout) {
		return ping(address, num, timeout, false);
	}

	/**
	 * @Title: ping
	 * @Description: TODO  执行ping命令
	 * @param address ip或者域名
	 * @param num ping的次数
	 * @param timeout 超时时间
	 * @param isCheckAddress 验证地址
	 * @return boolean
	 * @throws
	 */
	private static boolean ping(String address, int num, int timeout, boolean isCheckAddress) {
		BufferedReader in = null;
		try { // 执行命令并获取输出
			String osName = System.getProperties().getProperty("os.name");
			String pingCmd = null;
			boolean isWin = false;
			System.out.println(osName);
			if (osName.startsWith("Windows")) {
				pingCmd = "cmd /c ping -n {0} -w {1} {2}";
				isWin = true;
			} else if (osName.startsWith("Linux")) {
				pingCmd = "ping -c {0} -i 0 -W {1} {2}";
			} else {
				logger.error("not support OS");
				return false;
			}
			Process process = Runtime.getRuntime().exec(
					MessageFormat.format(pingCmd, num, timeout, address));
			if (process == null) {
				return false;
			}
			System.out.println();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			//链接次数
			int connectedCount = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				if (StringUtils.isEmpty(line)) continue;
				if (line.toLowerCase().contains("ping")) continue;
				boolean isValid = false;
				if (isWin)
					isValid = windowValid(line, isCheckAddress);
				else
					isValid = linuxValid(line, isCheckAddress);
				if (isCheckAddress) {
					return isValid;
				} else {
					if (isValid)connectedCount++;
				}
			}
			return (connectedCount == num);
		} catch (Exception ex) {
			logger.error("window ping cmd error!", ex);
			return false;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("close BufferedReader error!", e);
			}
		}
	}

	/**
	 * @Title: isAddress
	 * @Description: TODO 判断地址是否正确
	 * @param address
	 * @return boolean
	 * @throws
	 */
	public static boolean isAddress(String address, boolean isInetAddress,int timeout) {
		if (isInetAddress) {
			try {
				InetAddress.getByName(address).isReachable(timeout);
				return true;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO: handle exception
				logger.error("InetAddress isReachable error!", e);
			}
			return false;
		}
		return ping(address, 1, 500, true);
	}


	/**
	 * @Title: windowValid
	 * @Description: TODO window系统 检测ping数据是否有效
	 * @param result
	 * @return int
	 * @throws
	 */
	private static boolean windowValid(String result, boolean isCheckAddress) {
		if (isCheckAddress) {
			return !result.contains("请求找不到主机");
		}
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(result);
		return matcher.find();
	}

	/**
	 * @Title: linuxValid
	 * @Description: TODO linux系统 检测ping数据是否有效
	 * @param result
	 * @return boolean
	 * @throws
	 */
	private static boolean linuxValid(String result, boolean isCheckAddress) {
		if (isCheckAddress) {
			return !result.contains("unknown");
		}
		if (result.contains("bytes from") && result.contains("icmp_seq=")) {
			return true;
		}
		return false;
	}
}
