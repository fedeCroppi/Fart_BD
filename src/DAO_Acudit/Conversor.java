package DAO_Acudit;

import com.garu.BD.SQLiteHelper;
import com.garu.fart.Acudit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
/**
 * Classe conversora d'objectes Acudit a BD
 * 
 * @author Marc Nicolau Reixach
 *
 */
public class Conversor {
 
	private SQLiteHelper helper;
	private final String database = "acuditsBD";
	private final String colNum = "num";
	private final String colTit = "titol";
	private final String colCos = "cos";
	private final String colRate = "rate";
 
	/**
	 * Constructor per defecte
	 */
	public Conversor() {
 
	}
 
	/**
	 * Constructor amb paràmetres
	 * @param helper l'ajudant de la BD de Acudits
	 */
	public Conversor(SQLiteHelper helper) {
		this.helper = helper;
	}
 
	/**
	 * Desa un nou acudit a la taula
	 * @param acudit l'objecte a desar
	 * @return l'id del nou acudit desat
	 */
	public long insert(Acudit acudit) {		
		long index = -1;
		// s'agafa l'objecte base de dades en mode escriptura
		SQLiteDatabase db = helper.getWritableDatabase();
		// es crea un objecte de diccionari (clau,valor) per indicar els valors a afegir 
		ContentValues dades = new ContentValues();
 
		dades.put(colNum, acudit.getNum());
    	dades.put(colTit, acudit.getTitol());
    	dades.put(colCos, acudit.getCos());
    	if(acudit.getRate()!= null){
        	dades.put(colRate, acudit.getRate());
    	}else{
        	dades.putNull(colRate);
    	}
    	try {
    		index = db.insertOrThrow(database, null, dades);
    		// volem veure en el log el que passa
    		Log.i("Acudit", dades.toString() + " afegit amb codi " + index);
    	}
    	catch(Exception e) {
    		// volem reflectir en el log que hi ha hagut un error
    		Log.e("Acudit", e.getMessage());
    	}
    	return index;
	}
	

	public int update(Acudit acu) {
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colNum, acu.getNum());
		cv.put(colTit, acu.getTitol());
		cv.put(colCos, acu.getCos());
		cv.put(colRate, acu.getRate());
		return db.update(database, cv, "num = ?", new String[]{String.valueOf(acu.getNum())});
	}
 
	/**
	 * Retorna un cursor amb totes les dades de la taula
	 * @return
	 */
	public Cursor getAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
 
		return db.query(true, database, new String[]{colNum, colTit, colCos, colRate}, null, null, null, null, null, null);
	}
	
	/**
	 * Retorna un cursor amb totes les dades de la taula que compleixen amb la condició
	 * @return
	 */
	public Cursor getAll(String cerca) {
		SQLiteDatabase db = helper.getReadableDatabase();
 
		return db.query(true, database, new String[]{colNum, colTit, colCos, colRate}, "titol LIKE ?", new String[]{"%" + cerca + "%"}, null, null, null, null);
	}
	
	/**
	 * Retorna un cursor amb totes les dades de la taula que compleixen amb la condició
	 * @return
	 */
	public Cursor getAcudit(int cerca) {
		SQLiteDatabase db = helper.getReadableDatabase();
 
		return db.query(true, database, new String[]{colNum, colTit, colCos, colRate}, "num = ?", new String[]{"" + cerca}, null, null, null, null);
	}
 
	/**
	 * Esborra el acudit passat per paràmetre
	 * @param acu el acudit a esborrar
	 * @return la quantitat de acudits eliminats
	 */
	public boolean remove(Acudit acu) {		
		// obtenir l'objecte BD en mode esriptura
		SQLiteDatabase db = helper.getWritableDatabase();
 
		return db.delete(database, "num=?", new String[]{"" + acu.getNum()}) > 0;
	}
	/**
	 * Esborra tots els acudits de la taula
	 * @return 
	 */
	public boolean removeAll() {		
		// obtenir l'objecte BD en mode escriptura
		SQLiteDatabase db = helper.getWritableDatabase();
 
		return db.delete(database, null, null ) > 0;
	}
}