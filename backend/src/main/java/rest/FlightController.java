package rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import converter.BaseConverter;
import domain.Flight;
import dto.DepartureFilterDto;
import dto.FlightDto;
import service.IService;

@Stateless
@LocalBean
@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightController {

	private final Logger LOGGER = Logger.getLogger(FlightController.class.getName());

	@EJB
	private IService<Flight, Long> flightService;

	@EJB(beanName = "FlightConverter")
	private BaseConverter<Flight, FlightDto> flightConverter;

	@EJB(beanName = "DateTimeConverter")
	private BaseConverter<LocalDateTime, String> dateConverter;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFlight(FlightDto flightDto) {
		LOGGER.info("addFlight called:" + flightDto);
		Flight flight = flightConverter.convertFromDto(flightDto);
		try {
			flightService.addElement(flight);
			return Response.ok(Boolean.TRUE, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.ok(Boolean.FALSE, MediaType.APPLICATION_JSON).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FlightDto> getAllFlights() {
		LOGGER.info("getAllFlights called");

		List<FlightDto> flights = flightService.getElements().stream()
				.map(flight -> flightConverter.convertToDto(flight)).collect(Collectors.toList());

		LOGGER.info("getAllFlights result:" + flights);
		return flights;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateFlight(FlightDto flightDto) {
		LOGGER.info("updateFlight called:" + flightDto);

		Flight flight = flightConverter.convertFromDto(flightDto);
		try {
			flightService.updateElement(flight);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@DELETE
	@Path("/{flightId}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteFlight(@PathParam("flightId") Long flightId) {
		LOGGER.info("deleteFlight called:" + flightId);

		Boolean result = flightService.deleteElement(flightId);

		LOGGER.info("deleteFlight result:" + result);
		return result;
	}

	@GET
	@Path("/flight/{flightId}")
	public FlightDto findFlightById(@PathParam("flightId") Long flightId) {
		LOGGER.info("findFlightById:" + flightId);

		Optional<Flight> flight = flightService.findById(flightId);
		FlightDto flightDto = flightConverter.convertToDto(flight.orElse(Flight.builder().build()));

		LOGGER.info("findFlightById result:" + flightDto);
		return flightDto;
	}

	@POST
	@Path("/filterByDeparture")
	public List<FlightDto> filterByDeparture(DepartureFilterDto departureFilterDto) {
		LOGGER.info("filterByDeparture called:" + departureFilterDto);

		LocalDateTime startTime = dateConverter.convertFromDto(departureFilterDto.getStartTime());
		LocalDateTime endTime = dateConverter.convertFromDto(departureFilterDto.getEndTime());

		List<FlightDto> flights = flightService.getElements().stream()
				.filter(flight -> flight.getDepartureTime().compareTo(startTime) > 0
						&& flight.getDepartureTime().compareTo(endTime) < 0)
				.map(flight -> flightConverter.convertToDto(flight)).collect(Collectors.toList());

		LOGGER.info("filterByDeparture result:" + flights);
		return flights;
	}

}
