package rest;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import converter.BaseConverter;
import domain.Flight;
import dto.FlightDto;
import service.IService;

@Stateless
@LocalBean
@Path("/flights")
public class FlightController {

	private final Logger LOGGER = Logger.getLogger(FlightController.class.getName());

	@Inject
	private IService<Flight, Long> flightService;

	@Inject
	private BaseConverter<Flight, FlightDto> flightConverter;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean addFlight(FlightDto flightDto) {
		LOGGER.info("addFlight called:" + flightDto);

		Flight flight = flightConverter.convertFromDto(flightDto);
		try {
			flightService.addElement(flight);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FlightDto> getAllFlights() {
		LOGGER.info("getAllFlights called");

		List<FlightDto> flights = flightService.getElements().stream()
				.map(flight -> flightConverter.convertToDto(flight))
				.collect(Collectors.toList());

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
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteFlight(Long flightId) {
		LOGGER.info("deleteFlight called:" + flightId);

		Boolean result = flightService.deleteElement(flightId);

		LOGGER.info("deleteFlight result:" + result);
		return result;
	}

}