package com.whh.bookstore.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;


public class WEBUtils {
	//方法中属性是实体类的属性，属性值指的是网页中传递进来的，所以注意二者之间的命名
	public static <T> T paramtoBean(T t,HttpServletRequest request){
		//获得t的所有属性
		Field[] fields = t.getClass().getDeclaredFields();
		
		//遍历得到的属性
		for (Field field : fields) {
			//获得属性名
			String name = field.getName();
			//获得表单中传来的属性值
			String value = request.getParameter(name);
			
			if(value==null){
				continue;
			}
			
			try {
				//用工具类实现属性与属性值的封装
				BeanUtils.copyProperty(t, name, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*//更改属性访问权限
			field.setAccessible(true);
			try {
				//将属性与属性值进行绑定
				field.set(t, value);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		return t;
	}
	
	public static String generateOrderNum(int todayCount){
		//传入参数的订单数量并不包含正在生成的订单号，所以这次操作的订单号是参数的+1
		todayCount++ ;
		//生成日期字符串
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(new Date());
		
		//生成前导零的字符串
		//①计算订单数量占的位数
		String todayStr = todayCount+" ";
		int todayLen = todayStr.length();
		
		//零的个数
		int zeroLen = 6-todayLen;
		
		//②拼装带前导零的字符串
		StringBuilder sb = new StringBuilder("");
		sb.append(dateStr);
		for (int i = 0; i < zeroLen; i++) {
			sb.append("0");
		}
		
		sb.append(todayStr);
		
		
		return sb.toString();
	}

}
