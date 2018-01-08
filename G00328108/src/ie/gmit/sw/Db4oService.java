package ie.gmit.sw;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;

import xtea_db4o.XTeaEncryptionStorage;

//Service class to access Db4o database with Xtea Encryption implemented.

public class Db4oService {
	private ObjectContainer db;
	private String fileName;
	private String password;

	public Db4oService(String fileName, String password) {
		super();
		this.fileName = fileName;
		this.password = password;
	}

	// Method to retrieve object of Class objectClass from persistence storage.

	@SuppressWarnings("rawtypes")
	public ObjectSet<Object> getObjects(Class objectClass) {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.file().storage(new XTeaEncryptionStorage(password));
		db = Db4oEmbedded.openFile(config, fileName);
		Query query = db.query();
		query.constrain(objectClass);
		return query.execute();
	}

	// store Object into persistent storage.

	public void storeObject(Object object) {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.file().storage(new XTeaEncryptionStorage(password));
		db = Db4oEmbedded.openFile(config, fileName);
		db.store(object);
	}

	// Closes connection to the database. It is called externally, when ObjectSet

	public void closeDb() {
		db.close();
	}

	@Override
	protected void finalize() throws Throwable {
		closeDb();
		super.finalize();
	}
}
