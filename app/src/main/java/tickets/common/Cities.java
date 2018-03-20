
package tickets.common;

import java.util.List;
import java.util.ArrayList;

import tickets.common.Route;


public final class Cities {
	public static final String VANCOUVER = "Vancouver";
	public static final String SEATTLE = "Seattle";
	public static final String PORTLAND = "Portland";
	public static final String SAN_FRANCISCO = "San Fransisco";
	public static final String LOS_ANGELES = "Los Angeles";
	public static final String LAS_VEGAS = "Las Vegas";
	public static final String SALT_LAKE_CITY = "Salt Lake City";
	public static final String HELENA = "Helena";
	public static final String CALGARY = "Calgary";
	public static final String WINNIPEG = "Winnipeg";
	public static final String DULUTH = "Duluth";
	public static final String OMAHA = "Omaha";
	public static final String KANSAS_CITY = "Kansas City";
	public static final String DENVER = "Denver";
	public static final String PHOENIX = "Phoenix";
	public static final String SANTA_FE = "Santa Fe";
	public static final String EL_PASO = "El Paso";
	public static final String HOUSTON = "Houston";
	public static final String DALLAS = "Dallas";
	public static final String OKLAHOMA_CITY = "Oklahoma City";
	public static final String LITTLE_ROCK = "Little Rock";
	public static final String NEW_ORLEANS = "New Orleans";
	public static final String SAINT_LOUIS = "Saint Louis";
	public static final String CHICAGO = "Chicago";
	public static final String SAULT_ST_MARIE = "Sault St. Marie";
	public static final String TORONTO = "Toronto";
	public static final String PITTSBURG = "Pittsburg";
	public static final String NASHVILLE = "Nashville";
	public static final String ATLANTA = "Atlanta";
	public static final String MIAMI = "Miami";
	public static final String CHARLESTON = "Charleston";
	public static final String RALEIGH = "Raleigh";
	public static final String WASHINGTON = "Washington";
	public static final String NEW_YORK = "New York";
	public static final String BOSTON = "Boston";
	public static final String MONTREAL = "Montreal";

	private List<String> getAll() {
		List<String> locations = new ArrayList<>();
		locations.add(VANCOUVER);
		locations.add(SEATTLE);
		locations.add(PORTLAND);
		locations.add(SAN_FRANCISCO);
		locations.add(LOS_ANGELES);
		locations.add(LAS_VEGAS);
		locations.add(SALT_LAKE_CITY);
		locations.add(HELENA);
		locations.add(CALGARY);
		locations.add(WINNIPEG);
		locations.add(DULUTH);
		locations.add(OMAHA);
		locations.add(KANSAS_CITY);
		locations.add(DENVER);
		locations.add(PHOENIX);
		locations.add(SANTA_FE);
		locations.add(EL_PASO);
		locations.add(HOUSTON);
		locations.add(DALLAS);
		locations.add(OKLAHOMA_CITY);
		locations.add(LITTLE_ROCK);
		locations.add(NEW_ORLEANS);
		locations.add(SAINT_LOUIS);
		locations.add(CHICAGO);
		locations.add(SAULT_ST_MARIE);
		locations.add(TORONTO);
		locations.add(PITTSBURG);
		locations.add(NASHVILLE);
		locations.add(ATLANTA);
		locations.add(MIAMI);
		locations.add(CHARLESTON);
		locations.add(RALEIGH);
		locations.add(WASHINGTON);
		locations.add(NEW_YORK);
		locations.add(BOSTON);
		locations.add(MONTREAL);
		return locations;
	}
}