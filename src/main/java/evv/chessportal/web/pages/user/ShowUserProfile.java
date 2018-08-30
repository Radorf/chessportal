package evv.chessportal.web.pages.user;


import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class ShowUserProfile {

    @Property
    private UserProfile user;
    
    @Property
    private Long userId;
    
    @Inject
    private UserService userService;

    void onActivate(long userId)throws InstanceNotFoundException {
        this.userId = userId;
        user = userService.findUserProfile(userId);
    }
    
    public boolean getIsPlayer ()  {
        return user instanceof Player; 
    }

    public Integer getElo() {
        return ((Player) user).getElo();
    }
    
    public String getLicenseNumber(){
        return ((Player) user).getLicenseNumber();
    }

}
