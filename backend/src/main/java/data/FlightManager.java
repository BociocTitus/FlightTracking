package data;

import org.hibernate.cfg.Configuration;

import domain.Flight;
import domain.FlightCompany;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@LocalBean
@Stateless
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

	@SuppressWarnings("unchecked")
	public List<Flight> getAllFlights() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Flight> flights = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			List<Object[]> res = session.createSQLQuery("SELECT * from flights").list();
			res.forEach(flightObject -> {
				Flight flight = Flight.builder().arrivalAirport((String) flightObject[1])
						.departureAirport((String) flightObject[3])
						.flightCompany(FlightCompany.convertFromDatabase((int) flightObject[5]))
						.departureTime(((Timestamp) flightObject[4]).toLocalDateTime())
						.arrivalTime(((Timestamp) flightObject[2]).toLocalDateTime()).build();

				flight.setId(((BigInteger) flightObject[0]).longValue());

				flights.add(flight);
			});
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

	public Flight getById(Long id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Flight flight = null;

		try {
			tx = session.beginTransaction();
			flight = session.get(Flight.class, id);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return flight;
	}
}
