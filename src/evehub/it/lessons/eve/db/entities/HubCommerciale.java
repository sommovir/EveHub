/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evehub.it.lessons.eve.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
@Entity
@DiscriminatorColumn(name="BASE_TYPE")
@DiscriminatorValue("HUB")
public class HubCommerciale extends Base implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany
    private List<OggettoInVendita> oggettiInVendita = new ArrayList<OggettoInVendita>();


    public List<OggettoInVendita> getOggettiInVendita() {
        return oggettiInVendita;
    }

    public void setOggettiInVendita(List<OggettoInVendita> oggettiInVendita) {
        this.oggettiInVendita = oggettiInVendita;
    }
    
    public void addOggettoInVendita(OggettoInVendita inVendita){
        this.oggettiInVendita.add(inVendita);
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HubCommerciale)) {
            return false;
        }
        HubCommerciale other = (HubCommerciale) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evehub.it.lessons.eve.db.entities.HubCommerciale[ id=" + getId() + " ]";
    }
    
}
