/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author tonir
 */
// Clase NodoFragmento para representar cada fragmento en la lista enlazada
public class NodoFragmento {

    private String fragmento;
    private NodoFragmento siguiente;

    public NodoFragmento(String fragmento) {
        this.fragmento = fragmento;
        siguiente = null;
    }

    public String getFragmento() {
        return fragmento;
    }

    public void setFragmento(String fragmento) {
        this.fragmento = fragmento;
    }

    public NodoFragmento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoFragmento siguiente) {
        this.siguiente = siguiente;
    }
}
