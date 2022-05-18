package forms;

public class SerieForm {
    private String name;

    private Integer number_of_seasons;

    private Integer number_of_episodes;

    private String category;

    private Integer total_duration_minutes;

    private String release_date;

    private Integer classification;

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
