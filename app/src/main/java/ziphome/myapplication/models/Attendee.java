package ziphome.myapplication.models;

public class Attendee {
   private String seminar_id;
   private String external_id;
   private String external_type;
   private String attend_ts;
   private Boolean is_manual;

    public Attendee(String seminar_id, String external_id, String external_type, String attend_ts, String is_manual) {
        this.seminar_id = seminar_id;
        this.external_id = external_id;
        this.external_type = external_type;
        this.attend_ts = attend_ts;
        this.is_manual = Boolean.parseBoolean(is_manual);
    }

    public String getSeminar_id() {
        return seminar_id;
    }
}
