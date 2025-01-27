package models;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class BonusSymbolProbability {
    private Map<String, Integer> symbols;
}