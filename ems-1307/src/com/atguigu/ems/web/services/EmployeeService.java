package com.atguigu.ems.web.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.web.daos.DepartmentDao;
import com.atguigu.ems.web.daos.EmployeeDao;
import com.atguigu.ems.web.daos.RoleDao;
import com.atguigu.ems.web.exceptions.AccountException;
import com.atguigu.ems.web.exceptions.AccountLockedException;

@Service
@Transactional(readOnly=true)
public class EmployeeService extends BaseService<Employee> {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private DepartmentDao departmentDao;
	
	private EmployeeDao getEmployeeDao(){
		return (EmployeeDao) dao;
	}
	
	@Transactional(readOnly=false)
	public void addVisitiedTimes(Integer employeeId) {
		getEmployeeDao().updateVisitiedTimes(employeeId);
		
	}

	
	
	@Transactional
	public Employee login(String loginName,String password){
		//获得用户信息
		Employee employee = dao.getBy("loginName", loginName);
		
		//验证密码是否正确
		if(password==null || !password.equals(employee.getPassword())){
			//如果密码不匹配抛出自定义的runtime异常
			throw new AccountException();
		}
		
		if(employee.getEnabled() != 1){
			//检查用户是否可用，如果不可用也抛出自定义的异常
			throw new AccountLockedException();
		}
		
		//如果没有抛出异常，则登陆次数加一
		employee.setVisitedTimes(employee.getVisitedTimes()+1);
		
		//返回Employee对象
		return employee;
	}

	@Transactional
	public void delete(Integer id2) {
		Employee employee = dao.getById(id2);
		employee.setIsDeleted(1);
		
	}
	

	@SuppressWarnings("resource")
	public void downLoadEmployee(String realPath) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//1.创建wb对象
		Workbook wb = new HSSFWorkbook();
		
		//2.创建sheet
		Sheet sheet = wb.createSheet("employees");

		//3.填充标题
		fillTitles(wb);
		
		//4.填充内容
		fillContent(wb);
		
