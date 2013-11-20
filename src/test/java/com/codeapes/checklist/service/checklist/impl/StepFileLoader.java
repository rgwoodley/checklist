package com.codeapes.checklist.service.checklist.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistStatus;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.Step;
import com.codeapes.checklist.domain.StepExecutionInfo;
import com.codeapes.checklist.domain.StepStatus;
import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.DateUtil;
import com.codeapes.checklist.util.constants.DateTimeConstants;

public final class StepFileLoader {

    private static final AppLogger logger = new AppLogger(StepFileLoader.class); // NOSONAR
    private static final StepFileLoader instance = new StepFileLoader();

    private StepFileLoader() {
        super();
    }

    public static StepFileLoader getInstance() {
        return instance;
    }

    public StepData loadStepFile(String filename) {
        final StepData stepData = new StepData();
        final Map<String, Step> stepMap = new HashMap<String, Step>();
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final InputStream is = classLoader.getResourceAsStream(filename);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final Checklist checklist = new Checklist(ChecklistType.EXECUTABLE);
        checklist.setObjectKey(Long.parseLong("1"));
        stepData.setChecklist(checklist);
        try {
            String line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                if (line.startsWith("**")) {
                    loadExpectedValues(stepData, reader);
                    break;
                } else {
                    final Step step = loadStep(stepData, line, stepMap);
                    stepData.getSteps().add(step);
                }
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            throw new ChecklistException("Unable to load step file: " + filename, ioe);
        } finally {
            try {
                reader.close();
            } catch (IOException ioe2) {
                throw new ChecklistException("Could not close step file: " + filename, ioe2);
            }
        }
        return stepData;
    }

    private Step loadStep(StepData data, String line, Map<String, Step> stepMap) {
        final String[] stepParts = line.split(",");
        if (stepParts.length < 6) {
            throw new ChecklistException("Unable to load step data.  Line in file is: %s", line);
        }

        final Step step = createExecutionStep(stepParts, stepMap, data.getChecklist());
        stepMap.put(step.getName(), step);
        return step;
    }

    private Step createExecutionStep(String[] stepParts, Map<String, Step> stepMap, Checklist checklist) {
        int index = 0;     
        final Long objectKey = Long.parseLong(stepParts[index++]);
        final String name = stepParts[index++].trim();
        final String preconditions = stepParts[index++].trim();
        final String status = stepParts[index++].trim();
        final String execStart = stepParts[index++].trim();
        final String execEnd = stepParts[index++].trim();
        final String expectedDurationMin = stepParts[index++].trim();

        logger.debug("Loading step: %s", name);

        final Step step = new Step();
        step.setName(name);
        step.setObjectKey(objectKey);
        step.setStatus(StepStatus.valueOf(status));
        step.setExpectedDurationInMinutes(Integer.parseInt(expectedDurationMin));
        step.setChecklist(checklist);
        final StepExecutionInfo executionInfo = new StepExecutionInfo();
        if (!StringUtils.isBlank(execStart)) {
            executionInfo.setExecutionStart(DateUtil
                .createTimestampFromString(execStart, DateTimeConstants.FULL_DATE_TIME_FORMAT));
        }
        if (!StringUtils.isBlank(execEnd)) {
            executionInfo.setExecutionEnd(DateUtil
                .createTimestampFromString(execEnd, DateTimeConstants.FULL_DATE_TIME_FORMAT));
        }
        step.setExecutionInfo(executionInfo);
        executionInfo.setStep(step);
        checklist.addStep(step);

        loadPreconditions(step, preconditions, stepMap);
        return step;
    }

    private void loadPreconditions(Step step, String preconditions, Map<String, Step> stepMap) {
        if (!StringUtils.isBlank(preconditions)) {
            logger.debug("  preconditions: %s", preconditions);
            final String[] preconditionNames = preconditions.split("\\|");
            for (String preconditionName : preconditionNames) {
                logger.debug("  loading precondition: %s", preconditionName);
                final Step preconditionStep = stepMap.get(preconditionName);
                if (preconditionStep == null) {
                    throw new ChecklistException("Precondition %s is not yet defined.", preconditionName);
                }
                step.addPreCondition(preconditionStep);
            }
        }
    }

    private void loadExpectedValues(StepData data, BufferedReader reader) throws IOException {
        final String threshold = stripValue(reader.readLine());
        final String totalTime = stripValue(reader.readLine());
        final String timeRemaining = stripValue(reader.readLine());
        final String expectedStatus = stripValue(reader.readLine());

        data.setThresholdPercent(Double.parseDouble(threshold));
        data.setExpectedTotalTime(Integer.parseInt(totalTime));
        data.setExpectedTimeRemaining(Integer.parseInt(timeRemaining));
        data.setExpectedStatus(ChecklistStatus.valueOf(expectedStatus));
    }

    private String stripValue(String line) {
        final int equalSignIndex = line.indexOf("=");
        return line.substring(equalSignIndex + 1, line.length()).trim();
    }

}
