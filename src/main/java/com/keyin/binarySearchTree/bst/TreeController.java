package com.keyin.binarySearchTree.bst;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // tighten to your frontend origin when you deploy
public class TreeController {

    private final TreeService service;

    public TreeController(TreeService service) {
        this.service = service;
    }
    
    // POST /process-numbers  body: [7,3,9,1,5]
    @PostMapping("/process-numbers")
    public ResponseEntity<TreeModel> processNumbers(@RequestBody List<Integer> numbers) {
        TreeModel saved = service.createAndSave(numbers);
        return ResponseEntity.status(201).body(saved);
    }
    
    // GET /previous-trees
    @GetMapping("/previous-trees")
    public List<TreeModel> previousTrees() {
        return service.getAllSaved();
    }
    
    // GET /trees/{id}
    @GetMapping("/trees/{id}")
    public ResponseEntity<TreeModel> getTree(@PathVariable Long id) {
        TreeModel rec = service.getById(id);
        return rec == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(rec);
    }
}
