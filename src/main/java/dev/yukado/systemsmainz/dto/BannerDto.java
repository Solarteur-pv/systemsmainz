package dev.yukado.systemsmainz.dto;

import java.sql.Timestamp;

public class BannerDto {

    private Long id;
    private String filename;
    private String bannerText;
    private Boolean active;
    private Timestamp created_at;
    private String contentType;

    private byte[] data;
    public BannerDto() {

    }
    public BannerDto(Long id, String filename, String bannerText, Boolean active, Timestamp created_at, String contentType, byte[] data) {
        super();
        this.id = id;
        this.filename = filename;
        this.bannerText = bannerText;
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

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
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

