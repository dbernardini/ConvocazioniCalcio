package com.bernardini.danilo.convocazioniriofreddo;

import java.util.Arrays;

public class Call {

    private String home;
    private String away;
    private String place;
    private String date;
    private String callPlace;
    private String callTime;
    private String notes;
    private String[] convocates;

    public Call(String home, String away, String place, String date, String callPlace, String callTime, String notes, String[] convocates) {
        this.home = home;
        this.away = away;
        this.place = place;
        this.date = date;
        this.callPlace = callPlace;
        this.callTime = callTime;
        this.notes = notes;
        this.convocates = convocates;
    }

    public Call(String home, String away, String place, String date, String callPlace, String callTime, String notes) {
        this.home = home;
        this.away = away;
        this.place = place;
        this.date = date;
        this.callPlace = callPlace;
        this.callTime = callTime;
        this.notes = notes;
        this.convocates = convocates;
    }

    public Call(String home, String away, String place, String date, String callPlace, String callTime, String[] convocates) {
        this.home = home;
        this.away = away;
        this.place = place;
        this.date = date;
        this.callPlace = callPlace;
        this.callTime = callTime;
        this.convocates = convocates;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCallPlace() {
        return callPlace;
    }

    public void setCallPlace(String callPlace) {
        this.callPlace = callPlace;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String[] getConvocates() {
        return convocates;
    }

    public void setConvocates(String[] convocates) {
        this.convocates = convocates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call call = (Call) o;

        if (!home.equals(call.home)) return false;
        if (!away.equals(call.away)) return false;
        if (place != null ? !place.equals(call.place) : call.place != null) return false;
        if (!date.equals(call.date)) return false;
        if (callPlace != null ? !callPlace.equals(call.callPlace) : call.callPlace != null)
            return false;
        if (callTime != null ? !callTime.equals(call.callTime) : call.callTime != null)
            return false;
        return notes != null ? notes.equals(call.notes) : call.notes == null;

    }

    @Override
    public int hashCode() {
        int result = home.hashCode();
        result = 31 * result + away.hashCode();
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + (callPlace != null ? callPlace.hashCode() : 0);
        result = 31 * result + (callTime != null ? callTime.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Call{" +
                "home='" + home + '\'' +
                ", away='" + away + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", callPlace='" + callPlace + '\'' +
                ", callTime='" + callTime + '\'' +
                ", notes='" + notes + '\'' +
                ", convocates=" + Arrays.toString(convocates) +
                '}';
    }
}
