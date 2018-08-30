package evv.chessportal.web.util;


public class UserSession {

	private Long userProfileId;
	private String firstName;
	public enum Role {
	    ADMIN, PLAYER;
	}
	private Role role;

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
