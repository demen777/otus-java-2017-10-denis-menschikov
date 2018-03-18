package dm.otus.l15_msg.message_system;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SocketMessageDelivery implements MessageDelivery {
    private static final Logger logger = Logger.getLogger(MessageDelivery.class.getName());
    @SuppressWarnings("FieldCanBeLocal")
    private final int INPUT_MESSAGE_QUEUE_CAPACITY = 1000;
    private final BlockingQueue<Message> inputMessages;
    @SuppressWarnings("FieldCanBeLocal")
    private final int OUTPUT_MESSAGE_QUEUE_CAPACITY = 1000;
    private final BlockingQueue<Message> outputMessages;
    private final Socket socket;
    private final ExecutorService executor;

    public SocketMessageDelivery(Socket socket) {
        this.socket = socket;
        inputMessages = new ArrayBlockingQueue<>(INPUT_MESSAGE_QUEUE_CAPACITY);
        outputMessages = new ArrayBlockingQueue<>(OUTPUT_MESSAGE_QUEUE_CAPACITY);
        this.executor = Executors.newFixedThreadPool(2);
        executor.execute(this::receiveMessage);
        executor.execute(this::sendMessage);
    }

    private void receiveMessage() {
        logger.info("Start receiveMessage");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    logger.info(json);
                    Message msg = getMessageFromJSON(json);
                    inputMessages.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("End receiveMessage");
    }

    private Message getMessageFromJSON(String json) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        String className = (String) jsonObject.get(Message.CLASS_NAME_VARIABLE);
        Class msgClass = null;
        try {
            msgClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert msgClass != null;
        //noinspection unchecked
        return (Message) new Gson().fromJson(json, msgClass);
    }

    private void sendMessage() {
        logger.info("Begin sendingMessage");
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while(!executor.isShutdown()) {
                Message message = outputMessages.take();
                String json = new Gson().toJson(message);
                out.println(json);
                logger.info(json);
                out.println();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("End sendingMessage");
    }

    @Override
    public void send(Message message) {
        try {
            outputMessages.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message take() throws InterruptedException {
        return inputMessages.take();
    }

    @Override
    public void close() {
        this.executor.shutdown();
    }
}
