@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  discordbot startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and DISCORDBOT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\discordbot-2.0.jar;%APP_HOME%\lib\owm-japis-2.5.3.0.jar;%APP_HOME%\lib\json-simple-1.1.1.jar;%APP_HOME%\lib\javax.json-api-1.1.jar;%APP_HOME%\lib\mockito-core-3.4.0.jar;%APP_HOME%\lib\spring-boot-starter-webflux-2.4.1.jar;%APP_HOME%\lib\javacord-core-3.8.0.jar;%APP_HOME%\lib\spring-boot-starter-json-2.4.1.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.12.7.jar;%APP_HOME%\lib\jackson-annotations-2.12.7.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.12.7.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.12.7.jar;%APP_HOME%\lib\jackson-core-2.12.7.jar;%APP_HOME%\lib\jackson-databind-2.12.7.1.jar;%APP_HOME%\lib\javacord-api-3.8.0.jar;%APP_HOME%\lib\converter-gson-2.5.0.jar;%APP_HOME%\lib\gson-2.8.5.jar;%APP_HOME%\lib\retrofit-2.5.0.jar;%APP_HOME%\lib\logging-interceptor-4.9.3.jar;%APP_HOME%\lib\okhttp-4.9.3.jar;%APP_HOME%\lib\okio-jvm-2.8.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.4.10.jar;%APP_HOME%\lib\junit-4.13.1.jar;%APP_HOME%\lib\byte-buddy-1.10.10.jar;%APP_HOME%\lib\byte-buddy-agent-1.10.10.jar;%APP_HOME%\lib\objenesis-2.6.jar;%APP_HOME%\lib\spring-boot-starter-2.4.1.jar;%APP_HOME%\lib\spring-boot-starter-reactor-netty-2.4.1.jar;%APP_HOME%\lib\spring-webflux-5.3.2.jar;%APP_HOME%\lib\spring-web-5.3.2.jar;%APP_HOME%\lib\nv-websocket-client-2.14.jar;%APP_HOME%\lib\xsalsa20poly1305-0.11.0.jar;%APP_HOME%\lib\spring-boot-starter-logging-2.4.1.jar;%APP_HOME%\lib\log4j-to-slf4j-2.13.3.jar;%APP_HOME%\lib\log4j-api-2.17.2.jar;%APP_HOME%\lib\vavr-0.10.4.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.4.10.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.4.1.jar;%APP_HOME%\lib\spring-boot-2.4.1.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.5.jar;%APP_HOME%\lib\spring-context-5.3.2.jar;%APP_HOME%\lib\spring-aop-5.3.2.jar;%APP_HOME%\lib\spring-beans-5.3.2.jar;%APP_HOME%\lib\spring-expression-5.3.2.jar;%APP_HOME%\lib\spring-core-5.3.2.jar;%APP_HOME%\lib\snakeyaml-1.27.jar;%APP_HOME%\lib\reactor-netty-http-1.0.2.jar;%APP_HOME%\lib\reactor-netty-core-1.0.2.jar;%APP_HOME%\lib\reactor-core-3.4.1.jar;%APP_HOME%\lib\bcprov-jdk15on-1.60.jar;%APP_HOME%\lib\vavr-match-0.10.4.jar;%APP_HOME%\lib\logback-classic-1.2.3.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.30.jar;%APP_HOME%\lib\spring-jcl-5.3.2.jar;%APP_HOME%\lib\netty-codec-http2-4.1.54.Final.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.54.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.54.Final.jar;%APP_HOME%\lib\netty-resolver-dns-4.1.54.Final.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.54.Final-linux-x86_64.jar;%APP_HOME%\lib\reactive-streams-1.0.3.jar;%APP_HOME%\lib\logback-core-1.2.3.jar;%APP_HOME%\lib\slf4j-api-1.7.30.jar;%APP_HOME%\lib\netty-handler-4.1.54.Final.jar;%APP_HOME%\lib\netty-codec-dns-4.1.54.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.54.Final.jar;%APP_HOME%\lib\netty-codec-4.1.54.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.54.Final.jar;%APP_HOME%\lib\netty-transport-4.1.54.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.54.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.54.Final.jar;%APP_HOME%\lib\netty-common-4.1.54.Final.jar


@rem Execute discordbot
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %DISCORDBOT_OPTS%  -classpath "%CLASSPATH%" org.jointheleague.Launcher %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable DISCORDBOT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%DISCORDBOT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
