package com.keyin.binarySearchTree;

import com.keyin.binarySearchTree.bst.BinarySearchTree;
import com.keyin.binarySearchTree.bst.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BinarySearchTreeApplication {

	public static void main(String[] args) {

		BinarySearchTree tree = new BinarySearchTree();

		tree.insert(new Node(5));
		tree.insert(new Node(6));
		tree.insert(new Node(8));
		tree.insert(new Node(1));
		tree.insert(new Node(10));
		tree.insert(new Node(20));
		tree.insert(new Node(3));
		tree.insert(new Node(7));

		tree.remove(10);
		tree.remove(0);
		tree.display();
		System.out.println(tree.search(10));
		System.out.println(tree.search(3));



		SpringApplication.run(BinarySearchTreeApplication.class, args);
	}

}
