package converter;

import javax.ejb.Local;

@Local
public interface BaseConverter<T, E> {
	E convertToDto(T entity);
	
	T convertFromDto(E dto);
}
