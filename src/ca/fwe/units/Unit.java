package ca.fwe.units;

import java.util.ArrayList;

public class Unit extends UnitAlias implements Cloneable {

	private static final String UNIT_SEPARATOR = "-" ;
	private static final String FRACTION_SEPARATOR = "/" ;
	
	private ArrayList<BaseUnit> numerator ;
	private ArrayList<BaseUnit> denominator ;

	public Unit() {
		numerator = new ArrayList<BaseUnit>() ;
		denominator = new ArrayList<BaseUnit>() ;
	}
	
	public Unit(String name, String shortName, Unit baseUnit, double conversion) {
		this() ;
		numerator.addAll(baseUnit.getNumerator()) ;
		denominator.addAll(baseUnit.getDenominator()) ;
		numerator.add(new BaseUnit(conversion)) ;
		this.setName(name) ;
		this.setShort(shortName) ;
	}

	public Unit(BaseUnit unit) {
		this() ;
		numerator.add(unit) ;
		this.setName(unit.getName()) ;
	}

	public Unit(ArrayList<BaseUnit> numeratorUnits, ArrayList<BaseUnit> denominatorUnits) {
		this() ;
		numerator.addAll(numeratorUnits) ;
		denominator.addAll(denominatorUnits) ;
		this.simplify() ;
	}

	
	//below used to define in Units
	public Unit(BaseUnit[] numeratorUnits, BaseUnit[] denomUnits) {
		this() ;
		for(int i=0; i<numeratorUnits.length; i++) {
			numerator.add(numeratorUnits[i]) ;
		}
		for(int i=0; i<denomUnits.length; i++) {
			denominator.add(denomUnits[i]) ;
		}
		this.simplify() ;
	}

	public Unit(BaseUnit[] numeratorUnits) {
		this(numeratorUnits, new BaseUnit[] {}) ;
	}
	
	public Unit(String name, String shortName, BaseUnit unit) {
		this(unit) ;
		this.setName(name) ;
		this.setShort(shortName) ;
	}
	
	public Unit(String name, String shortName, BaseUnit[] numeratorUnits, BaseUnit[] denomUnits) {
		this(numeratorUnits, denomUnits) ;
		this.setName(name) ;
		this.setShort(shortName) ;
	}
	
	public Unit(String name, String shortName, BaseUnit[] numeratorUnits) {
		this(numeratorUnits) ;
		this.setName(name) ;
		this.setShort(shortName) ;
	}
	
	public ArrayList<BaseUnit> getNumerator() {
		return new ArrayList<BaseUnit>(numerator) ;
	}

	public ArrayList<BaseUnit> getDenominator() {
		return new ArrayList<BaseUnit>(denominator) ;
	}

	public double getConversionValue() {
		double value = 1 ;
		for(int i=0; i<numerator.size(); i++) {
			value *= numerator.get(i).getBaseUnitRatio() ;
		}
		for(int i=0; i<denominator.size(); i++) {
			value /= denominator.get(i).getBaseUnitRatio() ;
		}

		return value ;
	}

	public double getDerivedUnitType() {
		double value = 1.0 ;
		for(int i=0; i<numerator.size(); i++) {
			value *= (double)numerator.get(i).getUnitType() ;
		}
		for(int i=0; i<denominator.size(); i++) {
			value /= (double)denominator.get(i).getUnitType() ;
		}

		return value ;
	}

	private void simplify() {
		double ratio = this.getRatio() ;
		this.stripRatiosInSitu() ;
		if(ratio != 1) {
			numerator.add(new BaseUnit(ratio)) ;
		}
		this.simplifyUnits() ;
	}
	
	private void simplifyUnits() {
		
		for(int i=0; i<numerator.size(); i++) {
			if(denominator.contains(numerator.get(i))) {
				denominator.remove(numerator.get(i)) ;
				numerator.remove(i) ;
				this.simplifyUnits() ;
			}
		}
	}
	
	public boolean containsRatio() {
		if(this.stripRatios().equals(this)) {
			return false ;
		} else {
			return true ;
		}
	}
	
	public double getRatio() {
		double ratioValue = 1 ;
		for(int i=0; i<numerator.size(); i++) {
			if(numerator.get(i).getUnitType() == BaseUnit.RATIO.getUnitType()) {
				ratioValue *= numerator.get(i).getBaseUnitRatio() ;
			}
		}
		for(int i=0; i<denominator.size(); i++) {
			if(denominator.get(i).getUnitType() == BaseUnit.RATIO.getUnitType()) {
				ratioValue /= denominator.get(i).getBaseUnitRatio() ;
			}
		}
		return ratioValue ;
	}
	
