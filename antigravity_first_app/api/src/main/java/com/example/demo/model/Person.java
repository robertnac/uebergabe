package com.example.demo.model;

import java.time.LocalDate;

public class Person {
    private String vorname;
    private String nachname;
    private LocalDate birthDate;
    private boolean verheiratet;
    private String hautfarbe;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isVerheiratet() {
        return verheiratet;
    }

    public void setVerheiratet(boolean verheiratet) {
        this.verheiratet = verheiratet;
    }

    public String getHautfarbe() {
        return hautfarbe;
    }

    public void setHautfarbe(String hautfarbe) {
        this.hautfarbe = hautfarbe;
    }

}
