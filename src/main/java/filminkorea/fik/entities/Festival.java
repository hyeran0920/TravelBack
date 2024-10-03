package filminkorea.fik.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Festival {
    @Id
    private String festival_id;
    private String festival_name;
    private String festival_city;
    private String festival_sigungu;
    private String festival_dong;
    private String festival_addr;
    private String festival_lo;
    private String festival_la;
    private String festival_place_name;
    private LocalDate festival_begin_date;
    private String festival_season;
    private LocalDate festival_end_date;
    private String festival_content;
    private String festival_supervision;
    private String festival_host;
    private String festival_tel;
    private String festival_homepage;


}

