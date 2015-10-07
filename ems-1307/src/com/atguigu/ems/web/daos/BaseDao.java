package com.atguigu.ems.web.daos;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.web.utils.ReflectionUtils;

public class BaseDao<T> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Class<T> entityClass;
	
	public BaseDao(){
		entityClass=ReflectionUtils.getSuperGenericType(getClass());
	}
	
	//获取全部
	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean cacheable){
		Criteria criteria = getSession().createCriteria(entityClass).setCacheable(cacheable);
		return criteria.list();
	}
	
	//根据 propertyName(属性名) 是否等于 propertyVal(属性值) 返回满足条件的对象
	@SuppressWarnings("unchecked")
	public T getBy(String propertyName, Object propertyVal){

		Criteria criteria = getSession().createCriteria(entityClass);
		
		//Criterion 相当于是一个查询条件. 它有 Restrictions 静态工厂方法来获取
		//eq: propertyName 这个属性名 等于 propertyValue 这个属性值
		//若 propertyName 为 loginName, 而 propertyVal 为 aa, 则条件为: loginName='aa'
		Criterion criterion = Restrictions.eq(propertyName, propertyVal);
		criteria.add(criterion);
		
		return (T) criteria.uniqueResult();
	}

	public void save(T entity) {
		getSession().saveOrUpdate(entity);
		
	}
	
	//传进来的page中只有两个属性，pageNo和pageSize，这里还要为其设置进总的记录数和要显示的数据集合
	public Page<T> getPage(Page<T> page){
		//1. 查询总的记录数
		int totalItemNumber = getTotalItemNumber();
		page.setTotalItemNumber(totalItemNumber);
		
		//2. 查询当前页面的 List
		List<T> content = getContent(page);
		page.setContent(content);
		
		//3. 返回 Page
		return page;
	}
	
	private List<T> getContent(Page<T> page) {
		Criteria criteria = getSession().createCriteria(entityClass);
		
		int firstResult = (page.getPageNo() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		
		return criteria.list();
	}
	

	/*这里的验证放到了构造中
	//通用的不带查询条件的分页
	public Page<T> getPage(String pageNo){
		//查询总记录数
		int totalItemNumber = getTotalItemNumber();
		Page<T> page = new Page<>(pageNo, totalItemNumber);
		int firstResult = (page.getPageNo()-1)*page.getPageSize();
		int maxResult = page.getPageSize();
		
		//2.查询当前页的list
		Criteria criteria = getSession().createCriteria(entityClass);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.setFirstResult(firstResult).setMaxResults(maxResult).list();
		page.setList(list);
		//3.返回
		return page;
	}
*/
	private int getTotalItemNumber() {
		Criteria criteria = getSession().createCriteria(entityClass);
		
		String idName = getIdName();
		//设置统计查询
		criteria.setProjection(Projections.count(idName));
		
		return (int)((long) criteria.uniqueResult());
	}
	

	//利用 hibernate 的  ClassMetadata 对象得到映射的 id 属性的名字
	private String getIdName() {
		ClassMetadata cm = this.sessionFactory.getClassMetadata(entityClass);
		return cm.getIdentifierPropertyName();
	}
	
	public List<T> getByIn(String propertyName,Collection<?> propertyValue){
		Criteria criteria = getSession().createCriteria(entityClass);
		Criterion criterion = Restrictions.in(propertyName, propertyValue);
		criteria.add(criterion);
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public T getById(Integer id){
		return (T) getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getByIsNull(String propertyName){
		Criteria criteria = getSession().createCriteria(entityClass);
		//查询属性名为空的list（父权限，不需要显示）
		Criterion criterion = Restrictions.isNull(propertyName);
		criteria.add(criterion);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getbyIsNotNull(String propertyName){
		Criteria criteria = getSession().createCriteria(entityClass);
		
		Criterion criterion = Restrictions.isNotNull(propertyName);
		
		criteria.add(criterion);
		
		return criteria.list();
	}

	public void batchSave(List<T> entities) {
		for(int i=0;i<entities.size();i++){
			getSession().save(entities.get(i));
			
			if((i+1)/50== 0){
				getSession().flush();
				getSession().clear();
			}
		}
		
	}

	public void update(String hql, Integer employeeId) {
		getSession().createQuery(hql).setParameter(0, employeeId).executeUpdate();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
