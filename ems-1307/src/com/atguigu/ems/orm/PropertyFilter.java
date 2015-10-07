package com.atguigu.ems.orm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PropertyFilter {
	
	private MatchType matchType;
	private Class propertyType;
	private String propertyName;
	private Object propertyVal;
	
	//传入的是类似于 LIKES_loginName=aa 的键值对
	public static List<PropertyFilter> parseParamsToFilters(Map<String, Object> params){
		List<PropertyFilter> filters = new ArrayList<>();
		 
		if(params != null && params.size() > 0){
			for(Map.Entry<String, Object> entry: params.entrySet()){
				String key = entry.getKey();
				
				String str1 = key.substring(0, StringUtils.indexOf(key, "_")); //LKIES
				String matchTypeCode = str1.substring(0, str1.length() - 1); //LIKE
				String propertyTypeCode = str1.substring(str1.length() - 1); //S
				
				String propertyName = key.substring(StringUtils.indexOf(key, "_") + 1); //loginName
				MatchType matchType = Enum.valueOf(MatchType.class, matchTypeCode);
				Class propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getType();
				Object propertyVal = entry.getValue();
				
				if(StringUtils.isEmpty((String)propertyVal)){
					continue;
				}
				
				PropertyFilter filter = new PropertyFilter(matchType, propertyType, propertyName, propertyVal);
				filters.add(filter);
			}
		}
		
		return filters;
	}
	
	public PropertyFilter(MatchType matchType, Class propertyType,
			String propertyName, Object propertyVal) {
		this.matchType = matchType;
		this.propertyType = propertyType;
		this.propertyName = propertyName;
		this.propertyVal = propertyVal;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public enum MatchType{
		EQ, GT, LT, GTE, LTE, LIKE, ISNULL;
	}
	
	public enum PropertyType{
		I(Integer.class), F(Float.class), D(Date.class), S(String.class);
		
		private Class type;
		
		private PropertyType(Class type) {
			this.type = type;
		}
		
		public Class getType() {
			return type;
		}
	}
	
	@Override
	public String toString() {
		return "PropertyFilter [matchType=" + matchType + ", propertyType="
				+ propertyType + ", propertyName=" + propertyName
				+ ", propertyVal=" + propertyVal + "]";
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LIKES_loginName", "aa");
		params.put("GTI_age", 12);
		
		List<PropertyFilter> filters = parseParamsToFilters(params);
		System.out.println(filters);
	}
}
