package com.zzz.test.tree;

import com.zzz.test.tree.entity.TreeNode;

import java.util.Stack;

// 后序遍历
public class PostorderTraversal {

    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int height = 0;
        Stack<TreeNode> nodes = new Stack<>();
        Stack<Integer> tag = new Stack<>();
        while (root != null || !nodes.isEmpty()) {
            while (root != null) {
                nodes.push(root);
                tag.push(0);
                root = root.left;
            }
            if (tag.peek() == 1) {
                height = Math.max(height, nodes.size());
                nodes.pop();
                tag.pop();
                root = null;
            } else {
                root = nodes.peek();
                root = root.right;
                tag.pop();
                tag.push(1);
            }
        }
        return height;
    }
}