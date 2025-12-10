package ee.margus.rendipood.util;

import ee.margus.rendipood.entity.FilmType;

public class FeeCalculator {
    private static final int BASIC_PRICE = 3;
    private static final int PREMIUM_PRICE = 4;

    public static double initialFee(FilmType filmType, int days){
        double sum = 0;
        switch(filmType){
            case NEW -> sum += PREMIUM_PRICE * days;
            case REGULAR -> sum += BASIC_PRICE + BASIC_PRICE * Math.max(0, days - 3);
            case OLD -> sum += BASIC_PRICE + BASIC_PRICE * Math.max(0, days - 5);
        }
        return sum;
    }

    public static double lateFee(FilmType filmType, int initialDays, int actualDays){
        int days = Math.max(0, actualDays - initialDays);
        double sum = 0;
        switch(filmType){
            case NEW -> sum += PREMIUM_PRICE * days;
            case REGULAR,
                 OLD -> sum += BASIC_PRICE * days;
        }
        return sum;
    }
}
