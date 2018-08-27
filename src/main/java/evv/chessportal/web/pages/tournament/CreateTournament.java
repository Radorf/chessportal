/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournament.Tournament.TournamentPairingsType;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import java.util.Calendar;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author E_Villodas
 */
public class CreateTournament {

    @Property
    private Tournament tournament;
    @Property
    private TournamentPairingsType type;
    @Property
    private String name_;
    @Property
    private Calendar startDate;
    @Property
    private Calendar endDate;
    @Property
    private Calendar startEnrolmentDate;
    @Property
    private Calendar endEnrolmentDate;
    @Component
    private Form tournamentForm;
    @Component(id = "name")
    private TextField nameField;
    @Inject
    private TournamentService tournamentService;
    @Inject
    private Messages messages;
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    void onValidateFromTournamentForm() {
        if (!tournamentForm.isValid()) {
            return;
        }
        if (startDate.after(endDate)) {
            tournamentForm.recordError(messages.get("error-startDateLate"));

        }
        if (startEnrolmentDate != null && startEnrolmentDate.after(endEnrolmentDate)) {
            tournamentForm.recordError(messages.get("error-startDateLate"));

        }

    }

    Object onSuccess() throws DatesInconsistenceException {
        tournament = tournamentService.createRRIndividualTournament(name_, startDate, endDate, startEnrolmentDate, endEnrolmentDate, type);

        return pageRenderLinkSource.createPageRenderLinkWithContext(SelectPlayers.class, tournament.getId());
    }
}
