package dev.emileboucher.blackjackml.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportRow {
    private long  sessionNumber;
    private int sessionsWon;
    private int  sessionsLost;
    private long  gamesPlayed;
    private int  gamesWon;
    private int  gamesLost;

    public ReportRow(long p_sessionNumber, int p_sessionsWon, int p_sessionsLost, long p_gamesPlayed, int p_gamesWon, int p_gamesLost) {
        sessionNumber = p_sessionNumber;
        sessionsWon = p_sessionsWon;
        sessionsLost = p_sessionsLost;
        gamesPlayed = p_gamesPlayed;
        gamesWon = p_gamesWon;
        gamesLost = p_gamesLost;
    }

    public void increaseSessionNumber() {
        sessionNumber++;
    }

    public void reset() {
        sessionsWon = 0;
        sessionsLost = 0;
        gamesWon = 0;
        gamesLost = 0;
    }

    public String getWinLostRatio() {
        if (gamesWon + gamesLost < 1) return "Exploration";
        BigDecimal fractionaryNumber = BigDecimal.valueOf(
                ((double) gamesWon / (gamesWon + gamesLost)) * 100
        ).setScale(2, RoundingMode.HALF_UP);
        return fractionaryNumber + " %";
    }

    public long getSessionNumber() {
        return sessionNumber;
    }

    public int getSessionsWon() {
        return sessionsWon;
    }

    public int getSessionsLost() {
        return sessionsLost;
    }

    public long getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setSessionNumber(long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public void setSessionsWon(int sessionsWon) {
        this.sessionsWon = sessionsWon;
    }

    public void setSessionsLost(int sessionsLost) {
        this.sessionsLost = sessionsLost;
    }

    public void setGamesPlayed(long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }
}
