package ru.hpclab.hl.module1.service.statistics;

import java.time.Instant;

public class Timing {
    private String name;
    private Instant start;
    private Instant stop;

    public Timing(String name, Instant start, Instant stop) {
        this.name = name;
        this.start = start;
        this.stop = stop;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStop() {
        return stop;
    }

    public void setStop(Instant stop) {
        this.stop = stop;
    }
}