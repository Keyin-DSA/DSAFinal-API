package com.keyin.binarySearchTree.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TreeServiceTest {

    private TreeService service;

    @BeforeEach
    void setUp() {
        service = new TreeService();
    }

    @Test
    void createTree_buildsTreeFromList() throws Exception {
        String json = service.createTree(List.of(7, 3, 9));
        assertTrue(json.contains("\"value\":7"));
        assertTrue(json.contains("\"value\":3"));
        assertTrue(json.contains("\"value\":9"));
    }

    @Test
    void addNode_insertsNewValue() throws Exception {
        service.createTree(List.of(7, 3));
        String updated = service.addNode(5);
        assertTrue(updated.contains("\"value\":5"));
    }

    @Test
    void deleteNode_removesExistingValue() throws Exception {
        service.createTree(List.of(7, 3, 9));
        String afterDelete = service.deleteNode(3);
        assertFalse(afterDelete.contains("\"value\":3"));
        assertTrue(afterDelete.contains("\"value\":7"));
        assertTrue(afterDelete.contains("\"value\":9"));
    }

    @Test
    void deleteNode_nonExistentDoesNothing() throws Exception {
        service.createTree(List.of(7, 3, 9));
        String afterDelete = service.deleteNode(42);
        assertTrue(afterDelete.contains("\"value\":7"));
        assertTrue(afterDelete.contains("\"value\":3"));
        assertTrue(afterDelete.contains("\"value\":9"));
    }

    @Test
    void searchNode_returnsPathAndFoundTrue() throws Exception {
        service.createTree(List.of(7, 3, 9, 1));
        Map<String, Object> result = service.searchNode(1);

        assertTrue((Boolean) result.get("found"));
        @SuppressWarnings("unchecked")
        List<Integer> path = (List<Integer>) result.get("path");
        assertEquals(List.of(7, 3, 1), path);
    }

    @Test
    void searchNode_returnsNotFoundAndPath() throws Exception {
        service.createTree(List.of(7, 3, 9));
        Map<String, Object> result = service.searchNode(42);

        assertFalse((Boolean) result.get("found"));
        @SuppressWarnings("unchecked")
        List<Integer> path = (List<Integer>) result.get("path");
        // Traversal will show the route taken to decide it's missing
        assertFalse(path.isEmpty());
    }
}
