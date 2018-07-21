package service;

import java.util.List;

import javax.ejb.Local;


@Local
public interface IService<T, E> {
	E addElement(T element);
	
	boolean deleteElement(E id);
	
	T updateElement(T element);
	
	List<T> getElements();
	
	T findById(E id);
}
