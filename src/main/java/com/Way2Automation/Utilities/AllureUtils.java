package com.Way2Automation.Utilities;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {
    private static final String AllureResultDirectory = "test-outputs/allure-results";
    private static final String AllureReportDirectory = "test-outputs/allure-report";

    private AllureUtils() {
        super();
    }

    public static void attacheLogsToAllureReport() {
        try {
            File logFile = FilesUtils.getLatestFile(LogsUtil.LOGS_PATH);
            if (!logFile.exists()) {
                LogsUtil.warn("Log file does not exist: " + LogsUtil.LOGS_PATH);
                return;
            }
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
            LogsUtil.info("Logs attached to Allure report");
        } catch (Exception e) {
            LogsUtil.error("Failed to attach logs to Allure report: " + e.getMessage());
        }
    }

    public static void attachScreenshotToAllure(String screenshotName, String screenshotPath) {
        try {
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotPath)));
        } catch (Exception e) {
            LogsUtil.error("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }

    public static void opnAllureReportAfterExecution() {
        try {
            // First generate the report
            generateAllureReport();

            // Then serve the report if generation was successful
            serveAllureReport();

        } catch (Exception e) {
            LogsUtil.error("Failure during opening Allure report after execution: " + e.getMessage());
        }
    }

    public static void generateAllureReport() {
        try {
            LogsUtil.info("Generating Allure report...");

            // Step 1: Generate the Allure report
            ProcessBuilder generateBuilder = new ProcessBuilder("cmd.exe", "/c", "allure generate "
                    + AllureResultDirectory + "--clean -o " + AllureReportDirectory);
            generateBuilder.inheritIO();  // Optional: shows output in console
            Process generateProcess = generateBuilder.start();
            int generateExitCode = generateProcess.waitFor();
            if (generateExitCode != 0) {
                LogsUtil.error("Failed to generate Allure report. Exit code: " + generateExitCode);
                return;
            }
            LogsUtil.info("Allure report generated successfully.");
        } catch (IOException e) {
            LogsUtil.error("IOException occurred while generating Allure report: " + e.getMessage());
        } catch (Exception e) {
            LogsUtil.error("Unexpected error occurred while generating Allure report: " + e.getMessage());
        }
    }

    public static void serveAllureReport() {
        try {
            LogsUtil.info("Serving Allure report...");

            // Step 2: Serve the Allure report in a separate process (non-blocking)
            new ProcessBuilder("cmd.exe", "/c", "allure serve " + AllureResultDirectory).start();
            LogsUtil.info("Allure report is being served.");

        } catch (IOException e) {
            LogsUtil.error("IOException occurred while serving Allure report: " + e.getMessage());
        } catch (Exception e) {
            LogsUtil.error("Unexpected error occurred while serving Allure report: " + e.getMessage());
        }
    }


}




