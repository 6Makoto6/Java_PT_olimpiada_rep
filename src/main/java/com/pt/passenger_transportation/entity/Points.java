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
@Table(name = "points")
@NamedQueries({
    @NamedQuery(name = "Points.findAll", query = "SELECT p FROM Points p"),
    @NamedQuery(name = "Points.findByIdPoint", query = "SELECT p FROM Points p WHERE p.idPoint = :idPoint"),
    @NamedQuery(name = "Points.findByNamePoint", query = "SELECT p FROM Points p WHERE p.namePoint = :namePoint"),
    @NamedQuery(name = "Points.findByAddressPoint", query = "SELECT p FROM Points p WHERE p.addressPoint = :addressPoint")})
public class Points implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_point")
    private Integer idPoint;
    @Column(name = "name_point")
    private String namePoint;
    @Column(name = "address_point")
    private String addressPoint;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStartPoint")
    private Collection<Routes> routesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFinishPoint")
    private Collection<Routes> routesCollection1;

    public Points() {
    }

    public Points(Integer idPoint) {
        this.idPoint = idPoint;
    }

    public Integer getIdPoint() {
        return idPoint;
    }

    public void setIdPoint(Integer idPoint) {
        this.idPoint = idPoint;
    }

    public String getNamePoint() {
        return namePoint;
    }

    public void setNamePoint(String namePoint) {
        this.namePoint = namePoint;
    }

    public String getAddressPoint() {
        return addressPoint;
    }

    public void setAddressPoint(String addressPoint) {
        this.addressPoint = addressPoint;
    }

    public Collection<Routes> getRoutesCollection() {
        return routesCollection;
    }

    public void setRoutesCollection(Collection<Routes> routesCollection) {
        this.routesCollection = routesCollection;
    }

    public Collection<Routes> getRoutesCollection1() {
        return routesCollection1;
    }

    public void setRoutesCollection1(Collection<Routes> routesCollection1) {
        this.routesCollection1 = routesCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoint != null ? idPoint.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Points)) {
            return false;
        }
        Points other = (Points) object;
        if ((this.idPoint == null && other.idPoint != null) || (this.idPoint != null && !this.idPoint.equals(other.idPoint))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pt.passenger_transportation.entity.Points[ idPoint=" + idPoint + " ]";
    }
    
}
