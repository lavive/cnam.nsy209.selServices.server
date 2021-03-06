package shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO picturing categories
 * 
 * @author lavive
 *
 */

/**
 * Created by lavive on 23/09/17.
 */

public class CategoriesDto implements Serializable {

    /**
     * For checking version
     */
    private static final long serialVersionUID = 1L;

    private List<CategoryDto> categories = new ArrayList<CategoryDto>();

    /* getter and setter */

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    @Override
    public String toString(){
        String result ="{ ";
        for(CategoryDto categoryDto:categories){
            result += categoryDto.toString()+" , ";
        }
        result = result.substring(0,result.length()-1);
        result +="}";
        return result;
    }
}
