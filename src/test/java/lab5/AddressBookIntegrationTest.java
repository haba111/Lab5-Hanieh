package lab5;

import lab5.BuddyInfo;
import lab5.AddressBook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/addressbooks";
    }

    @Test
    public void testCreateAddressBook_AddBuddy_AndRetrieve() {
        // Step 1: Create a new AddressBook
        ResponseEntity<AddressBook> createResponse = restTemplate.postForEntity(baseUrl, null, AddressBook.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        AddressBook createdBook = createResponse.getBody();
        assertNotNull(createdBook);
        Long addressBookId = createdBook.getId();
        assertNotNull(addressBookId);

        // Step 2: Create a BuddyInfo object
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Alice");
        buddy.setPhoneNumber("123456789");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BuddyInfo> buddyRequest = new HttpEntity<>(buddy, headers);

        // Step 3: Add the Buddy to the AddressBook
        String addBuddyUrl = baseUrl + "/" + addressBookId + "/buddies";
        ResponseEntity<AddressBook> addBuddyResponse = restTemplate.postForEntity(addBuddyUrl, buddyRequest, AddressBook.class);
        assertEquals(HttpStatus.OK, addBuddyResponse.getStatusCode());

        AddressBook updatedBook = addBuddyResponse.getBody();
        assertNotNull(updatedBook);
        assertEquals(1, updatedBook.getBuddies().size());
        assertEquals("Alice", updatedBook.getBuddies().get(0).getName());

        // Step 4: Get the AddressBook and verify
        ResponseEntity<AddressBook> getResponse = restTemplate.getForEntity(baseUrl + "/" + addressBookId, AddressBook.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        AddressBook fetchedBook = getResponse.getBody();



        assertNotNull(fetchedBook);
        assertEquals(1, fetchedBook.getBuddies().size());
        assertEquals("123456789", fetchedBook.getBuddies().get(0).getPhoneNumber());
    }
}
