package dm.otus.l4_gc;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        MemoryLeaker memoryLeaker = new MemoryLeaker();
        memoryLeaker.run();
    }
}
