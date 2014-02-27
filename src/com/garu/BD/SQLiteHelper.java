package com.garu.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	// Sentència SQL per crear la taula de Titulars 
	private final String database = "acuditsBD";
	private final String colNum = "num";
	private final String colTit = "titol";
	private final String colCos = "cos";
	private final String colRate = "rate";
	private final String SQL_CREATE = "CREATE TABLE "+ database + "(" +
			colNum + " INTEGER PRIMARY KEY, " + colTit + " TEXT, " +
			colCos + " TEXT, " + colRate + " INTEGER)";
	/**
	 * Constructor amb paràmetres
	 * @param context el context de l'aplicació
	 * @param nom el nom de la base de dades a crear
	 * @param factory s'utilitza per crear objectes cursor, o null per defecte
	 * @param versio número de versió de la BD. Si és més gran que la versió actual, es farà un 
	 * 				 Upgrade; si és menor es farà un Downgrade
	 */
	public SQLiteHelper(Context context, String nom, CursorFactory factory, int versio) {
		super(context, nom, factory, versio);
	}
 
	/**
	 * Event que es produeix quan s'ha de crear la BD
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// S'executen les sentències SQL de creació de la BD	
		db.execSQL(SQL_CREATE);
		
		ContentValues cv = new ContentValues();
		cv.put(colNum, 1);
		cv.put(colTit, "Supervisión maternal");
		cv.put(colCos, " - Mamá, ¿puedo usar el coche?\n - No, no puedes sin mi supervisión.\n - ¡Uy! ¡Perdón por no tener súper-poderes como tú!");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 2);
		cv.put(colTit, "Monjas variadas");
		cv.put(colCos, " - ¿Quién es la monja más fuerte?\n - Sor Zenegger\n - ¿Y la monja más silenciosa?\n - Sor Domuda.\n - ¿Y la monja más original?\n - Sor Presiva.\n - ¿Y la monja más presumida?\n - Sor Tija.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 3);
		cv.put(colTit, "Respiración dificultosa");
		cv.put(colCos, " - Se abre el telón\n - aparece un señor dando traspiés y respirando fatigosamente.\n - Baja el telón. ¿Cómo se llama la peli?\n - ...\n - Asma letal.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 4);
		cv.put(colTit, "Sierras");
		cv.put(colCos, " - Un tipo entra a una tienda:\n - Hola, ¿venden serruchos?\n - Pues no, lo siento.\n - ¿Y sierras?\n - A las nueve en punto.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 5);
		cv.put(colTit, "Bocata de jamón");
		cv.put(colCos, " - Entra un tipo en una tienda:\n - Hola, me da un bocadillo de jamón.\n - ¿York?\n - Sí, túrk.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 6);
		cv.put(colTit, "Pulgas difuntas");
		cv.put(colCos, " - ¿A dónde van las pulgas cuando mueren?\n - ...\n - Al pulgatorio.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 7);
		cv.put(colTit, "Batman forever");
		cv.put(colCos, " - Llega un señor al videoclub y dice:\n - Quiero alquilar “Batman Forever”.\n - No se puede, señor, tiene que devolverla tomorrow.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 8);
		cv.put(colTit, "Palomas exageradas");
		cv.put(colCos, " - Dos hombres, amigos de la infancia, se encuentran:\n - ¿Y tú ahora a que te dedicas?\n - Pues tengo una granja con 10.000 palomas.\n - ¿Mensajeras?\n - No, no te exagero nada.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 9);
		cv.put(colTit, "Adicto");
		cv.put(colCos, " - ¡Mamá, mamá, en el cole me dicen que soy un adicto al Twitter!\n - ¿Y cuándo te han dicho eso?\n - \"Hace 45 minutos vía Twitter for Android\".");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 10);
		cv.put(colTit, "No seas tonto");
		cv.put(colCos, "- Manuel ¿como ha ido el juicio?\n - el juez ha dicho que 5 años de carcel o 95 mil euros.\n - Tú no seas tonto,coge el dinero!");
		cv.putNull(colRate);
		db.insert(database, null, cv);
		
		cv.put(colNum, 11);
		cv.put(colTit, "Aceros");
		cv.put(colCos, " - Van dos por la calle y pasan cerca de un cartel que pone:\n - \"Aceros inoxidables\"\n - Y le dice uno al otro:\n - Pss, Paco, ¿nos hacemos?.");
		cv.putNull(colRate);
		db.insert(database, null, cv);
	}
 
	/**
	 * Event que es produeix quan s'ha d'actualitzar la BD a una versió superior
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int versioAnterior, int versioNova) {
		// NOTA: Per simplificar l'exemple, aquí s'utilitza directament
		// l'opció d'eliminar la taula anterior i tornar-la a crear buida
		// amb la nova estructura.
		// No obstant, el més habitual és migrar les dades de la taula antiga
		// a la nova, per la qual cosa aquest mètode hauria de fer més coses.
 
		// S'elimina la versió anterior de la taula
		db.execSQL("DROP TABLE IF EXISTS Acudits");
		// Es crea la nova versió de la taula
		db.execSQL(SQL_CREATE);
	}
}
