package controllers;

import models.User;
import forms.UserForm;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.libs.concurrent.HttpExecutionContext;
import views.html.helper.form;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final FormFactory formFactory;

    private final Form<UserForm> userForm;

    private final HttpExecutionContext httpExecutionContext;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    public HomeController(FormFactory formFactory,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
        this.userForm = formFactory.form(UserForm.class);
    }


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        String title = this.messagesApi.preferred(request).at("index.title");

        return ok(views.html.index.render(title));
    }

    public Result login(Http.Request request) { return ok(views.html.login.render(userForm, request, messagesApi.preferred(request))); }

    public Result sign(Http.Request request) { return ok(views.html.signup.render(userForm, request, messagesApi.preferred(request))); }


    public Result log(Http.Request request) {
        //Está errada, é necessário validar informação e não guarda-la
        Form<UserForm> form = userForm.bindFromRequest(request);
        UserForm userFormData = form.get();
        System.out.println(userFormData.user);
        List<User> userExist = User.checkUser(userFormData.user, userFormData.password);
        if(userExist.size() == 1){
            return redirect(controllers.routes.HomeController.index());
        }
        else {
            return redirect(controllers.routes.HomeController.login())
                    .flashing("fail", "Could not login");
        }
    }

    public Result signup(Http.Request request) {
        Form<UserForm> form = userForm.bindFromRequest(request);
        UserForm userFormData = form.get();
        User user = new User();
        user.setUser(userFormData.user);
        user.setPassword(userFormData.password);
        System.out.println(userFormData.user);
        System.out.println(userFormData.password);
        user.save();
        return redirect(controllers.routes.HomeController.index());
    }

}
