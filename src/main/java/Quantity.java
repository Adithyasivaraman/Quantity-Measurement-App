import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-6;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
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

    private void validateOperand(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
        }
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    // ---------------- ADD (already existing from UC6/7) ----------------
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateOperand(other);
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseSum = this.toBase() + other.toBase();
        double result = targetUnit.convertFromBaseUnit(baseSum);

        return new Quantity<>(round(result), targetUnit);
    }

    // ---------------- UC12: SUBTRACT ----------------
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperand(other);
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseResult = this.toBase() - other.toBase();
        double converted = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(converted), targetUnit);
    }

    // ---------------- UC12: DIVIDE ----------------
    public double divide(Quantity<U> other) {
        validateOperand(other);

        double divisorBase = other.toBase();
        if (Math.abs(divisorBase) < EPSILON) {
            throw new ArithmeticException("Division by zero");
        }

        return this.toBase() / divisorBase;
    }

    // ---------------- CONVERT ----------------
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = this.toBase();
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(round(converted), targetUnit);
    }

    // ---------------- EQUALS ----------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double baseThis = this.toBase();
        double baseOther = ((Quantity<U>) other).toBase();

        return Math.abs(baseThis - baseOther) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(round(toBase()));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}