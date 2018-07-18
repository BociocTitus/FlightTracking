package converter;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import domain.Flight;
import domain.FlightCompany;
import dto.FlightDto;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

@RequiredArgsConstructor
@ApplicationScoped
public class FlightConverter implements BaseConverter<Flight, FlightDto> {

	private static final Logger LOGGER = Logger.getLogger(FlightConverter.class.getName());

	@Override
	public FlightDto convertToDto(Flight flight) {
		LOGGER.info("convertToDto called:" + flight);

		FlightDto flightDto = FlightDto
				.builder()
				.departureAirport(flight.getDepartureAirport())
				.arrivalAirport(flight.getArrivalAirport())
				.flightCompany(flight.getFlightCompany().toString())
				.flightEnd(flight.getFlightEnd())
				.flightStart(flight.getFlightStart()).build();
		flightDto.setId(flight.getId());

		LOGGER.info("convertToDto result:" + flightDto);

		return flightDto;
	}

	@Override
	public Flight convertFromDto(FlightDto flightDto) {
		LOGGER.info("convertFromDto called:" + flightDto);

		Flight flight = Flight.builder()
				.arrivalAirport(flightDto.getArrivalAirport())
				.departureAirport(flightDto.getDepartureAirport())
				.flightStart(flightDto.getFlightStart())
				.flightEnd(flightDto.getFlightEnd())
				.flightCompany(FlightCompany.valueOf(flightDto.getFlightCompany()))
				.build();
		flight.setId(flightDto.getId());

		LOGGER.info("convertFromDto result:" + flight);
		return flight;
	}

}
