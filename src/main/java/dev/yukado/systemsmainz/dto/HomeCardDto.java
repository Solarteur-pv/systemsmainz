package dev.yukado.systemsmainz.dto;

import java.sql.Timestamp;

public class HomeCardDto {

    private Long id;
    private String filename;
    private String title;
    private String homeCardText;
    private Boolean active;
    private Timestamp created_at;
    private String contentType;

    private byte[] data;
    public HomeCardDto() {

    }
    public HomeCardDto(Long id, String filename, String title, String homeCardText, Boolean active, Timestamp created_at, String contentType, byte[] data) {
        super();
        this.id = id;
        this.filename = filename;
        this.title = title;
        this.homeCardText = homeCardText;
        this.active = active;
        this.created_at = created_at;
        this.contentType = contentType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomeCardText() {
        return homeCardText;
    }

    public void setHomeCardText(String homeCardText) {
        this.homeCardText = homeCardText;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}

