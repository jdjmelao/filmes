package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Serie extends Model {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer number_of_seasons;

    private Integer number_of_episodes;

    private String category;

    private Integer total_duration_minutes;

    private Date release_date;

    private Integer classification;

    public static final Finder<Integer, Serie> finder = new Finder<Integer, Serie>(Serie.class);

    public static Serie checkSerie(String name){
        Serie serie = finder.query().where(Expr.eq( "name", name )).findOne();
        return serie;
    }

    public static Serie getSerieById(Integer id){
        Serie serie = finder.byId(id);
        return serie;
    }

    public static List<Serie> getAllSeries(){
        List<Serie> series = finder.all();
        return series;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(Integer number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(Integer number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotal_duration_minutes() {
        return total_duration_minutes;
    }

    public void setTotal_duration_minutes(Integer total_duration_minutes) {
        this.total_duration_minutes = total_duration_minutes;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }
}
