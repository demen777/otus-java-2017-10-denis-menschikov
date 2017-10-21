package dm.otus.l2_memory;

public class SizeCalculator {
    public static long getSizeByName(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        Class class_ = Class.forName(className);
        int quantityObjects = 1_000_000;
        Object[] data = new Object[quantityObjects];
        long usedAfter;
        int i;
        long usedBefore = getUsedMemory();
        for(i=0; i<quantityObjects; i++){
            data[i] = "java.lang.String".equals(className) ? new String(new char[0]) : class_.newInstance();
        }
        usedAfter = getUsedMemory();
        long res = Math.round((usedAfter-usedBefore)/(double)quantityObjects);
        System.out.println(data[0]);
        return res;
    }

    public static long getArraySizeInBytes(Object[] array) throws InterruptedException {
        long usedAfter;
        long usedBefore;
        usedBefore = getUsedMemory();
        Object[] newArray = array.clone();
        usedAfter = getUsedMemory();
        System.out.println(newArray);
        return usedAfter-usedBefore;
    }

    public static long getStringSizeInBytes(int length) throws InterruptedException {
        long usedAfter;
        long usedBefore;
        usedBefore = getUsedMemory();
        String str = new String(new char[length]);
        usedAfter = getUsedMemory();
        System.out.println(str);
        return usedAfter-usedBefore;
    }

    private static long getUsedMemory() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        Thread.sleep(1000);
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
