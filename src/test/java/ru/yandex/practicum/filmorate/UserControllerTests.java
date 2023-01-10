package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTests {
    /*
    private static ConfigurableApplicationContext springSrv;
    private static Gson gson;
    private final String DOMAIN_PATH = "http://localhost:8080";
    private final String LOCAL_ADDRESS = "/users";

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        return gsonBuilder.create();
    }

    @BeforeAll
    static void start() {
        gson = getGson();
    }

    @BeforeEach
    void startSrv() {
        springSrv = SpringApplication.run(FilmorateApplication.class);
    }

    @AfterEach
    void stopSrv() {
        SpringApplication.exit(springSrv);
    }


    @Test
    void checkCreateNewUser() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        User user1 = new User("mail@mail.ru", "dolore",
                LocalDate.of(1990, 12, 12));
        user1.setName("Nick Name");
        user1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(user1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        User user1Back = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode(), "неправильный код");
      //  assertEquals(user1, user1Back, "Объекты не равны");

    }

    @Test
    void checkCreateNewUserEmptyName() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        User user1 = new User("mail@mail.ru", "dolore",
                LocalDate.of(1990, 12, 12));
        user1.setName("");
        user1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(user1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        User user1Back = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode(), "неправильный код");
        user1.setName(user1.getLogin());
       // assertEquals(user1Back, user1, "Объекты отличаются");
    }

    @Test
    void checkCreateNewUserFailLogin() throws IOException, InterruptedException { // тест домашней страницы
        // отправляем запрос с неправильным логином
        User user1 = new User("mail@mail.ru", "dolore ullamco",
                LocalDate.of(1990, 12, 12));
        user1.setName("");

        String fromGson = gson.toJson(user1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 500) assertEquals(500, response.statusCode());
        else assertEquals(400, response.statusCode());

        assertTrue(response.body().toLowerCase().contains("error"));
    }

    @Test
    void checkCreateNewUserFailEmail() throws IOException, InterruptedException { // тест домашней страницы
        // отправляем запрос с неправильной почтой
        User user1 = new User("mail mail.ru", "dolore",
                LocalDate.of(1990, 12, 12));
        user1.setName("Nick Name");
        String fromGson = gson.toJson(user1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 500) assertEquals(500, response.statusCode());
        else assertEquals(400, response.statusCode());

        assertTrue(response.body().toLowerCase().contains("error"));
    }

    @Test
    void checkCreateNewUserFailBithDay() throws IOException, InterruptedException { // тест домашней страницы
        User user1 = new User("mail@mail.ru", "dolore",
                LocalDate.of(2090, 12, 12));
        user1.setName("Nick Name");

        String fromGson = gson.toJson(user1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 500) assertEquals(500, response.statusCode());
        else assertEquals(400, response.statusCode());

        assertTrue(response.body().toLowerCase().contains("error"));
    }

    @Test
    void checkUpdateUser() throws IOException, InterruptedException { // тест домашней страницы
        // обновление пользователя
        int localCounterId = 1;
        User user1 = new User("mail@mail.ru", "dolore",
                LocalDate.of(1990, 12, 12));
        user1.setName("Nick Name");
        user1.setId(localCounterId++); // назначение правильного id локальному объекту


        String fromGson = gson.toJson(user1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        User user1Back = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode(), "неправильный код");
//        assertEquals(user1, user1Back, "Объекты не равны");

        String updateText = "update";
        user1.setEmail(user1.getEmail() + updateText);
        user1.setLogin(user1.getLogin() + updateText);
        user1.setName(user1.getName() + updateText);
        user1.setBirthday(LocalDate.of(1980, 4, 30));

        fromGson = gson.toJson(user1);

        client = HttpClient.newHttpClient();
        uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        httpRequest = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        user1Back = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode(), "неправильный код");
        assertEquals(user1, user1Back, "Объекты не равны");
    }

    @Test
    void checkUpdateUnknownUser() throws IOException, InterruptedException { // тест домашней страницы
        // обновление неизвестного пользователя
        int localCounterId = 1;
        User user1 = new User("mail@mail.ru", "dolore",
                LocalDate.of(1990, 12, 12));
        user1.setName("Nick Name");
        user1.setId(localCounterId++); // назначение правильного id локальному объекту


        String fromGson = gson.toJson(user1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        User user1Back = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode(), "неправильный код");
   //     assertEquals(user1, user1Back, "Объекты не равны");

        String updateText = "update";
        user1.setEmail(user1.getEmail() + updateText);
        user1.setLogin(user1.getLogin() + updateText);
        user1.setName(user1.getName() + updateText);
        user1.setBirthday(LocalDate.of(1980, 4, 30));
        user1.setId(localCounterId++); // назначение неправильного id локальному объекту

        fromGson = gson.toJson(user1);

        client = HttpClient.newHttpClient();
        uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        httpRequest = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

//        if (response.statusCode() == 500) assertEquals(500, response.statusCode());
//        else assertEquals(400, response.statusCode());

//        assertTrue(response.body().toLowerCase().contains("error"));
    }


    @Test
    void checkAllUsers() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        HashMap<Integer, User> users = new HashMap<Integer, User>();
        HashMap<Integer, User> usersCheck = new HashMap<Integer, User>();
        for (int i = 0; i < 3; i++) { // создание трех пользователей
            User user1 = new User("mail@mail.ru" + localCounterId, "dolore" + localCounterId,
                    LocalDate.of(1990, 12, 12 + localCounterId));
            user1.setName("Nick Name" + localCounterId);
            user1.setId(localCounterId++); // назначение правильного id локальному объекту

            users.put(user1.getId(), user1);

            String fromGson = gson.toJson(user1);

            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                    .setHeader("content-type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            User user1Back = gson.fromJson(response.body(), User.class);

            assertEquals(200, response.statusCode(), "неправильный код");
            assertEquals(user1, user1Back, "Объекты не равны");
        }


        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement js :
                    jsonArray) {
                User user = gson.fromJson(js, User.class);
                usersCheck.put(user.getId(), user);
            }
        }
        assertEquals(200, response.statusCode());
        for (int i :
                users.keySet()) {
            assertEquals(users.get(i), usersCheck.get(i));
        }
    }
*/

}
