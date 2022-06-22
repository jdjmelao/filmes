package controllers;

import forms.*;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.libs.concurrent.HttpExecutionContext;
import views.html.index;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static play.libs.Scala.asScala;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private HttpExecutionContext httpExecutionContext;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    public HomeController() {
    }



    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        List<Records> eps = new ArrayList<>();
        List<Serie> allSeries = Serie.getAllSeries();
        try {
            String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
            User userObject = User.checkById(Integer.valueOf(id));
            eps = Records.getLastRecords(Integer.valueOf(id));
        }
        catch(Exception e){
            System.out.println("Not logged");
            return redirect(controllers.routes.HomeController.login());
        }

        List<Records> finalEps = eps;
        return request
                .session()
                .get("connected")
                .map(user -> ok(index.render(asScala(finalEps), asScala(allSeries), this.formFactory.form(RecordForm.class), "Hello", request, messagesApi.preferred(request))))
                .orElse(redirect(routes.HomeController.login()));
    }

    public Result series(Http.Request request) {
        List<Serie> series = Serie.getAllSeries();
        return ok(views.html.series.render(asScala(series), this.formFactory.form(SerieForm.class), "Series", request, messagesApi.preferred(request)));
    }

    public Result serieid(Http.Request request, Integer serieId) {
        Serie serie = Serie.getSerieById(serieId);
        return ok(views.html.serie.render(serie, serie.getName(), request, messagesApi.preferred(request)));
    }

    public Result seasons(Http.Request request, Integer serieId) {
        List<Season> seasons = Season.getSeasonsFromSerie(serieId);
        return ok(views.html.seasons.render(asScala(seasons), this.formFactory.form(SeasonForm.class), serieId, "Seasons", request, messagesApi.preferred(request)));
    }

    public Result seasonid(Http.Request request, Integer serieId, Integer seasonId) {
        Season season = Season.getSeasonFromSerieIds(serieId, seasonId);
        return ok(views.html.season.render(season, season.getSerie().getName() + " - Season " + season.getSeasonNumber(), request, messagesApi.preferred(request)));
    }

    public Result episodes(Http.Request request, Integer serieId, Integer seasonId) {
        Season season = Season.getSeasonFromSerieIds(serieId, seasonId);
        List<Episode> eps = Episode.getEpisodesFromSeason(seasonId);
        return ok(views.html.episodes.render(asScala(eps), this.formFactory.form(EpisodeForm.class), serieId, season.getSeasonNumber(), "Episodes", request, messagesApi.preferred(request)));
    }

    public Result episodeid(Http.Request request, Integer serieId, Integer seasonId, Integer episodeId) {
        Episode ep = Episode.getEpisodeFromSeasonIds(seasonId, episodeId);
        return ok(views.html.episode.render(ep, ep.getSeason().getSerie().getName() + " - Season " + ep.getSeason().getSeasonNumber() + " - " + ep.getName(), request, messagesApi.preferred(request)));
    }

    public Result login(Http.Request request) {
        return request
                .session()
                .get("connected")
                .map(user -> redirect(controllers.routes.HomeController.index()))
                .orElse(ok(views.html.login.render(this.formFactory.form(UserForm.class), request, messagesApi.preferred(request))));
    }

    public Result sign(Http.Request request) {
        return request
                .session()
                .get("connected")
                .map(user -> redirect(controllers.routes.HomeController.index()))
                .orElse(ok(views.html.signup.render(this.formFactory.form(UserForm.class), request, messagesApi.preferred(request))));
    }


    public Result logout(Http.Request request){
        return redirect(controllers.routes.HomeController.login()).withNewSession();
    }


    public Result log(Http.Request request) {
        Form<UserForm> form = this.formFactory.form(UserForm.class).bindFromRequest(request);
        UserForm userFormData = form.get();
        User userExist = User.checkUserPass(userFormData.getUser(), userFormData.getPassword());
        if(userExist != null){
            Integer id = userExist.getId();
            return redirect(controllers.routes.HomeController.index())
                    .addingToSession(request, "connected", userFormData.getUser())
                    .addingToSession(request, "id", String.valueOf(id));
        }
        else {
            return redirect(controllers.routes.HomeController.login())
                    .flashing("fail", "Could not login");
        }
    }

    public Result signup(Http.Request request) {
        Form<UserForm> form = this.formFactory.form(UserForm.class).bindFromRequest(request);
        UserForm userFormData = form.get();
        List<User> userExist = User.checkUser(userFormData.getUser());
        if (userExist.size() >= 1) {
            return redirect(controllers.routes.HomeController.signup())
                    .flashing("fail", "User Already Exist");
        }
        User user = new User();
        user.setUser(userFormData.getUser());
        user.setPassword(userFormData.getPassword());
        user.save();
        Integer id = user.getId();
        return redirect(controllers.routes.HomeController.index())
                .addingToSession(request, "connected", user.getUser())
                .addingToSession(request, "id", String.valueOf(id));
    }

    public Result addRecordsAjax(Http.Request request) throws InterruptedException {
        //TimeUnit.SECONDS.sleep(20);
        String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
        Form<RecordForm> form = this.formFactory.form(RecordForm.class).bindFromRequest(request);
        RecordForm recordFormData = form.get();

        if (recordFormData.getEpisodeNumber() == null || recordFormData.getSeasonNumber() == null || recordFormData.getSerieName() == null || recordFormData.getWatchedDate() == null) {
            return badRequest(Json.toJson("{\"Error\": \"Need more info!\"}")).as(Http.MimeTypes.JSON);
        }

        Serie serie = Serie.checkSerie(recordFormData.getSerieName());
        Season season = Season.getSeasonFromSerie(serie.getId(), Integer.valueOf(recordFormData.getSeasonNumber()));
        Episode episode = Episode.getEpisodeFromSeason(season.getId(), Integer.valueOf(recordFormData.getEpisodeNumber()));
        if (episode == null) {
            System.out.println("Error adding Episode");
            return badRequest(Json.toJson(recordFormData)).as(Http.MimeTypes.JSON)
                    .flashing("fail", "Error adding episode.");
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(recordFormData.getWatchedDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println(date);


        Records r = null;
        if(Records.getRecord(Integer.valueOf(id), episode.getId()) != null){
            r = Records.getRecord(Integer.valueOf(id), episode.getId());
            r.setWatchedDate(date);
        }
        else {
            r = new Records();
            r.setWatchedDate(date);
            r.setUser(User.checkById(Integer.valueOf(id))); //set user by id
            r.setEpisode(episode);
        }
        r.save();

        return ok(Json.toJson(r)).as(Http.MimeTypes.JSON);
    }


    public Result addRecords(Http.Request request) {
        try {
            String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
            Form<RecordForm> form = this.formFactory.form(RecordForm.class).bindFromRequest(request);
            RecordForm recordFormData = form.get();



            Serie serie = Serie.checkSerie(recordFormData.getSerieName());
            Season season = Season.getSeasonFromSerie(serie.getId(), Integer.valueOf(recordFormData.getSeasonNumber()));
            Episode episode = Episode.getEpisodeFromSeason(season.getId(), Integer.valueOf(recordFormData.getEpisodeNumber()));
            if (episode == null) {
                System.out.println("Error adding Episode");
                return redirect(controllers.routes.HomeController.index())
                        .flashing("fail", "Error adding episode.");
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(recordFormData.getWatchedDate());

            System.out.println(date);


            Records r = null;
            if(Records.getRecord(Integer.valueOf(id), episode.getId()) != null){
                r = Records.getRecord(Integer.valueOf(id), episode.getId());
                r.setWatchedDate(date);
            }
            else {
                r = new Records();
                r.setWatchedDate(date);
                r.setUser(User.checkById(Integer.valueOf(id))); //set user by id
                r.setEpisode(episode);
            }
            r.save();

            return redirect(controllers.routes.HomeController.index());

        }
        catch(Exception e){
            System.out.println("Error adding Episode");
            return redirect(controllers.routes.HomeController.index())
                    .flashing("fail", "Error adding episode.");
        }
    }




    public Result addSeries(Http.Request request) {
        try {
            String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
            Form<SerieForm> form = this.formFactory.form(SerieForm.class).bindFromRequest(request);
            SerieForm serieFormData = form.get();

            Serie s = new Serie();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(serieFormData.getRelease_date());


            s.setName(serieFormData.getName());
            s.setNumber_of_seasons(0);
            s.setNumber_of_episodes(0);
            s.setCategory(serieFormData.getCategory());
            s.setTotal_duration_minutes(0);
            s.setRelease_date(date);
            s.setClassification(Integer.valueOf(serieFormData.getClassification()));


            s.save();

            return redirect(controllers.routes.HomeController.series());

        }
        catch(Exception e){
            System.out.println("Error adding Serie");
            return redirect(controllers.routes.HomeController.series())
                    .flashing("fail", "Error adding Serie.");
        }
    }

    public Result addSeason(Http.Request request) {
        try {
            String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
            Form<SeasonForm> form = this.formFactory.form(SeasonForm.class).bindFromRequest(request);
            SeasonForm seasonFormData = form.get();

            Serie serie = Serie.getSerieById(seasonFormData.getSerieId());
            Season s = new Season();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(seasonFormData.getRelease_date());

            s.setSerie(serie);
            s.setSeasonNumber(Integer.valueOf(seasonFormData.getSeasonNumber()));
            s.setNumber_of_episodes(0);
            s.setTotal_duration_minutes(0);
            s.setRelease_date(date);
            s.setClassification(Integer.valueOf(seasonFormData.getClassification()));

            s.save();

            serie.setNumber_of_seasons(serie.getNumber_of_seasons() + 1);
            serie.save();

            return redirect(controllers.routes.HomeController.seasons(serie.getId()));

        }
        catch(Exception e){
            System.out.println("Error adding Season");
            return redirect(controllers.routes.HomeController.series())
                    .flashing("fail", "Error adding Season.");
        }
    }

    public Result addEpisode(Http.Request request) {
        try {
            String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");
            Form<EpisodeForm> form = this.formFactory.form(EpisodeForm.class).bindFromRequest(request);
            EpisodeForm episodeFormData = form.get();

            Season season = Season.getSeasonFromSerie(episodeFormData.getSerieId(), episodeFormData.getSeasonNumber());
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(episodeFormData.getRelease_date());
            Episode s = new Episode();

            s.setSeason(season);
            s.setEp_number(Integer.valueOf(episodeFormData.getEp_number()));
            s.setDuration_minutes(Integer.valueOf(episodeFormData.getDuration_minutes()));
            s.setRelease_date(date);
            s.setName(episodeFormData.getName());
            s.setClassification(Integer.valueOf(episodeFormData.getClassification()));

            season.setTotal_duration_minutes(season.getTotal_duration_minutes() + Integer.valueOf(episodeFormData.getDuration_minutes()));
            season.setNumber_of_episodes(season.getNumber_of_episodes() + 1);
            season.save();

            Serie serie = Serie.getSerieById(episodeFormData.getSerieId());
            serie.setTotal_duration_minutes(serie.getTotal_duration_minutes() + Integer.valueOf(episodeFormData.getDuration_minutes()));
            serie.setNumber_of_episodes(serie.getNumber_of_episodes() + 1);
            serie.save();

            s.save();

            return redirect(controllers.routes.HomeController.episodes(season.getSerie().getId(), season.getId()));

        }
        catch(Exception e){
            System.out.println("Error adding Episode");
            return redirect(controllers.routes.HomeController.series())
                    .flashing("fail", "Error adding Episode.");
        }
    }

    public Result delete(Http.Request request, Integer serieID, Integer seasonId, Integer episodeId) {

        String id = String.valueOf(request.session().get("id")).replace("Optional[", "").replace("]", "");

        Records record = Records.getRecord(Integer.valueOf(id), episodeId);

        if(record != null) {
            record.delete();
            return redirect(controllers.routes.HomeController.index())
                    .flashing("deleted", "Episode deleted!");
        }
        return redirect(controllers.routes.HomeController.index())
                .flashing("fail", "Could not find Episode.");
    }

}
