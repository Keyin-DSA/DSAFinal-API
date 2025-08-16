package com.keyin.binarySearchTree.bst;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeService {

    private final TreeRepo repo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TreeService(TreeRepo repo) {
        this.repo = repo;
    }

    // Build BST from posted numbers, save, and return the saved record
    public TreeModel createAndSave(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("numbers must contain at least one integer");
        }

        // Build in-memory BST
        BinarySearchTree bst = new BinarySearchTree();
        for (int n : numbers) {
            bst.insert(new Node(n));
        }

        // Serialize original numbers and the tree structure
        String numbersJson = toJson(numbers);
        String treeJson = toJson(serializeNode(bst.root));

        // Persist snapshot
        TreeModel rec = new TreeModel();
        rec.setNumbersJson(numbersJson);
        rec.setTreeJson(treeJson);
        return repo.save(rec);
    }

    // List all saved trees, newest first
    public List<TreeModel> getAllSaved() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    // Optional: fetch one by id
    public TreeModel getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // ---- helpers ----
    private Map<String, Object> serializeNode(Node node) {
        if (node == null) return null;
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("value", node.data);
        m.put("left", serializeNode(node.left));
        m.put("right", serializeNode(node.right));
        return m;
    }

    private String toJson(Object v) {
        try {
            return objectMapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize tree", e);
        }
    }
}
