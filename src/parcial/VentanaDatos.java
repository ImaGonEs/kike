package parcial;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Clase de ventana para muestra de datos de centros escolares y feedback de mentoras
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
@SuppressWarnings("serial")
public class VentanaDatos extends JFrame {
	
	private JTable tDatos;  // JTable de datos de la ventana
	
	/** Crea una nueva ventana
	 */
	public VentanaDatos() {
		// Configuraci�n general
		
		setTitle( "Ventana de datos" );
		setSize( 800, 600 ); // Tama�o por defecto
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creaci�n de componentes y contenedores
		JPanel pBotonera = new JPanel();
		tDatos = new JTable();
		JButton bCargaFeedback = new JButton( "Carga feedback" );
		// Asignaci�n de componentes
		pBotonera.add( bCargaFeedback );
		getContentPane().add( new JScrollPane( tDatos ), BorderLayout.CENTER );
		getContentPane().add( pBotonera, BorderLayout.SOUTH );
		// Eventos
		bCargaFeedback.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickCargaFeedback();
			}
		});
		// Cierre
		setLocationRelativeTo( null );  // Centra la ventana en el escritorio
	}
	
	/** Asigna una tabla de datos a la JTable principal de la ventana
	 * @param tabla	Tabla de datos a visualizar
	 */
	
	public void setTabla( Tabla tabla ) {
		tDatos.setModel( tabla.getTableModel() );
	}
	
	
	// ========================================
	// Eventos

	// Click bot�n de carga de feedback
	
	private void clickCargaFeedback() {
		// C�lculo de datos en funci�n del seguimiento del mentoring
		seguimientoSesiones();
	}
	
	private static void seguimientoSesiones() {
		try {
			Tabla mentoras = Tabla.processCSV( VentanaDatos.class.getResource( "Mentoring2018.csv" ).toURI().toURL() );
			int columnaSesion = mentoras.getColumnWithHeader( "N�mero de sesi�n", false );
			int columnaCentro = mentoras.getColumnWithHeader( "COD", true );
			int columnaNumEsts = mentoras.getColumnWithHeader( "N� de chicas/os", false );
			int columnaSatMent = mentoras.getColumnWithHeader( "Tu nivel de satisf", false );
			int columnaSatEst = mentoras.getColumnWithHeader( "satisfacci�n de los chicas/os", false );
			for (int fila=0; fila<mentoras.size(); fila++) {
				try {
					int numSesion = Integer.parseInt( mentoras.get( fila, columnaSesion ) );
					String codCentro = mentoras.get( fila, columnaCentro );
					int numEstuds = Integer.parseInt( mentoras.get( fila, columnaNumEsts ) );
					int satMentora = Integer.parseInt( mentoras.get( fila, columnaSatMent ) );
					int satEstuds = Integer.parseInt( mentoras.get( fila, columnaSatEst ) );
					CentroEd centro = Datos.centros.get( codCentro );
					
						if (centro!=null) {
						
						centro.getContSesiones().inc();    // Incrementa el contador de sesiones
						
						centro.addValMentor( satMentora ); // A�ade satisfacci�n de mentora
						
						centro.addValEstud( satEstuds );   // A�ade satisfacci�n de estudiantes
						
						centro.getEstudPorSesion()[ numSesion-1 ] += numEstuds;  // A�ade n�mero de estudiantes en la sesi�n correspondiente
						
					} else {
					
						System.err.println( "C�digo de centro incorrecto en l�nea de seguimiento: " + mentoras.getFila( fila ) );
						
					}
					
				} catch (Exception e) {
					System.err.println( "Error en l�nea de seguimiento: " + fila  );
					System.err.println( "Error en l�nea de seguimiento: " + mentoras.getFila( fila ) );
				
				}
			}
			Tabla c = Tabla.createTablaFromColl( Datos.centros.values() );
			Datos.v.setTabla( c );
		} catch (Exception e) { e.printStackTrace(); }
	}

	
}
