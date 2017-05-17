package com.zyf.admin.support.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public final class BrowserUtils {
    private static final Logger logger = Logger.getLogger("Browser");

    private BrowserUtils() {
    }

    public static String getUserAgent(HttpServletRequest request, String value) {
        StringBuffer sf = new StringBuffer();
        sf.append(value);
        sf.append("-");
        sf.append(request.getHeader("user-agent"));
        logger.fine("Browser info:" + sf.toString());
        return MD5.toMD5(sf.toString());
    }

    public static boolean isLegalUserAgent(HttpServletRequest request, String value, String userAgent) {
        String rlt = getUserAgent(request, value);
        if(rlt.equalsIgnoreCase(userAgent)) {
            logger.fine("Browser isLegalUserAgent is legal. Browser getUserAgent:" + rlt);
            return true;
        } else {
            logger.fine("Browser isLegalUserAgent is illegal. Browser getUserAgent:" + rlt);
            return false;
        }
    }
}
