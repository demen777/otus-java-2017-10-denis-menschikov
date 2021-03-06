package dm.otus.l2_memory;

public class Main {
    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, InterruptedException, ClassNotFoundException {
        SizeCalculator sizeCalculator = new SizeCalculator();
        String[] classNames = {"java.lang.Object", "java.lang.String", "dm.otus.l2_memory.Main"};
        for(String className: classNames) {
            System.out.printf("Class %s has size %d bytes%n", className, sizeCalculator.getSizeByName(className));
        }
        for(int length=0; length < 11; length++){
            Object[] array = new Object[length];
            System.out.printf("Object[%d] has size %d bytes%n", length,
                    sizeCalculator.getArraySizeInBytes(array));
        }
        for(int length=0; length < 11; length++){
            System.out.printf("String lenght=%d has size  %d bytes%n", length,
                    sizeCalculator.getStringSizeInBytes(length));
        }
    }
}
