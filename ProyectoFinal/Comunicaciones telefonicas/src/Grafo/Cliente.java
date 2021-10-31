
package Grafo;

/**
 * Esta clase modela los clientes en si.
 * Es decir los telefonos y solo la relevancia entre ellos
 * @author Zaldivar Rico William Oceloth
 * @version 1.0
 */
public class Cliente implements Comparable<Cliente>{//se comparan estos vatos
    private String name; //nombre del cliente
    private String telefono; //telefono del cliente, guardado como una cadena

    public Cliente(String name, String telefono) { //lo clasico
        this.name = name;
        this.telefono = telefono;
    }

    public String getName() {
        return name;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Telefono: " + telefono + "\nCliente: "+ name;
    }
    
    //El criterio de comparacion entre los clientes sera por su 
    //numero telefonico en este caso la cadena de telefono

    @Override //este el metodo de prioridad b), numero telefonico
    public int compareTo(Cliente t) {
        return telefono.compareTo(t.getTelefono()); //sabe, polimorfismo y delegacion
    }//pero aun asi se tiene que recuperar muchas veces
    
    
    
}
