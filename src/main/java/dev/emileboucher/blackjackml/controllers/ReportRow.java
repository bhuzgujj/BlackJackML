package dev.emileboucher.blackjackml.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportRow {
    private final int  sessionNumber;
    private final int sessionsWon;
    private final int  sessionsLost;
    private final int  gamesPlayed;
    private final int  gamesWon;
    private final int  gamesLost;

    public ReportRow(int p_sessionNumber, int p_sessionsWon, int p_sessionsLost, int p_gamesPlayed, int p_gamesWon, int p_gamesLost) {
        sessionNumber = p_sessionNumber;
        sessionsWon = p_sessionsWon;
        sessionsLost = p_sessionsLost;
        gamesPlayed = p_gamesPlayed;
        gamesWon = p_gamesWon;
        gamesLost = p_gamesLost;
    }

    public String getWinLostRatio() {
        BigDecimal fractionaryNumber = BigDecimal.valueOf(
                ((double) gamesWon / (gamesWon + gamesLost)) * 100
        ).setScale(2, RoundingMode.HALF_UP);
        return fractionaryNumber.toString() + " %";
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public int getSessionsWon() {
        return sessionsWon;
    }

    public int getSessionsLost() {
        return sessionsLost;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }
}
