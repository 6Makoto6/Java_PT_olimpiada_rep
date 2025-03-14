/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pt.passenger_transportation;

import com.pt.passenger_transportation.entity.BookingInfo;
import com.pt.passenger_transportation.entity.TripInfo;
import com.pt.passenger_transportation.entity.User;
import com.pt.passenger_transportation.repository.UserRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Telegrambot extends TelegramLongPollingBot {

    private boolean waitingForStartCity = false;
    private boolean waitingForEndCity = false;
    private boolean waitingForDate = false;
    private String startCity = null;
    private String endCity = null;
    private String date = null;
    private String param;
    private boolean updateName = false;
    private boolean updatePassport = false;
    private int currentIndex = 0;
    private int idbook = 0;
    private List<TripInfo> trips = new ArrayList<>();
    private List<BookingInfo> bookings = new ArrayList<>();
    private boolean isWaitingForBookingConfirmation = false;
    private boolean isWaitingForBookingCancel = false;
    private int tripIdToBook = 0;
    private int bookIdToDel = 0;
    private boolean AllTripShow = false;
    private boolean BookingShow = false;
    private OkHttpClient client = new OkHttpClient();

    @Autowired
    private UserRepository TgUser;
    private final BotConfig config;

    public Telegrambot(BotConfig config) throws TelegramApiException {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
    
    //Обработка сообщений пользователя
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getChat().getUserName(); //@name

            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                User userTg = findUserByTgname(username);
                String userName = userTg.getName(); //Имя

                //помощь
                if (messageText.equalsIgnoreCase("помощь")) {
                    sendMainMenu(chatId, "Выберите действие в меню:");
                    sendMessage(chatId, "Изменить имя - изменить имя для бронирования \n Изменить паспорт - изменить паспорт для бронирования \n Найти маршрут - поиск маршрутов по любым направлениям \n Мои бронирования - просмотреть забронированные поездки");
                } 

                //старт
                else if (messageText.equalsIgnoreCase("/start")) {
                    boolean userExists = TgUser.findByTgName(username).isPresent();
                    if (!userExists) {
                        requestPhoneNumber(chatId);
                    } else {
                        sendMessage(chatId, "Привет! Напиши помощь, чтобы вывести команды");
                        sendMainMenu(chatId, "Выберите действие в меню:");
                    }
                }

                //Изменить имя
                else if (messageText.equalsIgnoreCase("изменить имя")) {
                    updateName = true;
                    sendMessage(chatId, userName + ", введите новое имя или Отмена:");
                } else if (updateName == true) {
                    updateName = false;
                    if (messageText.equalsIgnoreCase("Отмена")) {
                        sendMessage(chatId, "Изменение имени отменено");
                    } else {
                        String newName = messageText;
                        param = "name";
                        updateUserInfo(chatId, username, param, newName);
                    }
                } 

                //Изменить паспорт
                else if (messageText.equalsIgnoreCase("изменить паспорт")) {
                    updatePassport = true;
                    sendMessage(chatId, userName + ", введите паспорт или Отмена:");
                } else if (updatePassport == true) {
                    updatePassport = false;
                    if (messageText.equalsIgnoreCase("Отмена")) {
                        updatePassport = false;
                        sendMessage(chatId, "Изменение паспорта отменено");
                    } else {
                        String newPassport = messageText;
                        param = "passport";
                        updateUserInfo(chatId, username, param, newPassport);
                    }
                } 

                //Найти маршрут
                else if (messageText.equalsIgnoreCase("найти маршрут")) {
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> keyboard = new ArrayList<>();

                    KeyboardRow row1 = new KeyboardRow();
                    row1.add(new KeyboardButton("Простой поиск"));
                    row1.add(new KeyboardButton("Сложный поиск"));

                    keyboard.add(row1);
                    keyboardMarkup.setKeyboard(keyboard);
                    keyboardMarkup.setResizeKeyboard(true);

                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("Выберите действие:");
                    message.setReplyMarkup(keyboardMarkup);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                    }
                } else if (messageText.equalsIgnoreCase("Сложный поиск")) {
                    String text = "Для работы сложного поиска необходима полная версия приложения. "
                            + "\n Для тестирования метода необходимо ввести в поисковую строку браузера http://localhost:8080/passenger_transportation/travel_filter?datetime=2024-01-01T18:00:00&transport=2&city_start=Москва&city_end=Екатеринбург";
                    sendMainMenu(chatId, text);
                } else if (messageText.equalsIgnoreCase("Простой поиск")) {
                    waitingForStartCity = true;
                    sendMessage(chatId, "Введите город отправления:");
                } else if (waitingForStartCity) {
                    startCity = messageText;
                    waitingForStartCity = false;
                    waitingForEndCity = true;
                    sendMessage(chatId, "Введите город назначения:");
                } else if (waitingForEndCity) {
                    endCity = messageText;
                    waitingForEndCity = false;
                    waitingForDate = true;
                    sendMessage(chatId, "Введите дату отправления в формате YYYY-MM-DD:");
                } else if (waitingForDate) {
                    date = messageText;
                    waitingForDate = false;
                    if (startCity != null && endCity != null && date != null) {
                        trips = getAllTrips(date, startCity, endCity);
                        if (trips.isEmpty()) {
                            sendMessage(chatId, "Нет доступных маршрутов.");
                        } else {
                            currentIndex = 0; 
                            showTrip(chatId, currentIndex);
                            AllTripShow = true;
                        }
                    } else {
                        sendMessage(chatId, "Ошибка: не все данные введены.");
                    }
                } else if (AllTripShow && (messageText.equalsIgnoreCase("Вперед") || messageText.equalsIgnoreCase("Назад") || messageText.equalsIgnoreCase("Забронировать"))) {
                    if (messageText.equalsIgnoreCase("Вперед")) {
                        if (currentIndex < trips.size() - 1) {
                            currentIndex++;
                            showTrip(chatId, currentIndex);
                        }
                    } else if (messageText.equalsIgnoreCase("Назад")) {
                        if (currentIndex > 0) {
                            currentIndex--;
                            showTrip(chatId, currentIndex);
                        }
                    } else if (messageText.equalsIgnoreCase("Забронировать")) {
                        isWaitingForBookingConfirmation = true;
                        tripIdToBook = trips.get(currentIndex).getIdTrip();
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> yesno = new ArrayList<>();

                        KeyboardRow row = new KeyboardRow();
                        row.add(new KeyboardButton("Да"));
                        row.add(new KeyboardButton("Нет"));

                        yesno.add(row);
                        keyboardMarkup.setKeyboard(yesno);
                        keyboardMarkup.setResizeKeyboard(true);

                        SendMessage message = new SendMessage();
                        message.setChatId(chatId);
                        message.setText("Вы уверены, что хотите забронировать этот маршрут? (Да/Нет)");
                        message.setReplyMarkup(keyboardMarkup);
                        try {
                            execute(message); // Отправка сообщения
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (isWaitingForBookingConfirmation) {
                    String text;
                    if (messageText.equalsIgnoreCase("Да")) {
                        boolean bookingResult = bookTrip(tripIdToBook, username);
                        if (bookingResult) {
                            text = "Бронирование успешно завершено!";
                        } else {
                            text = "Ошибка при бронировании.";
                        }
                    } else {
                        text = "Бронирование отменено.";
                    }
                    AllTripShow = false;
                    isWaitingForBookingConfirmation = false;
                    sendMainMenu(chatId, text);
                } 

                //Бронирования
                else if (messageText.equalsIgnoreCase("Мои бронирования")) {
                    bookings = getBookings(username);
                    if (bookings.isEmpty()) {
                        sendMessage(chatId, "Нет забронированных маршрутов.");
                    } else {
                        currentIndex = 0;
                        showBooking(chatId, currentIndex);
                        BookingShow = true;
                    }
                } else if (BookingShow == true && (messageText.equalsIgnoreCase("Вперед") || messageText.equalsIgnoreCase("Назад") || messageText.equalsIgnoreCase("Забронировать"))) {
                    if (messageText.equalsIgnoreCase("Вперед")) {
                        if (currentIndex < bookings.size() - 1) {
                            currentIndex++;
                            showBooking(chatId, currentIndex);
                        }
                    } else if (messageText.equalsIgnoreCase("Назад")) {
                        if (currentIndex > 0) {
                            currentIndex--;
                            showBooking(chatId, currentIndex);
                        }
                    }
                } else if (messageText.equalsIgnoreCase("Отменить бронирование")) {
                    isWaitingForBookingCancel = true;
                    bookIdToDel = bookings.get(currentIndex).getIdBooking();
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> yesno = new ArrayList<>();

                    KeyboardRow row = new KeyboardRow();
                    row.add(new KeyboardButton("Да"));
                    row.add(new KeyboardButton("Нет"));

                    yesno.add(row);
                    keyboardMarkup.setKeyboard(yesno);
                    keyboardMarkup.setResizeKeyboard(true);

                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("Вы уверены, что хотите отменить это бронирование? (Да/Нет)");
                    message.setReplyMarkup(keyboardMarkup);
                    try {
                        execute(message); // Отправка сообщения
                    } catch (TelegramApiException e) {
                    }
                } else if (isWaitingForBookingCancel) {
                    String text;
                    if (messageText.equalsIgnoreCase("Да")) {
                        boolean bookingDelResult = DeleteBooking(bookIdToDel);
                        if (bookingDelResult) {
                            text = "Бронирование успешно отменено!";
                        } else {
                            text = "Ошибка при отмене.";
                        }
                    } else {
                        text = "Бронирование не отменено.";
                    }
                    BookingShow = false;
                    isWaitingForBookingCancel = false;
                    sendMainMenu(chatId, text);
                } 

                //В иных случаях
                else if (updateName == false && updatePassport == false) {
                    sendMainMenu(chatId, "Я тебя не понял, " + userName + ". Напиши помощь и я все расскажу!");
                }
                
            //Код для регистрации если человек отправил свой номер  
            } else if (update.getMessage().hasContact()) {
                String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                String userFirstName = update.getMessage().getChat().getFirstName();

                FormBody body = new FormBody.Builder()
                        .add("name", userFirstName)
                        .add("tg_name", username)
                        .add("phone", phoneNumber)
                        .build();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/passenger_transportation/add_user")
                        .post(body)
                        .build();
                try (Response response = new OkHttpClient().newCall(request).execute()) {
                    if (response.isSuccessful() && Boolean.parseBoolean(response.body().string())) {
                        String text = "Добро пожаловать, " + userFirstName + "! Добро пожаловать в бот. Вперед за впечатлениями! Чтобы узнать команды, напиши помощь";
                        sendMainMenu(chatId, text);
                    } else {
                        sendMessage(chatId, "Произошла ошибка при регистрации. Пожалуйста, попробуйте еще раз.");
                    }
                } catch (IOException e) {
                    sendMessage(chatId, "Ошибка при отправке данных на сервер. Пожалуйста, попробуйте позже.");
                }
            }
        }
    }

    
    //Метод отправки текстовых сообщений
    private void sendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText(textToSend);

        try {
            execute(msg);
            System.out.println("Message sent to chatId: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("Error sending message to chatId: " + chatId);
        }
    }
    
    //Метод установки главного меню 
    private void sendMainMenu(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Изменить имя"));
        row1.add(new KeyboardButton("Изменить паспорт"));
        row1.add(new KeyboardButton("Найти маршрут"));
        row1.add(new KeyboardButton("Мои бронирования"));

        keyboard.add(row1);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }
    
    //Поиск пользователя по имени в ТГ
    private User findUserByTgname(String name) {
        User user = new User();
        for (User u : TgUser.findAll()) {
            if (u.getTgName().equals(name)) {
                user = u;
            }
        }
        return user;
    }
    //Запрос номера телефона
    private void requestPhoneNumber(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton phoneButton = new KeyboardButton("Поделиться номером телефона");
        phoneButton.setRequestContact(true);
        row.add(phoneButton);
        keyboardMarkup.setKeyboard(List.of(row));
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Пожалуйста, поделитесь своим номером телефона:");
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Обнаружена ошибка: " + e.getMessage());
        }
    }

    //Обновление информации о пользователе
    private void updateUserInfo(long chatId, String username, String param, String newValue) {
        FormBody body = new FormBody.Builder()
                .add("param", param)
                .add("tg_name", username)
                .add("update_info", newValue)
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/passenger_transportation/update_user")
                .post(body)
                .build();

        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && Boolean.parseBoolean(response.body().string())) {
                sendMessage(chatId, "Данные успешно изменены!");
            } else {
                sendMessage(chatId, "Ошибка в изменении данных, попробуйте еще раз.");
            }
        } catch (IOException e) {
            sendMessage(chatId, "Ошибка при отправке данных на сервер. Пожалуйста, попробуйте позже.");
        }
    }

    //Вывод путешествий
    private void showTrip(long chatId, int index) {
        TripInfo trip = trips.get(index);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Точка отправления: " + trip.getStartPoint()
                + "\nТочка назначения: " + trip.getFinishPoint()
                + "\nДата отправления: " + String.valueOf(trip.getDatetime()).replace("T", " ")
                + "\nДата прибытия: " + String.valueOf(trip.getDatetimeend()).replace("T", " ")
        );
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        if (currentIndex != 0) {
            KeyboardButton backButton = new KeyboardButton("Назад");
            row1.add(backButton);
        }
        if (currentIndex != trips.size() - 1) {
            KeyboardButton forwardButton = new KeyboardButton("Вперед");
            row1.add(forwardButton);
        }

        KeyboardButton bookButton = new KeyboardButton("Забронировать");
        row2.add(bookButton);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
        }
    }

    //Вывод броней
    private void showBooking(long chatId, int index) {
        BookingInfo book = bookings.get(index);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Точка отправления: " + book.getStartPoint()
                + "\nТочка назначения: " + book.getFinishPoint()
                + "\nДата отправления: " + String.valueOf(book.getDatetime()).replace("T", " ")
                + "\nДата прибытия: " + String.valueOf(book.getDatetimeend()).replace("T", " ")
        );
        idbook = book.getIdBooking();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        if (currentIndex != 0) {
            KeyboardButton backButton = new KeyboardButton("Назад");
            row1.add(backButton);
        }
        if (currentIndex != bookings.size() - 1) {
            KeyboardButton forwardButton = new KeyboardButton("Вперед");
            row1.add(forwardButton);
        }
        KeyboardButton bookButton = new KeyboardButton("Отменить бронирование");
        row2.add(bookButton);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
        }
    }

    //Бронирование путешествия
    private boolean bookTrip(int tripId, String username) {
        RequestBody formBody = new FormBody.Builder()
                .add("id_trip", String.valueOf(tripId))
                .add("id_user", String.valueOf(findUserByTgname(username).getIdUser()))
                .add("id_status", "1")
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/passenger_transportation/add_booking")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }

    //Поиск путешествий
    private List<TripInfo> getAllTrips(String datetimeuser, String city_start, String city_end) {
        List<TripInfo> allTripsMetod = new ArrayList<>();
        LocalDateTime correctDateTime = LocalDateTime.parse(datetimeuser + "T00:00:00");
        RequestBody formBody = new FormBody.Builder()
                .add("datetime", String.valueOf(correctDateTime))
                .add("city_start", city_start)
                .add("city_end", city_end)
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/passenger_transportation/all_trip")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Response from server: " + responseBody);
            JSONArray ja = new JSONArray(responseBody);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                TripInfo trip = new TripInfo();
                trip.setIdTrip(jo.getInt("idTrip"));
                trip.setDatetime(LocalDateTime.parse(jo.getString("datetime"), DateTimeFormatter.ISO_DATE_TIME));
                trip.setDatetimeend(LocalDateTime.parse(jo.getString("datetimeend"), DateTimeFormatter.ISO_DATE_TIME));
                trip.setStartPoint(jo.getString("startPoint"));
                trip.setFinishPoint(jo.getString("finishPoint"));
                allTripsMetod.add(trip);
            }
        } catch (IOException e) {
        }
        return allTripsMetod;
    }
    
    //Получения списка броней
    private List<BookingInfo> getBookings(String tg_name_user) {
        List<BookingInfo> bookingsUser = new ArrayList<>();
        RequestBody formBody = new FormBody.Builder()
                .add("tg_name_user", tg_name_user)
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/passenger_transportation/find_booking")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Response from server: " + responseBody);
            JSONArray ja = new JSONArray(responseBody);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                BookingInfo booking = new BookingInfo();
                booking.setIdBooking(jo.getInt("idBooking"));
                booking.setDatetime(LocalDateTime.parse(jo.getString("datetime"), DateTimeFormatter.ISO_DATE_TIME));
                booking.setDatetimeend(LocalDateTime.parse(jo.getString("datetimeend"), DateTimeFormatter.ISO_DATE_TIME));
                booking.setStartPoint(jo.getString("startPoint"));
                booking.setFinishPoint(jo.getString("finishPoint"));
                bookingsUser.add(booking);
            }
        } catch (IOException e) {
        }
        return bookingsUser;
    }
    
    //Отмена брони
    private boolean DeleteBooking(int idBooking) {
        boolean deleted = false;
        RequestBody formBody = new FormBody.Builder()
                .add("idBooking", String.valueOf(idBooking))
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/passenger_transportation/delete_booking")
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            deleted = Boolean.parseBoolean(response.body().string());
        } catch (IOException e) {
        }
        return deleted;
    }
}
