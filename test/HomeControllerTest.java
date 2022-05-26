import controllers.routes;
import models.User;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static net.sourceforge.htmlunit.corejs.javascript.UniqueTag.NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testLogin() {
        Http.RequestBuilder request = Helpers.fakeRequest(routes.HomeController.login());

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testLoginRedirect() {
        Http.RequestBuilder request = Helpers.fakeRequest(routes.HomeController.index());

        Result result = route(app, request);
        assertEquals(303, result.status());
    }

    @Test
    public void testUser() {
        User macintosh = User.checkById(1);
        assertEquals("123", macintosh.getUser());
    }


}
