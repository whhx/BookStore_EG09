package com.atguigu.ems.web.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.web.services.AuthorityService;
import com.atguigu.ems.web.services.RoleService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Scope(value="prototype")
@Controller
public class RoleAction extends ActionSupport implements RequestAware,ModelDriven<Role>,Preparable {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> request;
	
	private Role model;
	@Autowired
	private AuthorityService authoritiesService;
	@Autowired
	private RoleService roleService;
	private String roleId;
	private InputStream inputStream;
	
	public void prepareDetails(){
		Integer id = Integer.parseInt(roleId);
		model = roleService.getById(id);
	}
	//根据参数id获得指定的
	public String details(){
		//是否有parent权限存在
		/*String[] strings = model.getDisplayName().split(",");
		if(model.getDisplayName() != null){
			for(int i=0;i<strings.length;i++){
				String string = strings[i];
				//通过父权限的名字查找相应的子权限
				roleService.getBy(string, propertyVal);
			}
		}*/
		
		return "details";
	}
	
	
	public String delete(){
		int id = Integer.parseInt(roleId);
		//判断要删除的对象是否被Employee关联
		roleService.delete(id);
		inputStream = new ByteArrayInputStream("1".getBytes());
		
		return "ajax-success";
	}
	
	//显示所有的角色信息
	public String list(){
		List<Role> list = roleService.getAll(true);
		request.put("roleList", list);
		
		return "list";
	}
	
	public void prepareSave(){
		if(roleId != null){
			int id = Integer.parseInt(roleId);
			model=roleService.getById(id);
		}else{
			model=new Role();
		}
	}
	
	//执行保存操作
	public String save(){
		List<Authority> authroities = authoritiesService.getByIsNotNull("parentAuthority");
		List<Authority> parentAuthorities = authoritiesService.getByIsNull("parentAuthority");
		
		
		this.request.put("authroities", authroities);
		this.request.put("parentAuthorities", parentAuthorities);
		roleService.save(model);
		return "success";
	}
	
	
	public void prepareInput(){
		if(roleId != null){
			try {
				int roleId2 = Integer.parseInt(roleId);
				model = roleService.getById(roleId2);
			} catch (NumberFormatException e) {}
		}
	}
	
	public String input(){
		List<Authority> authroities = authoritiesService.getByIsNotNull("parentAuthority");
		List<Authority> parentAuthorities = authoritiesService.getByIsNull("parentAuthority");
		
		this.request.put("authroities", authroities);
		this.request.put("parentAuthorities", parentAuthorities);
		if(roleId != null){
			try {
				int roleId2 = Integer.parseInt(roleId);
			} catch (NumberFormatException e) {
				return "error";
			}
		}
		 
		return "input";
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;
		
	}


	
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void prepare() throws Exception {}

	@Override
	public Role getModel() {
		return model;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	

}
