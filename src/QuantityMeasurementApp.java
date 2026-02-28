public class QuantityMeasurementApp {

    /*
     * UC2: Feet and Inches Measurement Equality
     *
     * This class checks equality of measurements in Feet and Inches.
     * Feet and Inches are treated separately.
     */

    // Inner class for Feet measurement
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }


    // Inner class for Inches measurement
    public static class Inches {

        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }


    // Static method for Feet equality check
    public static void demonstrateFeetEquality() {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Feet Equality: " + f1.equals(f2));
    }


    // Static method for Inches equality check
    public static void demonstrateInchesEquality() {

        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        System.out.println("Inches Equality: " + i1.equals(i2));
    }


    public static void main(String[] args) {

        demonstrateFeetEquality();
        demonstrateInchesEquality();

    }
}