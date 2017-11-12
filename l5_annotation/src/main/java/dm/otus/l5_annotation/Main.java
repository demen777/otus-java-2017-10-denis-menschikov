package dm.otus.l5_annotation;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TestRunner.runTestsInClass(args[0]);
    }
}
