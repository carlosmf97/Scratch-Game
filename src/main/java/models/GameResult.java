package models;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResult {
    private final String[][] matrix;
    private final double reward;
    private final Map<String, List<String>> appliedWinningCombinations;
    private final String appliedBonusSymbol;

    public GameResult(String[][] matrix, double reward, 
                     Map<String, List<String>> appliedWinningCombinations,
                     String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @JsonValue
    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("matrix", matrix);
        json.put("reward", reward);
        if (reward > 0) {
            if (!appliedWinningCombinations.isEmpty()) {
                json.put("applied_winning_combinations", appliedWinningCombinations);
            }
            if (appliedBonusSymbol != null) {
                json.put("applied_bonus_symbol", appliedBonusSymbol);
            }
        }
        return json;
    }
}