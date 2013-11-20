package bbc.iplayer.spring.mashery.iodocs.dataextraction;

import java.lang.reflect.Method;

public abstract class AbstractExtractorTest {
    protected static Method getMethodByName(Class<?> clazz, String methodName) {
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new IllegalStateException("should have found the method - maybe you changed the name");
    }
}
