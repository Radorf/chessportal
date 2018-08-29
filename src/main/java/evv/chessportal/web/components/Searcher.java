package evv.chessportal.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Searcher {
    @Property
    @Parameter
    private String searchKey;
    
    Object onSuccessFromSearchForm() {
        return this;
    }
}
