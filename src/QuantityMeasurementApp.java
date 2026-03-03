public class QuantityMeasurementApp {

    public static Length demonstrateLengthAddition(
            Length length1,
            Length length2,
            LengthUnit targetUnit) {

        return length1.add(length2, targetUnit);
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(
                demonstrateLengthAddition(l1, l2, LengthUnit.FEET)
        );

        System.out.println(
                demonstrateLengthAddition(l1, l2, LengthUnit.INCHES)
        );

        System.out.println(
                demonstrateLengthAddition(l1, l2, LengthUnit.YARDS)
        );
    }
}