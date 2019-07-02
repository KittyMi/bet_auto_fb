package com.auto.ball.util;




import java.io.IOException;

import static com.auto.ball.sce.Constant.LOCALSERVER;
import static com.auto.ball.sce.Constant.SESSION_ID;

public class URLAndSession {

    public String getSessionID() throws IOException {

        return SESSION_ID;
    }

    public void setSessionID(String sessionID) throws IOException {

        SESSION_ID=sessionID;
    }

    public String getLocalserver() throws IOException {

        return LOCALSERVER;
    }

    public void setLocalserver(String localserver) throws IOException {
       LOCALSERVER=localserver;
    }




}
