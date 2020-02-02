package com.serheev.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

@Getter
@MappedSuperclass
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
public class BaseEntity  implements Serializable {

    private static final long serialVersionUID = -2099927622088121640L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hibernate_seq")
    @SequenceGenerator(
            name="hibernate_seq",
            sequenceName="hibernate_sequence",
            allocationSize=1
    )
    private long id;
}
