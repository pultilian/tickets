
package tickets.common;

import java.util.List;
import java.util.ArrayList;

import tickets.common.Route;


// Maps original Ticket to Ride cities to our version.
public final class Cities {
	public static final String VANCOUVER = "Verdona";
	public static final String SEATTLE = "Zee-A'tll";
	public static final String PORTLAND = "Stratus";
	public static final String SAN_FRANCISCO = "Jaqualind";
	public static final String LOS_ANGELES = "Lin";
	public static final String LAS_VEGAS = "Alpha-Lyrae";
	public static final String SALT_LAKE_CITY = "Kiflamar";
	public static final String HELENA = "Boisey";
	public static final String CALGARY = "Nonnog";
	public static final String WINNIPEG = "Kerrectice";
	public static final String DULUTH = "Paradus";
	public static final String OMAHA = "Kita-Sota";
	public static final String KANSAS_CITY = "Ico-Col";
	public static final String DENVER = "Aeontacht";
	public static final String PHOENIX = "Igio";
	public static final String SANTA_FE = "Fractine";
	public static final String EL_PASO = "Magmarse";
	public static final String HOUSTON = "Warfeld";
	public static final String DALLAS = "Bynodia";
	public static final String OKLAHOMA_CITY = "Aeuoni";
	public static final String LITTLE_ROCK = "Little Rock";
	public static final String NEW_ORLEANS = "Crepusculon";
	public static final String SAINT_LOUIS = "Zeroph";
	public static final String CHICAGO = "Orthok";
	public static final String SAULT_ST_MARIE = "Ayon";
	public static final String TORONTO = "Astern";
	public static final String PITTSBURG = "Petraqa";
	public static final String NASHVILLE = "Castine";
	public static final String ATLANTA = "Dallaman";
	public static final String MIAMI = "Altiere";
	public static final String CHARLESTON = "Brytis";
	public static final String RALEIGH = "Darkrim";
	public static final String WASHINGTON = "Kalishen";
	public static final String NEW_YORK = "Spheras";
	public static final String BOSTON = "Wence";
	public static final String MONTREAL = "Exen";

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