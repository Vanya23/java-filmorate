package ru.yandex.practicum.filmorate;

public class FilmControllerTests {
    /*
    private static ConfigurableApplicationContext springSrv;
    private static Gson gson;
    private final String DOMAIN_PATH = "http://localhost:8080";
    private final String LOCAL_ADDRESS = "/films";

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
    void checkCreateNewFilm() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Film film1Back = gson.fromJson(response.body(), Film.class);

        assertEquals(200, response.statusCode(), "неправильный код");
//        assertEquals(film1, film1Back, "Объекты не равны");

    }

    @Test
    void checkCreateNewFilmFailName() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("", "adipisicing",
                LocalDate.of(1967, 3, 25), 100);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

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
    void checkCreateNewFilmFailDescription() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("Film name", "Пятеро друзей ( комик-группа «Шарло»)," +
                " приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова," +
                " который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время " +
                "«своего отсутствия», стал кандидатом Коломбани.",
                LocalDate.of(1900, 3, 25), 200);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

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
    void checkCreateNewFilmFailReleaseDate() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("Name", "Description",
                LocalDate.of(1890, 3, 25), 200);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

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
    void checkCreateNewUserFaiDuration() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("Name", "Description",
                LocalDate.of(1980, 3, 25), -200);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

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
    void checkUpdateFilm() throws IOException, InterruptedException { // тест домашней страницы
        // обновление пользователя
        int localCounterId = 1;
        Film film1 = new Film("nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Film film1Back = gson.fromJson(response.body(), Film.class);

        assertEquals(200, response.statusCode(), "неправильный код");
//        assertEquals(film1, film1Back, "Объекты не равны");

        String updateText = "update";
        film1.setName(film1.getName() + updateText);
        film1.setDescription(film1.getDescription() + updateText);
        film1.setDuration(film1.getDuration() + 1);
        film1.setReleaseDate(LocalDate.of(1980, 4, 30));

        fromGson = gson.toJson(film1);

        client = HttpClient.newHttpClient();
        uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        httpRequest = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        film1Back = gson.fromJson(response.body(), Film.class);

        assertEquals(200, response.statusCode(), "неправильный код");
        assertEquals(film1, film1Back, "Объекты не равны");
    }

    @Test
    void checkUpdateUnknownFilm() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        Film film1 = new Film("nisi eiusmod", "adipisicing",
                LocalDate.of(1967, 3, 25), 100);
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        String fromGson = gson.toJson(film1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                .setHeader("content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Film film1Back = gson.fromJson(response.body(), Film.class);

        assertEquals(200, response.statusCode(), "неправильный код");
//        assertEquals(film1, film1Back, "Объекты не равны");

        String updateText = "update";
        film1.setName(film1.getName() + updateText);
        film1.setDescription(film1.getDescription() + updateText);
        film1.setDuration(film1.getDuration() + 1);
        film1.setReleaseDate(LocalDate.of(1980, 4, 30));
        film1.setId(localCounterId++); // назначение правильного id локальному объекту

        fromGson = gson.toJson(film1);

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
    void checkAllFilms() throws IOException, InterruptedException { // тест домашней страницы
        int localCounterId = 1;
        HashMap<Integer, Film> films = new HashMap<Integer, Film>();
        HashMap<Integer, Film> filmsCheck = new HashMap<Integer, Film>();
        for (int i = 0; i < 3; i++) { // создание трех пользователей
            Film film1 = new Film("nisi eiusmod", "adipisicing",
                    LocalDate.of(1967, 3, 25), 100);
            film1.setId(localCounterId++); // назначение правильного id локальному объекту

            String fromGson = gson.toJson(film1);

            films.put(film1.getId(), film1);

            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(DOMAIN_PATH + LOCAL_ADDRESS);
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(fromGson))
                    .setHeader("content-type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Film film1Back = gson.fromJson(response.body(), Film.class);

            assertEquals(200, response.statusCode(), "неправильный код");
            assertEquals(film1, film1Back, "Объекты не равны");
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
                Film film = gson.fromJson(js, Film.class);
                filmsCheck.put(film.getId(), film);
            }
        }
        assertEquals(200, response.statusCode());
        for (int i :
                films.keySet()) {
            assertEquals(films.get(i), filmsCheck.get(i));
        }
    }
    */
}
