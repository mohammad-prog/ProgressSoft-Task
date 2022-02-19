package com.progressoft.tools;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizerImplementation implements Normalizer {
    private final FileParser parser;
    private final Calculator calculator;
    private final FileExporter fileExporter;

    public NormalizerImplementation(FileParser parser, Calculator calculator, FileExporter fileExporter) {
        this.parser = parser;
        this.calculator = calculator;
        this.fileExporter = fileExporter;
    }

    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {

        checkArgument(csvPath, destPath, colToStandardize);
        List<String[]> parseList = parser.parse(csvPath);
        checkColToStandarize(colToStandardize, parseList);
        int index = Arrays.asList(parseList.get(0)).indexOf(colToStandardize);
        List<BigDecimal> dataCol = getColToNrmlize(parseList, index);
        ScoringSummary scoringSummary = generateScoringSummary(dataCol);
        List<BigDecimal> zScores = new ArrayList<>();
        List<String> records = new ArrayList<>(Arrays.asList(parseList.get(0)));
        String element = colToStandardize + "_z";
        records.add(index + 1, element);
        fileExporter.exportRecord(records, destPath);
        for (BigDecimal bigDecimal : dataCol) {
            zScores.add(calculator.calculateZscore(bigDecimal, scoringSummary.mean(), scoringSummary.standardDeviation()));

        }
        exportData(destPath, parseList, index, zScores);
        return scoringSummary;
    }

    private void checkColToStandarize(String colToStandardize, List<String[]> parseList) {
        if (!Arrays.asList(parseList.get(0)).contains(colToStandardize)) {
            throw new IllegalArgumentException("column " + colToStandardize + " not found");
        }
    }

    private void checkArgument(Path csvPath, Path destPath, String colToStandardize) {
        if (csvPath == null || !csvPath.toFile().exists())
            throw new IllegalArgumentException("source file not found");
        if (destPath == null)
            throw new IllegalArgumentException("dest path not found");
        if (colToStandardize == null)
            throw new IllegalArgumentException("colToStandardize is null");
    }

    private void checkColToNormalize(String colToNormalize, List<String[]> parseList) {
        if (!Arrays.asList(parseList.get(0)).contains(colToNormalize)) {
            throw new IllegalArgumentException("column " + colToNormalize + " not found");
        }
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        checkArgument(csvPath, destPath, colToNormalize);
        List<String[]> parseList = parser.parse(csvPath);
        checkColToNormalize(colToNormalize, parseList);
        int index = Arrays.asList(parseList.get(0)).indexOf(colToNormalize);
        List<BigDecimal> dataCol = getColToNrmlize(parseList, index);
        ScoringSummary scoringSummary = generateScoringSummary(dataCol);
        List<BigDecimal> minMax = new ArrayList<>();
        List<String> records = new ArrayList<>(Arrays.asList(parseList.get(0)));
        String element = colToNormalize + "_mm";
        records.add(index + 1, element);
        fileExporter.exportRecord(records, destPath);
        for (BigDecimal bigDecimal : dataCol) {
            minMax.add(calculator.calculateMinMaxScale(bigDecimal, scoringSummary.min(), scoringSummary.max()));
        }
        exportData(destPath, parseList, index, minMax);


        return scoringSummary;
    }

    private List<BigDecimal> getColToNrmlize(List<String[]> parseList, int index) {
        List<BigDecimal> dataCol = new ArrayList<>();
        for (int i = 1; i < parseList.size(); i++) {
            dataCol.add(new BigDecimal(parseList.get(i)[index]));
        }
        return dataCol;
    }

    private void exportData(Path destPath, List<String[]> parseList, int index, List<BigDecimal> minMax) {
        for (int i = 1; i < parseList.size(); i++) {
            List<String> list = new ArrayList<>(Arrays.asList(parseList.get(i)));
            list.add(index + 1, String.valueOf(minMax.get(i - 1)));
            fileExporter.exportRecord(list, destPath);
        }
    }

    private ScoringSummary generateScoringSummary(List<BigDecimal> dataCol) {
        BigDecimal mean = calculator.calculateMean(dataCol);
        BigDecimal min = calculator.calculateMin(dataCol);
        BigDecimal max = calculator.calculateMax(dataCol);
        BigDecimal standardDeviation = calculator.calculateStandardDeviation(dataCol);
        BigDecimal median = calculator.calculateMedian(dataCol);
        BigDecimal variance = calculator.calculateVariance(dataCol);
        return new ScoringSummaryImpl(mean, standardDeviation, variance, median, min, max);

    }
}

