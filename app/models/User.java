package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User extends Model {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private String password;

    public static final Finder<Integer, User> finder = new Finder<Integer, User>(User.class);

    public static List<User> checkUserPass(String user, String password){
        return finder.query().where().and(Expr.eq( "user", user ), Expr.eq("password", password)).findList();
    }

    public static List<User> checkUser(String user){
        List<User> userE = finder.query().where(Expr.eq( "user", user )).findList();
        System.out.println(userE);
        return userE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
