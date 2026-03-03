import java.util.Objects;

public final class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

    public Length(double value, LengthUnit unit) {
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

    public LengthUnit getUnit() {
        return unit;
    }

    // ---------- Base Conversion ----------

    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    private static double convertFromBase(double baseValue, LengthUnit targetUnit) {
        return baseValue / targetUnit.getConversionFactor();
    }

    // ---------- Equality ----------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Length)) return false;

        Length other = (Length) obj;

        double thisBase = this.convertToBaseUnit();
        double otherBase = other.convertToBaseUnit();

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    // ---------- UC6 (Implicit Target = First Operand Unit) ----------

    public Length add(Length other) {
        return add(other, this.unit);
    }

    // ---------- UC7 (Explicit Target Unit) ----------

    public Length add(Length other, LengthUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        return addAndConvert(other, targetUnit);
    }

    // ---------- Private DRY Helper ----------

    private Length addAndConvert(Length other, LengthUnit targetUnit) {

        double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();

        double converted = convertFromBase(baseSum, targetUnit);

        return new Length(converted, targetUnit);
    }

    // ---------- Conversion API ----------

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double base = convertToBaseUnit();
        double converted = convertFromBase(base, targetUnit);

        return new Length(converted, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}