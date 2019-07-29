package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;

/**
 * Entity holding a list of Strings
 */
@Entity
public class StringListParent {

    @Id
    private UUID OID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "MY_STRINGS",
        joinColumns = @JoinColumn(
            name = "STRINGLIST_OID",
            foreignKey = @ForeignKey(name = "FK_STRING_TO_PARENT")))
    @OrderColumn(name = "STRING_POSITION")
    private List<String> myStrings;

    public StringListParent() {
        myStrings = new ArrayList<>();
    }

    public void addString(String newString) {
        myStrings.add(Objects.requireNonNull(newString));
    }

    @PrePersist
    public void onPrePersist() {
        if (OID == null) {
            OID = UUID.randomUUID();
        }
    }

    public UUID getOID() {
        return OID;
    }

}
