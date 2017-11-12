package dm.otus.l5_annotation;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class TestRunner {

    static public void runTestsInClass(@NotNull String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> class_ = Class.forName(className);
        ArrayList<Method> beforeMethods = new ArrayList<>();
        ArrayList<Method> testMethods = new ArrayList<>();
        ArrayList<Method> afterMethods = new ArrayList<>();
        Method[] methods = class_.getDeclaredMethods();
        for(final Method method: methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (Arrays.stream(annotations).anyMatch(
                    a -> "dm.otus.l5_annotation.annotations.Before".equals(a.annotationType().getCanonicalName()))) {
                beforeMethods.add(method);
            }
            else if (Arrays.stream(annotations).anyMatch(
                    a -> "dm.otus.l5_annotation.annotations.After".equals(a.annotationType().getCanonicalName()))) {
                afterMethods.add(method);
            }
            else if (Arrays.stream(annotations).anyMatch(
                    a -> "dm.otus.l5_annotation.annotations.Test".equals(a.annotationType().getCanonicalName()))) {
                testMethods.add(method);
            }
        }
        for(Method test: testMethods) {
            System.out.printf("\nTest %s running\n", test.getName());
            Object testObject = class_.newInstance();
            try {
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(testObject);
                }
                test.invoke(testObject);
                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(testObject);
                }
                System.out.printf("Test %s OK\n", test.getName());
            }
            catch(InvocationTargetException e) {
                System.out.printf("Test %s FAIL!\n", test.getName());
                if ((e.getCause() != null) && "java.lang.AssertionError".equals(e.getCause().toString())) {
                    Throwable assertError = e.getCause();
                    System.out.println(assertError.toString());
                    Arrays.stream(assertError.getStackTrace()).forEach(System.out::println);
                }
                else {
                    throw new InvocationTargetException(e);
                }
            }
        }
    }

}
