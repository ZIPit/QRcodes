package ziphome.myapplication.models;

public class AttendeeResp {
    String seminar_id;
    String external_id;
    String external_type;
    int    registration_id;
    String registered_at;
    String feedback  ;
    Boolean is_registered;
    Boolean is_attended;
    Boolean is_manual;
    String[] metadata;
    String updated_at;

    public AttendeeResp(String seminar_id, String external_id, String external_type, int registration_id, String registered_at, String feedback, Boolean is_registered, Boolean is_attended, Boolean is_manual, String[] metadata, String updated_at) {
        this.seminar_id = seminar_id;
        this.external_id = external_id;
        this.external_type = external_type;
        this.registration_id = registration_id;
        this.registered_at = registered_at;
        this.feedback = feedback;
        this.is_registered = is_registered;
        this.is_attended = is_attended;
        this.is_manual = is_manual;
        this.metadata = metadata;
        this.updated_at = updated_at;
    }

    public Boolean getIs_registered() {
        return is_registered;
    }

    public Boolean getIs_attended() {
        return is_attended;
    }
}
