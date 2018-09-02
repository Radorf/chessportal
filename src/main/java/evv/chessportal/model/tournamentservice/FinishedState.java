package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.tournament.Tournament;

public class FinishedState implements TournamentState {

    @Override
    public boolean canStart(Tournament context, TournamentService tournamentService) {
        return false;
    }

    @Override
    public void start(Tournament context, TournamentService tournamentService) {
        return;
    }

    @Override
    public boolean canFinish(Tournament context, TournamentService tournamentService) {
        return false;
    }

    @Override
    public void finish(Tournament context, TournamentService tournamentService) {
        return;
    }

    @Override
    public boolean canShowGames(Tournament context, TournamentService tournamentService) {
        return true;
    }
}
