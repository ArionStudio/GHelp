package com.example.smallgithubhelper;

import java.util.ArrayList;

//Class for holding single repo data
public class RepoCard {
    private String name, type, desc, lang, url;
    private int eye, star;
    private ArrayList<String> langs;
    private ArrayList<String> bytes;

    public RepoCard(String name, String type, String desc, String lang) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.lang = lang;
    }

    public RepoCard(String name, String type, String desc, String url, int eye, int star) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.url = url;
        this.eye = eye;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }


    public String getDesc() {
        return desc;
    }


    public String getLang() {
        return lang;
    }


    public ArrayList<String> getLangs() {
        return langs;
    }
    public ArrayList<String> getBytes() {
        return bytes;
    }

    public String getUrl() {
        return url;
    }

    public int getEye() {
        return eye;
    }

    public int getStar() {
        return star;
    }
}
