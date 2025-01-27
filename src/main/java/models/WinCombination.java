package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WinCombination {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    
    private String when;
    private int count;
    private String group;
    
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public int getCount() {
        return count;
    }

    public String getGroup() {
        return group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }

    @JsonProperty("reward_multiplier")
    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @JsonProperty("covered_areas")
    public void setCoveredAreas(List<List<String>> coveredAreas) {
        this.coveredAreas = coveredAreas;
    }
}