package com.keyin.binarySearchTree;

import com.keyin.binarySearchTree.brocode.BinarySearchTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BinarySearchTreeApplication {

	public static void main(String[] args) {

		BinarySearchTree tree = new BinarySearchTree();

		SpringApplication.run(BinarySearchTreeApplication.class, args);
	}

}
