package dm.otus.l4_gc;

import java.util.ArrayList;
import java.util.Random;


class MemoryLeaker {
    public void run() throws InterruptedException {
        ArrayList<byte[]> data = new ArrayList<>();
        Random rand = new Random(123L);
        final int onePassQuantity = 1000;
        final int byteArraySize = 1000;
        int counter = 0;
        //noinspection InfiniteLoopStatement
        while(true) {
            for(int i=0;i<onePassQuantity;i++) {
                data.add(new byte[byteArraySize]);
            }
            for(int i=0;i<onePassQuantity/2;i++) {
                data.remove(rand.nextInt(data.size()));
            }
            Thread.sleep(300);
        }
    }
}
