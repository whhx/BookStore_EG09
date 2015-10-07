package com.atguigu.ems.web.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.security.EmsUserDetailsService.SecurityUser;
import com.atguigu.ems.security.Navigation;
import com.atguigu.ems.web.services.DepartmentService;
import com.atguigu.ems.web.services.EmployeeService;
import com.atguigu.ems.web.services.RoleService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller
@Scope(value="prototype")
@SuppressWarnings("serial")
public class EmployeeAction extends ActionSupport 
							implements RequestAware,SessionAware,ParameterAware,ModelDriven<Employee>,Preparable {

	public static final String DOWN_LOAD_TEMP_FILE_NAME = "downLoadTempFileName";
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;
	
	private Employee employee;
	private Map<String, String[]> parameters;
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Page<Employee> page;
	private String id;
	
	private InputStream inputStream;
	
	
	private List<Navigation> navigations = new ArrayList<>();
	
	
	public String list2(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		System.out.println(params);
		return "emp-list2";
	}
	
	public String criteriaInput(){
		request.put("departments", departmentService.getAll(true));
		return "criteria-input";
	}
	
//	public List<Navigation> getNavigation(){
//		return navigations;
//	}
	 public List<Navigation> getNavigations() {
			return navigations;
	 }
	
	public String navigate(){
		//初始化navigation集合
		//根据用户的权限对其进行初始化
		String contextPath = ServletActionContext.getServletContext().getContextPath();
		System.out.println(contextPath);
		
		//创建navigation对象
		Navigation navigation = new Navigation();
		navigation.setId(Integer.MAX_VALUE);
		navigation.setText("教育办公管理系统");
		navigation.setState("closed");
		
		//存放父 Navigation 的 Map, 键: 父权限的 Id, 值: 父权限对应的 Navigation 对象
    	Map<Integer, Navigation> parentNavigations = new HashMap<>();
		
		//利用用户的角色和权限动态构建导航菜单.
		Employee employee = (Employee) session.get("employee");
		for(Role role : employee.getRoles()){
			for(Authority authority:role.getAuthorities()){
				if(authority.getMainResource() != null){
					Navigation n = new Navigation();
					n.setId(authority.getId());
					n.setText(authority.getDisplayName());
					n.setUrl(contextPath+authority.getMainResource().getUrl());
					
					Authority parentAuthority = authority.getParentAuthority();
					Navigation parentNavigetion = parentNavigations.get(parentAuthority.getId());
					
					if(parentNavigetion == null){
						parentNavigetion = new Navigation();
						parentNavigetion.setId(parentAuthority.getId());
						parentNavigetion.setState("closed");
						parentNavigetion.setText(parentAuthority.getDisplayName());
						
						parentNavigations.put(parentAuthority.getId(), parentNavigetion);
						navigation.getChildren().add(parentNavigetion);
						
					}
					parentNavigetion.getChildren().add(n);
				}
			}
		
		}
		/*for(Role role: employee.getRoles()){
    		for(Authority authority: role.getAuthorities()){
    			if(authority.getMainResource() != null){
    				Navigation n = new Navigation();
    				n.setId(authority.getId());
    				n.setText(authority.getDisplayName());
    				n.setUrl(contextPath + authority.getMainResource().getUrl());
    				
    				Authority parentAuthority = authority.getParentAuthority();
    				Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
    				if(parentNavigation == null){
    					parentNavigation = new Navigation();
    					parentNavigation.setId(parentAuthority.getId());
    					parentNavigation.setState("closed");
    					parentNavigation.setText(parentAuthority.getDisplayName());
    					
    					parentNavigations.put(parentAuthority.getId(), parentNavigation);
    					navigation.getChildren().add(parentNavigation);
    				}
    				parentNavigation.getChildren().add(n);
    			}
    		}
    	}*/
		
		
		Navigation logout = new Navigation();
		logout.setId(Integer.MAX_VALUE-1);
		logout.setText("登出");
		logout.setUrl(contextPath+"/security-logout");
		
		navigation.getChildren().add(logout);
		navigations.add(navigation);
		
		
		return "json-success";
	}
	
	
	//文件下载相关
	private String contentType = "text/plain";
	//设置默认值为 1, 以供 ajax 校验能起作用
	private int contentLength = 1;
	private String downLoadFileName;
	
	public String getContentType() {
		return contentType;
	}
	
	public int getContentLength() {
		return contentLength;
	}
	
	public String getContentDisposition() {
		return "attachment;filename=" + downLoadFileName;
	}
	
	public String downToExcel() throws Exception{
		ServletContext servletContext = ServletActionContext.getServletContext();
		//获得真实路径
		String realPath = servletContext.getRealPath("/files/"+(System.currentTimeMillis() + ".xls"));
		
		
		//设置下载相关的参数
		//1.contentType(这里已经自己制定)
		this.contentType = "application/vnd.ms-excel";
		//2.inputStream
		employeeService.downLoadEmployee(realPath);
		inputStream = new FileInputStream(realPath);
		//3.contentLength
		contentLength=inputStream.available();
		//4. downLoadFileName
		downLoadFileName = "employees.xls";
		
		
		return "download-success";
	}
	
	//文件上传的相关属性
	private File file;
	
	public void setFile(File file){
		this.file = file;
	}
	
	public String upload() throws Exception{
		//1.得到上传的File文件：利用Struts2的FileUploadIntertception。
		//2.调用Service的方法完成数据的上传
		Employee eidtor = (Employee) session.get("employee");
		List<String[]> errors = employeeService.upload(file,eidtor);
		
		//3.若数据上传出现错误，则需要把错误保存在国际化文件中，以在页面上进行显示
		if(errors != null && errors.size() >0){
			for(String[] error:errors){
				String errorMessage = getText("errors.emp.upload", error);
				addActionError(errorMessage);
			}
			
			return "upload-input";
		}
		
		return SUCCESS;
	}
	
	
	
	public String delete(){
		Integer id2 = Integer.parseInt(id);
		//检测要删除的Employee是不是一个manager
		Employee manager = new Employee();
		manager.setEmployeeId(id2);
		Department department = departmentService.getBy("manager", manager);
		if(department != null){
			inputStream = new ByteArrayInputStream("2".getBytes());
			return "ajax-success";
		}
		employeeService.delete(id2);
		inputStream = new ByteArrayInputStream("1".getBytes());
		
		return "ajax-success";
	}
	
	
	public void prepareList(){
		this.page = new Page<Employee>();
	}
	public String list(){
		//获取pageNo的值
		/*int pageNo1 = page.getPageNo();
		String pageNo = pageNo1+"";
		page=employeeService.getPage(pageNo);*/
		page = employeeService.getPage(page);
		return "emp-list";
	}
	
	public void prepareSave(){
		if(id != null){
			try {
				Integer id2 = Integer.parseInt(id);
				employee = employeeService.getById(id2);
				
				//使指向的 department 设置为 null, 这样就会为 Department 赋值为一个新的对象
				//而不是把以前的 id 进行修改了.
				employee.setDepartment(null);
			} catch (NumberFormatException e) {}
			
		}else{
			employee = new Employee();
		}
	 }
	 
	public String save(){
		//flag 表示是否需要对 loginName 进行校验
		boolean flag = true;
		if(employee.getEmployeeId() !=null){
			String oldLoginName = parameters.get("oldLoginName")[0];
			if(oldLoginName.equals(employee.getLoginName())){
				flag = false;
			}
		}
		if(flag){
			//对 loginName 进行复杂验证
			Employee employee2 = employeeService.getBy("loginName", employee.getLoginName());
			if(employee2 != null){
				String message = getText("errors.emp.save.loginName");
				addActionError(message);
				request.put("departments", departmentService.getAll(true));
				request.put("roles", roleService.getAll(true));
				return "emp-input";
			}
		}
		employee.setPassword("123456");
		employeeService.save(employee);
		
		return SUCCESS;
	}
	
	//如何返回一个标记位 ?
	//Struts2 如何处理 Ajax
	//使用 Stream 结果类型. 参看 struts-2.3.15.3-all\struts-2.3.15.3\docs\WW\docs\ajax.html
	public String ajaxValidateLoginName() throws UnsupportedEncodingException {

		//获取 loginName 请求参数
		String result = "-1";
		String[] val = parameters.get("loginName");
		if(val.length == 1){
			String loginName = val[0];
			Employee employee = employeeService.getBy("loginName", loginName);
			if(employee == null){
				result = "1";
			}else{
				System.out.println(loginName);
				result = "0";
			}
		
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		return "ajax-success";
	}
	
	public void prepareInput(){
		if(id!=null){
			try {
				Integer id2 = Integer.parseInt(id);
				//必须保证 id 是合法的情况下, 才能从数据表中获取对应的记录. 否则可能会抛出异常!
				employee = employeeService.getById(id2);
			} catch (NumberFormatException e) {}
		}
	}
	
	public String input(){
		
		request.put("departments", departmentService.getAll(true));
		request.put("roles", roleService.getAll(true));
		
		if(id != null){
			try {
				Integer id2 = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				return "error";
			}
		}
		
		return "emp-input";
	}
	
	
	
	public void prepareLogin(){
		employee = new Employee();
	}
	
	
	public String login2(){
		//1. 从 SpringSecurity 中获取已经登陆的信息
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		
		//2. 把登陆信息对应的 Employee 加入到 Session 中
		if(principal instanceof SecurityUser){
			SecurityUser securityUser = (SecurityUser) principal;
			Employee employee = securityUser.getEmployee();
			session.put("employee", employee);
			
			//3. 登陆次数 + 1
			employeeService.addVisitiedTimes(employee.getEmployeeId());
		}
		
		
		
		
		return "login-sccess";
	}
	
	public String login(){
		//完成复杂验证
		String loginName = employee.getLoginName();
		String password = employee.getPassword();
		Employee emp = employeeService.login(loginName, password);
		//将对象放入session域中
		session.put("loginUser", emp);
		System.out.println("登陆成功");
		return "login-sccess";
	}
	
	
	@Override
	public void prepare() throws Exception {}

	@Override
	public Employee getModel() {
		return employee;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters=parameters;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setPage(Page<Employee> page) {
		this.page = page;
	}
    
    public Page<Employee> getPage() {
		return page;
	}
    
    public void setId(String id) {
		this.id = id;
	}

}
