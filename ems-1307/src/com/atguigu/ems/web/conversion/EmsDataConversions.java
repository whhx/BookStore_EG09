package com.atguigu.ems.web.conversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.struts2.util.StrutsTypeConverter;

public class EmsDataConversions extends StrutsTypeConverter {
	
	private DateFormat dataformat;
	
	{
		dataformat = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values != null && values.length>0){
			String val = values[0];
			try {
				Date date = dataformat.parse(val);
				return date;
			} catch (ParseException e) {}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(o instanceof Date){
			Data data = (Data) o;
			return dataformat.format(data);
		}
		
		return null;
	}

	

}
