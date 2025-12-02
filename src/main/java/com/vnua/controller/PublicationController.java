    package com.vnua.controller;

    import com.vnua.model.Publication;
    import com.vnua.service.PublicationService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @Controller
    public class PublicationController {

        private final PublicationService publicationService;

        public PublicationController(PublicationService publicationService) {
            this.publicationService = publicationService;
        }



        @GetMapping("/publication")
        @ResponseBody
        public List<Publication> getAllPublications() {
            return publicationService.getAllPublications();
        }

        @GetMapping("/publication/{id}")
        @ResponseBody
        public ResponseEntity<?> getPublicationById(@PathVariable("id") int id) {
            Publication publication = publicationService.getPublicationById(id);
            if (publication == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(publication);
        }

        @PostMapping("/publication")
        @ResponseBody
        public ResponseEntity<?> insertPublication(@RequestBody Publication publication) {
            publicationService.insertPublication(publication);
            return ResponseEntity.ok(publication);
        }

        @PutMapping("/publication/{id}")
        @ResponseBody
        public ResponseEntity<?> updatePublication(@PathVariable("id") int id, @RequestBody Publication publication) {
            publication.setPubId(id);
            publicationService.updatePublication(publication);
            return ResponseEntity.ok(publication);
        }

        @DeleteMapping("/publication/{id}")
        @ResponseBody
        public ResponseEntity<?> deletePublication(@PathVariable("id") int id) {
            publicationService.deletePublication(id);
            return ResponseEntity.ok().build();
        }

        @GetMapping("/authors")
        @ResponseBody
        public ResponseEntity<List<String>> getAuthors() {
            List<String> authors = publicationService.getAuthors();
            return ResponseEntity.ok(authors);
        }
    }
