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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;

/**
 *
 * @author 8314
 */
@Entity
@Table(name = "trip")
@NamedQueries({
    @NamedQuery(name = "Trip.findAll", query = "SELECT t FROM Trip t"),
    @NamedQuery(name = "Trip.findByIdTrip", query = "SELECT t FROM Trip t WHERE t.idTrip = :idTrip"),
    @NamedQuery(name = "Trip.findByDatetime", query = "SELECT t FROM Trip t WHERE t.datetime = :datetime"),
    @NamedQuery(name = "Trip.findByLimit", query = "SELECT t FROM Trip t WHERE t.limit = :limit"),
    @NamedQuery(name = "Trip.findByDatetimeend", query = "SELECT t FROM Trip t WHERE t.datetimeend = :datetimeend")})
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_trip")
    private Integer idTrip;
    @Basic(optional = false)
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datetime;
    @Column(name = "limit")
    private Integer limit;
    @Basic(optional = false)
    @Column(name = "datetimeend")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datetimeend;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrip")
    private Collection<Booking> bookingCollection;
    @JsonIgnore
    @JoinColumn(name = "id_route", referencedColumnName = "id_route")
    @ManyToOne(optional = false)
    private Routes idRoute;
    @JsonIgnore
    @JoinColumn(name = "id_transport", referencedColumnName = "id_transport")
    @ManyToOne(optional = false)
    private Transport idTransport;

    public Trip() {
    }

    public Trip(Integer idTrip) {
        this.idTrip = idTrip;
    }

    public Trip(Integer idTrip, LocalDateTime datetime, LocalDateTime datetimeend) {
        this.idTrip = idTrip;
        this.datetime = datetime;
        this.datetimeend = datetimeend;
    }

    public Integer getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(Integer idTrip) {
        this.idTrip = idTrip;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public LocalDateTime getDatetimeend() {
        return datetimeend;
    }

    public void setDatetimeend(LocalDateTime datetimeend) {
        this.datetimeend = datetimeend;
    }

    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    public Routes getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Routes idRoute) {
        this.idRoute = idRoute;
    }

    public Transport getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Transport idTransport) {
        this.idTransport = idTransport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrip != null ? idTrip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trip)) {
            return false;
        }
        Trip other = (Trip) object;
        if ((this.idTrip == null && other.idTrip != null) || (this.idTrip != null && !this.idTrip.equals(other.idTrip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pt.passenger_transportation.entity.Trip[ idTrip=" + idTrip + " ]";
    }
    
}
