package dev.emileboucher.blackjackml.intelligence;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.concretes.Deal;
import dev.emileboucher.blackjackml.api.requests.concretes.Flag;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.singletons.AiSingleton;

import java.io.IOException;

/**
 * Ai interaction with the api
 */
public class AiInteractions {
    private final ReportRow row = new ReportRow(0,0);
    private final RestClient client = new RestClient("http://localhost:3000");
    private int nbReport = 0;
    private int money = 1000;

    /**
     * Let the AI play
     * @param amountSessions played before ending
     * @return [ReportRow]
     */
    public ReportRow play(int amountSessions) {
        row.reset();
        Boolean exploration = nbReport % 5 == 0;
        do {
            session(exploration);
            row.increaseSessionNumber();
            if (!AiSingleton.getInstance().getPlaying()) break;
            AiSingleton.getInstance().setSessionNumber(AiSingleton.getInstance().getSessionNumber() + 1);
        } while (row.getSessionNumber() % amountSessions != 0);
        nbReport++;
        return row.copy();
    }

    /**
     * Play until the AI has either won or lost
     * @param isExploring other solution possible (choice random)
     */
    private void session(Boolean isExploring) {
        Response response = new Response();
        int gamePlayed = 0;
        do {
            response = game(isExploring);
            gamePlayed++;
            if (!AiSingleton.getInstance().getPlaying()) break;
        } while (!recordSession(response));
        client.resetCookies();
        row.setTotalGamesPlayed(row.getTotalGamesPlayed() + gamePlayed);
    }

    /**
     * Add the information of a session into the global [ReportRow] object
     * @param response from the api
     * @return if the session is done
     */
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

    /**
     * AI plays a game with the API
     * @param isExploring other solution possible (choice random)
     * @return The last [Response] from the api
     */
    private Response game(Boolean isExploring) {
        Response response = null;
        try {
            response = deal();
            while (response.isPlaying()) {
                response = client.send(DecisionMaker.reinforcementLearning(response, isExploring));
            }
            recordResults(response, isExploring);
        }  catch (IOException | InterruptedException e) {
            System.out.println("game(): " + e.getMessage());
        }
        return response;
    }

    /**
     * Do a deal command to the api
     * @return [Response] from the api
     * @throws IOException from the [HttpClient]
     * @throws InterruptedException from the [HttpClient]
     */
    private Response deal() throws IOException, InterruptedException {
        int bet = Math.min(money, 50);
        Response response = client.send(new Deal(bet));
        while (response == null) {
            response = client.send(new Deal(bet));
        }
        money = response.cash;
        return response;
    }

    /**
     * Add the response to the [ReportRow]
     * @param response from the api
     * @param isExploring other solution possible (choice random)
     */
    private void recordResults(Response response, Boolean isExploring) {
        if (!isExploring) {
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
