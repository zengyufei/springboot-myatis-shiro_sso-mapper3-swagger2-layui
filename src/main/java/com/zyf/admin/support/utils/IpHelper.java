package com.zyf.admin.support.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class IpHelper {
    private static final Logger logger = Logger.getLogger("IpHelper");
    private static String LOCAL_IP_STAR_STR = "192.168.";
    public static final String LOCAL_IP;
    public static final String HOST_NAME;

    public IpHelper() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException var4) {
                    logger.severe("IpHelper error." + var4.toString());
                }
            }
        }

        if(ip != null && ip.length() > 15 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    static {
        String ip = null;
        String hostName = null;

        try {
            hostName = InetAddress.getLocalHost().getHostName();
            InetAddress[] e = InetAddress.getAllByName(hostName);

            for(int i = 0; i < e.length; ++i) {
                ip = e[i].getHostAddress();
                if(ip.startsWith(LOCAL_IP_STAR_STR)) {
                    break;
                }
            }

            if(ip == null) {
                ip = e[0].getHostAddress();
            }
        } catch (UnknownHostException var4) {
            logger.severe("IpHelper error.");
            var4.printStackTrace();
        }

        LOCAL_IP = ip;
        HOST_NAME = hostName;
    }
}