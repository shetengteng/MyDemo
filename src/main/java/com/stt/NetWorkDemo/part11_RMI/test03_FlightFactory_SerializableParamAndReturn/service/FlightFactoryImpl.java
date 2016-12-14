package com.stt.NetWorkDemo.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class FlightFactoryImpl extends UnicastRemoteObject implements FlightFactory {
	private static final long serialVersionUID = 1L;
	// 存放Flight对象的缓存
	private Hashtable<String, Flight> flights;

	public FlightFactoryImpl() throws RemoteException {
		flights = new Hashtable<String, Flight>();
	}

	@Override
	public Flight getFlight(String flightNumber) throws RemoteException {
		Flight flight = flights.get(flightNumber);
		if (flight == null) {
			flight = new Flight(flightNumber, null, null, null, null);
			flights.put(flightNumber, flight);
		}
		return flight;
	}
}
