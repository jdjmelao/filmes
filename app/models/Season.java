package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Season extends Model {

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer serieId;

    @Column(nullable = false)
    private int seasonNumber;

    private int total_duration_minutes;

    private int number_of_episodes;

    private String release_date;

    private int classification;

    public static final Finder<Integer, Season> finder = new Finder<Integer, Season>(Season.class);

    public static List<Season> checkSeason(Integer id){
        List<Season> season = finder.query().where(Expr.eq( "id", id )).findList();
        return season;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getTotal_duration_minutes() {
        return total_duration_minutes;
    }

    public void setTotal_duration_minutes(int total_duration_minutes) {
        this.total_duration_minutes = total_duration_minutes;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

}


