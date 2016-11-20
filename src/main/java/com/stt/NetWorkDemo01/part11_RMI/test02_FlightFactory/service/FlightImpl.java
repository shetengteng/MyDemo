package com.stt.NetWorkDemo01.part11_RMI.test02_FlightFactory.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FlightImpl extends UnicastRemoteObject implements Flight {

	private String flightNumber;
	private String origin;
	private String destination;
	private String skdDeparture;
	private String skdArrival;

	public FlightImpl(String flightNumber, String origin, String destination, String skdDeparture, String skdArrival)
			throws RemoteException {
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.skdDeparture = skdDeparture;
		this.skdArrival = skdArrival;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getFligthNo() throws RemoteException {
		System.out.println("call getFlightNo:" + flightNumber);
		return flightNumber;
	}

	@Override
	public String getOrigin() throws RemoteException {
		return origin;
	}

	@Override
	public String getDestination() throws RemoteException {
		return destination;
	}

	@Override
	public String getSkdDeparture() throws RemoteException {
		return skdDeparture;
	}

	@Override
	public String getSkdArrival() throws RemoteException {
		return skdArrival;
	}

	@Override
	public void setOrigin(String origin) throws RemoteException {
		this.origin = origin;
	}

	@Override
	public void setDestination(String destination) throws RemoteException {
		this.destination = destination;
	}

	@Override
	public void setSkdArrival(String arrival) throws RemoteException {
		this.skdArrival = arrival;
	}

	@Override
	public void setSkdDeparture(String departure) throws RemoteException {
		this.skdDeparture = departure;
	}
}
