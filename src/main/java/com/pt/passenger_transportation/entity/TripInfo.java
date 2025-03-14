/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation.entity;

import java.time.LocalDateTime;

/**
 *
 * @author 8314
 */
    public class TripInfo {

        private Integer idTrip;
        private LocalDateTime datetime;
        private LocalDateTime datetimeend;
        private String startPoint;
        private String finishPoint;

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

        public LocalDateTime getDatetimeend() {
            return datetimeend;
        }

        public void setDatetimeend(LocalDateTime datetimeend) {
            this.datetimeend = datetimeend;
        }

        public String getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(String startPoint) {
            this.startPoint = startPoint;
        }

        public String getFinishPoint() {
            return finishPoint;
        }

        public void setFinishPoint(String finishPoint) {
            this.finishPoint = finishPoint;
        }
    }
