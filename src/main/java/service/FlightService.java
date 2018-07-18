package service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import data.FlightManager;
import domain.Flight;

@ApplicationScoped
public class FlightService implements IService<Flight, Long> {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	@Inject
	private FlightManager flightManager;

	@Override
	public Long addElement(Flight element) {
		LOGGER.info("addElement called:" + element);

		Long id = flightManager.addFlight(element);

		LOGGER.info("addElement result:" + id);
		return id;
	}

	@Override
	public boolean deleteElement(Long id) {
		LOGGER.info("deleteElement called:" + id);

		boolean result = flightManager.deleteFlight(id);

		LOGGER.info("deleteElement result:" + result);
		return result;
	}

	@Override
	public Flight updateElement(Flight element) {
		LOGGER.info("updateElement called:" + element);

		boolean result = flightManager.updateFlight(element);

		LOGGER.info("updateElement result:" + result);
		return element;
	}

	@Override
	public List<Flight> getElements() {
		LOGGER.info("getElements called");

		List<Flight> flights = flightManager.getAllFlights();

		LOGGER.info("getElements result:" + flights);
		return flights;
	}

}
