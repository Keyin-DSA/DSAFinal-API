package com.keyin.binarySearchTree.bst;

public class Node {
    int data;
    Node left;
    Node right;

    public Node() { };
    public Node(int data) {
        this.data = data;
        left = right = null;
    }
}
