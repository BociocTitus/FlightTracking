import java.time.LocalDate;

import javax.inject.Inject;

import domain.Flight;
import domain.FlightCompany;
import service.FlightService;

public class Application {
	private FlightService flightService;

	@Inject
	public Application(FlightService flightService) {
		this.flightService = flightService;
	}

	public void run() {
		System.out.println("Everything is working!");

		Flight flight = Flight.builder().arrivalAirport("Stansted").departureAirport("Otopeni")
				.flightCompany(FlightCompany.RYANAIR).flightEnd(LocalDate.now()).flightStart(LocalDate.now()).build();
		flightService.addElement(flight);
	}
}
