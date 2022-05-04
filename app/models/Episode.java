package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Episode extends Model {

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer seasonId;

    @Column(nullable = false)
    private int ep_number;

    @Column(nullable = false)
    private String name;

    private int duration_minutes;

    private String release_date;

    private int classification;


    public static final Finder<Integer, Episode> finder = new Finder<Integer, Episode>(Episode.class);

    public static List<Episode> checkEp(Integer id){
        List<Episode> ep = finder.query().where(Expr.eq( "id", id )).findList();
        return ep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public int getEp_number() {
        return ep_number;
    }

    public void setEp_number(int ep_number) {
        this.ep_number = ep_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
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
