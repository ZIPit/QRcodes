package ziphome.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ClientData {
    String myfxtm_id;
    String clName;
    String eMail;
    String seminarID;
    int idIndex;
    String extType;

    public ClientData(String myfxtm_id, String clName, String eMail, String seminarID, int idIndex, String extType) {
        this.myfxtm_id = myfxtm_id;
        this.clName = clName;
        this.eMail = eMail;
        this.seminarID = seminarID;
        this.idIndex =  idIndex;
        this.extType = extType;
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
    public String getIdIndex(){
        return Integer.toString(idIndex);
    }
    public String getExtType() {
        return extType;
    }

}
