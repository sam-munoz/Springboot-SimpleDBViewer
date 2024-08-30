package org.personal.SimpleDBViewer.Services.PresentableEntity;

public class PresentableSummary {
    private String cpuName;
    private Integer rankSum;
    private Long count;
    private Double average;

    public PresentableSummary() {}

    public PresentableSummary(String cpuName, Integer rankSum, Long count) {
        this.cpuName = cpuName;
        this.rankSum = rankSum;
        this.count = count;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Integer getRankSum() {
        return rankSum;
    }

    public void setRankSum(Integer rankSum) {
        this.rankSum = rankSum;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getAverage() {
        return this.average;
    }

    public Double computeAverage() {
        if(this.rankSum == null || this.count == null) return null;
        if(this.count == 0) return null;
        this.average = this.rankSum.doubleValue() / this.count.floatValue();
        return this.average;
    }

    @Override
    public String toString() {
        return "PresentableSummary{" +
                "cpuName='" + cpuName + '\'' +
                ", rankSum=" + rankSum +
                ", count=" + count +
                '}';
    }
}
