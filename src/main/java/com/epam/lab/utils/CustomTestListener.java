package com.epam.lab.utils;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {
    private final Logger LOG = Logger.getLogger(CustomTestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info(String.format("Test %s was successfully started", result.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info(String.format("Test %s finished successfully", result.getName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error(String.format("Test %s was failed", result.getName()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn(String.format("Test %s was ignored", result.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LOG.warn(String.format("Test %s was failed, but with success percentage", result.getName()));
    }

    @Override
    public void onStart(ITestContext context) {
        Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
        LOG.info(String.format("Tests from %s class was started", context.getName()));
    }

    @Override
    public void onFinish(ITestContext context) {
        LOG.info(String.format("Tests from %s class was finished", context.getName()));
    }
}
