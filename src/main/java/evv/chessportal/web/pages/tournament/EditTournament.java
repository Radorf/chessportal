/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class EditTournament {

    @Property
    private Tournament tournament;
   
    @Property
    private String name_;
    @Property
    private Date startDate;
    @Property
    private Date endDate;
    @Property
    private Date startEnrolmentDate;
    @Property
    private Date endEnrolmentDate;
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

    Long onPassivate() {
        return tournamentId;
    }
    void setupRender(){
        try {
            tournament = tournamentService.findIndividualTournament(tournamentId);
            name_ = tournament.getName_();
            startDate = calendarToDate(tournament.getStartDate());
            endDate = calendarToDate(tournament.getEndDate());
            startEnrolmentDate = calendarToDate(tournament.getStartEnrolmentDate());
            endEnrolmentDate = calendarToDate(tournament.getEndEnrolmentDate());
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(EditTournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Date calendarToDate(Calendar c) {
        return c!=null?c.getTime():null;
    }
    
    void onValidateFromTournamentForm() {
        if (!tournamentForm.isValid()) {
            return;
        }
        if (endDate!=null && startDate.after(endDate)) {
            tournamentForm.recordError(messages.get("error-startDateLate"));

        }
        if (startEnrolmentDate != null && endEnrolmentDate!=null && startEnrolmentDate.after(endEnrolmentDate)) {
            tournamentForm.recordError(messages.get("error-startDateLate"));
        }

    }

    private Calendar dateToCalendar(Date date) {
        if (date==null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    Object onSuccess() throws DatesInconsistenceException {
        try {
            tournament = tournamentService.updateTournament(tournamentId,name_, dateToCalendar(startDate), 
                    dateToCalendar(endDate), dateToCalendar(startEnrolmentDate), dateToCalendar(endEnrolmentDate));
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(EditTournament.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pageRenderLinkSource.createPageRenderLinkWithContext(TournamentDetails.class, tournamentId);
    }
}
