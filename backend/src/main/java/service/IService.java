package service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;



@Local
public interface IService<T, E> {
	E addElement(T element);
	
	boolean deleteElement(E id);
	
	T updateElement(T element);
	
	List<T> getElements();
	
	Optional<T> findById(E id);
}
