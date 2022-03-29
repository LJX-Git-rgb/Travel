package com.travel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MySpring
 * @description:
 * @author: LJX
 * @time: 2020/3/11 17:57
 * @version: 1.0
 */
public class MySpring {
    private static Map<String,Object> classMap = new HashMap<>();

    public static <E>E getClassBean (String className){
        Object obj = null;
        try {
            Class c = Class.forName(className);
            obj = classMap.get(className);  //从集合中搜索该对象
            if (obj == null) {              //如果没有就创建该类的对象并放入集合中
                obj = c.newInstance();
                classMap.put(className, obj);
            }
        }
        catch (Exception e){ }
        return (E)obj;
    }
}
