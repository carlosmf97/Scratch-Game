package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningCombinationFinder {
    private final GameConfig config;

    public WinningCombinationFinder(GameConfig config) {
        this.config = config;
    }

    public Map<String, List<String>> findWinningCombinations(String[][] matrix) {
        Map<String, List<String>> result = new HashMap<>();
        checkSameSymbolCombinations(matrix, result);
        checkLinearCombinations(matrix, result);
        return result;
    }

    private void checkSameSymbolCombinations(String[][] matrix, Map<String, List<String>> result) {
        Map<String, Integer> symbolCounts = new HashMap<>();
        
        for (String[] row : matrix) {
            for (String symbol : row) {
                if (config.getSymbols().containsKey(symbol) && 
                    "standard".equals(config.getSymbols().get(symbol).getType())) {
                    symbolCounts.merge(symbol, 1, Integer::sum);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();
            
            config.getWinCombinations().forEach((name, combination) -> {
                if ("same_symbols".equals(combination.getWhen()) && count >= combination.getCount()) {
                    result.computeIfAbsent(symbol, k -> new ArrayList<>()).add(name);
                }
            });
        }
    }

    private void checkLinearCombinations(String[][] matrix, Map<String, List<String>> result) {
        config.getWinCombinations().forEach((name, combination) -> {
            if ("linear_symbols".equals(combination.getWhen())) {
                for (List<String> area : combination.getCoveredAreas()) {
                    checkArea(matrix, area, name, result);
                }
            }
        });
    }

    private void checkArea(String[][] matrix, List<String> area, String combinationName, 
                         Map<String, List<String>> result) {
        String firstSymbol = null;
        boolean isLine = true;

        for (String position : area) {
            String[] coords = position.split(":");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);
            
            String currentSymbol = matrix[row][col];
            if (config.getSymbols().containsKey(currentSymbol) && 
                "standard".equals(config.getSymbols().get(currentSymbol).getType())) {
                if (firstSymbol == null) {
                    firstSymbol = currentSymbol;
                } else if (!currentSymbol.equals(firstSymbol)) {
                    isLine = false;
                    break;
                }
            }
        }

        if (isLine && firstSymbol != null) {
            result.computeIfAbsent(firstSymbol, k -> new ArrayList<>()).add(combinationName);
        }
    }
}