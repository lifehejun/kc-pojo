
package com.kc.common.web.interceptor;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.Properties;

/**
 * @ClassName: DiclectResultSetHandlerInterceptor  
 * @Description: TODO mybatis执行结果拦截器
 * @author jason  
 * @date 2017-8-15
 */
@Intercepts( {@Signature(type  = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DiclectResultSetHandlerInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		DefaultResultSetHandler resultSet = (DefaultResultSetHandler)invocation.getTarget();
	       
        RowBounds rowBounds = (RowBounds)ReflectUtil.getFieldValue(resultSet,
                "rowBounds");
      
        if (rowBounds.getLimit() > 0
                && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT)
        {
            ReflectUtil.setFieldValue(resultSet, "rowBounds", new RowBounds());
        }
        return invocation.proceed();
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

	

}
