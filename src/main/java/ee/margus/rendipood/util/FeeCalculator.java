package ee.margus.rendipood.util;

import ee.margus.rendipood.entity.FilmType;

public class FeeCalculator {
    private static final int BASIC_PRICE = 3;
    private static final int PREMIUM_PRICE = 4;

    public static double initialFee(FilmType filmType, int days){
        if(days == 0) throw new RuntimeException("Days cannot be zero!");
        return switch(filmType){
            case NEW -> PREMIUM_PRICE * days;
            case REGULAR -> BASIC_PRICE + BASIC_PRICE * Math.max(0, days - 3);
            case OLD -> BASIC_PRICE + BASIC_PRICE * Math.max(0, days - 5);
        };
    }

    public static double lateFee(FilmType filmType, int days){
        return switch(filmType){
            case NEW -> PREMIUM_PRICE * days;
            case REGULAR,
                 OLD -> BASIC_PRICE * days;
        };
    }
}
