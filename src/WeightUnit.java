public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactorToKilogram;

    WeightUnit(double conversionFactorToKilogram) {
        this.conversionFactorToKilogram = conversionFactorToKilogram;
    }

    public double getConversionFactor() {
        return conversionFactorToKilogram;
    }

    // Convert this unit TO base (kg)
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToKilogram;
    }

    // Convert base (kg) TO this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToKilogram;
    }
}