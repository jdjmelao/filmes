package forms;

import models.Serie;

import javax.persistence.Column;
import java.util.Date;

public class SeasonForm {
    private Integer serieId;

    private Integer seasonNumber;

    private String release_date;

    private Integer classification;

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
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
