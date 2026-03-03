import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

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

    // ==================== ENUM DISPATCH ====================

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    // ==================== CENTRALIZED VALIDATION ====================

    private void validateArithmeticOperands(Quantity<U> other,
                                            U targetUnit,
                                            boolean targetUnitRequired) {

        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
        }

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (targetUnitRequired && targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
    }

    // ==================== CORE ARITHMETIC HELPER ====================

    private double performBaseArithmetic(Quantity<U> other,
                                         ArithmeticOperation operation) {

        double baseThis = unit.convertToBaseUnit(this.value);
        double baseOther = other.unit.convertToBaseUnit(other.value);

        return operation.compute(baseThis, baseOther);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // ==================== ADD ====================

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        this.unit.validateOperationSupport("addition");

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);

        double converted = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
    }

    // ==================== SUBTRACT ====================

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        this.unit.validateOperationSupport("subtraction");

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double converted = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
    }

    // ==================== DIVIDE ====================

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        this.unit.validateOperationSupport("division");

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    // ==================== CONVERT ====================

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(this.value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
    }

    // ==================== EQUALS ====================

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double baseThis = unit.convertToBaseUnit(this.value);
        double baseOther =
                ((Quantity<U>) other).unit.convertToBaseUnit(((Quantity<U>) other).value);

        return Math.abs(baseThis - baseOther) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundToTwoDecimals(unit.convertToBaseUnit(value)));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}