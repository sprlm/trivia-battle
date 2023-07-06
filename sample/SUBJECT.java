package sample;

public enum SUBJECT {
    HISTORY("Assets/PNG/history.png"),
    PHYSICS("Assets/PNG/physics.png"),
    DATAANALYSIS("Assets/PNG/dataAnalysis.png"),
    EVERYTHING("Assets/PNG/everything.png")
    ;
    private String urlSubject;
    SUBJECT(String urlSubject) {
        this.urlSubject = urlSubject;
    }
    public String getUrlSubject(){
        return this.urlSubject;
    }
}
