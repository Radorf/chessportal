package evv.chessportal.web.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Cookies;

import evv.chessportal.web.pages.Index;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.CookiesManager;
import evv.chessportal.web.util.UserSession;
import evv.chessportal.web.util.UserSession.Role;

@Import(library = {"tapestry5/bootstrap/js/collapse.js", "tapestry5/bootstrap/js/dropdown.js"},
        stylesheet="tapestry5/bootstrap/css/bootstrap-theme.css")
public class Layout {

	@Property
    @Parameter(required = false)
    private boolean activeHome;
	@Property
    @Parameter(required = false)
    private boolean activeUsers;
	@Property
    @Parameter(required = false)
    private boolean activeTournaments;
    @Property
    @Parameter(required = false)
    private boolean activeMyTournaments;
    @Property
    @Parameter(required = false)
    private boolean activeOpenTournaments;
    
    @Parameter(defaultPrefix = "literal")
    private Boolean showTitleInBody;

    @Property
    @SessionState(create=false)
    private UserSession userSession;
    
    @Inject
    private Cookies cookies;
    @Inject
    private AssetSource assetSource;
    
    public boolean getShowTitleInBody() {
    	
    	if (showTitleInBody == null) {
    		return true;
    	} else {
    		return showTitleInBody;
    	} 
    	
    }
    
    @AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
    Object onActionFromLogout() {
        userSession = null;
        CookiesManager.removeCookies(cookies);
        return Index.class;
    }
    
    public String getActiveHomeClass() {
    	return activeHome ? "active" : "";
    }
    
    public String getActiveUsersClass() {
    	return activeUsers ? "active" : "";
    }
    
    public String getActiveTournamentsClass() {
        return activeTournaments ? "active" : "";
    }
    
    public String getActiveOpenTournamentsClass() {
        return activeOpenTournaments ? "active" : "";
    }
    
    public String getActiveMyTournamentsClass() {
        return activeMyTournaments ? "active" : "";
    }

    public boolean isPlayer() {
        return userSession!=null && Role.PLAYER.equals(userSession.getRole());
    }
    
    public boolean isAdmin() {
        return userSession!=null && Role.ADMIN.equals(userSession.getRole());
    }
    
    public boolean canShowHome() {
    	return userSession != null;
    }
    
    public Long getUserId() {
    	return userSession != null ? userSession.getUserProfileId() : null;
    }
}
