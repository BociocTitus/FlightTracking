package converter;

public interface BaseConverter<T, E> {
	E convertToDto(T entity);
	
	T convertFromDto(E dto);
}
