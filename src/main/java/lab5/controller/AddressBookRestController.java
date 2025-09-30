package lab5.controller;

import lab5.AddressBook;
import lab5.BuddyInfo;
import lab5.repository.AddressBookRepository;
import lab5.repository.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressBookRestController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @PostMapping("/addressbooks")
    public AddressBook createAddressBook() {
        return addressBookRepository.save(new AddressBook());
    }

    @PostMapping("/addressbooks/{id}/buddies")
    public AddressBook addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        Optional<AddressBook> optionalAddressBook = addressBookRepository.findById(id);
        if (optionalAddressBook.isPresent()) {
            AddressBook ab = optionalAddressBook.get();
            ab.addBuddy(buddy);
            return addressBookRepository.save(ab);
        }
        throw new RuntimeException("AddressBook not found");
    }

    @DeleteMapping("/addressbooks/{bookId}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Long bookId, @PathVariable Long buddyId) {
        Optional<AddressBook> optionalAddressBook = addressBookRepository.findById(bookId);
        Optional<BuddyInfo> optionalBuddy = buddyInfoRepository.findById(buddyId);

        if (optionalAddressBook.isPresent() && optionalBuddy.isPresent()) {
            AddressBook ab = optionalAddressBook.get();
            ab.removeBuddy(optionalBuddy.get());
            return addressBookRepository.save(ab);
        }

        throw new RuntimeException("AddressBook or Buddy not found");
    }

    @GetMapping("/addressbooks/{id}")
    public AddressBook getAddressBook(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));
    }
}
