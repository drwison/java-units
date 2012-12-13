package ca.fwe.units;

/**
 * @author Dewey Dunnington
 * 
 * A class containing units of the 7 base unit types (as defined by SI), defining them in terms of their SI unit and Dimension.
 * These units are not used on their own, but should be used as components in a Unit.
 *
 */
public class BaseUnit {
	private Dimension baseUnit ;
	private String unitName ;
	private String unitAbbreviation ;
	private double baseUnitOffset ;
	private double baseUnitRatio ;

	//Common Units
	public static final BaseUnit RATIO = new BaseUnit(Dimension.UNITLESS) ;
	public static final BaseUnit PI = new BaseUnit(Math.PI) ;

	//Distance
	public static final BaseUnit METRE = new BaseUnit(Dimension.METRE) ;
	public static final BaseUnit DECIMETRE = new BaseUnit(Dimension.METRE, "decimetre", "dm", 0.1) ;
	public static final BaseUnit HECTOMETRE = new BaseUnit(Dimension.METRE, "hectometre", "hm", 100) ;
	public static final BaseUnit KILOMETRE = new BaseUnit(Dimension.METRE, "kilometre", "km", 1000) ;
	public static final BaseUnit CENTIMETRE = new BaseUnit(Dimension.METRE, "centimetre", "cm", 0.01) ;
	public static final BaseUnit MILLIMETRE = new BaseUnit(Dimension.METRE, "millimetre", "mm", 0.001) ;
	public static final BaseUnit MICROMETRE = new BaseUnit(Dimension.METRE, "micrometer", "um", 1e-6) ;
	public static final BaseUnit MILE = new BaseUnit(Dimension.METRE, "mile", "mi", 1609.34) ;
	public static final BaseUnit FOOT = new BaseUnit(Dimension.METRE, "foot", "ft", 0.3048) ;
	public static final BaseUnit CHAIN = new BaseUnit(Dimension.METRE, "chain", "chain", 20.1168) ;
	public static final BaseUnit FURLONG = new BaseUnit(Dimension.METRE, "furlong", "furlong", 201.168) ;
	public static final BaseUnit INCH = new BaseUnit(Dimension.METRE, "inch", "in", 0.0254) ;
	public static final BaseUnit YARD = new BaseUnit(Dimension.METRE, "yard", "yd", 0.9144) ;

	//Mass
	public static final BaseUnit KILOGRAM = new BaseUnit(Dimension.KILOGRAM) ;
	public static final BaseUnit MILLIGRAM = new BaseUnit(Dimension.KILOGRAM, "milligram", "mg", 1e-6) ;
	public static final BaseUnit GRAM = new BaseUnit(Dimension.KILOGRAM, "gram", "g", 0.001) ;
	public static final BaseUnit SLUG = new BaseUnit(Dimension.KILOGRAM, "slug", "slug", 14.5939029) ;
	public static final BaseUnit TONNES = new BaseUnit(Dimension.KILOGRAM, "metric tonne", "tonne", 1000) ;

	public static final BaseUnit POUND_EARTH = new BaseUnit(Dimension.KILOGRAM, "pound (earth)", "lb_mass", 0.45359237) ;
	public static final BaseUnit SHORT_TON_EARTH = new BaseUnit(Dimension.KILOGRAM, "short ton (earth)", "ton_mass", 907.18474) ;

	//Time
	public static final BaseUnit SECOND = new BaseUnit(Dimension.SECOND) ;
	public static final BaseUnit MINUTE = new BaseUnit(Dimension.SECOND, "minute", "min", 60) ;
	public static final BaseUnit HOUR = new BaseUnit(Dimension.SECOND, "hour", "hr", 3600) ;
	public static final BaseUnit DAY = new BaseUnit(Dimension.SECOND, "day", "day", 86400) ;
	public static final BaseUnit WEEK = new BaseUnit(Dimension.SECOND, "week", "wk", 604800) ;
	public static final BaseUnit YEAR = new BaseUnit(Dimension.SECOND, "year", "yr", 3.15569e7) ;

	//Current
	public static final BaseUnit AMPERE = new BaseUnit(Dimension.AMPERE) ;
	public static final BaseUnit MILLIAMP = new BaseUnit(Dimension.AMPERE, "milliamp", "ma", 0.001) ;

