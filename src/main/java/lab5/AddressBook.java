package lab5;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public Long getId() {
        return id;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void addBuddy(BuddyInfo buddy) {
        buddy.setAddressBook(this);
        buddies.add(buddy);
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddy.setAddressBook(null);
        buddies.remove(buddy);
    }

    @Override
    public String toString() {
        return "AddressBook{id=" + id + ", buddies=" + buddies + "}";
    }
}
