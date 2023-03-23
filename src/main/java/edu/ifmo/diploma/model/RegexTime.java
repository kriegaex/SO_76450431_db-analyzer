package edu.ifmo.diploma.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegexTime {
    @JsonProperty
    private String regex;
    @JsonProperty
    private long ms;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }

    @Override
    public String toString() {
        return "RegexTime{" +
                "regex='" + regex + '\'' +
                ", ms=" + ms +
                '}';
    }
}