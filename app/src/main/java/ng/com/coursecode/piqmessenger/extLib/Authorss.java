package ng.com.coursecode.piqmessenger.extLib;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by harro on 13/12/2017.
 */

public class Authorss implements IUser {
    String id, name, avatar;

    public Authorss(String id1,String name1,String avatar1) {
        id=id1;
        name=name1;
        avatar=avatar1;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
