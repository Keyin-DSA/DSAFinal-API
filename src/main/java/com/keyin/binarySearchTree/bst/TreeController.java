package com.keyin.binarySearchTree.bst;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    // Build a new BST from a list of numbers
    @PostMapping
    public String createTree(@RequestBody List<Integer> numbers) throws Exception {
        return treeService.createTree(numbers);
    }

    // Add a new node
    @PostMapping("/add/{value}")
    public String addNode(@PathVariable int value) throws Exception {
        return treeService.addNode(value);
    }

    // Delete a node
    @DeleteMapping("/delete/{value}")
    public String deleteNode(@PathVariable int value) throws Exception {
        return treeService.deleteNode(value);
    }

    // Search for a node, return path traversed
    @GetMapping("/search/{value}")
    public Map<String, Object> searchNode(@PathVariable int value) {
        return treeService.searchNode(value);
    }
}
