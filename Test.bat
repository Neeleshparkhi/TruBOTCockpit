call mvn clean
call mvn compile

//call mvn exec:java -Dexec.mainClass="batchExecution.DriverScript"  -Dexec.classpathScope="test"
call mvn install
pause