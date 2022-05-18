package forms;

import models.Serie;

import javax.persistence.Column;
import java.util.Date;

public class SeasonForm {
    private Integer serieId;

    private Integer seasonNumber;

    private Integer total_duration_minutes;

    private Integer number_of_episodes;

    private String release_date;

    private Integer classification;

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieName(Integer serieId) {
        this.serieId = serieId;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }
}
