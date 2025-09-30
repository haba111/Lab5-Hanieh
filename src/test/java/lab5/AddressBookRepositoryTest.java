package lab5;

import lab5.repository.BuddyInfoRepository;
import lab5.repository.AddressBookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AddressBookRepositoryTest {

    @Autowired
    private BuddyInfoRepository buddyRepo;

    @Autowired
    private AddressBookRepository addressRepo;

    @Test
    public void testAddBuddyToAddressBook() {
        BuddyInfo buddy = new BuddyInfo("Jane Doe", "987-654-3210");

        AddressBook ab = new AddressBook();
        ab.addBuddy(buddy);  // Cascade persists buddy automatically
        addressRepo.save(ab);

        AddressBook result = addressRepo.findById(ab.getId()).orElse(null);
        assertNotNull(result);
        assertEquals(1, result.getBuddies().size());

        // Verify back-reference from BuddyInfo to AddressBook
        assertEquals(result, result.getBuddies().get(0).getAddressBook());
    }
}
