package com.garu.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	// Sent�ncia SQL per crear la taula de Titulars 
	private final String database = "acuditsBD";
	private final String colNum = "num";
	private final String colTit = "titol";
	private final String colCos = "cos";
	private final String colRate = "rate";
	private final String SQL_CREATE = "CREATE TABLE "+ database + "(" +
			colNum + " INTEGER PRIMARY KEY, " + colTit + " TEXT, " +
			colCos + " TEXT, " + colRate + " INTEGER)";
	/**
	 * Constructor amb par�metres
	 * @param context el context de l'aplicaci�
	 * @param nom el nom de la base de dades a crear
	 * @param factory s'utilitza per crear objectes cursor, o null per defecte
	 * @param versio n�mero de versi� de la BD. Si �s m�s gran que la versi� actual, es far� un 
	 * 				 Upgrade; si �s menor es far� un Downgrade
	 */
	public SQLiteHelper(Context context, String nom, CursorFactory factory, int versio) {
		super(context, nom, factory, versio);
	}
 
	/**
	 * Event que es produeix quan s'ha de crear la BD
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// S'executen les sent�ncies SQL de creaci� de la BD	
		db.execSQL(SQL_CREATE);
		
		ContentValues cv = new ContentValues();
		cv.put(colNum, 1);
		cv.put(colTit, "Supervisi�n maternal");
		cv.put(colCos, " - Mam�, �puedo usar el coche?\n - No, no puedes sin mi supervisi�n.\n - �Uy! �Perd�n por no tener s�per-poderes como t�!");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 2);
		cv.put(colTit, "Monjas variadas");
		cv.put(colCos, " - �Qui�n es la monja m�s fuerte?\n - Sor Zenegger\n - �Y la monja m�s silenciosa?\n - Sor Domuda.\n - �Y la monja m�s original?\n - Sor Presiva.\n - �Y la monja m�s presumida?\n - Sor Tija.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 3);
		cv.put(colTit, "Respiraci�n dificultosa");
		cv.put(colCos, " - Se abre el tel�n\n - aparece un se�or dando traspi�s y respirando fatigosamente.\n - Baja el tel�n. �C�mo se llama la peli?\n - ...\n - Asma letal.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 4);
		cv.put(colTit, "Sierras");
		cv.put(colCos, " - Un tipo entra a una tienda:\n - Hola, �venden serruchos?\n - Pues no, lo siento.\n - �Y sierras?\n - A las nueve en punto.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 5);
		cv.put(colTit, "Bocata de jam�n");
		cv.put(colCos, " - Entra un tipo en una tienda:\n - Hola, me da un bocadillo de jam�n.\n - �York?\n - S�, t�rk.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 6);
		cv.put(colTit, "Pulgas difuntas");
		cv.put(colCos, " - �A d�nde van las pulgas cuando mueren?\n - ...\n - Al pulgatorio.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 7);
		cv.put(colTit, "Batman forever");
		cv.put(colCos, " - Llega un se�or al videoclub y dice:\n - Quiero alquilar �Batman Forever�.\n - No se puede, se�or, tiene que devolverla tomorrow.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 8);
		cv.put(colTit, "Palomas exageradas");
		cv.put(colCos, " - Dos hombres, amigos de la infancia, se encuentran:\n - �Y t� ahora a que te dedicas?\n - Pues tengo una granja con 10.000 palomas.\n - �Mensajeras?\n - No, no te exagero nada.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 9);
		cv.put(colTit, "Adicto");
		cv.put(colCos, " - �Mam�, mam�, en el cole me dicen que soy un adicto al Twitter!\n - �Y cu�ndo te han dicho eso?\n - \"Hace 45 minutos v�a Twitter for Android\".");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 10);
		cv.put(colTit, "No seas tonto");
		cv.put(colCos, "- Manuel �como ha ido el juicio?\n - el juez ha dicho que 5 a�os de carcel o 95 mil euros.\n - T� no seas tonto,coge el dinero!");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 11);
		cv.put(colTit, "Aceros");
		cv.put(colCos, " - Van dos por la calle y pasan cerca de un cartel que pone:\n - \"Aceros inoxidables\"\n - Y le dice uno al otro:\n - Pss, Paco, �nos hacemos?.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
	}
 
	/**
	 * Event que es produeix quan s'ha d'actualitzar la BD a una versi� superior
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int versioAnterior, int versioNova) {
		// NOTA: Per simplificar l'exemple, aqu� s'utilitza directament
		// l'opci� d'eliminar la taula anterior i tornar-la a crear buida
		// amb la nova estructura.
		// No obstant, el m�s habitual �s migrar les dades de la taula antiga
		// a la nova, per la qual cosa aquest m�tode hauria de fer m�s coses.
 
		// S'elimina la versi� anterior de la taula
		db.execSQL("DROP TABLE IF EXISTS Acudits");
		// Es crea la nova versi� de la taula
		db.execSQL(SQL_CREATE);
	}
}
