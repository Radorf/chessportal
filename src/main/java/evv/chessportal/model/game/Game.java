/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.game;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.teammatch.TeamMatch;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


/**
 *
 * @author E_Villodas
 */
@Entity
public class Game {

    public Game() {
    }

    public enum ScoreResult {
        WIN, LOSE, DRAW
    };
    private Long id;
    private ScoreResult score;
    private Player whitePiecesPlayer;

    private Player blackPiecesPlayer;

    private TeamMatch teamMatch;

    @Enumerated(EnumType.STRING)
    public ScoreResult getScore() {
        return score;
    }

    public void setScore(ScoreResult score) {
        this.score = score;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idWhitePiecesPlayer")
    public Player getWhitePiecesPlayer() {
        return whitePiecesPlayer;
    }

    public void setWhitePiecesPlayer(Player whitePiecesPlayer) {
        this.whitePiecesPlayer = whitePiecesPlayer;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBlackPiecesPlayer")
    public Player getBlackPiecesPlayer() {
        return blackPiecesPlayer;
    }

    public void setBlackPiecesPlayer(Player blackPiecesPlayer) {
        this.blackPiecesPlayer = blackPiecesPlayer;
    }

    @SequenceGenerator( // It only takes effect for
            name = "idGameGenerator", // databases providing identifier
            sequenceName = "GameSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idGameGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long idGame) {
        this.id = idGame;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMatch")
    public TeamMatch getTeamMatch() {
        return teamMatch;
    }

    public void setTeamMatch(TeamMatch teamMatch) {
        this.teamMatch = teamMatch;
    }

}
