package com.progressoft.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;


public class Calculator {

    public BigDecimal calculateMean(List<BigDecimal> data) {
        BigDecimal sum = BigDecimal.ZERO;
        if (data.isEmpty())
            throw new IllegalArgumentException();
        for (BigDecimal value : data) {
            sum = sum.add(value);
        }
        BigDecimal avg = sum.divide(BigDecimal.valueOf(data.size()), HALF_EVEN);
        return avg.setScale(2);
    }


    public BigDecimal calculateStandardDeviation(List<BigDecimal> data) {
        if (data.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal variance = calculateVariance(data);
        double sd = Math.sqrt(Double.parseDouble(String.valueOf(variance)));
        return BigDecimal.valueOf(sd).setScale(2, HALF_EVEN);

    }

    public BigDecimal calculateVariance(List<BigDecimal> data) {
        if (data.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal mean = calculateMean(data);

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal value : data) {
            BigDecimal difference = value.subtract(mean).pow(2);
            sum = sum.add(difference);
        }
        BigDecimal va = sum.divide(new BigDecimal(data.size()), HALF_EVEN);
        return va.setScale(0, HALF_EVEN).setScale(2);


    }


    public BigDecimal calculateMedian(List<BigDecimal> data) {

        if (data.isEmpty())
            throw new IllegalArgumentException();
        int n = data.size();
        List<BigDecimal> sortedCopy = new ArrayList<>(data);
        Collections.sort(sortedCopy);
        int mid = (n - 1) / 2;
        if (n % 2 == 0) {
            return (sortedCopy.get(mid).add(sortedCopy.get(mid + 1))).divide(new BigDecimal(2)).setScale(2, HALF_EVEN);
        }
        return sortedCopy.get(mid).setScale(2, HALF_EVEN);

    }


    public BigDecimal calculateMin(List<BigDecimal> data) {
        if (data.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal minimum = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            minimum = data.get(i).min(minimum);
        }
        return minimum.setScale(2, HALF_EVEN);

    }


    public BigDecimal calculateMax(List<BigDecimal> data) {
        if (data.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal maximum = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            maximum = data.get(i).max(maximum);
        }
        return maximum.setScale(2, HALF_EVEN);
    }


    public BigDecimal calculateZscore(BigDecimal el, BigDecimal mean, BigDecimal sd) {
        BigDecimal difference = new BigDecimal(String.valueOf(el.subtract(mean)));
        return difference.divide(sd, 2, HALF_EVEN);
    }


    public BigDecimal calculateMinMaxScale(BigDecimal el, BigDecimal min, BigDecimal max) {
        return el.subtract(min).divide((max.subtract(min)), 2, BigDecimal.ROUND_HALF_EVEN);
    }
}
