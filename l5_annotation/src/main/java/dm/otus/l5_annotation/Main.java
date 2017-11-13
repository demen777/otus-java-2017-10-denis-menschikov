package dm.otus.l5_annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException,
            IOException
    {
//        TestRunner.runTestsInClass(args[0]);
        TestRunner.runTestsInPackage("dm.otus.l5_annotation");
    }
}
