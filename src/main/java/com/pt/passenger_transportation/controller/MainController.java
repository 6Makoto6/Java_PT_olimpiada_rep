/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation.controller;

import com.pt.passenger_transportation.entity.Booking;
import com.pt.passenger_transportation.entity.BookingInfo;
import com.pt.passenger_transportation.entity.Points;
import com.pt.passenger_transportation.entity.Routes;
import com.pt.passenger_transportation.entity.Status;
import com.pt.passenger_transportation.entity.Transport;
import com.pt.passenger_transportation.entity.Trip;
import com.pt.passenger_transportation.entity.TripInfo;
import com.pt.passenger_transportation.entity.User;
import com.pt.passenger_transportation.repository.BookingRepository;
import com.pt.passenger_transportation.repository.PointsRepository;
import com.pt.passenger_transportation.repository.RoutesRepository;
import com.pt.passenger_transportation.repository.StatusRepository;
import com.pt.passenger_transportation.repository.TransportRepository;
import com.pt.passenger_transportation.repository.TripRepository;
import com.pt.passenger_transportation.repository.UserRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 8314
 */
@RestController
@RequestMapping("/passenger_transportation")
public class MainController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PointsRepository pointsRepository;
    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private TransportRepository transportRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;

    public Integer findByTgName(String tg_name) {
        int id = 0;
        for (User user : userRepository.findAll()) {
            if (user.getTgName().equalsIgnoreCase(tg_name)) {
                id = user.getIdUser();
            }
        }
        return id;
    }

    //Создание пользователя
    @PostMapping("/add_user")
    public @ResponseBody
    boolean addUser(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "tg_name") String tg_name
    ) {
        User user = new User();
        user.setName(name);
        user.setTgName(tg_name);
        user.setPhone(phone);
        userRepository.save(user);
        return true;
    }

    //Изменение данных
    @PostMapping("/update_user")
    public @ResponseBody
    boolean updateUser(
            @RequestParam(name = "param") String param,
            @RequestParam(name = "update_info") String update_info,
            @RequestParam(name = "tg_name") String tg_name
    ) {
        User user = userRepository.findById(findByTgName(tg_name)).get();
        if (user != null) {
            if (param.equalsIgnoreCase("name")) {
                user.setName(update_info);
            }
            if (param.equalsIgnoreCase("passport")) {
                user.setPassport(update_info);
            }
        }
        userRepository.save(user);
        return true;
    }

    //Поиск маршрутов по дате/времени, транспорту и маршруту
    @PostMapping("/travel_filter")
    public @ResponseBody
    List<Trip> travelFilter(
            @RequestParam(name = "datetime") String datetime,
            @RequestParam(name = "transport") String transport,
            @RequestParam(name = "city_start") String city_start,
            @RequestParam(name = "city_end") String city_end
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateUser = LocalDateTime.parse(datetime, formatter); //Дата поездки
        Transport typeTransport = transportRepository.findById(Integer.parseInt(transport)).orElse(null); //Тип транспорта
        List<Points> startPointsByCities = PointsByCity(city_start);
        List<Points> endPointsByCities = PointsByCity(city_end);
        List<Trip> trip = new ArrayList<>(); //Список возможных путешествий

        for (Trip t : tripRepository.findAll()) {
            if (t.getDatetime().isAfter(dateUser) && startPointsByCities.contains(t.getIdRoute().getIdStartPoint())) {
                Routes route = t.getIdRoute(); //Переменная для хранения текущей точки маршрута
                if (endPointsByCities.contains(route.getIdFinishPoint())) {
                    LocalDateTime dateTrip = t.getDatetime(); //Дата поездки
                    int count = BookingCount(t);
                    if (count < t.getLimit()) { //Если количество броней меньше возможного
                        switch (transport) {
                            case "1":
                                LocalDateTime oneDayBefore = dateUser.plusDays(1);
                                if (TakeTrip(oneDayBefore, dateTrip, t, route, typeTransport)) {
                                    trip.add(t);
                                }
                                break;
                            case "4":
                                if (dateTrip.isAfter(dateUser) && t.getIdRoute().equals(route)) {
                                    trip.add(t);
                                }
                                break;
                            default:
                                if (TakeTrip(dateUser, dateTrip, t, route, typeTransport)) {
                                    trip.add(t);
                                }
                        }
                    }
                } else {
                    LocalDateTime currentEndTime = t.getDatetimeend(); // Время окончания текущей поездки
                    List<Trip> additionalTrips = findAllPaths(t, route, route.getIdFinishPoint(), currentEndTime);
                    for (Trip additionalTrip : additionalTrips) {
                        trip.add(additionalTrip);
                    }
                }
            }
        }
        return trip;
    }

    //Вывод всех маршрутов
    @PostMapping("/all_trip")
        public @ResponseBody
        List<TripInfo> allTrip(
                @RequestParam(name = "datetime") String datetime,
                @RequestParam(name = "city_start") String city_start,
                @RequestParam(name = "city_end") String city_end
        ) {
            LocalDateTime dateUser = LocalDateTime.parse(datetime); // Преобразуем строку в LocalDateTime
            List<TripInfo> tripInfoList = new ArrayList<>();
            List<Points> startPointsByCities = PointsByCity(city_start);
            List<Points> endPointsByCities = PointsByCity(city_end);

            for (Trip t : tripRepository.findAll()) {
                LocalDateTime dateTrip = t.getDatetime();
                if (startPointsByCities.contains(t.getIdRoute().getIdStartPoint()) && endPointsByCities.contains(t.getIdRoute().getIdFinishPoint())) {
                    Transport avia = new Transport();
                    avia.setIdTransport(1);
                    int count = BookingCount(t);
                    if (count < t.getLimit()) { // Если количество броней меньше возможного
                        if (t.getIdTransport().equals(avia)) { // Продажи на самолет заканчиваются за сутки до отправления
                            LocalDateTime oneDayBefore = dateUser.plusDays(1);
                            if (AllTrip(dateTrip, oneDayBefore, t)) {
                                tripInfoList.add(createTripInfo(t));
                            }
                        } else { // Для всех остальных
                            if (AllTrip(dateTrip, dateUser, t)) {
                                tripInfoList.add(createTripInfo(t));
                            }
                        }
                    }
                }
            }

            tripInfoList.sort(Comparator.comparing(TripInfo::getDatetime)); // Сортировка по дате
            return tripInfoList;
        }

    //Бронирование
    @PostMapping("/add_booking")
    public @ResponseBody
    boolean addBooking(
            @RequestParam(name = "id_trip") String id_trip,
            @RequestParam(name = "id_user") String id_user,
            @RequestParam(name = "id_status") String id_status
    ) {
        Booking booking = new Booking();
        Trip trip = new Trip();
        User user = new User();
        Status status = new Status();
        trip.setIdTrip(Integer.valueOf(id_trip));
        user.setIdUser(Integer.valueOf(id_user));
        status.setIdStatus(Integer.valueOf(id_status));
        booking.setIdTrip(trip);
        booking.setIdUser(user);
        booking.setIdStatus(status);
        bookingRepository.save(booking);
        return true;
    }

    //Поиск информации о бронированиях пользователя
    @PostMapping("/find_booking")
    public @ResponseBody
    Iterable<BookingInfo> findBooking(
            @RequestParam(name = "tg_name_user") String tg_name_user
    ) {
        List<BookingInfo> bookings = new ArrayList<>();
        User user = new User();
        user.setIdUser(findByTgName(tg_name_user));

        // Проходим по всем бронированиям
        for (Booking b : bookingRepository.findAll()) {
            if (b.getIdUser().equals(user)) {
                BookingInfo bookingInfo = new BookingInfo();
                bookingInfo.setIdBooking(b.getIdBooking()); // ID брони
                bookingInfo.setDatetime(b.getIdTrip().getDatetime()); // Дата и время начала
                bookingInfo.setDatetimeend(b.getIdTrip().getDatetimeend()); // Дата и время окончания
                bookingInfo.setStartPoint(b.getIdTrip().getIdRoute().getIdStartPoint().getNamePoint()); // Точка отправления
                bookingInfo.setFinishPoint(b.getIdTrip().getIdRoute().getIdFinishPoint().getNamePoint()); // Точка назначения
                bookings.add(bookingInfo);
            }
        }
        // Возвращаем список с информацией о бронированиях
        return bookings;
    }

    //Отмена бронирования
    @PostMapping("/delete_booking")
    public @ResponseBody
    boolean deleteBooking(@RequestParam(name = "idBooking") String idBooking
    ) {
        bookingRepository.deleteById(Integer.valueOf(idBooking));
        return true;
    }

    //Создание объекта TripInfo
    private TripInfo createTripInfo(Trip trip) {
        Routes route = trip.getIdRoute();
        Points startPoint = pointsRepository.findById(route.getIdStartPoint().getIdPoint()).orElse(null);
        Points finishPoint = pointsRepository.findById(route.getIdFinishPoint().getIdPoint()).orElse(null);

        TripInfo tripInfo = new TripInfo();
        tripInfo.setIdTrip(trip.getIdTrip());
        tripInfo.setDatetime(trip.getDatetime());
        tripInfo.setDatetimeend(trip.getDatetimeend());
        tripInfo.setStartPoint(startPoint != null ? startPoint.getNamePoint() : "Неизвестно");
        tripInfo.setFinishPoint(finishPoint != null ? finishPoint.getNamePoint() : "Неизвестно");

        return tripInfo;
    }

    //Определение кол-ва бронирований
    public Integer BookingCount(Trip t) {
        int count = 0;
        for (Booking b : bookingRepository.findAll()) {
            if (b.getIdTrip().equals(t)) { //Если в бронировании текущий маршрут, то count увеличивается
                count++;
            }
        }
        return count;
    }

    //Методы для поиска сложных маршрутов
    public boolean TakeTrip(LocalDateTime date, LocalDateTime dateTrip, Trip t, Routes route, Transport transport) {
        if (dateTrip.isAfter(date) && t.getIdTransport().equals(transport) && t.getIdRoute().equals(route)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean AllTrip(LocalDateTime dateTrip, LocalDateTime date, Trip t) {
        if (dateTrip.isAfter(date)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Trip> findAllPaths(Trip t, Routes route, Points endPoint, LocalDateTime currentEndTime) {
        List<Trip> trips = new ArrayList<>();
        trips.add(t);

        while (true) {
            boolean found = false;
            for (Trip nextTrip : tripRepository.findAll()) {
                if (nextTrip.getDatetime().isAfter(currentEndTime)) {
                    Routes nextRoute = nextTrip.getIdRoute();
                    if (nextRoute != null && nextRoute.getIdStartPoint().equals(route.getIdFinishPoint())) {
                        trips.add(nextTrip);
                        route = nextRoute;
                        currentEndTime = nextTrip.getDatetimeend();
                        found = true;
                        break;
                    }
                }
            }
            if (!found || route.getIdFinishPoint().equals(endPoint)) {
                break;
            }
        }
        return trips;
    }
    
    //Выборка города точки
    public List<Points> PointsByCity(String city) {
        List points = new ArrayList();
        for (Points p : pointsRepository.findAll()) {
            if(p.getAddressPoint().contains(city))
                points.add(p);
            }
        return points;
    }
}
