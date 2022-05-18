package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Records extends Model {

    @Id
    private Integer id;

    @ManyToOne
    private Episode episode;

    @ManyToOne
    private User user;

    private Date watchedDate;

    public static final Finder<Integer, Records> finder = new Finder<Integer, Records>(Records.class);

    public static List<Records> getRecords(Integer id){
        List<Records> records = finder.query().where(Expr.eq( "user.id", id )).findList();
        return records;
    }

    public static Records getRecord(Integer id, Integer episodeId){
        Records record = finder.query().where().and(Expr.eq( "user.id", id ), Expr.eq( "episode.id", episodeId )).findOne();
        return record;
    }

    public static Records getLastRecordsOfSerie(Integer id, Integer serieId){
        return finder.query().where().and(Expr.eq( "user.id", id ), Expr.eq( "episode.season.serie.id", serieId )).orderBy("episode.season.seasonNumber desc, episode.ep_number desc").setMaxRows(1).findOne();
    }

    public static List<Records> getLastRecords(Integer id){
        List<Records> result = new ArrayList<>();
        List<Records> records = getRecords(id);
        List<Integer> ids = new ArrayList<>();
        for (Records r: records){
            Integer serieId = r.getEpisode().getSeason().getSerie().getId();
            ids.remove(serieId);
            ids.add(serieId);
        }

        for (Integer i: ids){
            Records record = getLastRecordsOfSerie(id, i);
            result.add(record);
        }

        result.sort(Comparator.comparing(Records::getWatchedDate).reversed());

        return result;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }
}
