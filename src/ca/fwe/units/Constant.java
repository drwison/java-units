package ca.fwe.units;

public class Constant extends Number {
	private String name ;
	private int cat ;
	
	public Constant(String name, Number value) {
		super(value.getSIValue(), value.getUnit(), true) ;
	}
	
	public Constant(String constantName, int category, double value, Unit valueUnit) {
		super(value, valueUnit);
		name = constantName ;
		cat = category ;
	}

	public Constant(String constantName, int category, double value) {
		super(value);
		name = constantName ;
		cat = category ;
	}
	
	public String getName() {
		return name ;
	}
	
	public int getCategory() {
		return cat ;
	}

	
	
}
