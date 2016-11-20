package com.stt.NetWorkDemo01.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service;

import java.io.Serializable;

public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;
	private String flightNumber;
	private String origin;
	private String destination;
	private String skdDeparture;
	private String skdArrival;

	public Flight(String flightNumber, String origin, String destination, String skdDeparture, String skdArrival) {
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.skdDeparture = skdDeparture;
		this.skdArrival = skdArrival;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSkdDeparture() {
		return skdDeparture;
	}

	public void setSkdDeparture(String skdDeparture) {
		this.skdDeparture = skdDeparture;
	}

	public String getSkdArrival() {
		return skdArrival;
	}

	public void setSkdArrival(String skdArrival) {
		this.skdArrival = skdArrival;
	}

}
