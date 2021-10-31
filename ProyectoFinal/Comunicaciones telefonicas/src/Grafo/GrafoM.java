
package Grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Grafo no dirigido con una matriz de adyacencia
 * @author Zaldivar Rico William Oceloth
 */
public class GrafoM implements GrafoND{
    
   static int INFINITO = 1000000;//osea un numerote para los enteros, que represente un infinito
    
    private int numVerts; //numero de vertices del grafo
    private static int MaxVerts = 40; //ya para lo que pide y mas
    private Vertice [] verts;
    private int [][] matAdy; //la matriz de adyacencia enteros solo 0 y 1
    
    //este atributo es especial solo para recuperar los camnios
    private int [][] path; //t de traza, un camino de los indices naturales
    
    public GrafoM(){ //inicializar
        this(MaxVerts);
    }
    
    private GrafoM(int max){ //la chida
        matAdy = new int [max][max];
        verts = new Vertice[max];
        path = new int[max][max];
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                matAdy[i][j] = 0;
                path[i][j] = 0; //tambien a la par
            }
        }
        numVerts = 0;
    }
    

    @Override//busca ya estos vertices en el arreglo de vertices por su nombre
    public void arista(String u, String v) throws Exception{ //crea un nuevo arco
        int va, vb;
        va = numVertice(u);
        vb = numVertice(v);
        
        if (va < 0 || vb < 0 ) {
            throw new Exception("No exite el verice");
        }
        
        matAdy[va][vb] = 1; //registrado
        matAdy[vb][va] = 1; //simetrico porque es no dirigida
    }
    
    /**
     * El mismo que el de arriba pero con los identificadores ID de area
     * @param u
     * @param v
     * @throws Exception 
     */
    public void arista(int u, int v) throws Exception{
        int va, vb;
        va = numVertice(u);
        vb = numVertice(v);
        
        if (va < 0 || vb < 0) {
            throw new Exception("No existe el vertice");
        }
        
        matAdy[va][vb] = 1;
        matAdy[vb][va] = 1;
    }
    
    
    @Override //es una matriz de adyacencia por lo tanto...
    public void borrarArco(String u, String v) throws Exception{
        int va, vb;
        va = numVertice(u);
        vb = numVertice(v);
        
        if (va < 0 || vb < 0) {
            throw new Exception("No existe el vertice");
        }
        
        matAdy[va][vb] = 0;
        matAdy[vb][va] = 0; //porque es no dirigida
    }

    @Override
    public boolean adyacente(String u, String v) throws Exception{
        int va, vb;
        va = numVertice(u);
        vb = numVertice(v);
        
        if (va < 0 || vb < 0) {
            throw new Exception("No existe el vertice");
        }
        return matAdy[va][vb] == 1; //el ya tu sabhe
    }

    @Override
    public void nuevoVertice(Vertice u) { //ya tiene sus clientes este vertice
        boolean esta = numVertice(u) >= 0;
        if (!esta) {
            //u.setNumID(numVerts); ya directo con los vertices
            verts[numVerts++] = u; //cambia y se registra, el indice lleva el control
        }
    }
    
    /*
    Revisa si ya esta el vertice
    -1 si no esta
    */
    private int numVertice(Vertice u) {
        boolean encontrado = false;
        int i = 0;//ya con i, aqui
        for (; (i < numVerts) && !encontrado;) {
            if (verts[i] != null) { //por si los huecos
                encontrado = verts[i].equals(u); //el de los verices
                if (!encontrado) {
                    i++;
                }
            }else{
                return -1;
            }
            
        }
        return (i < numVerts) ? i: -1;
    }
    
    /*
    Lo mismo que el otro pero este busca cuando solo tiene que localizar 
    el verice
    */
    private int numVertice(String u){
        boolean encontrado = false;
        int i = 0;
        for (;(i<numVerts) && !encontrado;) {
            if (verts[i] != null) {
                encontrado = verts[i].getEstacion().equals(u); //comparacion por nombre
                if (!encontrado) {
                    i++;
                }
            }else{
                return -1; //sino exite no esta y punto
            }
            
        }
        return (i < numVerts) ? i: -1; //por si acaso
    }
    
    /*
    Lo mismo pero con identificador de numero
    */
    private int numVertice(int u){//por si algun vertice tiene el mismo ID, sera el ultimo
        boolean encontrado = false;
        int i = 0;
        for (; (i < numVerts) && !encontrado; ) {
            if (verts[i] != null) {//empiezan en 1 los vertices
                encontrado = verts[i].getNumID() == u;//pero se guardan desde cero
                if (!encontrado) {//getNumID ya sabe que hacer
                    i++;
                }
            }else{
                return -1;
            }
        }
        return (i < numVerts) ? i: -1;
    }
    
    
    @Override //solo ya no aparece este vertice en la matriz
    public void borraVertice(String u) throws Exception{//i.e la columna y fila donde se encuentra seran 0
        //tambien borra el vertice del arreglo asi no parece una componente disconexa
        int va = numVertice(u);
        
        if (va < 0) {
            throw new Exception("No existe el vertice");
        }
        
        for (int i = 0; i <MaxVerts ; i++) { //por fila
            matAdy[va][i] = 0;
        }
        for (int i = 0; i < MaxVerts; i++) { //por columna
            matAdy[i][va] = 0; //generico
        }
        
        verts[va] = null; //esto implica que pueden quedar huecos
        //de momento numVerts sigue siendo el indice en el arreglo de vertices
    }

    /*
    Aunque el proyecto dice que la grafica no se modificara, estos metodos para
    borrar estan aqui porque son el tramite de la interfaz del grafo
    y por lo tanto del TDA del grafo
    */
    
    /**
     * Si no tiene vertices un grafo no se le puede hacer nada
     * numVerts cambia a positivo con la llamada a nuevo vertice
     * @return true si no hay vertices en el grafo
     */
    public boolean isEmpty(){
        return numVerts == 0;
    }
    
    
    /**
     * Busca si se encuentra el telefono pasado en alguna
     * estacion i.e algun vertice y devuelve la posicion de este que 
     * ocupa en el arreglo y por lo tanto su identificador para la matriz
     * @param t Telefono a buscar
     * @return int posicion para buscar, -1 si no lo encuentra
     */
    public int buscarTel(String t){
        
        int z = -1; //por si no se encuentra, nunca cambiara
        for (int i=0; i < verts.length; i++) {
            if (verts[i] != null) {
                Vertice aux = verts[i];//recuperamos un vertice
                Iterator <Cliente>iterator = aux.getIteratorC();
                while(iterator.hasNext()){
                    Cliente c = iterator.next();
                    if (c.getTelefono().equals(t)) { //si es este telefono
                        z = i; //recordando esta iteracion
                    }
                }
            }
        }
        
        return z;
    }
    
    /**
     * Regresa el nombre de la estacion i.e el nombre del vertice
     * No esta muy desacoplado pero es solo para un unico uso
     * @param index indece a buscar en los verices
     * @return El nombre de la Estacion
     */
    public String getNameEstation(int index){
        return verts[index].getEstacion();
    }
    
    
    
    
    /**
     * Con la matriz de adyacencia podemos clacular TODOS
     * los caminos mininos de TODOS los vertices a TODOS los demas
     * vertices, este es el algoritmo de Floyd  que corre en O(n^3)
     * la modificacion aqui es que los caminos tienen peso 1 y INFINITO
     * si no existe tal arista, se tiene una matriz a partir de la de 
     * adyacencia para trabajar este algoritmo.
     * @return la matriz de todo los caminos minimos entre cualesquiera 
     * dos pares de vertices inidados en su matriz de adyacencia.
     */
    private int[][] todosLosCaminosM(){
        
        int n = numVerts;
        
        //preprocesamineto para la matriz final path -> ans
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matAdy[i][j] == 0 && i!=j) { //la diagonal se queda en ceros
                    path[i][j] = -1;
                } else {
                    path[i][j] = i;
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            path[i][i] = i;
        }
        
        //una copia adecuada para el algoritmo ya que Floyd maneja pesos
        int m [][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matAdy[i][j] == 0 && i!=j) {
                    m[i][j] = 1000000; //un numero grande que represente que es
                    //innalcanzable, i.e no existe el arco
                }else{
                    m[i][j] = matAdy[i][j];
                }
            }
        }
                
        int[][] ans = new int[n][n];
        copy(ans, m);
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (ans[i][k] + ans[k][j] < ans[i][j]) {
                        ans[i][j] = ans[i][k] + ans[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }
        
        return ans;
        
        
    }//al tener esto es peligroso no contemplar los huecos de verts
    
    
    /**
     * Copia de b en a
     * @param a
     * @param b 
     */
    public static void copy(int[][] a, int[][] b) 
    {
        for (int i=0;i<a.length;i++){
            for (int j=0;j<a[0].length;j++){
                a[i][j] = b[i][j];
            }
        }
    }
    
    
    /**
     * Usando ahora a t (traza) recuperamos el camino entr dos vertices
     * Es importante que primero se haya ejecutado el metodo de arriba
     * los parametros son indices naturales
     * @param start origen 
     * @param end destino
     */
    private void recuperarCamino(int start, int end){
        Vertice aux = verts[end];
        String theWay = aux.getEstacion(); //un backtraking sabrosongo por el camino
        //Do you know the way?
        
        
        
        
        while(path[start][end] != start){
            int indexN = path[start][end]; //para recuperar el nombre
            
            Vertice z = verts[indexN];
            theWay = z.getEstacion() + " -> " + theWay;
            //nueva comprovacion
            
            end = path[start][end];
            
        }
        
        aux = verts[start];
        
        theWay = aux.getEstacion() + " -> " + theWay;
        //el camino (theWay)
        System.out.println(theWay);
    }//directamente mostrara el camnio (theWay)
    
    // Esto es para la interfaz de un metodo chulo
    
    public int[][] getAllTheWays() {
        return todosLosCaminosM();
    }

    public void showMeTheWay(int origen, int destino) {
        recuperarCamino(origen, destino);
    }
    
    /**
     * Por ultimo una lista de los vertices para poder iterar sobre ella
     * Itera directamente sobre los vertices como lista
     * @return un ArrayList de vertices
     */
    public ArrayList<Vertice> getIteratorV(){
         ArrayList<Vertice> aux = new ArrayList<>(Arrays.asList(verts));
         return aux;
    }
    
}
