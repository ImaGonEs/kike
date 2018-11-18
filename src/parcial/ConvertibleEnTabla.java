package parcial;

/** Interfaz para las clases que pueden pasarse de forma autom�tica a objetos de tipo Tabla
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public interface ConvertibleEnTabla {
	/** Devuelve el n�mero de columnas que se representan para el objeto
	 * @return	N�mero de columnas
	 */
	public int getNumColumnas();
	/** Devuelve el valor String del objeto correspondiente a una columna concreta
	 * @param col	N�mero de columna (de 0 a getNumColumnas()-1)
	 * @return	Valor string de ese dato del objeto
	 */
	public String getValorColumna( int col );
	/** Devuelve el nombre de la columna
	 * @param col	N�mero de columna (de 0 a getNumColumnas()-1)
	 * @return	Nombre de t�tulo de ese dato del objeto
	 */
	public String getNombreColumna( int col );
}
