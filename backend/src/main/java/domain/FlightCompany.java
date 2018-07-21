package domain;

public enum FlightCompany {
	RYANAIR, WIZZAIR, BLUEAIR, TAROM, UNKOWN;

	public static FlightCompany convertFromDatabase(int id) {
		switch (id) {
		case 0:
			return RYANAIR;
		case 1:
			return WIZZAIR;
		case 2:
			return BLUEAIR;
		case 3:
			return TAROM;
		default:
			return UNKOWN;
		}
	}
}
