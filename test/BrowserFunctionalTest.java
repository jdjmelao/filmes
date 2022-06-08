import controllers.routes;
import models.Episode;
import models.Records;
import models.Serie;
import models.User;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.WithBrowser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BrowserFunctionalTest extends WithBrowser {

    @Test
    @Order(1)
    public void AloginWorks() {
        browser.goTo(routes.HomeController.login().toString());
        System.out.println(browser.pageSource());
        browser.$("#password").fill().with("123");
        browser.$("#user").fill().with("123");
        browser.$(".create").submit();
        String code = browser.el(".statusCode").text();
        //assertEquals("200", code);
        assertEquals("Hello", browser.el("title").text());
    }

    @Test
    @Order(2)
    public void BloginIsNotCaseSensitive() {
        browser.goTo(routes.HomeController.login().toString());
        //assertEquals(OK, browser);

        browser.$("#password").fill().with("ABC");
        browser.$("#user").fill().with("abc");
        browser.$(".create").submit();
        assertEquals("Hello", browser.el("title").text());
    }

    @Test
    @Order(3)
    public void CcreateAccountBad() {
        browser.goTo(routes.HomeController.signup().toString());

        browser.$(".password").fill().with("123");
        browser.$(".user").fill().with("123");
        browser.$(".create").submit();

        assertEquals("Sign", browser.el("title").text());
    }

    @Test
    @Order(4)
    public void DcreateAccountGood() {
        browser.goTo(routes.HomeController.signup().toString());

        browser.$(".password").fill().with("novo");
        browser.$(".user").fill().with("novo");
        browser.$(".create").submit();

        assertEquals("Hello", browser.el("title").text());

        User user = User.checkUser("novo").get(0);
        assertEquals("novo", user.getUser());

        user.delete();

    }


    @Test
    @Order(5)
    public void EaddRecord() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        Hashtable<String, String> login = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        my_dict.put("serieName", "Breaking Bad");
        my_dict.put("seasonNumber", "1");
        my_dict.put("episodeNumber", "2");
        my_dict.put("watchedDate", date);


        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.$(".serieName").fillSelect().withValue(my_dict.get("serieName"));
        browser.$(".seasonNumber").fill().with(my_dict.get("seasonNumber"));
        browser.$(".episodeNumber").fill().with(my_dict.get("episodeNumber"));
        browser.$(".watchedDate").fill().with(my_dict.get("watchedDate"));


        browser.$(".create").submit();

        FluentWebElement s = browser.$(".list").get(0);
        String title = s.$("td").get(0).text();
        String season = s.$("td").get(1).text();
        String ep = s.$("td").get(2).text();
        String epName = s.$("td").get(3).text();



        User user = User.checkUser(login.get("user")).get(0);
        Records r = Records.getLastFromUser(user.getId());
        assertEquals(r.getEpisode().getName(), epName);

        assertEquals(title, my_dict.get("serieName"));
        assertEquals(season, my_dict.get("seasonNumber"));
        assertEquals(ep, my_dict.get("episodeNumber"));

        System.out.println("Adicionei o " + title);
    }


    @Test
    @Order(6)
    public void FaddRecordFailDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        Hashtable<String, String> login = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        my_dict.put("serieName", "Narcos");
        my_dict.put("seasonNumber", "1");
        my_dict.put("episodeNumber", "2");
        my_dict.put("watchedDate", "1231231");

        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.$(".seasonNumber").fill().with(my_dict.get("seasonNumber"));
        browser.$(".episodeNumber").fill().with(my_dict.get("episodeNumber"));
        browser.$(".watchedDate").fill().with(my_dict.get("watchedDate"));
        browser.$(".serieName").fillSelect().withValue(my_dict.get("serieName"));

        browser.$(".create").submit();

        if(browser.$(".list").size() > 0){
            FluentWebElement s = browser.$(".list").get(0);
            String title = s.$("td").get(0).text();
            String season = s.$("td").get(1).text();
            String ep = s.$("td").get(2).text();
            String epName = s.$("td").get(3).text();

            User user = User.checkUser(login.get("user")).get(0);
            Records r = Records.getLastFromUser(user.getId());
            assertEquals(r.getEpisode().getName(), epName);

            assertTrue("One of those should not be the same!", season != my_dict.get("seasonNumber") || title != my_dict.get("serieName") || ep != my_dict.get("episodeNumber"));

        }
        else {
            System.out.println("No episodes to delete");
        }

    }


    @Test
    @Order(7)
    public void GaddRecordFailEpisode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        Hashtable<String, String> login = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        my_dict.put("serieName", "Narcos");
        my_dict.put("seasonNumber", "1");
        my_dict.put("episodeNumber", "episode");
        my_dict.put("watchedDate", date);

        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.$(".seasonNumber").fill().with(my_dict.get("seasonNumber"));
        browser.$(".episodeNumber").fill().with(my_dict.get("episodeNumber"));
        browser.$(".watchedDate").fill().with(my_dict.get("watchedDate"));
        browser.$(".serieName").fillSelect().withValue(my_dict.get("serieName"));

        browser.$(".create").submit();

        if(browser.$(".list").size() > 0){
            FluentWebElement s = browser.$(".list").get(0);
            String title = s.$("td").get(0).text();
            String season = s.$("td").get(1).text();
            String ep = s.$("td").get(2).text();
            String epName = s.$("td").get(3).text();

            User user = User.checkUser(login.get("user")).get(0);
            Records r = Records.getLastFromUser(user.getId());
            assertEquals(r.getEpisode().getName(), epName);

            assertTrue("One of those should not be the same!", season != my_dict.get("seasonNumber") || title != my_dict.get("serieName") || ep != my_dict.get("episodeNumber"));
        }
        String html = browser.pageSource();
        assertTrue("Fail should be on html Page!", html.contains("Fail"));
    }

    @Test
    @Order(8)
    public void HaddRecordNonExistingEpisode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        Hashtable<String, String> login = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        my_dict.put("serieName", "Narcos");
        my_dict.put("seasonNumber", "10");
        my_dict.put("episodeNumber", "10");
        my_dict.put("watchedDate", date);

        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.$(".seasonNumber").fill().with(my_dict.get("seasonNumber"));
        browser.$(".episodeNumber").fill().with(my_dict.get("episodeNumber"));
        browser.$(".watchedDate").fill().with(my_dict.get("watchedDate"));
        browser.$(".serieName").fillSelect().withValue(my_dict.get("serieName"));

        browser.$(".create").submit();

        if(browser.$(".list").size() > 0){
            FluentWebElement s = browser.$(".list").get(0);
            String title = s.$("td").get(0).text();
            String season = s.$("td").get(1).text();
            String ep = s.$("td").get(2).text();
            String epName = s.$("td").get(3).text();

            User user = User.checkUser(login.get("user")).get(0);
            Records r = Records.getLastFromUser(user.getId());
            assertEquals(r.getEpisode().getName(), epName);

            assertTrue("One of those should not be the same!", season != my_dict.get("seasonNumber") || title != my_dict.get("serieName") || ep != my_dict.get("episodeNumber"));
        }
        String html = browser.pageSource();
        assertTrue("Fail should be on html Page!", html.contains("Fail"));
    }

    @Test
    @Order(9)
    public void IdeleteRecord() {
        //login
        login("123", "123");
        assertEquals("Hello", browser.el("title").text());

        String title = "";
        String season = "";
        String ep = "";

        if( browser.$(".serie").size() > 0 ){
            title = String.valueOf(browser.$(".serie").get(0).text());
            season = String.valueOf(browser.$(".season").get(0).text());
            ep = String.valueOf(browser.$(".epNumber").get(0).text());
            browser.$(".delete").get(0).click();
        }

        //delete


        //get New values
        String title1 = "";
        String season1 = "";
        String ep1 = "1";

        if( browser.$(".serie").size() > 0 ){
            title1 = String.valueOf(browser.$(".serie").get(0).text());
            season1 = String.valueOf(browser.$(".season").get(0).text());
            ep1 = String.valueOf(browser.$(".epNumber").get(0).text());
        }
        System.out.println("FIZ DELETE DO " + ep + " e sobrou o " + ep1);

        //compare them
        assertTrue("One of those should not be the same!", title != title1 || season != season1 || ep != ep1);
    }


    @Test
    @Order(10)
    public void JdeleteRecordEmpty() {
        //login
        login("vazio", "vazio");
        assertEquals("Hello", browser.el("title").text());

        String title = "";
        String season = "";
        String ep = "";

        if( browser.$(".serie").size() > 0 ){
            title = String.valueOf(browser.$(".serie").get(0).text());
            season = String.valueOf(browser.$(".season").get(0).text());
            ep = String.valueOf(browser.$(".epNumber").get(0).text());
            browser.$(".delete").get(0).click();
        }


        //get New values
        String title1 = "";
        String season1 = "";
        String ep1 = "";

        if( browser.$(".serie").size() > 0 ){
            title1 = String.valueOf(browser.$(".serie").get(0).text());
            season1 = String.valueOf(browser.$(".season").get(0).text());
            ep1 = String.valueOf(browser.$(".epNumber").get(0).text());
        }

        //compare them
        assertTrue("One of those should not be the same!", title == title1 && season == season1 && ep == ep1);
    }

    @Test
    @Order(11)
    public void KaddRecordAjax() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        Hashtable<String, String> login = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        my_dict.put("serieName", "Breaking Bad");
        my_dict.put("seasonNumber", "1");
        my_dict.put("episodeNumber", "2");
        my_dict.put("watchedDate", date);


        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());


        browser.$(".seasonNumber").fill().with(my_dict.get("seasonNumber"));
        browser.$(".episodeNumber").fill().with(my_dict.get("episodeNumber"));
        browser.$(".watchedDate").fill().with(my_dict.get("watchedDate"));
        browser.$(".serieName").fillSelect().withValue(my_dict.get("serieName"));


        browser.$(".javascript").click();

        FluentWebElement s = browser.$(".list").get(0);
        String title = s.el(".serie").text();
        String season = s.el(".season").text();
        String ep = s.el(".epNumber").text();
        String epName = s.el(".epName").text();

        System.out.println(title + season + ep + epName);



        User user = User.checkUser(login.get("user")).get(0);
        System.out.println(user.getUser());
        Records r = Records.getLastFromUser(user.getId());
        System.out.println(r.getEpisode().getName());
        assertEquals(r.getEpisode().getName(), epName);

        System.out.println(r.getEpisode().getName());

        assertEquals(title, my_dict.get("serieName"));
        assertEquals(season, my_dict.get("seasonNumber"));
        assertEquals(ep, my_dict.get("episodeNumber"));

        //browser.$(".delete").get(0).click();
    }

    @Test
    @Order(12)
    public void LgetHTML() {
        //login
        browser.goTo("/login");
        browser.$("#password").fill().with("abc");
        browser.$("#user").fill().with("abc");
        browser.$(".create").submit();

        //get page source of index
        String html = browser.pageSource();

        assertNotNull(html);
    }

    @Test
    @Order(13)
    public void MaddSerie() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> login = new Hashtable<String, String>();
        Hashtable<String, String> serie = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        serie.put("name", "NewSerie");
        serie.put("category", "1");
        serie.put("classification", "9");
        serie.put("date", date);

        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.goTo(routes.HomeController.series().toString());
        assertEquals("Series", browser.el("title").text());

        for(Map.Entry i: serie.entrySet()){
            browser.el("." + i.getKey().toString()).fill().with(i.getValue().toString());
        }
        browser.$(".create").submit();

        assertEquals("Series", browser.el("title").text());
        FluentWebElement s = browser.el(".series");
        String title = s.$("td").get(s.$("td").size()-1).text();
        Serie serie1 = Serie.getAllSeries().get(Serie.getAllSeries().size() - 1);

        assertEquals(serie1.getName(), serie.get("name"));
        assertTrue(title.equals(serie.get("name")));


        serie1.delete();
        browser.goTo(routes.HomeController.series().toString());
        String html = browser.pageSource();
        assertFalse("Serie should not be on the page", html.contains(serie.get("name")));
    }


    @Test
    @Order(14)
    public void NaddEpisode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDate.now().format(formatter);

        Hashtable<String, String> login = new Hashtable<String, String>();
        Hashtable<String, String> ep = new Hashtable<String, String>();

        login.put("password", "123");
        login.put("user", "123");
        ep.put("name", "Eppppp");
        ep.put("duration", "20");
        ep.put("classification", "9");
        ep.put("release_date", date);

        login(login.get("user"), login.get("password"));
        assertEquals("Hello", browser.el("title").text());

        browser.goTo(routes.HomeController.series().toString());

        String title = "";
        if(browser.el(".series").$("td").size() > 0){
            title = browser.el(".series").$("td").get(0).text();
            browser.el(".series").$("td").get(0).el("a").click();
            assertEquals(title, browser.el("title").text());
        }

        browser.el(".seasons").el("a").click();
        assertEquals("Seasons", browser.el("title").text());

        if(browser.el(".list").$("td").size() > 0){
            title += " - Season " + browser.el(".list").$("td").get(0).text();
            browser.el(".list").$("td").get(0).el("a").click();
            assertEquals(title, browser.el("title").text());
        }

        browser.el(".episodes").el("a").click();
        assertEquals("Episodes", browser.el("title").text());

        for(Map.Entry i: ep.entrySet()){
            browser.el("." + i.getKey().toString()).fill().with(i.getValue().toString());
        }
        browser.$(".create").submit();

        String[] URList = browser.url().split("seasons/");
        String seasonID = URList[1].split("/")[0];

        String epName = "";
        String lastEpisode = "";
        Episode last = null;
        List<Episode> episodes = Episode.getEpisodesFromSeason(Integer.valueOf(seasonID));
        if( episodes.size() > 0 ){
            last = episodes.get(episodes.size() - 1);
            lastEpisode = last.getName();

        }

        if(browser.el(".list").$("td").size() > 0){
            epName = browser.el(".list").$("td").get(browser.el(".list").$("td").size()-1).text();

        }

        String html = browser.pageSource();

        assertTrue("name should be on the page", html.contains(ep.get("name")));
        assertEquals(ep.get("name"), lastEpisode);
        assertEquals(ep.get("name"), epName);

        last.delete();

        episodes = Episode.getEpisodesFromSeason(Integer.valueOf(seasonID));

        assertNotEquals(last, episodes.get(episodes.size()-1));

    }

    @Test
    @Order(15)
    public void OcanClickTitle(){
        login("123", "123");
        boolean isClickable = browser.el("title").clickable();
        assertEquals("Hello", browser.el("title").text());
        assertFalse(isClickable);
    }

    public void login(String user, String pass){
        browser.goTo(routes.HomeController.login().toString());
        browser.$("#password").fill().with(pass);
        browser.$("#user").fill().with(user);
        browser.$(".create").submit();
    }






}

