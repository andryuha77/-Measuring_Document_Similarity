# Measuring_Document_Similarity
Advanced Object Oriented Project

This is project for the Advanced Object-Oriented Design Principles & Patterns 2017 module.
The module is taught to undergraduate students at [GMIT](http://www.gmit.ie) in the Department of Computer Science and Applied Physics.
The lecturer is John Healey.

### Project Requirements Overview

> You are required to develop a Java web application that enables two or more text documents to be compared for similarity.

> Your implementation should include the following features:   
> * **A document or URL should be specified or selected** from a web browser and then dispatched to a servlet instance running under Apache Tomcat.  
> * Each submitted document should be **parsed into its set of constituent shingles** and then compared against the existing document(s) in an object-oriented database (**db4O**) and then stored in the database.  
> * The **similarity of the submitted document** to the set of documents stored in the database should be returned and presented to the session user.

## Servlet.
Servlets provide a component-based, platform-independent method for building Webbased applications, without the performance limitations of CGI programs. Servlets have access to the entire family of Java APIs, including the JDBC API to access enterprise databases.

## Apache Tomcat.
Apache Tomcat, often referred to as Tomcat Server, is an open-source Java Servlet Container developed by the Apache Software Foundation (ASF). Tomcat implements several Java EE specifications including Java Servlet, JavaServer Pages (JSP), Java EL, and WebSocket, and provides a "pure Java" HTTP web server environment in which Java code can run.

## DB4O.
db4o represents an object-oriented database model. One of its main goals is to provide an easy and native interface to persistence for object oriented programming languages. Development with db4o database does not require a separate data model creation, the application’s class model defines the structure of the data. db4o attempts to avoid the object/relational impedance mismatch by eliminating the relational layer from a software project. db4o is written in Java and .NET and provides the respective APIs. It can run on any operating system that supports Java or .NET. It is offered under licenses including GPL, the db4o Opensource Compatibility License (dOCL), and a commercial license for use in proprietary software.

Developers using relational databases can view db40 as a complementary tool. The db4o-RDBMS data exchange can be implemented using db4o Replication System (dRS). dRS can also be used for migration between object (db4o) and relational (RDBMS) technologies.

## How to run the application

The Application can be started by executing [Server.jar](https://github.com/andryuha77/Asynchronous_RMI_Dictionary_Service/blob/master/Server.jar) and [Client.war](https://github.com/andryuha77/Asynchronous_RMI_Dictionary_Service/blob/master/Client.war) archives.

1. CD to location containing [Server.jar](https://github.com/andryuha77/Asynchronous_RMI_Dictionary_Service/blob/master/Server.jar) 

2. Execute .jar using:
```
java -cp ./Server.jar ie.gmit.sw.DictionaryServiceServer
```
[Tom-cat 9](https://tomcat.apache.org/download-90.cgi) was used to run the Dynamic Web App. 

3. Place [Client.war](https://github.com/andryuha77/Asynchronous_RMI_Dictionary_Service/blob/master/Client.war) file in the /webapps folder of [Tom-cat](https://tomcat.apache.org/download-90.cgi) installation location.
```
C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps
```
4. Start [Tom-cat](https://tomcat.apache.org/download-90.cgi) by Executing Tomcat9.exe in:
```
C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin
```
5. Access the application by navigating in your browser to:
```
http://localhost:8080/Client/
```
Alternatively, the application can be launched  in [Eclipse](https://www.eclipse.org/).
