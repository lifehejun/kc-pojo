package com.kc.common.web.interceptor;

/**
 * @ClassName: Dialect  
 * @Description: TODO mybatis 分页转化
 * @author jason  
 * @date 2017-8-15
 */
public interface Dialect {
    
   final String  MYSQL = "mysql";
   
   final String ORACLE = "oracle";
   
   final String HSQLDB = "hsql";
   
   final String DB2    = "db2";
   
   final String SQL_END_DELIMITER = ";";

	public boolean supportsLimit();

    public String getLimitString(String sql, boolean hasOffset);

    public String getLimitString(String sql, int offset, int limit);
}