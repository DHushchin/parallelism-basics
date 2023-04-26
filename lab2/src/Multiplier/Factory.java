package Multiplier;

import Multiplier.Sequential.SequentialMultiplier;
import Multiplier.Striped.StripedMultiplier;
import Multiplier.Fox.FoxMultiplier;

public class Factory {
    public static IMultiplier getMultiplier(String multiplierType) {
        switch (multiplierType) {
            case "sequential":
                return new SequentialMultiplier();
            case "striped":
                return new StripedMultiplier();
            case "fox":
                return new FoxMultiplier(50);
            default:
                return null;
        }
    }
}
