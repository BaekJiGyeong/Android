package com.ktds.sems;

import java.util.List;
import java.util.Map;

/**
 * Created by 206-022 on 2016-07-05.
 */
public class EducationAttend {

    private Education eduInfo;
    private Map<String, List<String>> attends;

    public Education getEduInfo() {
        return eduInfo;
    }

    public void setEduInfo(Education eduInfo) {
        this.eduInfo = eduInfo;
    }

    public Map<String, List<String>> getAttends() {
        return attends;
    }

    public void setAttends(Map<String, List<String>> attends) {
        this.attends = attends;
    }
}
