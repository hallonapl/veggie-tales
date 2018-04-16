package se.hallonapl.veggietales.persistence;

import java.util.*;
import se.hallonapl.veggietales.model.Vegetable;

public interface IPersistenceService {
	public List<Vegetable> retrieveAllVegetables() throws Exception;
	public Vegetable retrieveVegetable(int id) throws Exception;
	public boolean tryDeleteVegetable(int id) throws Exception;
	public boolean tryUpdateVegetable(int id, double newPrice, String newName) throws Exception;
	public int createVegetable(double price, String name) throws Exception;
}
