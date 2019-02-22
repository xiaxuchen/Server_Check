package com.cxyz.check.entity;

public class Photo {

    private Integer id;//图片id

    private String uri;//图片路径

    private Vacate vac;//请假信息

    public Photo() {
    }

    public Photo(String uri) {
        this.uri = uri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Vacate getVac() {
        return vac;
    }

    public void setVac(Vacate vac) {
        this.vac = vac;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", vac=" + vac +
                '}';
    }
}
