package com.sharearide.research.jnapor.osprojectqueue.model;

import java.io.Serializable;

/**
 * Created by jnapor on 8/28/2016.
 */
public class ProcessModel implements Serializable{
    private int processId;
    private int arrivalTime;
    private int cpuBurst;

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public ProcessModel(int processId, int arrivalTime, int cpuBurst) {

        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
    }
}
