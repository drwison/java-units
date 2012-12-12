package ca.fwe.units;

public class IncomparableUnitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Unit firstUnit ;
	private Unit secondUnit ;
	
	public IncomparableUnitException(Unit unit1, Unit unit2) {
		this.fillInStackTrace() ;
		firstUnit = unit1 ;
		secondUnit = unit2 ;
	}

	public Unit getFirstUnit() {
		return firstUnit;
	}

	public Unit getSecondUnit() {
		return secondUnit;
	}
}
