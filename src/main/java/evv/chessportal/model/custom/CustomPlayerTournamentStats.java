package evv.chessportal.model.custom;

import evv.chessportal.model.player.Player;

public class CustomPlayerTournamentStats {
    private Player player;
    private int won;
    private int lost;
    private int draw;
    private int games;
    private float score;
    private int wonWhite;
    private int lostWhite;
    private int drawWhite;
    private int gamesWhite;
    private float scoreWhite;

    public CustomPlayerTournamentStats(Player player) {
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getWonWhite() {
        return wonWhite;
    }

    public void setWonWhite(int wonWhite) {
        this.wonWhite = wonWhite;
    }

    public int getLostWhite() {
        return lostWhite;
    }

    public void setLostWhite(int lostWhite) {
        this.lostWhite = lostWhite;
    }

    public int getDrawWhite() {
        return drawWhite;
    }

    public void setDrawWhite(int drawWhite) {
        this.drawWhite = drawWhite;
    }
    
    public int getWonBlack() {
        return won-wonWhite;
    }

    public int getLostBlack() {
        return lost-lostWhite;
    }

    public int getDrawBlack() {
        return draw-drawWhite;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }
    
    public int getGamesWhite() {
        return gamesWhite;
    }

    public void setGamesWhite(int gamesWhite) {
        this.gamesWhite = gamesWhite;
    }

    public float getScoreWhite() {
        return scoreWhite;
    }

    public void setScoreWhite(float scoreWhite) {
        this.scoreWhite = scoreWhite;
    }
    
    public int getGamesBlack() {
        return games-gamesWhite;
    }

    public float getScoreBlack() {
        return score-scoreWhite;
    }

    public void addWin(boolean isWhite) {
        won++;
        if (isWhite) {
            wonWhite++;
            gamesWhite++;
            scoreWhite++;
        }
        games++;
        score++;
    }
    
    public void addLost(boolean isWhite) {
        lost++;
        if (isWhite) {
            lostWhite++;
            gamesWhite++;
        }
        games++;
    }
    
    public void addDraw(boolean isWhite) {
        draw++;
        if (isWhite) {
            drawWhite++;
            gamesWhite++;
            scoreWhite+=0.5;
        }
        games++;
        score+=0.5;
    }
}
