package domain;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import util.LocalDateTimeConverter;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity<Long> {
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime arrivalTime;
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime departureTime;
	private String departureAirport;
	private String arrivalAirport;
	@Enumerated
	private FlightCompany flightCompany;
}
