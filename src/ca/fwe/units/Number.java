package ca.fwe.units;


public class Number {
	private static final String SEPARATOR = "," ;

	private double SIvalue ;
	private Unit unit ;

	public Number(double value, Unit valueUnit) {
		this.unit = valueUnit ;
		this.setValue(value) ;
	}

	public Number(double value) {
		this.unit = new Unit() ;
		this.setValue(value) ;
	}

	public Number() {
		this(Double.NaN) ;
	}

	public Number(Unit valueUnit) {
		this.unit = valueUnit ;
		this.SIvalue = Double.NaN ;
	}

	public void forceUnit(Unit newUnit) {
		this.unit = newUnit ;
	}

	public Number setUnit(Unit newUnit) throws IncomparableUnitException {
		Number newNumber = new Number(this.getSIValue()) ;
		newNumber.forceUnit(newUnit) ;
		return newNumber ;
	}

	private void setValue(double newValue) {
		this.SIvalue = newValue * this.getUnit().getConversionValue() ;
	}

	public double getValue() {
		return SIvalue / this.getUnit().getConversionValue() ;
	}

	public String toString() {
		if(!Double.isNaN(this.getValue())) {
			if(this.isInteger()) {
				return new Long(Math.round(this.getValue())).toString() ;
			} else {
				return new Float(this.getValue()).toString() ;
			}
		} else {
			return "" ;
		}
	}

	public boolean isInteger() {
		Double value = new Double(this.getValue()) ;
		long intValue = Math.round(value) ;
		if(value == intValue) {
			return true ;
		} else {
			return false ;
		}
	}

	public double getValue(Unit otherUnit) throws IncomparableUnitException {
		if(otherUnit.getDerivedUnitType() == this.unit.getDerivedUnitType()) {
			return this.getSIValue() / otherUnit.getConversionValue() ;
		} else {
			throw new IncomparableUnitException(this.unit, otherUnit) ;
		}
	}

	public double getSIValue() {
		return SIvalue ;
	}

	public Unit getUnit() {
		return unit.clone() ;
	}

	public Number getSINumber() {
		double value = this.getSIValue() ;
		Unit unit = this.getUnit().getSI() ;
		Number newNumber = new Number(value) ;
		newNumber.forceUnit(unit) ;
		return newNumber ;
	}

	public Number multiplyBy(Number otherNumber) {
		Number newNumber = new Number(this.getSIValue() * otherNumber.getSIValue()) ;
		newNumber.forceUnit(this.getUnit().multiplyBy(otherNumber.getUnit())) ;
		return newNumber ;
	}

	public Number divideBy(Number otherNumber) {
		Number newNumber = new Number(this.getSIValue() / otherNumber.getSIValue()) ;
		newNumber.forceUnit(this.getUnit().divideBy(otherNumber.getUnit())) ;
		return newNumber ;
	}

	public Number addTo(Number otherNumber) throws IncomparableUnitException {
		if(otherNumber.getUnit().getDerivedUnitType() == this.unit.getDerivedUnitType()) {
			Number newNumber = new Number(this.getSIValue() + otherNumber.getSIValue()) ;
			newNumber.forceUnit(this.getUnit()) ;
			return newNumber ;
		} else {
			throw new IncomparableUnitException(this.unit, otherNumber.getUnit()) ;
		}
	}

	public Number subtract(Number otherNumber) throws IncomparableUnitException {
		if(otherNumber.getUnit().getDerivedUnitType() == this.unit.getDerivedUnitType()) {
			Number newNumber = new Number(this.getSIValue() - otherNumber.getSIValue()) ;
			newNumber.forceUnit(this.unit) ;
			return newNumber ;
		} else {
			throw new IncomparableUnitException(this.unit, otherNumber.getUnit()) ;
		}
	}

	public Number invert() {
		Number newNumber = new Number(1.0 / this.getSIValue()) ;
		newNumber.forceUnit(this.unit.invert()) ;
		return newNumber ;
	}


	public String encode() {
		String encodedUnit = this.getUnit().encode() ;
		String value = new Double(this.getValue()).toString() ;
		return value + SEPARATOR + encodedUnit ;
	}

	public static Number valueOf(String encoded) {
		String[] parts = encoded.split(SEPARATOR) ;
		if(parts.length >= 2) {
			try {
				double value = new Double(parts[0]) ;
				Unit unit = Unit.valueOf(parts[1]) ;
				if(unit != null) {
					return new Number(value, unit) ;
				} else {
					return new Number(value) ;
				}
			} catch(NumberFormatException e) {
				return null ;
			}
		} else {
			return null ;
		}
	}

}
