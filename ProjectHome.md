# Using this library #

Java Units is designed for use with rational units, when calculations and multiple conversions are needed. The best example of how this library can be used is the Android app [Unit Coverter & Calculator](https://play.google.com/store/apps/details?id=ca.fwe.unitcalc). The app is very similar to [Units In Java](http://units-in-java.sourceforge.net/), which was discovered after writing nearly all of this code. The main difference is that this one is simpler. It will not find your weight in eggs, but works really well for your basic multiplication, addition, and division of units in response to user input. Any combination of dimensions is assigned a unique `double` value, so finding units to convert to using the included list of units (`Units` class) is easy.

# Basic Classes #

## Elements ##
### BaseUnit.Dimension ###
A unique and non-derived dimension, such as length, mass, time, etc. Assigned a unique prime number from any other dimension.
```
BaseUnit.Dimension length = new BaseUnit.Dimension(BaseUnit.Dimension.TYPE_LENGTH) ; 
BaseUnit.Dimension time = new BaseUnit.Dimension(BaseUnit.Dimension.TYPE_TIME) ;
```

### BaseUnit ###
Any unit that can be expressed only in one Dimension. This includes metres, feet, kilograms, grams, etc. Every BaseUnit is defined in terms of how many of that unit are in the SI unit of that dimension. The abbreviation of the BaseUnit is used in serializing, displaying, and decoding `String` versions of `Unit` objects.
```
BaseUnit metres = new BaseUnit(length, "Metres", "m", 1.0) ;
BaseUnit feet = new BaseUnit(length, "Feet", "ft", 0.3048) ;
BaseUnit second = new BaseUnit(time, "Second", "s", 1.0) ;
```

### Unit ###
An object representing a series of `BaseUnit` objects in a numerator and denominator. Units will automatically be simplified if a `BaseUnit` is in both the numerator and denominator.
```
Unit metresPerSecondSquared = new Unit(new BaseUnit[] {metres}, new BaseUnit[] {second, second}) ;
Unit feetPerSecondSquared = new Unit(new BaseUnit[] {feet}, new BaseUnit[] {second, second}) ;
```

Name information can also be attached, or left `null` for automatic handling.
```
Unit named = new Unit("Feet per second", "ft/s", new BaseUnit[] {feet}, new BaseUnit[] {second}) ;
```

### Number ###
An object representing a value expressed in a unit.
```
Number gravity = new Number(-9.806, metresPerSecondSquared) ;
```

### Constant ###
Essentially a `Number` with a name attached. A `ConstantCategory` can also be attached for organization of constants.
```
Constant c = new Constant("Accelleration due to gravity", gravity) ;
```

## Unit and Constant Lists ##
### Units ###
A list of units, organized by categories in nested classes. See examples below, or JavaDoc for full list.
```
Number n = new Number(3.1, Units.length.METRES) ;
ArrayList<Unit> allUnits = Units.getAll() ;
```

### Constants ###
A list of constants, organized by category.

# Examples #
Working with `Number` objects:

```
Number n = new Number(4.0, Units.length.METRES) ;

//multiply by 2
Number nTimesTwo = n.multiplyBy(new Number(2)) ;

//multiply by 2 inches
Number twoInchesbyFourMetres = n.multiplyBy(new Number(2.0, Units.length.INCHES)) ;

//add 4 square feet
Number newArea = twoInchesByFourMetres.add(new Number(4.0, Units.area.SQUARE_FEET)) ;

//get Number in different unit
double valueInSquareCm = twoInchesByFourMetres.getValue(Units.area.SQUARE_CENTIMETRES) ;

//will throw an IncomparableUnitException
double invalid = twoInchesbyFourMetres.getValue(Units.energy.JOULE) ;

```

Working with `Unit` objects:

```
//create a Unit using an array of BaseUnit objects for the numerator and denominator.
Unit u = new Unit("Metres per second sqared", "m/s/s",
                   new BaseUnit[] {BaseUnit.METRE},
                   new BaseUnit[] {BaseUnit.SECOND, Base Unit.SECOND} ) ;

//create a Unit using another Unit
Unit newton = new Unit("Newton", "N", u.multiplyBy(Units.mass.KILOGRAM) ;

//create a Unit using another unit and a conversion factor
Unit kiloNewton = new Unit("Kilonewton", "kN", newton, 1000) ;

```

Creating custom `Unit`s:

```
//create the dimensions
BaseUnit.Dimension thingamabob = new BaseUnit.Dimension(BaseUnit.Dimension.USER1) ;

//create BaseUnit objects. make sure your BaseUnit abbreviation is not used by any other BaseUnits you will use, directly or indirectly, if you would like to use the Unit.encode() method, or Unit.getSI().
BaseUnit thingamabobSI = new BaseUnit(thingamabob, "thingamabob SI", "th", 1.0) ;

//make Units
Unit thingamabobsPerMetre = new Unit("Thingamabobs per metre", "th/m", new BaseUnit[] {thingamabobSI},  new BaseUnit[] {Units.length.METRES}) ;
Unit thingamabobsPerFoot = new Unit("Thingamabobs per foot", "th/ft", new BaseUnit[] {thingamabobSI},  new BaseUnit[] {Units.length.FEET}) ;

//make Numbers
Number n = new Number(42.1, thingamabobsPerMetre) ;

//convert
double valueInThingamabobsPerFoot = n.getValue(thingamabobsPerFoot) ;
```

Using the `Units` class:

```
//create a complicated number
Number n = new Number(2.0, Units.length.FEET)
            .multiplyBy(new Number(3.53, Units.mass.KILOGRAMS)
            .divideBy(new Number(.781, Units.time.SECONDS)
            .divideBy(new Number(1.38, Units.time.HOURS)     //force units
            .divideBy(new Number(3.1, Units.area.FEET_SQARED) ; //pressure units

//find comparable units - returns a list of common pressure units.
ArrayList<Unit> comparableUnits = Units.filter(n.getUnit().getUnitType()) ;

//or, get the SI version of the unit, whatever it may be.
Unit SIUnit = n.getUnit().getSI() ;
```