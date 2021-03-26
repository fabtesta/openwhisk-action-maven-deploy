package com.github.fabtesta.openwhisk;

public class OpenWhiskActionDeploy {

    private boolean generateConstants;
    private boolean beanValidation;
    private String packageName;
    private String sourceDirectory;
    private String requestSuperClass;
    private String responseSuperClass;

    public OpenWhiskActionDeploy(boolean generateConstants, boolean beanValidation, String packageName, String sourceDirectory, String requestSuperClass, String responseSuperClass) {
        this.generateConstants = generateConstants;
        this.beanValidation = beanValidation;
        this.packageName = packageName;
        this.sourceDirectory = sourceDirectory;
        this.requestSuperClass = requestSuperClass;
        this.responseSuperClass = responseSuperClass;
    }

    public void deployAction() {
    }
}
