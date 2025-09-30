package lab5.controller;

import lab5.AddressBook;
import lab5.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @GetMapping("/addressbooks/view/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepository.findById(id).orElse(null);
        model.addAttribute("addressBook", ab);
        return "view";
    }
}
