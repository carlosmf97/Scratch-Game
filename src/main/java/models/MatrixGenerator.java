package models;

import java.util.Map;
import java.util.Random;

public class MatrixGenerator {
    private final GameConfig config;
    private final Random random;

    public MatrixGenerator(GameConfig config) {
        this.config = config;
        this.random = new Random();
    }

    public String[][] generateMatrix() {
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        fillStandardSymbols(matrix);
        addBonusSymbol(matrix);
        return matrix;
    }

    private void fillStandardSymbols(String[][] matrix) {
        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                matrix[row][col] = selectRandomSymbol(row, col);
            }
        }
    }

    private void addBonusSymbol(String[][] matrix) {
        Map<String, Integer> bonusProbs = config.getProbabilities().getBonusSymbols().getSymbols();
        String bonusSymbol = selectRandomSymbolFromProbabilities(bonusProbs);
        int row = random.nextInt(config.getRows());
        int col = random.nextInt(config.getColumns());
        matrix[row][col] = bonusSymbol;
    }

    private String selectRandomSymbol(int row, int col) {
        Map<String, Integer> symbolProbs = config.getProbabilities()
            .getStandardSymbols()
            .stream()
            .filter(p -> p.getRow() == row && p.getColumn() == col)
            .findFirst()
            .map(p -> p.getSymbols())
            .orElseThrow(() -> new RuntimeException("No probabilities found for position " + row + ":" + col));
        
        return selectRandomSymbolFromProbabilities(symbolProbs);
    }

    private String selectRandomSymbolFromProbabilities(Map<String, Integer> symbolProbs) {
        int totalWeight = symbolProbs.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);
        
        int currentWeight = 0;
        for (Map.Entry<String, Integer> entry : symbolProbs.entrySet()) {
            currentWeight += entry.getValue();
            if (randomValue < currentWeight) {
                return entry.getKey();
            }
        }
        
        return symbolProbs.keySet().iterator().next();
    }
}