package evv.chessportal.web.util;

import evv.chessportal.model.administrator.Administrator;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.web.util.UserSession.Role;

public class UserSession {

	private Long userProfileId;
	private String firstName;
	public enum Role {
	    ADMIN, PLAYER;
	}
	private Role role;

	public UserSession(UserProfile userProfile) {
        this.setUserProfileId(userProfile.getId());
        this.setFirstName(userProfile.getPerson().getFirstName());
        if (userProfile instanceof Player) {
            this.setRole(Role.PLAYER);
        } else if (userProfile instanceof Administrator) {
            this.setRole(Role.ADMIN);
        }
	}
	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
