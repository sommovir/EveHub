/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.logic.events;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public interface DBEventListener {
    
    public void entitiesCreated(Object entity);
    
    public void entitiesUpdated(Object entity);
    
    public void entitiesDeleted(Object entity);
    
}
