package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Episode extends Model {

    @Id
    private Integer id;

    @ManyToOne
    private Season season;

    @Column(nullable = false)
    private Integer ep_number;

    @Column(nullable = false)
    private String name;

    private Integer duration_minutes;

    private Date release_date;

    private Integer classification;


    public static final Finder<Integer, Episode> finder = new Finder<Integer, Episode>(Episode.class);

    public static Episode checkEp(Integer id){
        Episode ep = finder.byId(id);
        return ep;
    }

    public static Episode getEpisodeFromSeason(Integer id, Integer episodeNumb){
        Episode episode = finder.query().where().and(Expr.eq( "season.id", id ), Expr.eq("ep_number", episodeNumb)).findOne();
        return episode;
    }

    public static Episode getEpisodeFromSeasonIds(Integer id, Integer episodeId){
        Episode episode = finder.query().where().and(Expr.eq( "season.id", id ), Expr.eq("id", episodeId)).findOne();
        return episode;
    }

    public static List<Episode> getEpisodesFromSeason(Integer id){
        List<Episode> episodes = finder.query().where(Expr.eq( "season.id", id )).findList();
        return episodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Integer getEp_number() {
        return ep_number;
    }

    public void setEp_number(Integer ep_number) {
        this.ep_number = ep_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(Integer duration_minutes) {
        this.duration_minutes = duration_minutes;
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
