package dm.otus.l4_gc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Double.max;

class GCInfoRecord {
    private final String gcEvent;
    private final double timestamp;
    private final double duration;

    public GCInfoRecord(String gcType, double msTimestamp, double msDuration) {
        this.gcEvent = gcType;
        this.timestamp = msTimestamp;
        this.duration = msDuration;
    }

    public String getGcEvent() {
        return gcEvent;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public double getDuration() {
        return duration;
    }
}

public class GCLogParser {
    private final String gcLogFilename;
    private static final Pattern gcLogLineRegex =
            Pattern.compile("^\\S+ (\\d+\\.\\d{3}): \\[(.+?)[),:].*[ =](\\d+\\.\\d+) secs].*$");

    public GCLogParser(String gcLogFilename) {
        this.gcLogFilename = gcLogFilename;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        GCLogParser gcLogParser = new GCLogParser(args[0]);
        gcLogParser.run();
    }

    private static GCInfoRecord stringToGCInfoRecord(String s) {
        Matcher m = gcLogLineRegex.matcher(s);
        if (!m.matches()) {
            return null;
        }
        return new GCInfoRecord(m.group(2), Double.valueOf(m.group(1)), Double.valueOf(m.group(3)));
    }

    private void run() throws IOException {
        List<GCInfoRecord> gcInfoList = Files.lines(new File(gcLogFilename).toPath()).map(String::trim).map(
                GCLogParser::stringToGCInfoRecord).filter(Objects::nonNull).collect(Collectors.toList());
        double maxTimestamp = 0.0;
        HashMap<String, Integer> eventCounter = new HashMap<>();
        HashMap<String, Double> eventDuration = new HashMap<>();
        for(GCInfoRecord gcInfo: gcInfoList) {
            eventCounter.put(gcInfo.getGcEvent(), eventCounter.getOrDefault(gcInfo.getGcEvent(), 0)+1);
            eventDuration.put(gcInfo.getGcEvent(),
                    eventDuration.getOrDefault(gcInfo.getGcEvent(), 0.0)+gcInfo.getDuration());
            maxTimestamp = max(maxTimestamp, gcInfo.getTimestamp());
        }
        double sumDuration = 0.0;
        if (maxTimestamp > 0.0) {
            for(String gcEvent: eventDuration.keySet()){
                System.out.printf("'%s' Count=%d UsedPerMinute=%fs\n", gcEvent, eventCounter.get(gcEvent),
                        eventDuration.get(gcEvent)*60/maxTimestamp);
                sumDuration+=eventDuration.get(gcEvent);
            }
            System.out.printf("Total UsedPerMinute=%fs\n", sumDuration*60/maxTimestamp);
        }
    }
}
