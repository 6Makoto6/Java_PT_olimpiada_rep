/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author 8314
 */
@Entity
@Table(name = "routes")
@NamedQueries({
    @NamedQuery(name = "Routes.findAll", query = "SELECT r FROM Routes r"),
    @NamedQuery(name = "Routes.findByIdRoute", query = "SELECT r FROM Routes r WHERE r.idRoute = :idRoute")})
public class Routes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_route")
    private Integer idRoute;
    @JsonIgnore
    @JoinColumn(name = "id_start_point", referencedColumnName = "id_point")
    @ManyToOne(optional = false)
    private Points idStartPoint;
    @JsonIgnore
    @JoinColumn(name = "id_finish_point", referencedColumnName = "id_point")
    @ManyToOne(optional = false)
    private Points idFinishPoint;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRoute")
    private Collection<Trip> tripCollection;

    public Routes() {
    }

    public Routes(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Integer getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Points getIdStartPoint() {
        return idStartPoint;
    }

    public void setIdStartPoint(Points idStartPoint) {
        this.idStartPoint = idStartPoint;
    }

    public Points getIdFinishPoint() {
        return idFinishPoint;
    }

    public void setIdFinishPoint(Points idFinishPoint) {
        this.idFinishPoint = idFinishPoint;
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
        hash += (idRoute != null ? idRoute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routes)) {
            return false;
        }
        Routes other = (Routes) object;
        if ((this.idRoute == null && other.idRoute != null) || (this.idRoute != null && !this.idRoute.equals(other.idRoute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pt.passenger_transportation.entity.Routes[ idRoute=" + idRoute + " ]";
    }
    
}
