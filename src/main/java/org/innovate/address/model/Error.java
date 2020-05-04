package org.innovate.address.model;

/**
 * @author BChekuri
 */
public class Error {
    private int id;
    private String message;
    private String developerText;

    public Error(int id, String message, String developerText) {
        this.id = id;
        this.message = message;
        this.developerText = developerText;
    }

    public Error(int id, String message) {
        this.id = id;
        this.message = message;
        this.developerText = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperText() {
        return developerText;
    }

    public void setDeveloperText(String developerText) {
        this.developerText = developerText;
    }
}
