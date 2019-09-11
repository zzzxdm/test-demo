package com.zzz.test.tree;

import com.zzz.test.tree.entity.TreeNode;

public class Recursive {

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

}