	public boolean isRatio() {
		if(numerator.size() == 1 && numerator.get(0).getUnitType() == BaseUnit.RATIO.getUnitType()) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public Unit stripRatios() {
		ArrayList<BaseUnit> newNum = new ArrayList<BaseUnit>() ;
		ArrayList<BaseUnit> newDen = new ArrayList<BaseUnit>() ;
		
		for(int i=0; i<numerator.size(); i++) {
			if(numerator.get(i).getUnitType() != BaseUnit.RATIO.getUnitType()) {
				newNum.add(numerator.get(i)) ;
			}
		}
		for(int i=0; i<denominator.size(); i++) {
			if(denominator.get(i).getUnitType() != BaseUnit.RATIO.getUnitType()) {
				newDen.add(denominator.get(i)) ;
			}
		}
		return new Unit(newNum, newDen) ;
	}
	
	private void stripRatiosInSitu() {
		ArrayList<BaseUnit> newNum = new ArrayList<BaseUnit>() ;
		ArrayList<BaseUnit> newDen = new ArrayList<BaseUnit>() ;
		for(int i=0; i<numerator.size(); i++) {
			if(numerator.get(i).getUnitType() != BaseUnit.RATIO.getUnitType()) {
				newNum.add(numerator.get(i)) ;
			}
		}
		for(int i=0; i<denominator.size(); i++) {
			if(denominator.get(i).getUnitType() != BaseUnit.RATIO.getUnitType()) {
				newDen.add(denominator.get(i)) ;
			}
		}
		numerator = newNum ;
		denominator = newDen ;
	}
	
	public boolean equals(Object otherObject) {
		if(otherObject instanceof Unit) {
			Unit otherUnit = (Unit)otherObject ;
		if(otherUnit.getConversionValue() == this.getConversionValue() &&
				this.getDerivedUnitType() == otherUnit.getDerivedUnitType()) {
			return true ;
		} else {
			return false ;
		}
		} else {
			return false ;
		}
	}


	public Unit getSI() {
		ArrayList<BaseUnit> newNum = new ArrayList<BaseUnit>() ;
		ArrayList<BaseUnit> newDen = new ArrayList<BaseUnit>() ;
		for(int i=0; i<numerator.size(); i++) {
			newNum.add(getSIUnit(numerator.get(i).getUnitType())) ;
		}
		for(int i=0; i<denominator.size(); i++) {
			newDen.add(getSIUnit(denominator.get(i).getUnitType())) ;
		}
		return new Unit(newNum, newDen) ;
	}
	
	public Unit invert() {
		return new Unit(denominator, numerator) ;
	}

	public Unit multiplyBy(Unit otherUnit) {
		ArrayList<BaseUnit> newNumerator = new ArrayList<BaseUnit>(numerator) ;
		ArrayList<BaseUnit> newDenominator = new ArrayList<BaseUnit>(denominator) ;

		newNumerator.addAll(otherUnit.getNumerator()) ;
		newDenominator.addAll(otherUnit.getDenominator()) ;

		Unit newUnit = new Unit(newNumerator, newDenominator) ;

		return newUnit ;
	}

	public Unit divideBy(Unit otherUnit) {
		return multiplyBy(otherUnit.invert()) ;
	}

	public Unit raiseToPower(int number) {
		if(number != 0) {
			ArrayList<BaseUnit> newNumerator = new ArrayList<BaseUnit>(numerator) ;
			ArrayList<BaseUnit> newDenominator = new ArrayList<BaseUnit>(denominator) ;
			for(int i=1; i<=number; i++) {
				newNumerator.addAll(newNumerator) ;
				newDenominator.addAll(newDenominator) ;
			}
			if(number > 0) {
				return new Unit(newNumerator, newDenominator) ;
			} else {
				return new Unit(newDenominator, newNumerator) ;
			}
		} else { //number == 0
			return new Unit() ;
		}
	}

	public Unit clone() {
		return new Unit(this.getNumerator(), this.getDenominator()) ;
	}


	public String getHtml() {
		if(numerator.size() > 0 || denominator.size() > 0) {
		ArrayList<BaseUnit> numUnits = new ArrayList<BaseUnit>() ;
		ArrayList<Integer>numCounts = new ArrayList<Integer>() ;

		for(int i=0; i<numerator.size(); i++) {
			int index = numUnits.indexOf(numerator.get(i)) ;
			if(index != -1) {
				numCounts.set(index, numCounts.get(index) + 1) ;
			} else {
				numUnits.add(numerator.get(i)) ;
				numCounts.add(1) ;
			}
		}

		ArrayList<BaseUnit> denUnits = new ArrayList<BaseUnit>() ;
		ArrayList<Integer>denCounts = new ArrayList<Integer>() ;

		for(int i=0; i<denominator.size(); i++) {
			int index = denUnits.indexOf(denominator.get(i)) ;
			if(index != -1) {
				denCounts.set(index, denCounts.get(index) - 1) ;
			} else {
				denUnits.add(denominator.get(i)) ;
				denCounts.add(-1) ;
			}
		}

		String outString = "" ;

		for(int i=0; i<numUnits.size(); i++) {
			String exponent ;
			if(numCounts.get(i) == 1) {
				exponent = " " ;
				if(i == numUnits.size() - 1 && denUnits.size() == 0)
					exponent = "" ;
			} else {
				exponent = supString(numCounts.get(i).toString()) ;
			}
			
			outString += numUnits.get(i).getAbbreviation() + exponent ;
		}

		for(int i=0; i<denUnits.size(); i++) {
			outString += denUnits.get(i).getAbbreviation() + supString(denCounts.get(i).toString()) ;
		}

		return outString ;
		} else {
			return null ;
		}
	}

	private static String supString(String text) {
		return "<sup>" + text + "</sup>" ;
	}

	public String encode() {
		String numeratorString = UNIT_SEPARATOR ;
		String denominatorString = UNIT_SEPARATOR ;
		String times = "" ;
		for(int i=0; i<numerator.size(); i++) {
			numeratorString += times + numerator.get(i).getAbbreviation() ;
			times = UNIT_SEPARATOR ;
		}
		times = "" ;
		for(int i=0; i<denominator.size(); i++) {
			denominatorString += times + denominator.get(i).getAbbreviation() ;
			times = UNIT_SEPARATOR ;
		}

		return numeratorString + FRACTION_SEPARATOR + denominatorString ;

	}

	public static Unit valueOf(String value) {
		return valueOf(value, BaseUnit.ALL) ;
	}

	public static Unit valueOf(String value, BaseUnit[] listOfUnits) {
		String trimmed = value.trim() ;
		String[] parts = trimmed.split(FRACTION_SEPARATOR) ;
		if(parts.length >= 2) {
			ArrayList<BaseUnit> num = new ArrayList<BaseUnit>() ;
			ArrayList<BaseUnit> den = new ArrayList<BaseUnit>() ;

			String[] partsNumerator = parts[0].trim().split(UNIT_SEPARATOR) ;
			
			for(int i=0; i<partsNumerator.length; i++) {
				BaseUnit unit = getUnitFromArray(partsNumerator[i].trim(), listOfUnits) ;
				if(unit != null) {
					num.add(unit) ;
				}
			}

			String[] partsDenominator = parts[1].substring(1).trim().split(UNIT_SEPARATOR) ;

			for(int i=0; i<partsDenominator.length; i++) {
				BaseUnit unit = getUnitFromArray(partsDenominator[i].trim(), listOfUnits) ;
				if(unit != null) {
					den.add(unit) ;
				}
			}
			return new Unit(num, den) ;

		} else {
			return null ;
		}
	}

	private static BaseUnit getUnitFromArray(String abbreviation, BaseUnit[] array) {
		for(int i=0; i<array.length; i++) {
			if(array[i].getAbbreviation().equals(abbreviation))
				return array[i] ;
		}
		
		try {
			double ratioValue = new Double(abbreviation)  ;
			return new BaseUnit(ratioValue) ;
		} catch(NumberFormatException e) {
			
		}
		return null ;
	}
	
	public static BaseUnit getSIUnit(int unitType) {
		return getSIUnit(unitType, BaseUnit.ALL) ;
	}
	
	private static BaseUnit getSIUnit(int unitType, BaseUnit[] array) {
		for(int i=0; i<array.length; i++) {
			if(array[i].getUnitType() == unitType)
				return array[i] ;
		}
		return null ;
	}

}
