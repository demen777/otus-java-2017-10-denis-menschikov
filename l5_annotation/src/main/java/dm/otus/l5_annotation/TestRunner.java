package dm.otus.l5_annotation;

import com.google.common.reflect.ClassPath;
import com.sun.istack.internal.NotNull;
import dm.otus.l5_annotation.annotations.After;
import dm.otus.l5_annotation.annotations.Before;
import dm.otus.l5_annotation.annotations.Test;

import java.io.IOException;
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
                    a -> Before.class.equals(a.annotationType()))) {
                beforeMethods.add(method);
            }
            else if (Arrays.stream(annotations).anyMatch(
                    a -> After.class.equals(a.annotationType()))) {
                afterMethods.add(method);
            }
            else if (Arrays.stream(annotations).anyMatch(
                    a -> Test.class.equals(a.annotationType()))) {
                testMethods.add(method);
            }
        }
        for(Method test: testMethods) {
            System.out.printf("\nTest %s running\n", test.getName());
            Object testObject = class_.newInstance();
            for (Method beforeMethod : beforeMethods) {
                beforeMethod.invoke(testObject);
            }
            try {
                test.invoke(testObject);
                System.out.printf("Test %s OK\n", test.getName());
            }
            catch(InvocationTargetException e) {
                System.out.printf("Test %s FAIL!\n", test.getName());
                if ((e.getCause() != null) && "java.lang.AssertionError".equals(e.getCause().toString())) {
                    Throwable assertError = e.getCause();
                    System.out.println(assertError.toString());
                    Arrays.stream(assertError.getStackTrace()).forEach(System.out::println);
                } else {
                    throw new InvocationTargetException(e);
                }
            }
            for (Method afterMethod : afterMethods) {
                afterMethod.invoke(testObject);
            }
        }
    }

    static public void runTestsInPackage(@NotNull String packageName)
            throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException,
            IllegalAccessException
    {
        final ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        for(ClassPath.ClassInfo classInfo:classPath.getTopLevelClasses(packageName)) {
            System.out.printf("\nRun tests in %s\n", classInfo.getName());
            TestRunner.runTestsInClass(classInfo.getName());
        }
    }

}
