# openwhisk-action-maven-deploy
a maven plugin to deploy an openwhisk action

Plugin is available in jitpack repository.  
Latest build [![](https://travis-ci.org/fabtesta/openwhisk-action-maven-deploy.svg)](https://travis-ci.org/fabtesta/openwhisk-action-maven-deploy)  
Latest artifact [![Build Status](https://jitpack.io/v/fabtesta/openwhisk-action-maven-deploy.svg)](https://jitpack.io/#fabtesta/openwhisk-action-maven-deploy)

## Features
From version 1.0.0 supports create/update wsk action, web export and API creation  

## Basic Usage
1) Include following plugin-block in the pom.xml of your project.
2) Define OpenWhisk action parameters
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
...

<build>
    <plugins>
        <plugin>
            <groupId>com.github.fabtesta</groupId>
            <artifactId>openwhisk-action-maven-deploy</artifactId>
            <version>1.0.0</version>
            <configuration>
              <wskBinPath>wsk</wskBinPath>
              <actionName>action-demo</actionName>
              <mainClass>com.github.fabtesta.WskActionDemo</mainClass>
              <artifactFullPath>target/action-demo.jar</artifactFullPath>
              <webEnabled>true</webEnabled>
              <actionApiPath>/action-demo</actionApiPath>
              <actionApiVerb>POST</actionApiVerb>
              <actionApiResponseType>json</actionApiResponseType>
            </configuration>
            <executions>
              <execution>
                <phase>install</phase>
                <goals>
                  <goal>action-deploy</goal>
                </goals>
              </execution>
            </executions>
         </plugin>
    </plugins>
</build>
```
