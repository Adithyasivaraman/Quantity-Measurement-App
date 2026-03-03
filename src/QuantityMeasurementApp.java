public class QuantityMeasurementApp {

    public static void demonstrateAddition(
            QuantityLength l1,
            QuantityLength l2) {

        QuantityLength result = l1.add(l2);

        System.out.println(
                "add(" + l1 + ", "
                        + l2 + ") = "
                        + result);
    }

    public static void main(String[] args) {

        demonstrateAddition(
                new QuantityLength(
                        1.0,
                        LengthUnit.FEET),

                new QuantityLength(
                        2.0,
                        LengthUnit.FEET));

        demonstrateAddition(
                new QuantityLength(
                        1.0,
                        LengthUnit.FEET),

                new QuantityLength(
                        12.0,
                        LengthUnit.INCHES));

        demonstrateAddition(
                new QuantityLength(
                        12.0,
                        LengthUnit.INCHES),

                new QuantityLength(
                        1.0,
                        LengthUnit.FEET));

        demonstrateAddition(
                new QuantityLength(
                        1.0,
                        LengthUnit.YARDS),

                new QuantityLength(
                        3.0,
                        LengthUnit.FEET));

        demonstrateAddition(
                new QuantityLength(
                        2.54,
                        LengthUnit.CENTIMETERS),

                new QuantityLength(
                        1.0,
                        LengthUnit.INCHES));
    }
}