package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final GameConfig config;
    private final MatrixGenerator matrixGenerator;
    private final WinningCombinationFinder combinationFinder;

    public Game(GameConfig config) {
        this.config = config;
        this.matrixGenerator = new MatrixGenerator(config);
        this.combinationFinder = new WinningCombinationFinder(config);
    }

    public GameResult play(double betAmount) {
        String[][] matrix = matrixGenerator.generateMatrix();
        Map<String, List<String>> winningCombinations = combinationFinder.findWinningCombinations(matrix);
        double reward = calculateReward(betAmount, matrix, winningCombinations);
        String appliedBonus = findAppliedBonus(matrix);

        return new GameResult(matrix, reward, winningCombinations, appliedBonus);
    }

    private double calculateReward(double betAmount, String[][] matrix,
                                 Map<String, List<String>> winningCombinations) {
        if (winningCombinations.isEmpty()) {
            return 0.0;
        }

        double totalReward = 0.0;

        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();
            
            Symbol symbolConfig = config.getSymbols().get(symbol);
            if (symbolConfig == null) continue;
            
            double symbolMultiplier = symbolConfig.getRewardMultiplier();
            Map<String, Double> groupMultipliers = new HashMap<>();
            
            for (String combinationName : combinations) {
                WinCombination combination = config.getWinCombinations().get(combinationName);
                if (combination != null) {
                    String group = combination.getGroup();
                    double multiplier = combination.getRewardMultiplier();
                    groupMultipliers.merge(group, multiplier, Math::max);
                }
            }
            
            double totalCombinationMultiplier = groupMultipliers.values()
                .stream()
                .reduce(1.0, (a, b) -> a * b);
            
            totalReward += betAmount * symbolMultiplier * totalCombinationMultiplier;
        }

        String bonusSymbol = findAppliedBonus(matrix);
        if (bonusSymbol != null && totalReward > 0) {
            Symbol bonus = config.getSymbols().get(bonusSymbol);
            if ("multiply_reward".equals(bonus.getImpact())) {
                totalReward *= bonus.getRewardMultiplier();
            } else if ("extra_bonus".equals(bonus.getImpact())) {
                totalReward += bonus.getExtra();
            }
        }

        return totalReward;
    }

    private String findAppliedBonus(String[][] matrix) {
        for (String[] row : matrix) {
            for (String symbol : row) {
                Symbol symbolConfig = config.getSymbols().get(symbol);
                if (symbolConfig != null && "bonus".equals(symbolConfig.getType())) {
                    return symbol;
                }
            }
        }
        return null;
    }
}