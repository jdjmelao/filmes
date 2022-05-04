package auxiliar;

public class Auxiliar {
    private String serieName;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeName;

    public Auxiliar(String serieName, int seasonNumber, int episodeNumber, String episodeName) {
        this.serieName = serieName;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeName = episodeName;
    }

    public String getSerieName() {
        return serieName;
    }

    public void setSerieName(String serieName) {
        this.serieName = serieName;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }
}
