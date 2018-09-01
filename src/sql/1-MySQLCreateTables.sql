DROP TABLE IF EXISTS team_Player;	
DROP TABLE IF EXISTS team_captainPlayer;	
DROP TABLE IF EXISTS teamTournament_teamRound;
DROP TABLE IF EXISTS TeamRound; 
DROP TABLE IF EXISTS teamTournament_team;
DROP TABLE IF EXISTS individualTournament_individualRound;
DROP TABLE IF EXISTS IndividualRound;
DROP TABLE IF EXISTS individualTournament_player;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS TeamMatch;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS Tournament;
DROP TABLE IF EXISTS UserProfile;
DROP TABLE IF EXISTS Person;

CREATE TABLE Person (
		id	 		BIGINT NOT NULL AUTO_INCREMENT,	
		firstName	VARCHAR(255),
		surname 	VARCHAR(255),
		email 		VARCHAR(255),
		phoneNumber	VARCHAR(255),					
		CONSTRAINT	PersonPK PRIMARY KEY(id)) ENGINE = InnoDB;

CREATE TABLE UserProfile (
		id	 					BIGINT NOT NULL AUTO_INCREMENT,					
		loginName				VARCHAR(255),
		encryptedPassword 		VARCHAR(255),
		elo 					INTEGER,
		licenseNumber			VARCHAR(255),
		idPerson				BIGINT NOT NULL,					
		userType				VARCHAR(255),
		CONSTRAINT	UserPK PRIMARY KEY(id),
		CONSTRAINT UserPersonFK FOREIGN KEY(idPerson) REFERENCES Person(id)) ENGINE = InnoDB;
		
CREATE TABLE Tournament (
		id	 				BIGINT NOT NULL AUTO_INCREMENT,					
		name_				VARCHAR(255),
		startDate			DATE,
		endDate				DATE,
		startEnrolmentDate  DATE,
		endEnrolmentDate 	DATE,
		tournamentType 		VARCHAR(255),
                pairingsType            VARCHAR(255),
		CONSTRAINT	TournamentPK PRIMARY KEY(id)) ENGINE = InnoDB;

CREATE TABLE Team (
		id			BIGINT NOT NULL AUTO_INCREMENT,
		name_		VARCHAR(255),
		CONSTRAINT TeamPK PRIMARY KEY(id)) ENGINE = InnoDB;		


CREATE TABLE TeamMatch (
		id	 				BIGINT NOT NULL AUTO_INCREMENT,
		homeTeamPoints		FLOAT,
		awayTeamPoints		FLOAT,
		idHomeTeam			BIGINT,
		idAwayTeam			BIGINT,
		CONSTRAINT TeamMatchPK PRIMARY KEY(id),
		CONSTRAINT TeamMatchHomeTeamFK FOREIGN KEY (idHomeTeam) REFERENCES Team(id),
		CONSTRAINT TeamMatchAwayTeamFK FOREIGN KEY (idAwayTeam) REFERENCES Team(id)) ENGINE = InnoDB;

CREATE TABLE Game (
		id					BIGINT NOT NULL AUTO_INCREMENT,
		score				VARCHAR(255),
		idWhitePiecesPlayer	BIGINT,
		idBlackPiecesPlayer BIGINT,
		idMatch				BIGINT,
        individualRound_id             BIGINT,
		CONSTRAINT	GamePK PRIMARY KEY(id),
		CONSTRAINT GameWhitePiecesPlayerFK FOREIGN KEY(idWhitePiecesPlayer) REFERENCES UserProfile(id),
		CONSTRAINT GameBlackPiecesPlayerFK FOREIGN KEY(idBlackPiecesPlayer) REFERENCES UserProfile(id),
		CONSTRAINT IndividualRound_IdGameFK FOREIGN KEY (individualRound_id) REFERENCES IndividualRound(id),
		CONSTRAINT GameMatchFK FOREIGN KEY(idMatch) REFERENCES TeamMatch(id)) ENGINE = InnoDB;

CREATE TABLE individualTournament_player (
		idTournament	BIGINT,
		idPlayer		BIGINT,
		CONSTRAINT IndividualTournament_playerIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id),
		CONSTRAINT IndividualTournament_playerIdPlayerFK1 FOREIGN KEY (idPlayer) REFERENCES UserProfile(id)) ENGINE = InnoDB;

CREATE TABLE IndividualRound (
		id	 				BIGINT NOT NULL AUTO_INCREMENT,
		number_				INTEGER,
		date_				DATE,
		idTournament		BIGINT,
		CONSTRAINT IndividualRoundPK PRIMARY KEY(id),
		CONSTRAINT IndividualRoundIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id)) ENGINE = InnoDB;
		
CREATE TABLE individualTournament_individualRound (
		idTournament BIGINT,
		idRound		 BIGINT,
		CONSTRAINT IndividualTournament_individualRoundIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id),
		CONSTRAINT IndividualTournament_individualRoundIdRound  FOREIGN KEY (idRound) REFERENCES IndividualRound(id)) ENGINE = InnoDB;


CREATE TABLE teamTournament_team (
		idTournament BIGINT,
		idTeam	BIGINT,
		CONSTRAINT TeamTournament_teamIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id),
		CONSTRAINT TeamTournament_teamIdTeam FOREIGN KEY (idTeam) REFERENCES Team(id))  ENGINE = InnoDB;	

CREATE TABLE TeamRound (
		id	 				BIGINT NOT NULL AUTO_INCREMENT,
		number_				INTEGER,
		date_				DATE,
		idTournament		BIGINT,
		CONSTRAINT IndividualRoundPK PRIMARY KEY(id),
		CONSTRAINT TeamRoundIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id)) ENGINE = InnoDB;			
	   
CREATE TABLE teamTournament_teamRound (
		idTournament BIGINT,
		idRound		 BIGINT,
		CONSTRAINT TeamTournament_roundIdTournamentFK FOREIGN KEY (idTournament) REFERENCES Tournament(id),
		CONSTRAINT IndividualTournament_teamRoundIdRound  FOREIGN KEY (idRound) REFERENCES TeamRound(id)) ENGINE = InnoDB;	

CREATE TABLE team_captainPlayer (
		idTeam		BIGINT,
		idPlayer	BIGINT,
        CONSTRAINT Team_captainPlayerIdTeamFK FOREIGN KEY (idTeam) REFERENCES Team(id),
        CONSTRAINT Team_captainPlayerIdPlayerFK FOREIGN KEY (idPlayer) REFERENCES UserProfile(id)) ENGINE = InnoDB;


CREATE TABLE team_Player (
		idTeam		BIGINT,
		idPlayer	BIGINT,
        CONSTRAINT Team_PlayerIdTeamFK FOREIGN KEY (idTeam) REFERENCES Team(id),
        CONSTRAINT Team_PlayerIdPlayerFK FOREIGN KEY (idPlayer) REFERENCES UserProfile(id)) ENGINE = InnoDB;	
