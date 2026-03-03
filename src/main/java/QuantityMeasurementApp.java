public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<LengthUnit> length1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println("Subtract: " + length1.subtract(length2));
        System.out.println("Divide: " + length1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

        Quantity<WeightUnit> weight1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weight2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println("Subtract Weight: " + weight1.subtract(weight2));
        System.out.println("Divide Weight: " + weight1.divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)));

        Quantity<VolumeUnit> volume1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println("Subtract Volume: " + volume1.subtract(volume2));
        System.out.println("Divide Volume: " + volume1.divide(new Quantity<>(10.0, VolumeUnit.LITRE)));
    }
}