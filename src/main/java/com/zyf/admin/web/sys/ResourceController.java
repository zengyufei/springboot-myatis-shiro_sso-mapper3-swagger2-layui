package com.zyf.admin.web.sys;

import com.zyf.commons.entity.sys.Resource;
import com.zyf.commons.entity.sys.User;
import com.zyf.commons.result.Msg;
import com.zyf.commons.service.sys.ResourceService;
import com.zyf.framework.utils.ValidUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "资源管理", description = "管理资源")
@Slf4j
@RestController
@RequestMapping("resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("queryList")
	public String queryList(){
		log.info("查询资源列表信息");
		Msg msg = new Msg();

		List<Resource> resources = resourceService.queryList();
		msg.setData(resources);
		return msg.toString();
	}

	@RequestMapping("queryListByRole")
	public String queryListByRole(){
		Subject subject = SecurityUtils.getSubject();
		User.DTO user = (User.DTO) subject.getPrincipal();

		log.info("查询角色拥有的资源列表信息");
		Msg msg = new Msg();

		List<Resource> resources = resourceService.queryList(user.getResourceIds());
		msg.setData(resources);
		return msg.toString();
	}
	
	@RequestMapping("queryById/{id}")
	public String queryById(@PathVariable("id") long id){
		log.info("查询单个资源信息");
		Msg msg = ValidUtils.validMsg(Resource.class, "id", id);
		if(msg.hasError()){
			return msg.toString();
		}

		Resource resource = resourceService.queryById(id);
		if (resource != null) {
			msg.setData(resource);
		}
		return msg.toString();
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(Resource resource){
		log.info("新增单个资源");
		Msg msg = ValidUtils.validMsg(resource, "name,url,permission");
		if(msg.hasError()){
			return msg.toString();
		}

		int effect = resourceService.insert(resource);
		if(effect > 0){
			msg.setSuccess("新增资源成功");
		}else{
			msg.setError("新增资源失败");
		}
		return msg.toString();
	}

	@RequestMapping(value = "update", method = {RequestMethod.GET, RequestMethod.POST})
	public String update(Resource resource){
		log.info("修改单个资源");
		Msg msg = ValidUtils.validMsg(resource, "name,url,permission");
		if(msg.hasError()){
			return msg.toString();
		}

		int effect = resourceService.update(resource);
		if(effect > 0){
			return msg.withSuccess("编辑资源成功").toString();
		}
		return msg.withError("编辑资源失败").toString();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id){
		log.info("删除单个资源");
		Msg msg = ValidUtils.validMsg(Resource.class, "id", id);
		if(msg.hasError()){
			return msg.toString();
		}

		if(!resourceService.queryListByParentId(id).isEmpty()){
			return msg.withError("该资源下有子资源，不能删除").toString();
		}
		int effect = resourceService.delete(id);
		if(effect > 0){
			return msg.withSuccess("删除资源成功").toString();
		}
		return msg.withError("删除资源失败").toString();
	}

}
