package socialapp.File;

import lombok.Data;
import socialapp.Entity.Tweet;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class FileAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fileType;

    @Temporal(TemporalType.TIMESTAMP) // yazmasak da olur gün mü, saat mi onu tutuyor
    private Date date;

    @OneToOne
    private Tweet tweet;
}
