package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Season extends Model {

    @Id
    private Integer id;

    @ManyToOne
    private Serie serie;

    @Column(nullable = false)
    private Integer seasonNumber;

    private Integer total_duration_minutes;

    private Integer number_of_episodes;

    private Date release_date;

    private Integer classification;

    public static final Finder<Integer, Season> finder = new Finder<Integer, Season>(Season.class);

    public static Season checkSeason(Integer id){
        Season season = finder.byId(id);
        return season;
    }

    public static Season getSeasonFromSerie(Integer id, Integer seasonNum){
        Season season = finder.query().where().and(Expr.eq( "serie.id", id ), Expr.eq("seasonNumber", seasonNum)).findOne();
        return season;
    }

    public static Season getSeasonFromSerieIds(Integer id, Integer seasonId){
        Season season = finder.query().where().and(Expr.eq( "serie.id", id ), Expr.eq("id", seasonId)).findOne();
        return season;
    }

    public static List<Season> getSeasonsFromSerie(Integer id){
        List<Season> seasons = finder.query().where(Expr.eq( "serie.id", id )).findList();
        return seasons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getTotal_duration_minutes() {
        return total_duration_minutes;
    }

    public void setTotal_duration_minutes(Integer total_duration_minutes) {
        this.total_duration_minutes = total_duration_minutes;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(Integer number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
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


