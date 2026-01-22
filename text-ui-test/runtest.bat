@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\sappy\*.java ..\src\main\java\sappy\task\*.java ..\src\main\java\sappy\ui\*.java ..\src\main\java\sappy\storage\*.java ..\src\main\java\sappy\parser\*.java ..\src\main\java\sappy\command\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
REM Ensure the main class is referred to by its fully qualified name: sappy.Sappy
java -classpath ..\bin sappy.Sappy < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT