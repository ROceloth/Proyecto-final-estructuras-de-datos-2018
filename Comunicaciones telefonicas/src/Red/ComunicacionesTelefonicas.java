
package Red;

import Grafo.*;//la estructura que usare
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * En esta clase se corre la red de comunicaciones
 * @author Zaldivar Rico William Oceloth
 * @version 1.0
 */
public class ComunicacionesTelefonicas {
    
    /**
     * Inicializacion del grafo segun el archivo XML que le pasen
     * @param f de tipo file para XML
     * @return Grafo no dirigido con matrice de adyacencia, maximo de 40x40
     * @throws java.lang.Exception
     */
    public GrafoM inicializate(File f) throws Exception{
        GrafoM grafo = new GrafoM(); //la red
       

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(f);

            document.getDocumentElement().normalize();

            //comenzamos la inizializacion de los vertices
            NodeList listaEstaciones = document.getElementsByTagName("Estacion");
            for (int temp = 0; temp < listaEstaciones.getLength(); temp++) {

                Element estacion = (Element) listaEstaciones.item(temp);
                //atributos de la estacion (verice)
                String nameStation = estacion.getAttribute("nombreEstacion");
                String codigo = estacion.getAttribute("codigo");
                int ID = Integer.valueOf(codigo);

                Vertice v = new Vertice(nameStation, ID); //elvertice inicial

                //ahora su lista de clientes del vertice, bajando un nivel
                NodeList listaClientes = estacion.getElementsByTagName("Cliente");
                for (int aux = 0; aux < listaClientes.getLength(); aux++) {
                    Element cel = (Element) listaClientes.item(aux);
                    //atibutos de los clientes
                    String nameCel = cel.getAttribute("nombreCliente");
                    String telefono = cel.getAttribute("telefono");

                    //creamos al cliente
                    Cliente c = new Cliente(nameCel, telefono);
                    v.addCliente(c);//añadido al vertice
                }

                //agregamos el vertice al grafo
                grafo.nuevoVertice(v);
            }//end iteraciones de estaciones

            //ahora los enlaces para la matriz de adyacencias
            NodeList listaEnlaces = document.getElementsByTagName("Enlace");
            for (int i = 0; i < listaEnlaces.getLength(); i++) {

                Element enlace = (Element) listaEnlaces.item(i);
                //atributo de los enlaces
                String firts = enlace.getAttribute("primeraEstacion");
                int u = Integer.valueOf(firts);

                String second = enlace.getAttribute("segundaEstacion");
                int v = Integer.valueOf(second);

                //se registra en la matriz de adyacencia, es simetra ya este rollo
                grafo.arista(u, v);//sabe directo por el identificador segun el .xml
                //aun comprueva por el TDA del grafo
            }//end enlaces

        
        return grafo;
    }
    
    
    /**
     * Implementa las operaciones sobre el grafo que se pasa
     * todos los grafos manejan una matriz de adyacencias
     * @param grafo GrafoM ya inicializado
     */
    public void menu(GrafoM grafo) throws Exception {
        if (grafo.isEmpty()) {//si pasa este punto continua con los demas
            throw new Exception("Grafo vacio");
        } else {
            Scanner sc = new Scanner(System.in);
            int x;
            do {
                System.out.println("Operaciones sobre la red de comunicaciones telefonicas");
                System.out.println("(1).- Determinar si es posible la comunicacion sobre dos telefonos.\n"
                        + "(2).- Despliegue del reparto de la publicidad sobre los usuarios.\n"
                        + "(3).- Salir.\n"
                        + "(1/2/3)");
                x = sc.nextInt();
                ejecute(x,grafo);//ejecuta las opciones, puro tramite en interfaz
            } while (x != 3);
        }
    }

    /*
    Opciones 1 y 2, la 2 describe mas comportamineto
    */
    private void ejecute(int x, GrafoM g) {
        Scanner sc = new Scanner(System.in);
        switch(x){
            case 1:
                System.out.println("Primer telefono");
                String t1 = sc.nextLine();
                System.out.println("Segundo telefono");
                String t2 = sc.nextLine();
                conexion(t1,t2,g);
                break;
            case 2:
                System.out.println("Elige un criterio para el reparto de la publicidad.\n"
                        + "(a).-  Por codigo de area.\n"
                        + "(b).- Por el numero de telefono.\n"
                        + "(a/b)");
                String p = sc.nextLine();
                repartoPub(p,g);
                break;
        }
    }

