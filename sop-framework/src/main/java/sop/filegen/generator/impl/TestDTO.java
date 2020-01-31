package sop.filegen.generator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: LCF
 * @Date: 2020/1/8 18:01
 * @Package: sop.filegen.generator.impl
 */

public class TestDTO {
    private String name;
    private String procUnit;
    private String schmType;
    private String serNum;
    private String grantType;
    private String appealIndicator;
    private String caseStatus;
    private String processingFlowStage;
    private String pendingAction;
    private String caseNature;
    private List<TestDTO> dtoList = new ArrayList<TestDTO>();
    private List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcUnit() {
        return procUnit;
    }

    public void setProcUnit(String procUnit) {
        this.procUnit = procUnit;
    }

    public String getSchmType() {
        return schmType;
    }

    public void setSchmType(String schmType) {
        this.schmType = schmType;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAppealIndicator() {
        return appealIndicator;
    }

    public void setAppealIndicator(String appealIndicator) {
        this.appealIndicator = appealIndicator;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getProcessingFlowStage() {
        return processingFlowStage;
    }

    public void setProcessingFlowStage(String processingFlowStage) {
        this.processingFlowStage = processingFlowStage;
    }

    public String getPendingAction() {
        return pendingAction;
    }

    public void setPendingAction(String pendingAction) {
        this.pendingAction = pendingAction;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public List<TestDTO> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<TestDTO> dtoList) {
        this.dtoList = dtoList;
    }

    public List<Map<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }
}
