package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;

public class StartedState implements TournamentState {

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
        return tournamentService.hasAllGamesScored(context.getId());
    }

    @Override
    public void finish(Tournament context, TournamentService tournamentService) {
        try {
            tournamentService.changeTournamentStatus(context, new FinishedState());
        } catch (InstanceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean canShowGames(Tournament context, TournamentService tournamentService) {
        return true;
    }

    @Override
    public boolean canEdit(Tournament context, TournamentService tournamentService) {
        return true;
    }
}
