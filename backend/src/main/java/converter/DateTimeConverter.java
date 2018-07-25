package converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.NoArgsConstructor;

@Stateless
@EJB(name = "DateTimeConverter", beanName = "DateTimeConverter", beanInterface = BaseConverter.class)
@NoArgsConstructor
public class DateTimeConverter implements BaseConverter<LocalDateTime, String> {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	@Override
	public String convertToDto(LocalDateTime entity) {
		LOGGER.info("convertToDto called:" + entity);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formatedDate = entity.format(formatter);

		LOGGER.info("convertToDto result:" + formatedDate);
		return formatedDate;
	}

	@Override
	public LocalDateTime convertFromDto(String dto) {
		LOGGER.info("convertFromDto called:" + dto);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime date = LocalDateTime.parse(dto, formatter);

		LOGGER.info("convertFromDto result:" + date);
		return date;
	}

}
