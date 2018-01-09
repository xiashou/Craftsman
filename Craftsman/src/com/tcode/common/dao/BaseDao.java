package com.tcode.common.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.tcode.core.util.Utils;

@Component
public class BaseDao<T, K extends Serializable> {
	
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public Class<T> getEntityType() {
		return (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 通用的查询所有
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadAll() throws Exception{
		return getHibernateTemplate().loadAll(getEntityType());
	}
	
	/**
	 * 通用的根据id查找一条信息
	 */
	@SuppressWarnings("unchecked")
	public T loadById(Class<T> entityClass, K id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}
	
	/**
	 * 通用的添加一条信息
	 */
	public void save(T t) throws Exception{
		getHibernateTemplate().save(t);
	}
	
	/**
	 * 保存或修改
	 * @param t
	 * @throws Exception
	 */
	public void saveOrUpdate(T t) throws Exception{
		getHibernateTemplate().saveOrUpdate(t);
	}
	
	/**
	 * 通用的修改一条信息
	 */
	public void edit(T t) throws Exception {
		getHibernateTemplate().merge(t);
	}
	
	/**
	 * 通用的删除一条信息
	 */
	public void remove(T entity) throws Exception {
		if(entity != null){
			getHibernateTemplate().delete(entity);
		}
	}
	
	/**
	 * 查询唯一值
	 * @param hql 查询语句
	 */
	public Object loadUniqueResult (final String hql) throws Exception{
		return (Object) getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	Query query = session.createQuery(hql); 
	            return (Object) query.uniqueResult();
	        }
	    });
	}

	/**
	 * 根据HQL查询List
	 * @param hql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadList(final String hql, final Object...params) throws DataAccessException {
		return (List<T>) this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameterValue(query, params);
				List<T> list = query.list();
				return list;
			}
		});
	}

	/**
	 * 根据SQL查询List
	 * @param sql
	 * @param beanClass
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadListBySql(final String sql, final Class<T> beanClass, final Object...params) throws DataAccessException {
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createSQLQuery(sql);
				setParameterValue(query, params);
				query.setResultTransformer(new AliasToBeanResultTransformer(beanClass));
				return query.list();
			}
		});
	}
	
	/**
	 * 执行HQL语句
	 * @param hql
	 * @param params
	 * @throws DataAccessException
	 */
	public void executeHql(final String hql, final Object...params) throws DataAccessException {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameterValue(query, params);
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * 执行SQL语句
	 * @param sql
	 * @param params
	 * @throws DataAccessException
	 */
	public void executeSql(final String sql, final Object...params) throws DataAccessException {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createSQLQuery(sql);
				setParameterValue(query, params);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 批量操作
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 */
	public int bulkUpdate(String hql, Object...params) throws DataAccessException {
		return this.getHibernateTemplate().bulkUpdate(hql, params);
	}

	/**
	 * 根据HQL查询一条记录
	 * @param hql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public Object loadEntity(final String hql, final Object...params) throws DataAccessException {
		Object object = this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameterValue(query, params);
				return query.uniqueResult();
			}
		});
		return object;
	}
	
	/**
	 * 分页方法1
	 * HQL分页查询
	 * @param hql
	 * @param start	开始
	 * @param limit	长度
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> loadListForPage(final String hql, final int start, final int limit, final Object...params) throws DataAccessException {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query queryList = s.createQuery(hql);
				setParameterValue(queryList, params);
				queryList.setFirstResult(start).setMaxResults(limit);
				return queryList.list();
			}
		});
		return list;
	}
	
	public List<T> loadListForSqlPage(final String hql, final int start, final int limit, final Object...params) throws DataAccessException {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query queryList = s.createSQLQuery(hql);
				setParameterValue(queryList, params);
				queryList.setFirstResult(start).setMaxResults(limit);
				return queryList.list();
			}
		});
		return list;
	}
	
	public Long loadListSqlCount(final String hql, final Object...params) throws DataAccessException {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createSQLQuery("select count(*) " + hql);
				setParameterValue(query, params);
				Object obj = query.uniqueResult();
				Long queryCount = 0L;
				if ((obj.getClass().getName()).equals("java.lang.Integer"))
					queryCount = ((Integer) obj).longValue();
				else
					queryCount = (Long) obj;
				return queryCount;
			}
		};
		return (Long) this.getHibernateTemplate().execute(hibernateCallback);
	}
	
	/**
	 * 分页方法2
	 * 简单条件自动分页查询
	 * @param clazz 实体类类型
	 * @param entity 参数实体类
	 * @param start	开始
	 * @param limit	长度
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadListForPage (final Class<T> clazz, final T entity, final int start, final int limit) throws Exception{
		return (List<T>) getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return getHibernateTemplate().findByCriteria(setCriteria(clazz, entity), start, limit);
			}
		});
	}
	
	/**
	 * 分页方法3
	 * 复杂分页查询(需添加好DetachedCriteria)
	 * @param criteria 条件参数
	 * @param start	开始
	 * @param limit	长度
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadListForPage (final DetachedCriteria criteria, final int start, final int limit) throws Exception{
		return (List<T>) getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return getHibernateTemplate().findByCriteria(criteria, start, limit);
			}
		});
	}
	
	/**
	 * 分页方法1
	 * HQL查询List总数量
	 * @param hql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public Long loadListCount(final String hql, final Object...params) throws DataAccessException {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery("select count(*) " + hql);
				setParameterValue(query, params);
				Object obj = query.uniqueResult();
				Long queryCount = 0L;
				if ((obj.getClass().getName()).equals("java.lang.Integer"))
					queryCount = ((Integer) obj).longValue();
				else
					queryCount = (Long) obj;
				return queryCount;
			}
		};
		return (Long) this.getHibernateTemplate().execute(hibernateCallback);
	}
	
	/**
	 * 分页方法2
	 * 简单条件自动分页查询总记录数
	 * @param clazz 实体类类型
	 * @param entity 参数实体类
	 */
	public Integer loadListCount (final Class<T> clazz, final T entity) throws Exception{
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
		    public Integer doInHibernate(Session session) throws HibernateException, SQLException {
		    	Criteria criteria = setCriteria(clazz, entity).getExecutableCriteria(session);
	            return (Integer)(criteria.setProjection(Projections.rowCount()).uniqueResult());
	        }
		});
	}
	
	/**
	 * 分页方法3
	 * 复杂分页查询总记录数(需添加好DetachedCriteria)
	 * @param criteria 条件参数
	 */
	public Integer loadListCount (final DetachedCriteria dCriteria) throws Exception{
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
		    public Integer doInHibernate(Session session) throws HibernateException, SQLException {
		    	Criteria criteria = dCriteria.getExecutableCriteria(session);
	            return (Integer)(criteria.setProjection(Projections.rowCount()).uniqueResult());
	        }
		});
	}
	
	
	
	/**
	 * 自动匹配参数类型
	 * @param query
	 * @param params
	 */
	protected void setParameterValue(Query query, Object[] params) {
		if (null == params || params.length == 0) {
			return;
		}
		String[] names = query.getNamedParameters();
		int nameCount = 0;
		for (int key = 0; key < params.length; key++) {
			Object value = params[key];
			if (value instanceof Boolean) {
				query.setBoolean(key, ((Boolean) value).booleanValue());
			} else if (value instanceof String) {
				query.setString(key, (String) value);
			} else if (value instanceof Integer) {
				query.setInteger(key, ((Integer) value).intValue());
			} else if (value instanceof Long) {
				query.setLong(key, ((Long) value).longValue());
			} else if (value instanceof Float) {
				query.setFloat(key, ((Float) value).floatValue());
			} else if (value instanceof Double) {
				query.setDouble(key, ((Double) value).doubleValue());
			} else if (value instanceof BigDecimal) {
				query.setBigDecimal(key, (BigDecimal) value);
			} else if (value instanceof Byte) {
				query.setByte(key, ((Byte) value).byteValue());
			} else if (value instanceof Calendar) {
				query.setCalendar(key, (Calendar) value);
			} else if (value instanceof Character) {
				query.setCharacter(key, ((Character) value).charValue());
			} else if (value instanceof Timestamp) {
				query.setTimestamp(key, (Timestamp) value);
			} else if (value instanceof Date) {
				query.setDate(key, (Date) value);
			} else if (value instanceof Short) {
				query.setShort(key, ((Short) value).shortValue());
			} else if (value instanceof List<?>) {// 为：name那个参数设置的 特别用在hql的in查询中
				query.setParameterList(names[nameCount], (List<?>) value);
				nameCount += 1;
			} else if (value instanceof Object[]) {
				query.setParameterList(names[nameCount], (Object[]) value);
				nameCount += 1;
			}
		}
	}
	
	/**
	 * 组装查询条件
	 * @param clazz
	 * @param entity
	 * @return
	 */
	protected DetachedCriteria setCriteria(Class<T> clazz, T entity) {
		DetachedCriteria criteria = null;
		try {
			criteria = DetachedCriteria.forClass(clazz);
			if(entity != null){
				// 获取实体类的所有属性，返回Field数组  
		        Field[] field = entity.getClass().getDeclaredFields();
		        for (int j = 0; j < field.length; j++) {
		        	String name = field[j].getName();
		        	String type = field[j].getGenericType().toString();
		        	String getName = name.substring(0, 1).toUpperCase() + name.substring(1);
		        	if (type.equals("class java.lang.String")) {
		        		Method m = entity.getClass().getMethod("get" + getName);
		        		String value = (String) m.invoke(entity); 
		        		if(!Utils.isEmpty(value))
		        			criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
		        	}
		        	if (type.equals("class java.lang.Integer")) {  
		        		Method m = entity.getClass().getMethod("get" + getName);
                        Integer value = (Integer) m.invoke(entity);  
                        if (!Utils.isEmpty(value))
                        	criteria.add(Restrictions.eq(name, value));
		        	}
		        }
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
        return criteria;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}

