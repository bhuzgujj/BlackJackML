package dev.emileboucher.blackjackml.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An object to show a row of X sessions played
 */
public class ReportRow {
    private long  sessionNumber;
    private int sessionsWon;
    private int  sessionsLost;
    private long totalGamesPlayed;
    private int  gamesWon;
    private int  gamesLost;

    /**
     * Full constructor if we need to change any value
     * @param sessionNumber of the row
     * @param sessionsWon of the row
     * @param sessionsLost of the row
     * @param totalGamesPlayed in total
     * @param gamesWon in the row
     * @param gamesLost in the row
     */
    public ReportRow(long sessionNumber, int sessionsWon, int sessionsLost, long totalGamesPlayed, int gamesWon, int gamesLost) {
        this.sessionNumber = sessionNumber;
        this.sessionsWon = sessionsWon;
        this.sessionsLost = sessionsLost;
        this.totalGamesPlayed = totalGamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }

    /**
     * Partial constructor if we need to change any value
     * @param sessionNumber of the row
     * @param totalGamesPlayed in total
     */
    public ReportRow(long sessionNumber, long totalGamesPlayed) {
        this.sessionNumber = sessionNumber;
        this.sessionsWon = 0;
        this.sessionsLost = 0;
        this.totalGamesPlayed = totalGamesPlayed;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    /**
     * Increase the session number by 1
     */
    public void increaseSessionNumber() {
        sessionNumber++;
    }

    /**
     * Reset infos that are linked to the row
     */
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

    public long getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getGamesPlayed() {
        return gamesLost + gamesWon;
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

    public void setTotalGamesPlayed(long totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    /**
     * Create a full copy of the row
     * @return [ReportRow]
     */
    public ReportRow copy() {
        return new ReportRow(sessionNumber, sessionsWon, sessionsLost, totalGamesPlayed, gamesWon, gamesLost);
    }
}
