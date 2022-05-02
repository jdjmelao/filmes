package models;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Records extends Model {

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer episodeId;

    private String watchedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Integer episodeId) {
        this.episodeId = episodeId;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
