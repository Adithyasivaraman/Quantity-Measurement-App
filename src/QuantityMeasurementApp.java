/**
 * QuantityMeasurementApp – UC1: Feet measurement equality
 *
 * This class is responsible for checking the equality of two numerical values
 * measured in feet in the Quantity Measurement Application.
 */

public class QuantityMeasurementApp {

    // Inner class to represent Feet measurement
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        /**
         * Override equals() method to compare two Feet objects based on value
         */
        @Override
        public boolean equals(Object obj) {

            // Reference check
            if (this == obj)
                return true;

            // Null or type check
            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            // Double comparison
            return Double.compare(this.value, other.value) == 0;
        }
    }


    // Main method to demonstrate equality
    public static void main(String[] args) {

        Feet value1 = new Feet(1.0);
        Feet value2 = new Feet(1.0);

        boolean result = value1.equals(value2);

        System.out.println("Equal (" + result + ")");
    }
}