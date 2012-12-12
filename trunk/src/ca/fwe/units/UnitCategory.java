package ca.fwe.units;

public class UnitCategory {

	private String name ;
	private double unitType ;
	
	public UnitCategory(String typeName, double typeValue) {
		this.name = typeName ;
		this.unitType = typeValue ;
	}
	
	public UnitCategory(String typeName, Unit unit) {
		this.name = typeName ;
		this.unitType = unit.getDerivedUnitType() ;
	}

	public String getName() {
		return name;
	}

	public double getUnitType() {
		return unitType;
	}
	
	public String toString() {
		return this.getName() ;
	}
	
}
