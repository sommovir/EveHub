/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.gui.sistemi.mr;

import evehub.it.lessons.eve.db.entities.SistemaSolare;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author Luca
 */
public class SolarSystemListRenderer extends DefaultListCellRenderer{

    public SolarSystemListRenderer() {
    }
    
    

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.
        if(c != null && c instanceof JLabel){
            ((JLabel)c).setText(((SistemaSolare)value).getNome());
            ((JLabel)c).setForeground(Color.blue);
        }
        return c;
    }
    
    
    
    
    
}
