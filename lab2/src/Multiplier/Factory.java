package Multiplier;

import Multiplier.Sequential.SequentialMultiplier;
import Multiplier.Striped.StripedMultiplier;
import Multiplier.Fox.FoxMultiplier;

public class Factory {
    public static IMultiplier getMultiplier(String multiplierType, int threads) {
        switch (multiplierType) {
            case "sequential":
                return new SequentialMultiplier();
            case "striped":
                return new StripedMultiplier(threads);
            case "fox":
                return new FoxMultiplier(10, threads);
            default:
                return null;
        }
    }
}
