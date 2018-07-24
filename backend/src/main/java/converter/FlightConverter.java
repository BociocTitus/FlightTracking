package converter;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.jboss.logging.Logger;

import domain.Flight;
import domain.FlightCompany;
import dto.FlightDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Stateless
@EJB(name = "FlightConverter", beanName = "FlightConverter", beanInterface = BaseConverter.class)
public class FlightConverter implements BaseConverter<Flight, FlightDto> {

	private static final Logger LOGGER = Logger.getLogger(FlightConverter.class.getName());
	
	@EJB(beanName = "DateTimeConverter")
	private BaseConverter<LocalDateTime, String> dateConverter;

	@Override
	public FlightDto convertToDto(Flight flight) {
		LOGGER.info("convertToDto called:" + flight);

		FlightDto flightDto = FlightDto.builder()
				.departureAirport(flight.getDepartureAirport())
				.arrivalAirport(flight.getArrivalAirport()).flightCompany(flight.getFlightCompany().toString())
				.arrivalTime(dateConverter.convertToDto(flight.getArrivalTime()))
				.departureTime(dateConverter.convertToDto(flight.getDepartureTime()))
				.build();
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
				.arrivalTime(dateConverter.convertFromDto(flightDto.getArrivalTime()))
				.departureTime(dateConverter.convertFromDto(flightDto.getDepartureTime()))
				.flightCompany(FlightCompany.valueOf(flightDto.getFlightCompany()))
				.build();
		flight.setId(flightDto.getId());

		LOGGER.info("convertFromDto result:" + flight);
		return flight;
	}

}
