/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author 8314
 */
@Entity
@Table(name = "transport")
@NamedQueries({
    @NamedQuery(name = "Transport.findAll", query = "SELECT t FROM Transport t"),
    @NamedQuery(name = "Transport.findByIdTransport", query = "SELECT t FROM Transport t WHERE t.idTransport = :idTransport"),
    @NamedQuery(name = "Transport.findByNameTransport", query = "SELECT t FROM Transport t WHERE t.nameTransport = :nameTransport")})
public class Transport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_transport")
    private Integer idTransport;
    @Column(name = "name_transport")
    private String nameTransport;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransport")
    private Collection<Trip> tripCollection;

    public Transport() {
    }

    public Transport(Integer idTransport) {
        this.idTransport = idTransport;
    }

    public Integer getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Integer idTransport) {
        this.idTransport = idTransport;
    }

    public String getNameTransport() {
        return nameTransport;
    }

    public void setNameTransport(String nameTransport) {
        this.nameTransport = nameTransport;
    }

    public Collection<Trip> getTripCollection() {
        return tripCollection;
    }

    public void setTripCollection(Collection<Trip> tripCollection) {
        this.tripCollection = tripCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransport != null ? idTransport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transport)) {
            return false;
        }
        Transport other = (Transport) object;
        if ((this.idTransport == null && other.idTransport != null) || (this.idTransport != null && !this.idTransport.equals(other.idTransport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pt.passenger_transportation.entity.Transport[ idTransport=" + idTransport + " ]";
    }
    
}
