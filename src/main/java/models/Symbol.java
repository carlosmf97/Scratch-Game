package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Symbol {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    private String type;
    private Double extra;
    private String impact;

    @JsonProperty("reward_multiplier")
    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }
}