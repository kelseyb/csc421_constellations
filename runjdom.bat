REM Windoze batch file to compile and run Java programs that use JDOM
REM Usage: runjdom javafile(no ext) xmlfile
REM JMW 141029

javac -cp .;jdom.jar %1.java
java  -cp .;jdom.jar %1 %2
