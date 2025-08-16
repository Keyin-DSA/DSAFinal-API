package com.keyin.binarySearchTree.bst;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreeRepo extends CrudRepository<TreeModel, Long> {
    List<TreeModel> findAllByOrderByCreatedAtDesc();


}
