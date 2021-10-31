
package Grafo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Clase que modela los vertices del grafo
 * todo esto esta epecialidado para la red de comunicacion
 * por lo tanto modela especificamentes estos veritices lo que necesitamos.
 * 
 * El método que añade el vértice al grafo establece el número 
 * que le corresponda. (ID)
 * @author Zaldivar Rico William Oceloth
 * @version 1.0
 */
public class Vertice implements Comparable<Vertice>{
    private String nameS;//el nombre del vertice a.k.a el nombre de la estacion
    private int numID; //el numero de identificador del vertice (es unico)
    //osea el identificador de area
    private ArrayList <Cliente> listaDeClientes;//con su metodo para añadir clientes
    
    public Vertice(String name, int ID){
        this.nameS = name;
        this.numID = ID;
        listaDeClientes = new ArrayList<>(); //inicializada
    }
    
    public int getNumID(){
        return numID;//para lo basico
    }
    
    /**
     * Devuelve el nombre de la estacion 
     * osea el nombre del vertice
     * @return nameS
     */
    public String getEstacion(){
        return nameS;
    }

    @Override
    public int hashCode() { //por tramite
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nameS);
        return hash;
    }

    @Override//por el nombre
    public boolean equals(Object obj) { //si los vetices son iguales
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertice other = (Vertice) obj;
        if (!Objects.equals(this.nameS, other.nameS)) {
            return false;
        }
        return true;
    }
    
    //asiga su ID
    public void setNumID(int numID){ 
        this.numID = numID;
    }
    
    /**
     * Cliente añadido a la lista de clientes del vertice
     * @param c cliente que se añade
     */
    public void addCliente(Cliente c){
        listaDeClientes.add(c);//un metodo asi desacopla que uso una arraylist
    }

   
    @Override //el metodo de prioridad de publicidad a)codigo de area
    public int compareTo(Vertice t) {
        return numID - t.getNumID(); //un orden muy naturalon entre areas
    }

    /**
     * Regresa un iterador para su lista de clientes
     * @return 
     */
    public Iterator<Cliente> getIteratorC(){
        return listaDeClientes.iterator();
    }
    
    @Override
    public String toString(){//polimorfismo sabrosongo entre los toString
        String s = "Codigo de Area: "+numID + "\n"
                + "Lista de Clientes:\n"
                + listaDeClientes + "\n";
        return s;
    }//es el criterio de publicidad a) 
    
}
