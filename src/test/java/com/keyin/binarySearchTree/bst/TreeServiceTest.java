package com.keyin.binarySearchTree.bst;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreeServiceTest {

    @Mock
    private TreeRepo repo;

    @InjectMocks
    private TreeService service;

    private final ObjectMapper om = new ObjectMapper();

    @Captor
    private ArgumentCaptor<TreeModel> treeModelCaptor;

    @BeforeEach
    void setup() {
        // no-op; @InjectMocks wires service with mocked repo
    }

    @Test
    void createAndSave_buildsTree_serializesAndPersists() throws Exception {
        // arrange
        List<Integer> input = List.of(7, 3, 9, 1, 5);

        // repo.save returns the same obj with an id set (simulate DB)
        doAnswer(invocation -> {
            TreeModel m = invocation.getArgument(0);
            // pretend DB assigned id
            // if your TreeModel has setId, use it; else skip
            try {
                TreeModel.class.getMethod("setId", Long.class).invoke(m, 42L);
            } catch (NoSuchMethodException ignored) {}
            return m;
        }).when(repo).save(any(TreeModel.class));

        // act
        TreeModel saved = service.createAndSave(input);

        // assert repo interaction
        verify(repo, times(1)).save(treeModelCaptor.capture());
        TreeModel toSave = treeModelCaptor.getValue();
        assertNotNull(toSave, "Saved entity should not be null");

        // numbersJson should be exact original order
        assertEquals("[7,3,9,1,5]", toSave.getNumbersJson());

        // treeJson should represent the BST structure; parse and assert key nodes
        Map<String, Object> root = om.readValue(toSave.getTreeJson(), new TypeReference<>() {});
        assertEquals(7, (int) getInt(root, "value"));

        Map<String, Object> left = getNode(root, "left");
        Map<String, Object> right = getNode(root, "right");
        assertEquals(3, (int) getInt(left, "value"));
        assertEquals(9, (int) getInt(right, "value"));

        // left subtree details
        assertEquals(1, (int) getInt(getNode(left, "left"), "value"));
        assertEquals(5, (int) getInt(getNode(left, "right"), "value"));

        // returned model should include same JSON (and may have an id)
        assertEquals(toSave.getNumbersJson(), saved.getNumbersJson());
        assertEquals(toSave.getTreeJson(), saved.getTreeJson());
        // if your model has getId, check it’s non-null
        try {
            assertNotNull(TreeModel.class.getMethod("getId").invoke(saved));
        } catch (NoSuchMethodException ignored) {}
    }

    @Test
    void createAndSave_rejectsEmpty() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> service.createAndSave(Collections.emptyList()));
        assertTrue(ex1.getMessage().toLowerCase().contains("numbers"));

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                ()-> service.createAndSave(null));
        assertTrue(ex2.getMessage().toLowerCase().contains("numbers"));

        verifyNoInteractions(repo);
    }

    @Test
    void getAllSaved_returnsNewestFirst_fromRepo() {
        List<TreeModel> expected = List.of(new TreeModel(), new TreeModel());
        when(repo.findAllByOrderByCreatedAtDesc()).thenReturn(expected);

        List<TreeModel> actual = service.getAllSaved();

        assertSame(expected, actual);
        verify(repo, times(1)).findAllByOrderByCreatedAtDesc();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getById_found_returnsEntity() {
        TreeModel model = new TreeModel();
        when(repo.findById(99L)).thenReturn(Optional.of(model));

        TreeModel result = service.getById(99L);

        assertSame(model, result);
        verify(repo).findById(99L);
    }

    @Test
    void getById_missing_returnsNull() {
        when(repo.findById(100L)).thenReturn(Optional.empty());

        TreeModel result = service.getById(100L);

        assertNull(result);
        verify(repo).findById(100L);
    }

    // -------- helpers for JSON assertions --------

    @SuppressWarnings("unchecked")
    private Map<String, Object> getNode(Map<String, Object> node, String key) {
        Object child = node.get(key);
        assertTrue(child == null || child instanceof Map,
                () -> "Expected " + key + " to be a Map or null, got: " + (child == null ? "null" : child.getClass()));
        return (Map<String, Object>) child;
    }

    private Integer getInt(Map<String, Object> node, String key) {
        assertNotNull(node, "Node is null");
        Object v = node.get(key);
        assertNotNull(v, () -> "Missing key '" + key + "' in node: " + node);
        // Jackson may deserialize numbers as Integer (or LinkedHashMap path)
        if (v instanceof Integer i) return i;
        if (v instanceof Number n) return n.intValue();
        fail("Value for key '" + key + "' is not a number: " + v);
        return null; // unreachable
    }
}
