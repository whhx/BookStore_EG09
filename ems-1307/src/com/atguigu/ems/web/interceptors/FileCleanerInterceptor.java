package com.atguigu.ems.web.interceptors;

import java.io.File;
import java.util.Map;

import com.atguigu.ems.web.actions.EmployeeAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class FileCleanerInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = invocation.invoke();
		
		//1.获取临时文件的名字
		@SuppressWarnings("unchecked")
		Map<String,Object> requestMap = (Map<String, Object>) invocation.getInvocationContext().get("request");
		
		String realPath = (String) requestMap.get(EmployeeAction.DOWN_LOAD_TEMP_FILE_NAME);
		//System.out.println(realPath+"inters----");
		
		//2.检查临时文件是否存在
		if(realPath == null){
			return result;
		}
		File file = new File(realPath);
		
		//3.如果存在则删除
		if(file.exists()){
			file.delete();
		}
		
		return result;
	}



}
