package com.zzz.test.tree;

import com.zzz.test.tree.entity.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

// 层次遍历
public class LevelTraversal {

    public static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int height = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.peek();
            if (node.left == null && node.right == null) {
                break;
            } else {
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                queue.poll();
                height++;
            }
        }
        return height;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        TreeNode left = root.left;
        left.right = new TreeNode(3);
        System.out.println(getHeight(root));
    }

}