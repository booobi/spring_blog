package bobi.blog.bindingModel;

import java.util.ArrayList;
import java.util.List;

public class UserEditBingingModel extends  UserBindingModel {
    private List<Integer> roles;
    public UserEditBingingModel() {
        this.roles = new ArrayList<>();
    }
    public List<Integer> getRoles() {return roles;}

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
