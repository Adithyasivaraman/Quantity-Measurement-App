import java.util.Objects;

public final class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {

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

    public WeightUnit getUnit() {
        return unit;
    }

    // ---------------- Conversion ----------------

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(converted, targetUnit);
    }

    // ---------------- Addition (Implicit Target) ----------------

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // ---------------- Addition (Explicit Target) ----------------

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double baseSum =
                this.unit.convertToBaseUnit(this.value)
                        + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(baseSum);

        return new QuantityWeight(result, targetUnit);
    }

    // ---------------- Equality ----------------

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}