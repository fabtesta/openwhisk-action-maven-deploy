package com.github.fabtesta.openwhisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.maven.plugin.logging.Log;

public class OpenWhiskActionDeploy {

    private String wskBinPath;
    private String actionName;
    private String mainClass;
    private String artifactFullPath;
    private boolean webEnabled;
    private String actionApiPath;
    private String actionApiVerb;
    private String actionApiResponseType;
    private Log log;

    public OpenWhiskActionDeploy(String wskBinPath, String actionName, String mainClass, String artifactFullPath, boolean webEnabled, String actionApiPath,
      String actionApiVerb, String actionApiResponseType, Log log) {

        this.wskBinPath = wskBinPath;
        this.actionName = actionName;
        this.mainClass = mainClass;
        this.artifactFullPath = artifactFullPath;
        this.webEnabled = webEnabled;
        this.actionApiPath = actionApiPath;
        this.actionApiVerb = actionApiVerb;
        this.actionApiResponseType = actionApiResponseType;
        this.log = log;
    }

    public void deployAction() throws IOException, InterruptedException {
        boolean alreadyDeployed = checkIfActionIsAlreadyDeployed();
        if(!alreadyDeployed)
        {
            createNewAction();
        }
        else
        {
            updateExistingAction();
        }

        if(webEnabled){
            createUpdateActionApi();
        }

    }

    private boolean checkIfActionIsAlreadyDeployed() throws IOException, InterruptedException {
        log.info("checkIfActionIsAlreadyDeployed --> "+wskBinPath +" action get "+actionName);
        Process process = Runtime.getRuntime().exec(wskBinPath +" action get "+actionName);
        String line = printGoodResults(process);

        if(line.startsWith("ok"))
        {
            return true;
        }

        return false;
    }

    private boolean createNewAction() throws IOException, InterruptedException {
        String command = wskBinPath +" action create "+actionName
                           + " " +artifactFullPath + " --main " + mainClass + (webEnabled ? " --web true" :
                                                                            "");
        log.info("createNewAction --> "+command);
        Process process = Runtime.getRuntime().exec(command);
        String line = printGoodResults(process);

        if(line.startsWith("ok: created action"))
        {
            return true;
        }

        return false;
    }


    private boolean updateExistingAction() throws IOException, InterruptedException {
        String command = wskBinPath +" action update "+actionName
                           + " " +artifactFullPath + " --main " + mainClass + (webEnabled ? " --web true" :
                                                                                 "");
        log.info("updateExistingAction --> "+command);
        Process process = Runtime.getRuntime().exec(command);
        String line = printGoodResults(process);

        if(line.startsWith("ok: updated action"))
        {
            return true;
        }

        return false;
    }

    private boolean createUpdateActionApi() throws IOException, InterruptedException {
        String command = wskBinPath +" api create "+actionApiPath
                           + " " +actionApiVerb + " " + actionName + " --response-type " + actionApiResponseType;
        log.info("createUpdateActionApi --> "+command);
        Process process = Runtime.getRuntime().exec(command);
        String line = printGoodResults(process);

        if(line.startsWith("ok: created API"))
        {
            return true;
        }

        return false;
    }

    private String printGoodResults(Process process) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder resultLine = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            log.info(line);
            resultLine.append(line);
        }

        BufferedReader readerError = new BufferedReader(new
                                                          InputStreamReader(process.getErrorStream()));
        StringBuilder resultLineError = new StringBuilder();
        String lineError = "";
        while ((lineError = readerError.readLine()) != null) {
            log.error(lineError);
            resultLineError.append(lineError);
        }

        int exitVal = process.waitFor();
        log.info("wsk cli command exit value: " + exitVal);
        if(exitVal == 0)
        {
            return resultLine.toString();
        }
        else
        {
            return resultLineError.toString();
        }
    }
}
