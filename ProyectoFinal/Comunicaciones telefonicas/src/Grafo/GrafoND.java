
package Grafo;

/**
 * Interfaz para el TDA de un grafo no dirgido
 * Se utilizara para la representacion de las relaciones una matriz de 
 * adyacencia, esta matriz es un atributo del grafo en si
 * @author Zaldivar Rico William Oceloth
 * @version 1.0
 */
public interface GrafoND {
    
    /**
     * Añade el arco o arista (u,v) al grafo.
     * dado sus identificadores de estacion
     * @param u primer vertice
     * @param v segundo vertice
     * @throws java.lang.Exception si no exite un vetice para crear la arista
     */
    void arista(String u,String v) throws Exception;
    
    /**
     * Elimina del grafo el arco(u,v).
     * @param u primer vertice
     * @param v segundo veritce
     * @throws java.lang.Exception por si algun problema de existencia
     */
    void borrarArco(String u, String v) throws Exception;
    
    /**
     * Operación que devuelve cierto si los vértices u, v forman un arco.
     * Con puro identificador de vertice 
     * i.e su nombre = nombre de la estacion
     * @param u primer vertice
     * @param v segundo vertice
     * @return true si son adyacentes
     */
    boolean adyacente(String u, String v) throws Exception;
    
    /**
     * Añade el vertice u al grafo, ya con su lista de clientes
     * i.e el nombre de la estacion
     * @param u verice a añadir
     */
    void nuevoVertice(Vertice u);
    
    /**
     * Elimina el vertice u del grafo
     * por el nombre de la estacion (nodo del grafo)
     * @param u vertice a eliminar
     * @throws java.lang.Exception
     */
    void borraVertice(String u) throws Exception;
}
