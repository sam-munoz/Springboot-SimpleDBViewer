package org.personal.SimpleDBViewer.Services.PresentableEntity;

public class PresentableRanking {
    private String cpuName;
    private String userName;
    private Integer ranking;

    public PresentableRanking() {}

    public PresentableRanking(String cpuName, String userName, Integer ranking) {
        this.cpuName = cpuName;
        this.userName = userName;
        this.ranking = ranking;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "PresentableRanking{" +
                "cpuName='" + cpuName + '\'' +
                ", userName='" + userName + '\'' +
                ", ranking=" + ranking +
                '}';
    }
}
