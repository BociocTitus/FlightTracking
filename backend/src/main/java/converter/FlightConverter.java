package converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;
import org.jboss.logging.Logger;

import domain.Flight;
import domain.FlightCompany;
import dto.FlightDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Stateless
public class FlightConverter implements BaseConverter<Flight, FlightDto> {

	private static final Logger LOGGER = Logger.getLogger(FlightConverter.class.getName());

	@Override
	public FlightDto convertToDto(Flight flight) {
		LOGGER.info("convertToDto called:" + flight);

		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	     String flightStart = flight.getDepartureTime().format(formatter);
	     String flightEnd = flight.getArrivalTime().format(formatter);
		
		FlightDto flightDto = FlightDto
				.builder()
				.departureAirport(flight.getDepartureAirport())
				.arrivalAirport(flight.getArrivalAirport())
				.flightCompany(flight.getFlightCompany().toString())
				.arrivalTime(flightEnd)
				.departureTime(flightStart)
				.build();
		flightDto.setId(flight.getId());

		LOGGER.info("convertToDto result:" + flightDto);

		return flightDto;
	}

	@Override
	public Flight convertFromDto(FlightDto flightDto) {
		LOGGER.info("convertFromDto called:" + flightDto);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime flightStart = LocalDateTime.parse(flightDto.getArrivalTime(), formatter);
        LocalDateTime flightEnd = LocalDateTime.parse(flightDto.getDepartureTime(), formatter);
		
		Flight flight = Flight.builder()
				.arrivalAirport(flightDto.getArrivalAirport())
				.departureAirport(flightDto.getDepartureAirport())
				.arrivalTime(flightStart)
				.departureTime(flightEnd)
				.flightCompany(FlightCompany.valueOf(flightDto.getFlightCompany()))
				.build();
		flight.setId(flightDto.getId());

		LOGGER.info("convertFromDto result:" + flight);
		return flight;
	}

}
