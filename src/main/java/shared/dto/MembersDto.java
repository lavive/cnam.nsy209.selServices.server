package shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavive on 23/09/17.
 */

public class MembersDto implements Serializable {

    /**
     * For checking version
     */
    private static final long serialVersionUID = 1L;

 
    private List<MemberDto> members = new ArrayList<MemberDto>();

    /* getter and setter */

    public List<MemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDto> members) {
        this.members = members;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(MemberDto memberDto:members){
            result += memberDto.toString()+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }
}
