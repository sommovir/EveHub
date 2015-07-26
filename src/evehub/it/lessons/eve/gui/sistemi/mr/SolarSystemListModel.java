/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.gui.sistemi.mr;
import evehub.it.lessons.eve.db.entities.SistemaSolare;
import evehub.it.lessons.eve.logic.events.DBEventDispatcher;
import evehub.it.lessons.eve.logic.events.DBEventListener;
import java.beans.Beans;
import javax.swing.DefaultListModel;

/**
 *
 * @author Luca
 */
public class SolarSystemListModel extends DefaultListModel<SistemaSolare> implements DBEventListener{

    public SolarSystemListModel() {
        if(!Beans.isDesignTime()){
            DBEventDispatcher.getInstance().addDBEventListener(this);
        }
    }

    @Override
    public void entitiesCreated(Object entity) {
        if(entity instanceof SistemaSolare){
            this.addElement((SistemaSolare)entity);
        }
    }

    @Override
    public void entitiesUpdated(Object entity) {
        
    }

    @Override
    public void entitiesDeleted(Object entity) {
        
    }
    
    

    
}