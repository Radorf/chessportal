/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class EditTournament {

    @Property
    private Tournament tournament;
   
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
    @Property
    private Long tournamentId;
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
    

    
    void onActivate(long tournamentId) {
        this.tournamentId =  tournamentId;
    }
    
    void setupRender(){
        try {
            tournament = tournamentService.findTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(EditTournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
        try {
            tournament = tournamentService.updateTournament(tournamentId,name_, startDate, endDate, startEnrolmentDate, endEnrolmentDate);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(EditTournament.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pageRenderLinkSource.createPageRenderLinkWithContext(TournamentDetails.class, tournament.getId());
    }
}