	//Temperature
	public static final BaseUnit KELVIN = new BaseUnit(Dimension.KELVIN) ;
	public static final BaseUnit DEGREES_CELCIUS = new BaseUnit(Dimension.KELVIN, "degrees Celcius", "deg_C", 1, 273.15) ;
	public static final BaseUnit DEGREES_FARENHEIGHT = new BaseUnit(Dimension.KELVIN, "degrees Farenheight", "deg_F", 1.8, 459.67) ;

	//luminous intensity
	public static final BaseUnit CANDELA = new BaseUnit(Dimension.CANDELA) ;

	//Amount
	public static final BaseUnit MOLE = new BaseUnit(Dimension.MOLE) ;
	public static final BaseUnit MILLIMOLE = new BaseUnit(Dimension.MOLE, "millimole", "mmol", 0.001) ;

	//Angles
	public static final BaseUnit RADIAN = new BaseUnit(Dimension.RADIAN) ;
	public static final BaseUnit REVOLUTION = new BaseUnit(Dimension.RADIAN, "revolution", "rev", 2 * Math.PI) ;
	public static final BaseUnit DEGREE = new BaseUnit(Dimension.RADIAN, "degree", "deg", 0.0174532925) ;
	public static final BaseUnit GRADIAN = new BaseUnit(Dimension.RADIAN, "gradian", "grad", 15.707963e-3) ;

	//Currency
	public static final BaseUnit DOLLAR = new BaseUnit(Dimension.DOLLAR) ;

	//Data storage
	public static final BaseUnit BYTE = new BaseUnit(Dimension.BYTE) ;
	public static final BaseUnit BIT = new BaseUnit(Dimension.BYTE, "bit", "bit", 0.125) ;
	public static final BaseUnit KILOBYTE = new BaseUnit(Dimension.BYTE, "kilobyte", "kiB", 1024) ;
	public static final BaseUnit MEGABYTE = new BaseUnit(Dimension.BYTE, "megabyte", "MiB", 1e6) ;
	public static final BaseUnit GIGABYTE = new BaseUnit(Dimension.BYTE, "gigabyte", "GiB", 1e9) ;



	/**
	 * A list of all BaseUnit objects contained in this library.
	 */
	public static final BaseUnit[] ALL = {METRE, DECIMETRE, HECTOMETRE, KILOMETRE, CENTIMETRE, MILLIMETRE, MICROMETRE,
		MILE, FOOT, CHAIN, FURLONG, INCH, YARD, KILOGRAM, MILLIGRAM, GRAM, SLUG, TONNES, POUND_EARTH, SHORT_TON_EARTH,
		SECOND, MINUTE, HOUR, DAY, WEEK, YEAR, AMPERE, KELVIN,
		DEGREES_CELCIUS, DEGREES_FARENHEIGHT, CANDELA, MOLE, MILLIMOLE, RADIAN, REVOLUTION, DEGREE, GRADIAN, 
		DOLLAR, BYTE, BIT, KILOBYTE, MEGABYTE, GIGABYTE} ;

	/**
	 * @param base dimension associated with this BaseUnit
	 * @param name long form name
	 * @param abbreviation unique abbreviation for the BaseUnit. Must be unique, as it is used to store units.
	 * @param ratio ratio between the SI unit and this unit (i.e. what you would multiply a value in this unit to get the value in SI units)
	 * @param offset used to accommodate degrees celcius and farenheight, but not implemented in the Unit class.
	 */
	public BaseUnit(Dimension base, String name, String abbreviation, double ratio, double offset) {
		baseUnit = base ;
		unitName = name ;
		unitAbbreviation = abbreviation ;
		baseUnitOffset = offset ;
		baseUnitRatio = ratio ;
	}

	/**
	 * @param base dimension associated with this BaseUnit
	 * @param name long form name
	 * @param abbreviation unique abbreviation for the BaseUnit. Must be unique, as it is used to store units.
	 * @param ratio ratio between the SI unit and this unit (i.e. what you would multiply a value in this unit to get the value in SI units)
	 */
	public BaseUnit(Dimension base, String name, String abbreviation, double ratio) {
		this(base, name, abbreviation, ratio, 0) ;
	}


	/**
	 * Creates a unitless, nameless dimension, with ratio specified.
	 * 
	 * @param ratio the ratio.
	 */
	public BaseUnit(double ratio) {
		this(Dimension.UNITLESS, "", "", ratio, 0) ;
	}


