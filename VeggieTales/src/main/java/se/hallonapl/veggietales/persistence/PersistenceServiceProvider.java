package se.hallonapl.veggietales.persistence;

import java.sql.SQLException;

public class PersistenceServiceProvider {
	
	private static IPersistenceService persistenceService = null;
	
	public static IPersistenceService getPersistenceService() throws Exception {
		if (persistenceService == null) {
			try {				
				persistenceService = new SQLService();
			}
			catch (SQLException se) {
				throw new Exception(se);
			}
		}
		return persistenceService;
	}
}
