package com.kc.common.web.interceptor;

/**
 * @ClassName: MySqlDialect  
 * @Description: TODO mysql 分页
 * @author jason  
 * @date 2017-8-15
 */
public class MySqlDialect implements Dialect {

    private String trim(String sql) {
        sql = sql.trim();
        if (sql.endsWith(SQL_END_DELIMITER)) {
            sql = sql.substring(0, sql.length() - 1
                    - SQL_END_DELIMITER.length());
        }
        return sql;
    }

    
	@Override
	public boolean supportsLimit() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getLimitString(String sql, boolean hasOffset) {
		// TODO Auto-generated method stub
		 return new StringBuffer(sql.length() + 20).append(trim(sql)).append(
	                hasOffset ? " limit ?,?" : " limit ?")
	                .append(SQL_END_DELIMITER).toString();
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		// TODO Auto-generated method stub
	     sql = trim(sql);
	        StringBuffer sb = new StringBuffer(sql.length() + 20);
	        sb.append(sql);
	        if (offset > 0) {
	            sb.append(" limit ").append(offset).append(',').append(limit)
	                    .append(SQL_END_DELIMITER);
	        } else {
	            sb.append(" limit ").append(limit).append(SQL_END_DELIMITER);
	        }
	        return sb.toString();
	}

}