	/**
	 * Creates an SI unit of Dimension specified using dimension name and abbreviation contained within the dimension for convenience.
	 * 
	 * @param base the Dimension
	 */
	public BaseUnit(Dimension base) {
		this(base, base.getDimensionSIName(), base.getDimensionSIAbbreviation(), 1) ;
	}

	public boolean equals(Object otherObject) {
		if(otherObject instanceof BaseUnit) {
			BaseUnit otherUnit = (BaseUnit)otherObject ;
			if(this.baseUnitRatio == otherUnit.getBaseUnitRatio() && this.getUnitType() == otherUnit.getUnitType()) {
				return true ;
			} else {
				return false ;
			}
		} else {
			return false ;
		}
	}

	/**
	 * @return the BaseUnit long name.
	 */
	public String getName() {
		return unitName ;
	}

	/**
	 * @return the BaseUnit short name.
	 */
	public String getAbbreviation() {
		if(unitAbbreviation.length() > 0) {
			return unitAbbreviation ;
		} else {
			return new Double(baseUnitRatio).toString() ;
		}
	}

	/**
	 * @return offset from base unit.
	 */
	public double getBaseUnitOffset() {
		return baseUnitOffset ;
	}

	/**
	 * @return the ratio between this unit and the SI form of the unit in the same dimension.
	 */
	public double getBaseUnitRatio() {
		return baseUnitRatio ;
	}

	/**
	 * @return the unique prime number associated with a unit of this dimension.
	 */
	public int getUnitType() {
		return baseUnit.getDimensionType() ;
	}

	/**
	 * @author Dewey Dunnington
	 * 
	 * Stores SI units for dimensions. A prime number is assigned to each dimension, so that when multiplied/divided in a more complex
	 * unit, a unique double is created for that combination of dimensions. Names and short forms are not used in any other part
	 * of this library, convenience only.
	 *
	 */
	public static class Dimension {
		public static final int NO_UNIT = 1 ;
		public static final int LENGTH = 2 ;
		public static final int MASS = 3 ;
		public static final int TIME = 5 ;
		public static final int ELECTRIC_CURRENT = 7 ;
		public static final int TEMPERATURE = 11 ;
		public static final int LUMINOUS_INTENSITY = 13 ;
		public static final int AMOUNT_OF_SUBSTANCE = 17 ;
		public static final int ANGLE = 19 ;
		public static final int CURRENCY = 23 ;
		public static final int DATA_STORAGE = 29 ;

		public static final int USER1 = 101 ;
		public static final int USER2 = 103 ;
		public static final int USER3 = 107 ;
		public static final int USER4 = 109 ;
		public static final int USER5 = 113 ;
		public static final int USER6 = 127 ;

		public static final Dimension UNITLESS = new Dimension("","", NO_UNIT) ;
		public static final Dimension METRE = new Dimension("metre", "m", LENGTH) ;
		public static final Dimension KILOGRAM = new Dimension("kilogram", "kg", MASS) ;
		public static final Dimension SECOND = new Dimension("second", "s", TIME) ;
		public static final Dimension AMPERE = new Dimension("ampere", "A", ELECTRIC_CURRENT) ;
		public static final Dimension KELVIN = new Dimension("kelvin", "K", TEMPERATURE) ;
		public static final Dimension CANDELA = new Dimension("candela", "cd", LUMINOUS_INTENSITY) ;
		public static final Dimension MOLE = new Dimension("mole", "mol", AMOUNT_OF_SUBSTANCE) ;
		public static final Dimension RADIAN = new Dimension("radian", "rad", ANGLE) ;
		public static final Dimension DOLLAR = new Dimension("dollar", "$", CURRENCY) ;
		public static final Dimension BYTE = new Dimension("byte", "b", DATA_STORAGE) ;


		private String baseUnitName ;
		private String baseUnitAbbreviation ;
		private int unitType ;

		private Dimension(String name, String abbreviation, int type) {
			baseUnitName = name ;
			baseUnitAbbreviation = abbreviation ;
			unitType = type ;
		}

		public String getDimensionSIName() {
			return baseUnitName;
		}

		public String getDimensionSIAbbreviation() {
			return baseUnitAbbreviation;
		}

		/**
		 * @return the unique prime number assigned to this dimension.
		 */
		public int getDimensionType() {
			return unitType;
		}
	}

}
