package by.clevertec.controller;

import by.clevertec.domain.Cake;
import by.clevertec.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CakeController {

    private final CakeService cakeService;

    @GetMapping("cakes")
    public ResponseEntity<List<Cake>> findAll() {
        List<Cake> cakes = cakeService.findCakes();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cakes);
    }

    @GetMapping("cake/{id}")
    public ResponseEntity<Cake> findById(@PathVariable(name = "id") UUID id) {
        Cake cake = cakeService.findCakeById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cake);
    }

    @PostMapping("cake")
    public ResponseEntity<Cake> createCake(@RequestBody Cake cake) {
        Cake createdCake = cakeService.createCake(cake);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdCake);
    }

    @PutMapping("cake/{id}")
    public ResponseEntity<Cake> updateCake(@PathVariable(name = "id") UUID id, @RequestBody Cake cake) {
        Cake updatedCake = cakeService.updateCake(id, cake);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedCake);
    }

    @DeleteMapping("cake/{id}")
    public void deleteCake(@PathVariable(name = "id") UUID id) {
        cakeService.deleteCake(id);
    }
}