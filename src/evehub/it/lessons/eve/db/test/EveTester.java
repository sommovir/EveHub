/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.test;

import evehub.it.lessons.eve.db.controllers.BaseJpaController;
import evehub.it.lessons.eve.db.controllers.HubCommercialeJpaController;
import evehub.it.lessons.eve.db.entities.Base;
import evehub.it.lessons.eve.db.entities.HubCommerciale;
import javax.persistence.Persistence;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public class EveTester {
    
    public static void main(String [] args){
        
        System.out.println("ciao eve");
        Base b = new Base();
        b.setNome("Base di Saturno");
        
        Base b2 = new Base();
        b2.setNome("Base di Urano");
        
        HubCommerciale hub = new HubCommerciale();
        hub.setNome("Hub commerciale");
        
        BaseJpaController baseJpaController = new BaseJpaController(Persistence.createEntityManagerFactory("EveHubPU"));
        HubCommercialeJpaController hubCommercialeJpaController = new HubCommercialeJpaController(Persistence.createEntityManagerFactory("EveHubPU"));
        
        baseJpaController.create(b);
        System.out.println("creata base 1");
        baseJpaController.create(b2);
        System.out.println("creata base 2");
        hubCommercialeJpaController.create(hub);
        System.out.println("creato hub");
        
        System.out.println("fine.");
        
        
        
        
    }
    
}
