package dto;

import java.time.LocalDate;

import domain.FlightCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class FlightDto extends BaseDto<Long> {
	private String arrivalAirport;
	private String departureAirport;
	private LocalDate flightStart;
	private LocalDate flightEnd;
	private String flightCompany;
}
