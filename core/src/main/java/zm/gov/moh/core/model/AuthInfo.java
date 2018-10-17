package zm.gov.moh.core.model;

class User {

    private String uuid;
    private String display;
    private String username;
    private String systemId;

}

class Token{

    private String token;
}

public class AuthInfo {

    private User user;
    private String token;

}

  /*      "user": {
        "uuid": "8a425edd-9c82-11e8-ad8c-0a38cfe5bccc",
        "display": "admin",
        "username": null,
        "systemId": "admin"
        },
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InV1aWQiOiI4YTQyNWVkZC05YzgyLTExZTgtYWQ4Yy0wYTM4Y2ZlNWJjY2MiLCJkaXNwbGF5IjoiYWRtaW4iLCJ1c2VybmFtZSI6bnVsbCwic3lzdGVtSWQiOiJhZG1pbiJ9LCJpYXQiOjE1Mzk3NTk0MzUsImV4cCI6MTUzOTc1OTU1NX0.JpOGnrTZroPCc0pY5sBs2ySQO_WoQ5wkTp9Gk5BFj9s"
        }*/