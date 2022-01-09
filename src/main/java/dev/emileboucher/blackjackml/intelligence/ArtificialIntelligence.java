package dev.emileboucher.blackjackml.intelligence;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.concretes.Deal;
import dev.emileboucher.blackjackml.api.requests.concretes.Flag;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.singletons.AiSingleton;

import java.io.IOException;

public class ArtificialIntelligence {
    private final ReportRow row = new ReportRow(0,0,0,0,0,0);
    private final RestClient client = new RestClient("http://localhost:3000");
    private int nbReport = 0;
    private int money = 1000;
    public ReportRow play() {
        row.reset();
        report();
        return row.copy();
    }

    private void report() {
        Boolean exploration = nbReport % 5 == 0;
        do {
            session(exploration);
            row.increaseSessionNumber();
        } while (row.getSessionNumber() % 100 != 0);
        nbReport++;
    }

    private void session(Boolean exploration) {
        Response response = new Response();
        int gamePlayed = 0;
        do {
            response = game(exploration);
            gamePlayed++;
            if (!AiSingleton.getInstance().getPlaying()) break;
        } while (!recordSession(response));
        client.resetCookies();
        row.setGamesPlayed(row.getGamesPlayed() + gamePlayed);
    }

    private boolean recordSession(Response response) {
        switch (response.getSessionState()) {
            case WON -> {
                row.setSessionsWon(row.getSessionsWon() + 1);
                try {
                    client.send(new Flag());
                } catch (IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return true;
            }
            case LOST -> {
                row.setSessionsLost(row.getSessionsLost() + 1);
                return true;
            }
        }
        return false;
    }

    private Response game(Boolean exploration) {
        Response response = null;
        try {
            int bet = Math.min(money, 50);
            response = client.send(new Deal(bet));
            while (response == null) {
                response = client.send(new Deal(bet));
            }
            money = response.cash;
            while (response.isPlaying()) {
                response = client.send(DecisionMaker.reinforcementLearning(response, exploration));
            }
            recordResults(response, exploration);
        }  catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    private void recordResults(Response response, Boolean exploration) {
        if (!exploration) {
            switch (response.getResult()) {
                case WON -> {
                    row.setGamesWon(row.getGamesWon() + 1);
                }
                case LOST -> {
                    row.setGamesLost(row.getGamesLost() + 1);
                }
            }
        }
        AiSingleton.getInstance().reward(response.toString(), response.getResult().reward());
    }
}
