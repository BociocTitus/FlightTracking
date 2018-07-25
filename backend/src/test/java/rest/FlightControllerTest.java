package rest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import converter.BaseConverter;
import converter.DateTimeConverter;
import data.FlightManager;
import domain.Flight;
import domain.FlightCompany;
import dto.BaseDto;
import dto.DepartureFilterDto;
import dto.FlightDto;
import service.IService;
import util.LocalDateTimeConverter;
import web.CORSFilter;
import web.RestApplication;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Arquillian.class)
public class FlightControllerTest {

	@Deployment(testable = true)
	public static WebArchive createDeployment() {
		File[] files = Maven.resolver().resolve("org.hibernate:hibernate-core:5.1.0.Final").withTransitivity().asFile();
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(FlightController.class, RestApplication.class)
				.addPackage(IService.class.getPackage())
				.addPackage(BaseConverter.class.getPackage())
				.addClass(LocalDateTime.class)
				.addClass(FlightManager.class)
				.addPackage(Flight.class.getPackage())
				.addClass(LocalDateTimeConverter.class)
				.addPackage(BaseDto.class.getPackage())
				.addClass(CORSFilter.class)
				.addAsResource("hibernate.cfg.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(files)
				.addAsLibrary("postgresql-42.2.4.jar");
	}
	
	@Before
	public void setUp() {
		toAdd = FlightDto.builder().arrivalAirport("Girona").departureAirport("Oradea")
				.arrivalTime(
						dateConverter.convertToDto(LocalDateTime.of(LocalDate.of(2018, 10, 4), LocalTime.of(14, 0))))
				.departureTime(
						dateConverter.convertToDto(LocalDateTime.of(LocalDate.of(2018, 10, 4), LocalTime.of(15,0))))
				.flightCompany(FlightCompany.TAROM.name()).build();
	}

	private BaseConverter<LocalDateTime, String> dateConverter = new DateTimeConverter();

	private FlightDto toAdd;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	@RunAsClient
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void testPaths(@ArquillianResteasyResource WebTarget webTarget) {
		Response res = webTarget.path("/flights").request(MediaType.APPLICATION_JSON).post(Entity.json(toAdd));
		assertThat(res.readEntity(Boolean.class), equalTo(Boolean.TRUE));

		res = webTarget.path("/flights").request(MediaType.APPLICATION_JSON).get();
		List<FlightDto> result;
		try {
			result = objectMapper.readValue(res.readEntity(String.class), objectMapper.getTypeFactory().constructCollectionType(List.class, FlightDto.class));
			assertThat(result.size(), equalTo(1));
			
			FlightDto flight = result.get(0);
			res = webTarget.path("/flights").request(MediaType.APPLICATION_JSON).put(Entity.json(flight));
			assertThat(res.readEntity(Boolean.class), equalTo(Boolean.TRUE));

			res = webTarget.path("/flights/flight/" + flight.getId()).request(MediaType.APPLICATION_JSON).get();
			assertThat(res.readEntity(FlightDto.class), equalTo(flight));

			res = webTarget.path("/flights/" + flight.getId()).request(MediaType.APPLICATION_JSON).delete();
			assertThat(res.readEntity(Boolean.class), equalTo(Boolean.TRUE));
			
			res = webTarget.path("/flights").request(MediaType.APPLICATION_JSON).get();
			result = objectMapper.readValue(res.readEntity(String.class), objectMapper.getTypeFactory().constructCollectionType(List.class, FlightDto.class));
			assertThat(result.size(), equalTo(0));

			webTarget.path("/flights").request(MediaType.APPLICATION_JSON).post(Entity.json(toAdd));
			toAdd.setDepartureTime(dateConverter.convertToDto(LocalDateTime.of(LocalDate.of(2018, 8, 4), LocalTime.of(14, 0))));
			webTarget.path("/flights").request(MediaType.APPLICATION_JSON).post(Entity.json(toAdd));
			
			DepartureFilterDto departureFilterDto = DepartureFilterDto
					.builder()
					.startTime(dateConverter.convertToDto(LocalDateTime.of(LocalDate.of(2018, 10, 4), LocalTime.of(14, 0))))
					.endTime(dateConverter.convertToDto(LocalDateTime.of(LocalDate.of(2018, 11, 4), LocalTime.of(14, 0))))
					.build();
					
			res = webTarget.path("/flights/filterByDeparture").request(MediaType.APPLICATION_JSON).post(Entity.json(departureFilterDto));
			result = objectMapper.readValue(res.readEntity(String.class), objectMapper.getTypeFactory().constructCollectionType(List.class, FlightDto.class));
			assertThat(result.size(), equalTo(1));
			
			res.close();
		} catch (JsonParseException e) {
			assert(false);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			assert(false);
			e.printStackTrace();
		} catch (IOException e) {
			assert(false);
			e.printStackTrace();
		}
	}
}
