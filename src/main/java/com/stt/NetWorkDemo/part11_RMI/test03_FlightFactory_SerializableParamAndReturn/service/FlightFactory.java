package com.stt.NetWorkDemo.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightFactory extends Remote {
	public Flight getFlight(String flightNumber) throws RemoteException;
}
