package com.zyf.other.web.test;

import com.alibaba.fastjson.JSONObject;
import com.zyf.framework.utils.DownLoadUtils;
import com.zyf.framework.utils.EhCacheUtils;
import com.zyf.framework.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("test")
@Api(value = "测试使用的接口", description = "测试接口都写这")
public class TestController {

	@ApiOperation(value="MVC测试", notes="直接根据URL测试")
	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public String test() throws IOException {
		/*Token token = SSOHelper.attrToken(null);
		System.out.println(token.toString());*/
		return "test";
	}

	@ApiOperation(value="测试下载功能", notes="直接根据URL进行下载测试")
	@RequestMapping(value = "down", method = RequestMethod.GET)
	@ResponseBody
	public void down(HttpServletResponse response) throws IOException {
		String path = PathUtils.getPath("static/ehcache/ehcache.xml");
		DownLoadUtils.download(response, "ss.zip", path);
	}

	@ApiOperation(value="测试缓存存取功能", notes="直接根据URL进行缓存测试")
	@RequestMapping(value = "cache", method = RequestMethod.GET)
	@ResponseBody
	public String cache() throws IOException {
		//测试将json对象存入缓存中
		JSONObject obj = new JSONObject();
		obj.put("name", "lsz");
		EhCacheUtils.addToCache("cache_json", obj);
		//从缓存中获取
		JSONObject getobj = (JSONObject) EhCacheUtils.getCacheElement("cache_json");
		return getobj.toString();
	}

	@ApiOperation(value="测试全局异常抛出错误统一处理", notes="直接根据URL进行测试")
	@RequestMapping(value = "error",method = RequestMethod.GET)
	public void error() throws Exception {
		throw new Exception("错误");
	}

}
