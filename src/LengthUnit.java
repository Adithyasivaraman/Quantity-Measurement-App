public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    public double getConversionFactor() {
        return conversionFactorToFeet;
    }

    // Convert this unit value TO base (feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToFeet;
    }

    // Convert base (feet) TO this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToFeet;
    }
}