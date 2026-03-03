import java.util.Objects;

public final class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-6;

    public Quantity(double value, U unit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // ---------------- Conversion ----------------

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(round(converted), targetUnit);
    }

    // ---------------- Addition ----------------

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double baseSum =
                this.unit.convertToBaseUnit(this.value)
                        + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(baseSum);

        return new Quantity<>(round(result), targetUnit);
    }

    // ---------------- Equality ----------------

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || !(obj instanceof Quantity<?>)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        // Prevent cross-category comparison
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.getClass(), unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}