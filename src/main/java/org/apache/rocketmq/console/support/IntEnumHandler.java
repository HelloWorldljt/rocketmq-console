package org.apache.rocketmq.console.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.rocketmq.console.util.MyEnumUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lijiangtao
 */
public class IntEnumHandler<E extends Enum<E> & IntEnum> extends BaseTypeHandler<IntEnum> {

    private Class<E> type;

    public IntEnumHandler(Class<E> type){
        if (type == null) {
            throw new IllegalStateException("Type argument cannot be null");
        }
        this.type = type;
    }

    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getCode());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull()?null:MyEnumUtils.getEnum(type,code);
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull()?null:MyEnumUtils.getEnum(type,code);
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull()?null:MyEnumUtils.getEnum(type,code);
    }
}
