/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.logic.events;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public class DBEventDispatcher {
    
    private static DBEventDispatcher _instance = null;
    
    //PARTE FONDAMENTALE 1) Lista di listeners a cui registrarsi
    private List<DBEventListener> listeners = new ArrayList<>();
    
    public static DBEventDispatcher getInstance() {
        if (_instance == null) {
            _instance = new DBEventDispatcher();
            return _instance;
        } else {
            return _instance;
        }
    }
    
    private DBEventDispatcher() {
        super();
    }
    
    
    //PARTE FONDAMENTALE 2) DARE LA POSSIBILITA' DI REGISTRARSI
    public void addDBEventListener(DBEventListener listener){
        this.listeners.add(listener);
    }
    
    
    //PARTE FONDAMENTALE 3) METODO FIRE, CHE SCATENA L'EVENTO E INFORMA TUTTI I LISTENER REGISTRATI
    public void informEntityCreated(Object entity){
        for (DBEventListener listener : listeners) {
            listener.entitiesCreated(entity);
        }
    }
    
    public void informEntityUpdated(Object entity){
        for (DBEventListener listener : listeners) {
            listener.entitiesUpdated(entity);
        }
    }
    
    public void informEntityDeleted(Object entity){
        for (DBEventListener listener : listeners) {
            listener.entitiesDeleted(entity);
        }
    }
    
    
}
