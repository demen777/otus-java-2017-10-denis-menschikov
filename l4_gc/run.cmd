set JAVAMEMORY=-Xms512m -Xmx512m
set JAVAGC=-verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails
set GCLOGFILENAME=gc_serial.log
java %JAVAMEMORY% %JAVAGC%  -Xloggc:%GCLOGFILENAME% -XX:+UseSerialGC -cp target/classes dm.otus.l4_gc.Main
java -cp target/classes dm.otus.l4_gc.GCLogParser %GCLOGFILENAME%
set GCLOGFILENAME=gc_parallel.log
java %JAVAMEMORY% %JAVAGC% -Xloggc:%GCLOGFILENAME% -XX:+UseParallelGC -XX:+UseParallelOldGC -cp target/classes dm.otus.l4_gc.Main
java -cp target/classes dm.otus.l4_gc.GCLogParser %GCLOGFILENAME%
set GCLOGFILENAME=gc_concurrent.log
java %JAVAMEMORY% %JAVAGC%  -Xloggc:%GCLOGFILENAME% -XX:+UseConcMarkSweepGC -cp target/classes dm.otus.l4_gc.Main
java -cp target/classes dm.otus.l4_gc.GCLogParser %GCLOGFILENAME%
set GCLOGFILENAME=gc_g1.log
java %JAVAMEMORY% %JAVAGC%  -Xloggc:%GCLOGFILENAME% -XX:+UseG1GC -cp target/classes dm.otus.l4_gc.Main
java -cp target/classes dm.otus.l4_gc.GCLogParser %GCLOGFILENAME%
