package com.Way2Automation.Listners;


import com.Way2Automation.Drivers.DriverManager;
import com.Way2Automation.Utilities.AllureUtils;
import com.Way2Automation.Utilities.FilesUtils;
import com.Way2Automation.Utilities.LogsUtil;
import com.Way2Automation.Utilities.ScreenshotsUtils;
import org.testng.*;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.Way2Automation.Utilities.PropertiesUtils.loadProperties;

public class TestNGListeners implements IExecutionListener, ITestListener, IInvokedMethodListener {

    File allure_results = new File("test-outputs/allure-results");
    File allure_report = new File("test-outputs/allure-report");
    File logs = new File("test-outputs/Logs");
    File screenshots = new File("test-outputs/screenshots");
    private static final Map<String, Throwable> configFailures = new ConcurrentHashMap<>();
    int passed = 0, failed = 0, skipped = 0;

    @Override
    public void onExecutionStart() {
        LogsUtil.info("Test Execution started............");
        loadProperties();
        FilesUtils.deleteFiles(allure_results);
        FilesUtils.deleteFiles(allure_report);

        FilesUtils.cleanDirectory(logs);
        FilesUtils.cleanDirectory(screenshots);

        FilesUtils.createDirectory(allure_results);
        FilesUtils.createDirectory(allure_report);
        FilesUtils.createDirectory(logs);
        FilesUtils.createDirectory(screenshots);
    }


    @Override
    public void onExecutionFinish() {

        LogsUtil.info("===== Execution Summary =====");
        LogsUtil.info("Total Passed: " + passed);
        LogsUtil.info("Total Failed: " + failed);
        LogsUtil.info("Total Skipped: " + skipped);
        LogsUtil.info("Test Execution finished");

        LogsUtil.info("Generating and serving Allure report...");
        AllureUtils.opnAllureReportAfterExecution();

    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            LogsUtil.info("⚙️ Preparing configuration for ", method.getTestMethod().getMethodName());
        }
        if (method.isTestMethod()) {
            LogsUtil.info("🚀 Starting test: ", testResult.getName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "✅ PASSED -" + testResult.getName());
                case ITestResult.FAILURE ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "❌ FAILED - " + testResult.getName());
                case ITestResult.SKIP ->
                        ScreenshotsUtils.takeScreenshot(DriverManager.getDriver(), "⏭️ SKIPPED - " + testResult.getName());
            }
            AllureUtils.attacheLogsToAllureReport();
        }
        if (method.isConfigurationMethod() && testResult.getStatus() == ITestResult.FAILURE) {
            String classKey = "CLASS:" + method.getTestMethod().getTestClass().getName();
            configFailures.put(classKey, testResult.getThrowable());
        }


    }

    @Override
    public void onTestStart(ITestResult result) {
        // Check for class-level config failures
        if (configFailures.containsKey("CLASS:" + result.getTestClass().getName())) {
            result.setStatus(ITestResult.SKIP);
            result.setThrowable(configFailures.get("CLASS:" + result.getTestClass().getName()));
            throw new SkipException("Skipped due to @BeforeClass failure");
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "passed");
        passed++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "failed");
        failed++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtil.info(" test case : ", result.getName(), "⏭️ Skipped", "With failure" + result.getThrowable().getMessage());
        skipped++;
    }


}