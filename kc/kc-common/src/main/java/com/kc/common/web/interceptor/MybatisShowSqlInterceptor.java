package com.kc.common.web.interceptor;


import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @ClassName: MybatisShowSqlInterceptor  
 * @Description: TODO mybatis 分页插件
 * @author jason  
 * @date 2017-8-15
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MybatisShowSqlInterceptor implements Interceptor {
	
	private static final Logger log = Logger.getLogger(MybatisShowSqlInterceptor.class);
	
	private String dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler handler = (StatementHandler) ReflectUtil.getFieldValue(statement, "delegate");
		// 获取绑定sql对象
		BoundSql boundSql = statement.getBoundSql();
		if( handler instanceof PreparedStatementHandler  ){
			
			PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler) handler;
			// 获取分页参数
			RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(preparedStatementHandler, "rowBounds");
			
			// 判断rowBounds 是否已经被赋值
			if( 0 < rowBounds.getLimit() && RowBounds.NO_ROW_LIMIT > rowBounds.getLimit()  ){
				// 获取SQl语句
				String  sql = boundSql.getSql();
				Dialect dia = null ;
				if( null == dialect || "".equals(dialect) ){
					dia = new MySqlDialect();
				} else {
					if (dialect.equalsIgnoreCase("mysql")) {
						dia = new MySqlDialect();
					}
				}
				if (dia != null) {
					sql = dia.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
					//	            log.info("Paged SQL:" + sql);
					ReflectUtil.setFieldValue(boundSql, "sql", sql);
				}
			}
		}
		
		if(log.isDebugEnabled()){
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler,"mappedStatement");
		    Configuration configuration = mappedStatement.getConfiguration();	
		    String sqlId = mappedStatement.getId();
			String sql = showSql(configuration, boundSql);
			log.debug("sqlId:" + sqlId);
			log.debug("sql:" + sql);
			log.debug("---------------------------------------------------------");
		}
		
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		this.dialect = (String) properties.getProperty("dialect");
	}
	
	/**
	 * 打印mybatis 执行的sql,用于跟踪sql执行情况
	 * @since
	 * @param configuration
	 * @param boundSql
	 * @return
	 * <br><b>作者： 周志钧</b>
	 * <br>创建时间：2014-5-6 下午12:36:56
	 */
	public static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql();//.replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}
	
	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}
}
