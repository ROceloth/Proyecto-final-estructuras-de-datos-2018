
package Red;

import Grafo.GrafoM;//solo esta
import java.io.File;

/**
 * Es en esta clase que provaremos el grafo con el xml
 * @author Zaldivar Rico William Oceloth
 * @version 1.0
 */
public class TestComunicacionesTelefonicas {
    public static void main(String [] args){
        try{
            ComunicacionesTelefonicas ct = new ComunicacionesTelefonicas();//es el tramite
            File f = new File("comunicacion.xml");
            GrafoM g = ct.inicializate(f);
            ct.menu(g);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
