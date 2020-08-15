package org.apache.rocketmq.console.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoEnumHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private BaseTypeHandler typeHandler = null;

    /**
     * 创建一个泛型类型处理器，它可以处理多于一个类。为达到此目的， 需要增加一个接收该类作为参数的构造器，
     * 这样在构造一个类型处理器的时候 MyBatis 就会传入一个具体的类
     * @param type
     */
    public AutoEnumHandler(Class<E> type){
        if (type == null) {
            throw new IllegalStateException("Type argument cannot be null");
        }
        if(IntEnum.class.isAssignableFrom(type)){
            //如果继承了 intEnum 则返回自定义处理器
            typeHandler = new IntEnumHandler(type);
        }else {
            typeHandler = new EnumTypeHandler(type);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        typeHandler.setNonNullParameter(ps,i,parameter,jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (E) typeHandler.getNullableResult(rs,columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (E) typeHandler.getNullableResult(rs,columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (E) typeHandler.getNullableResult(cs,columnIndex);
    }
}
