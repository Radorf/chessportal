package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.tournament.Tournament;

public interface TournamentState {
    public default boolean canStart(Tournament context, TournamentService tournamentService) {return false;};
    public void start(Tournament context, TournamentService tournamentService);
    public default boolean canFinish(Tournament context, TournamentService tournamentService) {return false;};
    public void finish(Tournament context, TournamentService tournamentService);
    public default boolean canShowGames(Tournament context, TournamentService tournamentService) {return false;};
    public default boolean canEnrolPlayers(Tournament context, TournamentService tournamentService) {return false;};
    public default boolean canEdit(Tournament context, TournamentService tournamentService) {return false;};
}
