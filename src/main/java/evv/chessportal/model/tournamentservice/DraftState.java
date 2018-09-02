package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;

public class DraftState implements TournamentState {

    @Override
    public boolean canStart(Tournament context, TournamentService tournamentService) {
        if (context instanceof IndividualTournament) {
            return ((IndividualTournament)context).getPlayerList().size()>1;
        } else {
            return false;
        }
    }

    @Override
    public void start(Tournament context, TournamentService tournamentService) {
        try {
            tournamentService.generateRRIndividualTournamentRounds(context.getId());
            tournamentService.changeTournamentStatus(context, new StartedState());
        } catch (InstanceNotFoundException e) {
        }
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
    public boolean canEnrolPlayers(Tournament context, TournamentService tournamentService) {
        return true;
    }

    @Override
    public boolean canEdit(Tournament context, TournamentService tournamentService) {
        return true;
    }
}
