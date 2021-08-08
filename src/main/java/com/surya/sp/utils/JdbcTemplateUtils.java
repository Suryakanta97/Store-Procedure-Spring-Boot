package com.surya.sp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Suryakanta Sahoo on 10/09/2021.
 */
@Component
public class JdbcTemplateUtils {
    Logger logger = LoggerFactory.getLogger(JdbcTemplateUtils.class);
    
    private SimpleJdbcCall simpleJdbcCall;
    
    @Autowired(required=true)
    @Qualifier("data_oracle")
    DataSource datasource;
    
    public void callStoreProcedure(String procedureName, Map<String,Object> parameters){
    	simpleJdbcCall =new SimpleJdbcCall(datasource);
        simpleJdbcCall.withProcedureName(procedureName);
        MapSqlParameterSource inParams = new MapSqlParameterSource();
        if(null!=parameters) {
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                inParams.addValue(parameter.getKey(), parameter.getValue());
            }
        }
        simpleJdbcCall.execute(inParams);
        logger.info("PROCEDURE {} IS CALLED",procedureName);
    }

    public Object callStoredFunction(String functionName, Map<String,Object> parameters, Class<?> classreturn){
    	simpleJdbcCall =new SimpleJdbcCall(datasource);
    	simpleJdbcCall.withFunctionName(functionName);
        simpleJdbcCall.withReturnValue();
        MapSqlParameterSource inParams = new MapSqlParameterSource();
        if(null!=parameters) {
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                inParams.addValue(parameter.getKey(), parameter.getValue());
            }
        }
        logger.info("FUNCTION {} IS CALLED",functionName);
        return simpleJdbcCall.executeFunction(classreturn,inParams);
    }
    
    public Object callStoredRefFunction(String procedureName, String reffunction, Map<String,Object> parameters, Class<?> classreturn){
    	simpleJdbcCall =new SimpleJdbcCall(datasource);
    	simpleJdbcCall.withProcedureName(procedureName);
        simpleJdbcCall.returningResultSet(reffunction, BeanPropertyRowMapper.newInstance(classreturn));
        MapSqlParameterSource inParams = new MapSqlParameterSource();
        if(null!=parameters) {
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                inParams.addValue(parameter.getKey(), parameter.getValue());
            }
        }
        logger.info("PROCEDURE {} IS CALLED",procedureName);
        Map out = simpleJdbcCall.execute(inParams);
        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get(reffunction);
        }
    }

}
