package domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity<Long> {
	private LocalDate flightStart;
	private LocalDate flightEnd;
	private String departureAirport;
	private String arrivalAirport;
	@Enumerated
	private FlightCompany flightCompany;
}
