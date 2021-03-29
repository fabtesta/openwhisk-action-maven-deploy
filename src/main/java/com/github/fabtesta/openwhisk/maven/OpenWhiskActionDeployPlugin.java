package com.github.fabtesta.openwhisk.maven;

import com.github.fabtesta.openwhisk.OpenWhiskActionDeploy;
import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal action-deploy
 * @phase install
 */
public class OpenWhiskActionDeployPlugin extends AbstractMojo {

    /**
     * The wsk cli executable full path
     * 
     * @parameter
     * @required
     */
    private String wskBinPath;

    /**
     * The unique final action name
     * 
     * @parameter
     * @required
     */
    private String actionName;

    /**
     * The action main class
     * 
     * @parameter
     */
    private String mainClass;

    /**
     * The jar full path
     *
     * @parameter
     */
    private String artifactFullPath;

    /**
     * If action must be web exportable
     *
     * @parameter
     */
    private boolean webEnabled;

    /**
     * The action API web path (webEnabled must be true)
     *
     * @parameter
     */
    private String actionApiPath;

    /**
     * The action API HTTP verb (webEnabled must be true and actionApiPath configured)
     *
     * @parameter
     */
    private String actionApiVerb;

    /**
     * The action API response type (webEnabled must be true, actionApiPath and actionApiVerb configured)
     *
     * @parameter
     */
    private String actionApiResponseType;

    @Override
    public void execute() throws MojoFailureException {

        OpenWhiskActionDeploy
          openWhiskActionDeploy = new OpenWhiskActionDeploy(wskBinPath, actionName, mainClass, artifactFullPath, webEnabled, actionApiPath
          , actionApiVerb, actionApiResponseType, getLog());
        getLog().info("wsk cli path " + wskBinPath);
        getLog().info("deploying actionName " + actionName + " with main " + mainClass + " from artifact "+artifactFullPath);
        getLog().info("web enabled? " + webEnabled);
        if(webEnabled)  {
            getLog().info("api path " + actionApiPath);
            getLog().info("api verb " + actionApiVerb);
            getLog().info("api response type " + actionApiResponseType);
        }

        try {
            openWhiskActionDeploy.deployAction();
        } catch (IOException | InterruptedException e) {
            getLog().error(e);
            throw new MojoFailureException(e.getMessage());
        }
    }
}
