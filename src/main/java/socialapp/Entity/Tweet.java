package socialapp.Entity;


import lombok.Data;
import socialapp.File.FileAttachment;

import javax.persistence.*;


import java.util.Date;



import java.util.List;



@Data
@Entity
public class Tweet {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private UserEntity userEntity;


    @OneToOne(mappedBy = "tweet",orphanRemoval = true) // orphanRemoval =Tweet siliğinde onla ilgili field da sil
    private FileAttachment fileAttachment;            // orphanRemoval altarnetifi: CascadeType.REMOVE

    @OneToMany(mappedBy = "tweet",cascade = CascadeType.REMOVE)
    private List<Like> likes;

}
