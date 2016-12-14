package com.stt.NetWorkDemo.part11_RMI.test02_FlightFactory.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Flight extends Remote {

	public String getFligthNo() throws RemoteException;

	public String getOrigin() throws RemoteException;

	public String getDestination() throws RemoteException;

	public String getSkdDeparture() throws RemoteException;

	public String getSkdArrival() throws RemoteException;

	public void setOrigin(String origin) throws RemoteException;

	public void setDestination(String destination) throws RemoteException;

	public void setSkdArrival(String arrival) throws RemoteException;

	public void setSkdDeparture(String departure) throws RemoteException;
}
