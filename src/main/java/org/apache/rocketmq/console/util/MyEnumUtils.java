package org.apache.rocketmq.console.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.rocketmq.console.support.IntEnum;

import java.lang.reflect.Constructor;

/**
 * 扩展枚举工具类
 * @author lijiangtao
 */
public class MyEnumUtils extends EnumUtils {

    /**
     * 通过code 获取枚举实例对象
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<E> & IntEnum> E getEnum(Class<E> enumClass, int code){
        E[] constructors = enumClass.getEnumConstants();
        for (E e : constructors) {
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }
}
