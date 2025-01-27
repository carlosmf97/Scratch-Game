package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Probabilities {
    @JsonProperty("standard_symbols")
    private List<CellProbability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private BonusSymbolProbability bonusSymbols;

}