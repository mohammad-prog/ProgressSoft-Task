package com.progressoft.tools;

import java.math.BigDecimal;

public class ScoringSummaryImpl implements ScoringSummary {
    private BigDecimal mean;
    private BigDecimal standardDeviation;
    private BigDecimal variance;
    private BigDecimal median;
    private BigDecimal min;
    private BigDecimal max;

    public ScoringSummaryImpl(BigDecimal mean, BigDecimal standardDeviation, BigDecimal variance, BigDecimal median, BigDecimal min, BigDecimal max) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.variance = variance;
        this.median = median;
        this.min = min;
        this.max = max;
    }

    @Override
    public BigDecimal mean() {
        return mean;
    }

    @Override
    public BigDecimal standardDeviation() {
        return standardDeviation;
    }

    @Override
    public BigDecimal variance() {
        return variance;
    }

    @Override
    public BigDecimal median() {
        return median;
    }

    @Override
    public BigDecimal min() {
        return min;
    }

    @Override
    public BigDecimal max() {
        return max;
    }
}
