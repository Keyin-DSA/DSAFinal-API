package com.keyin.binarySearchTree.bst;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TreeService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BinarySearchTree bst = new BinarySearchTree();


    public String createTree(List<Integer> numbers) throws Exception {
        for (int n : numbers) {
            bst.insert(new Node(n));
        }
        return serializeTree();
    }

    public String addNode(int value) throws Exception {
        bst.insert(new Node(value));
        return serializeTree();
    }

    public String deleteNode(int value) throws Exception {
        bst.remove(value);
        return serializeTree();
    }

    public Map<String, Object> searchNode(int value) {
        List<Integer> path = new ArrayList<>();
        boolean found = searchWithPath(bst.root, value, path);

        Map<String, Object> result = new HashMap<>();
        result.put("found", found);
        result.put("path", path);
        return result;
    }

    // --- helpers ---

    private boolean searchWithPath(Node root, int value, List<Integer> path) {
        if (root == null) return false;
        path.add(root.data);
        if (root.data == value) return true;
        if (value < root.data) return searchWithPath(root.left, value, path);
        else return searchWithPath(root.right, value, path);
    }

    private String serializeTree() throws Exception {
        return objectMapper.writeValueAsString(serializeNode(bst.root));
    }

    private Map<String, Object> serializeNode(Node node) {
        if (node == null) return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("value", node.data);
        map.put("left", serializeNode(node.left));
        map.put("right", serializeNode(node.right));
        return map;
    }
}
