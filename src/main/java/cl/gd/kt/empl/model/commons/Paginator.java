package cl.gd.kt.empl.model.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paginator {

    private int page;
    private int limit;
}
