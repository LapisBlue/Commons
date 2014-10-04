package blue.lapis.common.persistence.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

/**
 * Created on 05.10.2014.
 * @author Thomas
 */
@Entity
@Table(name = "PERSISTENC_STRING_MAPS")
public class PersistentStringMap{

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @JoinTable(name="ATTRIBUTE_VALUE_RANGE", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn(name="RANGE_ID")
    @Column(name="VALUE")
    private Map<String, String> backingMap = new HashMap<String, String>();

    protected PersistentStringMap() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getBackingMap() {
        return backingMap;
    }

    public void setBackingMap(Map<String, String> backingMap) {
        this.backingMap = backingMap;
    }
}
