package com.cxyz.check.typehandler;

import com.cxyz.check.util.date.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DateOnlyTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setDate(i,date.toSDate());
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        java.sql.Date sqlDate = resultSet.getDate(s);
        return sqlDate != null ? Date.fromUDate(new java.util.Date(sqlDate.getTime())) : null;
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        java.sql.Date sqlDate = resultSet.getDate(i);
        return sqlDate != null ? Date.fromUDate(new java.util.Date(sqlDate.getTime())): null;
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        java.sql.Date sqlDate = callableStatement.getDate(i);
        return sqlDate != null ? Date.fromUDate(new java.util.Date(sqlDate.getTime())): null;
    }
}
