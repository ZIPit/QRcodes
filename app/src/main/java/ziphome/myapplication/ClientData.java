package ziphome.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ClientData {
    String myfxtm_id;
    String clName;
    String eMail;
    String seminarID;

    public ClientData(String myfxtm_id, String clName, String eMail, String seminarID) {
        this.myfxtm_id = myfxtm_id;
        this.clName = clName;
        this.eMail = eMail;
        this.seminarID = seminarID;
    }

    public String getMyfxtm_id() {
        return myfxtm_id;
    }

    public String getClName() {
        return clName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getSeminarID() {
        return seminarID;
    }

}
