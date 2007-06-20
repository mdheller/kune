#!/bin/bash
#LOGLEVEL=ALL
LOGLEVEL=ERROR
#LOGLEVEL=INFO
DETAIL=detailed
#DETAIL=obf
/usr/lib/j2sdk1.5-ibm/jre/bin/java -classpath /home/vjrj/proyectos/ourproject.org/kune/svn/trunk/target/classes:/home/vjrj/.m2/repository/concurrent/concurrent/1.3.4/concurrent-1.3.4.jar:/home/vjrj/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/home/vjrj/.m2/repository/nekohtml/nekohtml/0.9.4/nekohtml-0.9.4.jar:/home/vjrj/.m2/repository/org/apache/derby/derby/10.2.1.6/derby-10.2.1.6.jar:/home/vjrj/.m2/repository/org/jivesoftware/smack/smack/3.0.0/smack-3.0.0.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-jcr-commons/1.3/jackrabbit-jcr-commons-1.3.jar:/home/vjrj/.m2/repository/org/slf4j/slf4j-log4j12/1.0.1/slf4j-log4j12-1.0.1.jar:/home/vjrj/.m2/repository/commons-httpclient/commons-httpclient/3.0/commons-httpclient-3.0.jar:/home/vjrj/.m2/repository/poi/poi/2.5.1-final-20040804/poi-2.5.1-final-20040804.jar:/home/vjrj/.m2/repository/javax/jcr/jcr/1.0/jcr-1.0.jar:/home/vjrj/.m2/repository/commons-collections/commons-collections/3.1/commons-collections-3.1.jar:/home/vjrj/.m2/repository/org/slf4j/slf4j-api/1.3.0/slf4j-api-1.3.0.jar:/home/vjrj/.m2/repository/org/apache/geronimo/specs/geronimo-jta_1.0.1B_spec/1.0.1/geronimo-jta_1.0.1B_spec-1.0.1.jar:/home/vjrj/.m2/repository/commons-codec/commons-codec/1.2/commons-codec-1.2.jar:/home/vjrj/.m2/repository/org/apache/lucene/lucene-core/2.0.0/lucene-core-2.0.0.jar:/home/vjrj/.m2/repository/pdfbox/pdfbox/0.6.4/pdfbox-0.6.4.jar:/home/vjrj/.m2/repository/com/google/code/guice/guice/1.0/guice-1.0.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-text-extractors/1.3/jackrabbit-text-extractors-1.3.jar:/home/vjrj/.m2/repository/org/easymock/easymock/2.2/easymock-2.2.jar:/home/vjrj/.m2/repository/xml-apis/xml-apis/1.3.03/xml-apis-1.3.03.jar:/home/vjrj/.m2/repository/commons-logging/commons-logging/1.0.3/commons-logging-1.0.3.jar:/home/vjrj/.m2/repository/xerces/xercesImpl/2.8.1/xercesImpl-2.8.1.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-core/1.3/jackrabbit-core-1.3.jar:/home/vjrj/.m2/repository/org/jivesoftware/smackx/debugger/smackx-debugger/3.0.0/smackx-debugger-3.0.0.jar:/home/vjrj/.m2/repository/org/jivesoftware/smackx/smackx/3.0.0/smackx-3.0.0.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-webdav/1.3/jackrabbit-webdav-1.3.jar:/home/vjrj/.m2/repository/com/google/gwt/gwt-servlet/1.4.10/gwt-servlet-1.4.10.jar:/home/vjrj/.m2/repository/org/textmining/tm-extractors/0.4/tm-extractors-0.4.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-jcr-server/1.3/jackrabbit-jcr-server-1.3.jar:/home/vjrj/.m2/repository/org/apache/jackrabbit/jackrabbit-api/1.3/jackrabbit-api-1.3.jar:/home/vjrj/.m2/repository/log4j/log4j/1.2.14/log4j-1.2.14.jar:/home/vjrj/.m2/repository/org/gwm/gwm/0.6.6/gwm-0.6.6.jar:/usr/local/lib/gwt/gwt-linux-1.4.10/gwt-dev-linux.jar:/usr/local/lib/gwt/gwt-linux-1.4.10/gwt-dev-mac.jar:/usr/local/lib/gwt/gwt-linux-1.4.10/gwt-dev-windows.jar:/usr/local/lib/gwt/gwt-linux-1.4.10/gwt-user.jar:/home/vjrj/proyectos/ourproject.org/kune/svn/trunk/src/main/resources:/home/vjrj/proyectos/ourproject.org/kune/svn/trunk/src/main/java com.google.gwt.dev.GWTCompiler -logLevel $LOGLEVEL -style $DETAIL -out /home/vjrj/proyectos/ourproject.org/kune/svn/trunk/target/org.ourproject.kune-0.0.1 org.ourproject.kune.Main
RESULT=$?
which kdialog > /dev/null 2>&1
if [[ $? == 0 ]]
then
	if [[ $RESULT == 0 ]]
	then
    kdialog --msgbox "Compilation finished successfully" 2>/dev/null &
    firefox $PWD/target/org.ourproject.kune-0.0.1/org.ourproject.kune.Main/Main.html
	else
    kdialog --error "Compilation finished with errors"  2>/dev/null &
	fi
fi
