package org.exoplatform.bch.jpadynamicentitiesregistration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bdechateauvieux on 7/14/15.
 */
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
