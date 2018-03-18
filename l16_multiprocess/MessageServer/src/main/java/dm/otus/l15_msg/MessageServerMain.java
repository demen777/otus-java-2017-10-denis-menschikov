package dm.otus.l15_msg;

import dm.otus.l15_msg.message_system.MessageSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MessageServerMain {
    private static final String DB_SERVER_RUN_COMMAND
            = "java -jar ../DBServer/target/dbserver.jar";
    private static final String WAR_NAME = "root.war";
    private static final String PATH_TO_WAR = "../Frontend/target/" + WAR_NAME;
    private static final String PATH_TO_JETTY1 = "C:\\opt\\jetty\\webapps";
    private static final String PATH_TO_JETTY2 = "C:\\opt\\jetty2\\webapps";

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Process> processList = new ArrayList<>();
        MessageSystem messageSystem = new MessageSystem();
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    messageSystem.runServerSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            processList.add(runDBServer());
            processList.add(runDBServer());
            copyWarToJetty(PATH_TO_WAR, PATH_TO_JETTY1);
            copyWarToJetty(PATH_TO_WAR, PATH_TO_JETTY2);
            //noinspection InfiniteLoopStatement
            while(true) { Thread.sleep(1000); }
        } finally {
            for(Process process:processList) {
                process.destroy();
            }
            messageSystem.dispose();
        }
    }

    private static Process runDBServer() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(DB_SERVER_RUN_COMMAND.split(" "));
        pb.redirectError(new File("dbservice.log"));
        return pb.start();
    }

    @SuppressWarnings("SameParameterValue")
    private static void copyWarToJetty(String pathToWar, String pathToJetty) throws IOException {
        Files.copy(Paths.get(pathToWar), Paths.get(pathToJetty, WAR_NAME), StandardCopyOption.REPLACE_EXISTING);
    }
}
