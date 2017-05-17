package com.zyf.admin.web.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyf.commons.entity.sys.User;
import com.zyf.commons.option.sys.UserOption;
import com.zyf.commons.result.Msg;
import com.zyf.commons.service.sys.UserService;
import com.zyf.framework.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "用户管理", description = "用户增删改查")
@Slf4j
@RestController //如果一个 controller 的所有方法都返回 json 字符串，则可以使用该注解，抵消方法上的 @ResponseBody
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "查看用户列表带分页", notes = "直接查询带分页")
	@ApiResponse(code = 401, message = "查询失败")
	@RequestMapping(value = "queryList")
	public String queryList(@RequestParam(required = false, name = "pageIndex", defaultValue = "0") int pageIndex,
	                        @RequestParam(required = false, name = "pageSize", defaultValue = "10") int pageSize,
	                        UserOption userOption) {
		log.info("查看用户列表带分页");

		Msg msg = new Msg();
		PageInfo<User> pageInfo = PageHelper
				.startPage(pageIndex, pageSize)
				.doSelectPageInfo(() -> userService.queryList(userOption));
		msg.setData(pageInfo);
		return msg.toString();
	}

	@ApiOperation(value = "查看用户信息", notes = "根据账户查询用户信息")
	@ApiImplicitParam(name = "id", value = "1", required = true, dataType = "Long")
	@ApiResponse(code = 401, message = "查询失败")
	@RequestMapping(value = "queryById/{id}", method = RequestMethod.GET)
	public String queryById(@PathVariable("id") long id) {
		log.info("查看单个用户信息");
		Msg msg = ValidUtils.validMsg(User.class, "id", id);
		if(msg.hasError()){
			return msg.toString();
		}

		User user = userService.queryById(id);
		msg.setData(user);
		return msg.toString();
	}

	@ApiOperation(value = "添加用户", notes = "添加用户信息")
	@ApiImplicitParam(name = "用户实体", value = "用户信息", required = true, paramType = "User.class")
	@ApiResponse(code = 401, message = "新增失败")
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(User user) {
		log.info("新增单个用户信息");
		Msg msg = ValidUtils.validMsg(user, "account,password,roleId");
		if(msg.hasError()){
			return msg.toString();
		}

		int effect = userService.insert(user);
		if(effect>0){
			return msg.withSuccess("新增用户成功!").toString();
		}
		return msg.withError("新增用户失败!").toString();
	}

	@ApiOperation(value = "修改用户", notes = "修改用户信息")
	@ApiImplicitParam(name = "用户实体", value = "用户信息", required = true, paramType = "User.class")
	@ApiResponse(code = 401, message = "修改失败")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(User user) {
		log.info("修改单个用户信息");
		Msg msg = new Msg();

		int effect = userService.update(user);
		if(effect>0){
			return msg.withSuccess("修改用户成功!").toString();
		}
		return msg.withError("修改用户失败!").toString();
	}

	@RequestMapping(value = "upload")
	public String uoload(@RequestParam("file") MultipartFile file){
		Msg msg = new Msg();
		String uploadPath = UploadUtils.upload(file);
		try {
			FileInputStream in = FileUtils.openInputStream(new File(uploadPath));
			Map<Integer, String> excelContent = ExcelReader.readExcelContent(in);
			User user;
			int size = excelContent.entrySet().size();
			List<User> users = new ArrayList<>(size);
			for (Map.Entry<Integer, String> entry : excelContent.entrySet()) {
				String[] strs = entry.getValue().split("\000");
				String account = strs[0];
				String password = strs[1];
				if(StringUtils.isBlank(account) || StringUtils.isBlank(password))
					continue;
				user = new User();
				user.setAccount(account);
				user.setPassword(password);
				user.setCreateTime(LocalDateTime.now());
				user.setUpdateTime(LocalDateTime.now());
				String md5Password = MD5.encode(user.getAccount()+String.valueOf(user.getPassword()));
				user.setPassword(md5Password);
				users.add(user);
			}
			userService.batchInsert(users);
			FileUtils.forceDelete(new File(uploadPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	@RequestMapping(value = "export")
	public void export(HttpServletResponse response){
		DownLoadUtils.download(response, "2.txt", "tmp/1.txt");
	}

	@RequestMapping(value = "downExcel")
	public void downExcel(HttpServletResponse response){
		DownLoadUtils.download(response, "新用户导入格式.xlsx", "tmp/execl/新用户导入格式.xlsx");
	}

}
