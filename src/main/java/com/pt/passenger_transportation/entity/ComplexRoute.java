package com.pt.passenger_transportation.entity;


import com.pt.passenger_transportation.entity.Points;
import com.pt.passenger_transportation.entity.Trip;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComplexRoute {
    private List<Trip> segments; // Список сегментов маршрута

    public ComplexRoute() {
        this.segments = new ArrayList<>();
    }

    public void addSegment(Trip trip) {
        segments.add(trip);
    }

    public List<Trip> getSegments() {
        return segments;
    }

    public LocalDateTime getStartTime() {
        return segments.get(0).getDatetime();
    }

    public LocalDateTime getEndTime() {
        return segments.get(segments.size() - 1).getDatetimeend();
    }

    public Points getStartPoint() {
        return segments.get(0).getIdRoute().getIdStartPoint();
    }

    public Points getEndPoint() {
        return segments.get(segments.size() - 1).getIdRoute().getIdFinishPoint();
    }
}