    /*
     * Determina el estado de la conexion sobre dos telefonos
     * i.e hace una busqueda sobre la grafica a patir del primer telefono
     * @param t1 primer telefono
     * @param t2 segundo telefono
     * @param g la grafica va solo pasando
     */
    private void conexion(String t1, String t2, GrafoM g) {
        int index1 = g.buscarTel(t1);//ya son como indeces naturales
        int index2 = g.buscarTel(t2);
        if (index1 < 0 || index2 < 0) {//alguno no esta en la red
            System.out.println("No se puede establecer la conexion porque los"
                    + " telefonos no se encuentran en la red");
            return;
        }
        
        int [][] allTheWays = g.getAllTheWays();
        //Comprovamos el camnino
        int distancia = allTheWays[index1][index2];
        
        if (distancia == 1000000) {//no exite el camino
            System.out.println("No hay forma de llegar de la estacion de un "
                    + "cliente a la otra");
           
        }else if(distancia > 5){//hay conexion pero no por video-fono
            System.out.println("Se puede establecer una conexion tradicional, pero "
                    + "no una conexion por el video-fono");
            System.out.println("La ruta es:");
            g.showMeTheWay(index1, index2);
            
        }else{ //se pueden ambas
            System.out.println("Se pude esblecer la conexion tradicional y"
                    + " la conexion por video-fono");
            System.out.println("La ruta es:");
            if (distancia==0) { //son de la misma estacion
                String st = g.getNameEstation(index1);
                System.out.println(st + "   (Son de la misma estacion)");
            }else{
                g.showMeTheWay(index1, index2);
            }   
        }
        
        
    }

    /**
     * Una intefaz mas que decide los metodos de recorrido sobre la grafica
     * @param p segun el criterio esta es decidida (a/b)
     * @param g la grafica va solo pasando
     */
    private void repartoPub(String p, GrafoM g) {
        switch (p){
            case "a": 
                //criterio por codigo de area, i.e los vertices
                ArrayList <Vertice>myVerts = g.getIteratorV();
                Collections.sort(myVerts);//se ordena la lista por vertices
                System.out.println(myVerts);
                
                break;
            case "b":
                //criterio por numero de telefono
                //por cada numero de telefono se recupera el codigo al 
                //que pertenece
                
                ArrayList <Vertice> oneVertOneT = g.getIteratorV(); 
                //un telefono, un vertice hasta acabar los vertices
                
                //ahora una lista de criterios B la ordenamos y nos vamos por uno tacos
                ArrayList <CriterioB> listCritB = new ArrayList<>();
                
                //por cada cliente de cada vertice se crea un CriterioB
                Iterator <Vertice>iteratorV = oneVertOneT.iterator();
                while(iteratorV.hasNext()){
                    Vertice v = iteratorV.next();
                    int ID = v.getNumID();//para todos sus clientes
                    Iterator <Cliente> iteratorC = v.getIteratorC();
                    while(iteratorC.hasNext()){
                        Cliente c = iteratorC.next();
                        listCritB.add(new CriterioB(ID,c));//y ya esta
                    }
                }
                Collections.sort(listCritB); //estos ordenamientos son Merge-Sort
                System.out.println(listCritB);
                
                break;
        }
    }
 
    /**
     * Para el Criterio B tendremos Objetos de criterio B
     * Su comparable no necesita tanto custom
     */
    private class CriterioB implements Comparable<CriterioB>{
        //lo que necesito de un criterio b) 
        
        private int ID; //el codigo de area del cliente
        private String telC; //telefono del cliente, caracteristica comparable
        private String nameC; //nombre del cliente

        public String getTelC() {
            return telC;
        }
        
        public CriterioB(int ID, Cliente c){
            this.ID=ID;
            String t = c.getTelefono();
            String n = c.getName();
            this.telC=t;
            this.nameC=n;
        }
        
        
        @Override
        public int compareTo(CriterioB t) {
            return this.telC.compareTo(t.getTelC());
        }

        @Override
        public String toString() {
            return "Codigo de Area: " + ID 
                    + "\nTelefono: " + telC 
                    + "\nCliente: " + nameC +"\n";
        }
    }//end iner class
    
}
