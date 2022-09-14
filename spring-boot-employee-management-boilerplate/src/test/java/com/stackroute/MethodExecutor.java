package com.stackroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.fail;

public class MethodExecutor {
    private static Logger logger = LoggerFactory.getLogger(MethodExecutor.class);

    public static Object executeMethod(Object object, String methodName, Class[] parameterTypes, Object[] parameterValues) {
        Object returnObject = null;
        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);
            returnObject = method.invoke(object, parameterValues);
        } catch (NoSuchMethodException e) {
            fail("Method not defined as per requirement " + methodName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("METHOD EXECUTION FAILED -" + methodName, e);
        }
        return returnObject;
    }

    public static Method getMethod(Object object, String methodName, Class<?>... parameters) {
        Method optionalMethod = null;
        try {
            optionalMethod = object.getClass().getMethod(methodName, parameters);
        } catch (NoSuchMethodException e) {
            optionalMethod = null;
        }
        return optionalMethod;
    }

    public static Object invokeMethod(Object object, Method method, Object... parameters) {
        Object returnObject = null;
        try {
            returnObject = method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("METHOD EXECUTION FAILED -" + method.getName(), e);
        }
        return returnObject;
    }
}
