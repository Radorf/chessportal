package evv.chessportal.web.pages.personal;

import org.apache.tapestry5.annotations.Property;

import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.PLAYER_USERS)
public class ShowGames {

    @Property
    private Long tournamentId;
    
    void onActivate(Long tournamentId){
        this.tournamentId=tournamentId;
    }

    Long onPassivate() {
        return tournamentId;
    }
    
    
}