		//5.写出为文本
		FileOutputStream fileOut = new FileOutputStream(realPath);
		wb.write(fileOut);
		fileOut.close();
	}

	private void fillContent(Workbook wb) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Employee> emps = dao.getAll(true);
		Sheet sheet = wb.getSheet("employees");
		Cell cell = null;
		String[] propertyNames = new String[]{"loginName","employeeName","gender","enabled","department.departmentName","email","roleNames"};
		
		for(int i=0;i<emps.size();i++){
			Employee emp = emps.get(i);
			
			Row row = sheet.createRow(i+1);
			row.setHeightInPoints(25);
			
			cell = row.createCell(0);
			cell.setCellValue(""+(i+1));
			cell.setCellStyle(getCellStyle(wb));
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(0, (int)(sheet.getColumnWidth(0) * 1.2));
			
			for(int j=0;j<propertyNames.length;j++){
				Object property = BeanUtils.getProperty(emp, propertyNames[j]);
				cell=row.createCell(j+1);
				cell.setCellValue(""+property);
				cell.setCellStyle(getCellStyle(wb));
				
				sheet.autoSizeColumn(j+1);
				sheet.setColumnWidth((j + 1), (int)(sheet.getColumnWidth(j + 1) * 1.2));
				
			}
			
		}
		
	}

	private void fillTitles(Workbook wb) {
		String[] titles = new String[]{"序号","登录名","姓名","性别","登录许可","部门","Email","角色"};
		Sheet sheet = wb.getSheet("employees");
		Row row = sheet.createRow(0);
		row.setHeightInPoints(25);
		
		for(int i=0;i< titles.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(getCellStyle(wb));
			
			sheet.autoSizeColumn(i);//设置自动的列大小
			//设置猎德宽度比原有的基础上再大一点2倍
			sheet.setColumnWidth(i, (int)(sheet.getColumnWidth(i) * 1.2));
		}
		
	}
	

	private CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
	    style.setBorderBottom(CellStyle.BORDER_THIN);
	    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

	    style.setBorderLeft(CellStyle.BORDER_THIN);
	    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    
	    style.setBorderRight(CellStyle.BORDER_THIN);
	    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    
	    style.setBorderTop(CellStyle.BORDER_THIN);
	    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    
	    return style;
	}
	public List<String[]> upload(File file ,Employee editor) throws Exception {
		List<String[]> errors = new ArrayList<>();
		
		/*errors.add(new String[]{"1","2"});
		errors.add(new String[]{"5","6"});*/
		//解析File对象，得到Workbook对象
		InputStream inp = new FileInputStream(file);

	    Workbook wb = WorkbookFactory.create(inp);
		
		//2.解析Workbook，获取sheet
	    Sheet sheet = wb.getSheet("employees");
		
		//3. 解析每一行, 并对每一个字段进行验证, 若验证都通过, 则可以把这一行封装为一个 Employee 对象
		
		//4. 若验证出错, 则把错误保存在 errors 中
	    List<Employee> employees = parseWorkbook(sheet, errors,editor);
		
		//5. 若 errors 没有错误, 则进行批量的插入
	    if(errors.size() == 0){
			dao.batchSave(employees);
		}
		
		//6. 若有错误, 则返回保存错误的 errors/
	    return  errors;
	}

	private List<Employee> parseWorkbook(Sheet sheet, List<String[]> errors,Employee editor) {
		List<Employee> employees = new ArrayList<>();
		
		Row row = null;
		Cell cell = null;
		boolean flag = true;
		boolean flag2 = true;
		System.out.println(sheet.getLastRowNum());
		for(int i=1;i<=sheet.getLastRowNum();i++){
			row = sheet.getRow(i);
			
			cell = row.getCell(1);
			String loginName = getCellValue(cell);
			flag = validateLoginName(loginName);
			if(!flag){
				errors.add(new String[]{i+1+"","2"});
			}
			cell = row.getCell(2);
			String employeeName = getCellValue(cell);
			
			cell = row.getCell(3);
			String gender = getCellValue(cell);
			flag2 = validateGenderAndEnabled(gender);
			if(!flag2){
				errors.add(new String[]{i+1+"","4"});
			}else{
				gender = gender.equals("0")?"0":"1";
			}
			
			flag = flag && flag2;
			cell = row.getCell(4);
			String enabled = getCellValue(cell);
			flag2 = validateGenderAndEnabled(enabled);
			if(!flag2){
				errors.add(new String[]{i + 1 + "","5"});
			}else{
				enabled = enabled.equals("0") ? "0" : "1";
			}
			flag = flag && flag2;
			
			cell = row.getCell(5);
			String departmentName = getCellValue(cell);
			Department department = validateDepartment(departmentName);
			if(department == null){
				errors.add(new String[]{i+1+"","6"});
				flag2 = false;
			}
			flag = flag && flag2;
			
			cell = row.getCell(6);
			String email = getCellValue(cell);
			
			cell = row.getCell(7);
			String roleNames = getCellValue(cell);
			Set<Role> roles = validateRoles(roleNames);
			if(roles == null){
				errors.add(new String[]{i + 1 + "","8"});
				flag2 = false;
			}
			flag = flag && flag2;
			
			//判定是否都验证通过了. 若都通过, 则把属性封装为一个 Employee 对象
			if(flag){
				Employee employee = new Employee(loginName, employeeName, roles, 
						Integer.parseInt(enabled), department, gender, email, editor);
				employees.add(employee);
			}
			
		}
		
		return employees;
	}

	//只要一个 roleName 不存在, 就算验证失败
	private Set<Role> validateRoles(String roleNames) {
		if(roleNames == null){
			return null;
		}
		String[] names = roleNames.split(",");
		List<Role> roles = roleDao.getByIn("roleName", Arrays.asList(names));
		if(roles.size() != names.length){
			return null;
		}
		
		return new HashSet<>(roles);
	}

	private Department validateDepartment(String departmentName) {
		return departmentDao.getBy("departmentName", departmentName);
	}

	private boolean validateGenderAndEnabled(String gender) {
		try {
			//转为 double 类型. 若传入的不能转为对应的类型, 则抛出异常
			double d = Double.parseDouble(gender);
			
			if(d == 1 || d == 0){
				return true;
			}
		} catch (NumberFormatException e) {}
		
		return false;
	}

	private boolean validateLoginName(String loginName) {
		// 具体对loginName进行简单和复杂验证
		//是否为空，；length进行验证
		if(loginName == null || loginName.trim().length()<6){
			return false;
		}
		//正则表达式验证
		Pattern p = Pattern.compile("^[a-zA-Z]\\w+\\w$");
		Matcher m = p.matcher(loginName);
		if(!m.matches()){
			return false;
		}
		
		//复杂验证
		Employee employee = dao.getBy("loginName", loginName);
		
		return employee == null;
	}

	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			//对 Date 类型, 当前项目不做要求. 
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue() + "";
			} else {
				return cell.getNumericCellValue() + "";
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula() + "";
		}
		return null;
	}

}
