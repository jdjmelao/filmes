import org.junit.Test;
import play.test.WithBrowser;

import static org.junit.Assert.*;

public class BrowserFunctionalTest extends WithBrowser {

    @Test
    public void loginWorks() {
        browser.goTo("/login");
        System.out.println(browser.el("title").text());
        browser.$("#password").fill().with("123");
        browser.$("#user").fill().with("123");
        browser.$(".create").submit();
        assertEquals("Hello", browser.el("title").text());
    }


    @Test
    public void addRecord() {
        String serie = "Breaking Bad";
        String season = "1";
        String ep = "3";
        String date = "2022-09-22";
        browser.goTo("/login");
        browser.$("#password").fill().with("123");
        browser.$("#user").fill().with("123");
        browser.$(".create").submit();
        browser.$(".serieName").fillSelect().withValue(serie);
        browser.$(".seasonNumber").fill().with(season);
        browser.$(".episodeNumber").fill().with(ep);
        browser.$(".watchedDate").fill().with(date);
        browser.$(".create").submit();
        System.out.print(browser.$(".list").get(0).text());
        String s = String.valueOf(browser.$(".list").get(0).text());
        String title = s.split(" ")[0].toString() + " " + s.split(" ")[1].toString();
        String season1 = s.split(" ")[2].toString();
        String ep1 = s.split(" ")[3].toString();
        assertEquals(title, serie);
        assertEquals(season1, season);
        assertEquals(ep1, ep);

        //delete
    }

    @Test
    public void deleteRecord() {
        //login
        browser.goTo("/login");
        browser.$("#password").fill().with("123");
        browser.$("#user").fill().with("123");
        browser.$(".create").submit();

        String title = "";
        String season = "";
        String ep = "";

        try {
            title = String.valueOf(browser.$(".serie").get(0).text());
            season = String.valueOf(browser.$(".season").get(0).text());
            ep = String.valueOf(browser.$(".epNumber").get(0).text());
            browser.$(".delete").get(0).click();
        } catch(Exception e) {
            System.out.println("There's nothing to delete... :(");
        }

        //delete


        //get New values
        String title1 = "";
        String season1 = "";
        String ep1 = "1";

        try {
            title1 = String.valueOf(browser.$(".serie").get(0).text());
            season1 = String.valueOf(browser.$(".season").get(0).text());
            ep1 = String.valueOf(browser.$(".epNumber").get(0).text());
        } catch(Exception e) {
            System.out.println("There's nothing there... :(");
        }

        //compare them
        assertTrue("One of those should not be the same!", title != title1 || season != season1 || ep != ep1);
    }

    @Test
    public void getHTML() {
        //login
        browser.goTo("/login");
        browser.$("#password").fill().with("abc");
        browser.$("#user").fill().with("abc");
        browser.$(".create").submit();

        //get page source of index
        String html = browser.pageSource();
        System.out.println(html);
        assertNotNull(html);
    }




}

