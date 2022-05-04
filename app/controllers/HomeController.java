package controllers;

import auxiliar.Auxiliar;
import models.Episode;
import models.Records;
import models.Season;
import models.User;
import forms.UserForm;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.libs.concurrent.HttpExecutionContext;
import scala.collection.immutable.Seq;
import views.html.index;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static play.libs.Scala.asScala;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private FormFactory formFactory;

    @Inject
    private HttpExecutionContext httpExecutionContext;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }



    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        List<Auxiliar> eps = new ArrayList<>();
        try {
            String username = String.valueOf(request.session().get("connected")).replace("Optional[", "").replace("]", "");
            System.out.println(username);
            User userObject = User.checkUser(username).get(0);
            Integer userId = userObject.getId();
            List<Records> records = Records.getRecords(userId);
            for (Records r: records){
                Episode ep = Episode.checkEp(r.getEpisodeId()).get(0);
                Season season = Season.checkSeason(ep.getSeasonId()).get(0);
                System.out.println("Ei");
                Auxiliar aux = new Auxiliar("Narcos", season.getSeasonNumber(), ep.getEp_number(), ep.getName());
                eps.add(aux);
            }
        }
        catch(Exception e){
            System.out.println("Not logged");
        }

        return request
                .session()
                .get("connected")
                .map(user -> ok(index.render(asScala(eps), "Hello " + user, request, messagesApi.preferred(request))))
                .orElse(redirect(routes.HomeController.login()));
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
        List<User> userExist = User.checkUserPass(userFormData.getUser(), userFormData.getPassword());
        if(userExist.size() == 1){
            return redirect(controllers.routes.HomeController.index())
                    .addingToSession(request, "connected", userFormData.getUser());
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
        System.out.println(userExist.size());
        if (userExist.size() >= 1) {
            return redirect(controllers.routes.HomeController.signup())
                    .flashing("fail", "User Already Exist");
        }
        User user = new User();
        user.setUser(userFormData.getUser());
        user.setPassword(userFormData.getPassword());
        user.save();
        return redirect(controllers.routes.HomeController.index())
                .addingToSession(request, "connected", user.getUser());
    }

}
