![Logo of Treebase](https://treebase.org/treebase-web/images/TreeBASE.png)

Building the artifacts in this repository
=========================================

This document describes the steps taken to compile the classes of the TreeBASE project proper.
Specifically, this includes:

- the object-relational mappings implemented in [treebase-core](treebase-core), and 
- the web application, i.e. [treebase-web](treebase-web)

which are both bundled into a WAR archive for the Tomcat servlet container. (For a fully 
functioning web application, the headless mesquite library packaged as `mesquite-2.01.tb.jar` 
and the `PhyloWidget.zip` browser applet also need to be available to the web application.)
All these artifacts, if successfully rebuilt, should be committed to 
[treebase-artifact](https://github.com/naturalis/treebase-artifact) so that the server provisioning
process can make use of it.

Setting up the building environment
-----------------------------------

Assuming you are on a Ubuntu 16.04LTS operating system, have the following Java installed:

    $ java -version
    openjdk version "1.8.0_131"
    OpenJDK Runtime Environment (build 1.8.0_131-8u131-b11-0ubuntu1.16.04.2-b11)
    OpenJDK 64-Bit Server VM (build 25.131-b11, mixed mode)

Also, ensure you have the maven installed as tested below. **NOTE**: if you get a message about 
`JAVA_HOME` not being set, or you get downstream error messages about `javac` missing once you're 
trying to compile, it means that your JDK was not fully installed and you only have the JRE part.
This is addressed by re-installing the JDK, e.g. `sudo apt install openjdk-8-jdk`.

    $ mvn -V
    Apache Maven 3.3.9
    Maven home: /usr/share/maven
    Java version: 1.8.0_131, vendor: Oracle Corporation
    Java home: /usr/lib/jvm/java-8-openjdk-amd64/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux", version: "4.4.0-83-generic", arch: "amd64", family: "unix"

Compiling
---------

Then, check out and compile the source tree:

    $ sudo su
    # cd /usr/local/src
    # git clone https://github.com/TreeBASE/treebase.git
    # cd treebase
    # mvn compiler:compile

This should result in a successful build:

    ...previous omitted...
    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Summary:
    [INFO] 
    [INFO] Treebase ........................................... SUCCESS [  0.396 s]
    [INFO] treebase-core ...................................... SUCCESS [  3.234 s]
    [INFO] treebase-web ....................................... SUCCESS [ 11.620 s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 15.445 s
    [INFO] Finished at: 2017-07-19T11:58:57+00:00
    [INFO] Final Memory: 34M/390M
    [INFO] ------------------------------------------------------------------------

Bundling
--------

Assuming we have compiled successfully, this means that all the dependencies were found
and there were no errors in the code. What needs to happen next is to bundle the compiled
code into artifacts that can be deployed. Because TreeBASE is (partly) a web application that 
runs in the Tomcat servlet container we need to make a WAR archive, specifically from the
subproject `treebase-web`. However, this web application in turn depends on the object 
relational mappings of `treebase-core`, whose functionality is exposed to the web application
by bundling the core into a JAR. So, we first bundle the core into a JAR like so:

    # cd treebase-core
    # mvn jar:jar

The output should look like this:

    ...previous omitted...
    [INFO] Building jar: /usr/local/src/treebase/treebase-core/target/treebase-core-1.0-SNAPSHOT.jar
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 3.065 s
    [INFO] Finished at: 2017-07-19T12:55:41+00:00
    [INFO] Final Memory: 16M/307M
    [INFO] ------------------------------------------------------------------------

This JAR is referred to in the [treebase-web/pom.xml](treebase-web/pom.xml) by way of a 
relative path so that, once it's been created, it can then be bundled into the web archive 
(WAR). This WAR file can be created after `compiler:compile` by invoking the 
[war:war](http://maven.apache.org/plugins/maven-war-plugin/usage.html) maven goal, i.e.

    # cd ../treebase-web
    # mvn war:war

Which should produce the following output:

    ...previous omitted...
    [INFO] Packaging webapp
    [INFO] Assembling webapp [treebase-web] in [/usr/local/src/treebase/treebase-web/target/treebase-web]
    [INFO] Processing war project
    [INFO] Copying webapp resources [/usr/local/src/treebase/treebase-web/src/main/webapp]
    [INFO] Webapp assembled in [420 msecs]
    [INFO] Building war: /usr/local/src/treebase/treebase-web/target/treebase-web.war
    [INFO] WEB-INF/web.xml already added, skipping
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 3.743 s
    [INFO] Finished at: 2017-07-19T13:42:41+00:00
    [INFO] Final Memory: 15M/298M
    [INFO] ------------------------------------------------------------------------