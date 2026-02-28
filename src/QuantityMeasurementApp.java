/**
 * QuantityMeasurementApp – UC3: Generic Quantity Class for DRY Principle
 *
 * Refactors Feet and Inches classes into a single QuantityLength class
 * using LengthUnit enum.
 *
 * Supports:
 * - Feet equality
 * - Inches equality
 * - Feet ↔ Inches equality
 */

public class QuantityMeasurementApp {

    /*
     * LengthUnit Enum
     * Defines conversion factors relative to Feet
     */
    public enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }



    /*
     * Generic QuantityLength Class
     */
    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }


        private double toFeet() {
            return value * unit.getConversionFactor();
        }


        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj)
                return true;

            // Null or type check
            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            // Convert to common unit (Feet)
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }



    public static void main(String[] args) {

        QuantityLength q1 =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength q2 =
                new QuantityLength(12.0, LengthUnit.INCHES);

        boolean result1 = q1.equals(q2);

        System.out.println("Equal (" + result1 + ")");


        QuantityLength q3 =
                new QuantityLength(1.0, LengthUnit.INCHES);

        QuantityLength q4 =
                new QuantityLength(1.0, LengthUnit.INCHES);

        boolean result2 = q3.equals(q4);

        System.out.println("Equal (" + result2 + ")");
    }
}