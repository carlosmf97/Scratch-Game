package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CellProbability {
    private int column;
    private int row;
    private Map<String, Integer> symbols;
}