package service;

import java.io.File;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import data.FlightManager;
import domain.Flight;
import domain.FlightCompany;
import util.LocalDateTimeConverter;
import static org.hamcrest.core.IsEqual.equalTo;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class FlightServiceTest {

	@Deployment
	public static WebArchive createDeployment() {
		File[] files = Maven.resolver().resolve("org.hibernate:hibernate-core:5.1.0.Final").withTransitivity().asFile();
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(IService.class.getPackage())
				.addClass(FlightManager.class)
				.addClass(LocalDateTimeConverter.class)
				.addPackage(Flight.class.getPackage())
				.addAsResource("hibernate.cfg.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(files)
				.addAsLibrary("postgresql-42.2.4.jar");
	}
	
	@Before
	public void setUp() {
		flight = Flight.builder().arrivalAirport(STANSTED).departureAirport(OTOPENI)
				.arrivalTime(LocalDateTime.of(LocalDate.of(2018, 10, 11), LocalTime.of(20, 10)))
				.departureTime(LocalDateTime.of(LocalDate.of(2018, 10, 11), LocalTime.of(20, 10)))
				.flightCompany(FlightCompany.TAROM).build();
	}

	private final String OTOPENI = "Otopeni";
	private final String STANSTED = "Stansted";
	private final String GIRONA = "Girona";
	
	private Flight flight;

	@EJB
	private IService<Flight, Long> flightService;
	
	@Test
	public void testService() {		
		Long res = flightService.addElement(flight);
		assertThat(res, not(0L));
		flight.setId(res);
	
		List<Flight> result = flightService.getElements();
		assertThat(result.size(), equalTo(1));
		
		flight.setDepartureAirport(GIRONA);
		flightService.updateElement(flight);
		
		flight = flightService.findById(flight.getId()).orElse(Flight.builder().build());
		assertThat(flight.getDepartureAirport(), equalTo(GIRONA));
		
		flightService.deleteElement(flight.getId());
		assertThat(flightService.getElements().size(), equalTo(0));
	}
}
