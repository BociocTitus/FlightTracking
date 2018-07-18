package data;

import org.hibernate.cfg.Configuration;

import domain.Flight;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ApplicationScoped
public class FlightManager {
	private static SessionFactory factory;

	public FlightManager() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Long addFlight(Flight flight) {
		Session session = factory.openSession();
		Transaction tx = null;
		Long flightId = null;

		try {
			tx = session.beginTransaction();
			flightId = (Long) session.save(flight);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return flightId;
	}

	public boolean deleteFlight(Long flightId) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Flight flight = (Flight) session.get(Flight.class, flightId);
			session.delete(flight);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return Boolean.FALSE;
		} finally {
			session.close();
		}

		return Boolean.TRUE;
	}

	public boolean updateFlight(Flight flight) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(flight);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return Boolean.FALSE;
		} finally {
			session.close();
		}

		return Boolean.TRUE;
	}

	public List<Flight> getAllFlights() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Flight> flights = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			flights = (List<Flight>) session.createQuery("FROM Flights").list();
			tx.commit();

			return flights;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return flights;
	}
}
