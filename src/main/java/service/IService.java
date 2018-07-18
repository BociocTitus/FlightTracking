package service;

import java.util.List;

public interface IService<T, E> {
	E addElement(T element);
	
	boolean deleteElement(E id);
	
	T updateElement(T element);
	
	List<T> getElements();
}
