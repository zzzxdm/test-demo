package com.zzz.springdemo.linked_list;

import org.junit.Before;
import org.junit.Test;

public class ListTest {

    ListNode head = new ListNode(1);
    int nodeCount = 10;

    @Before
    public void initList() {
        ListNode tNode = head;
        for (int i = 1; i < nodeCount; i++) {
            ListNode tmpNode = new ListNode(i + 1);
            tNode.next = tmpNode;
            tNode = tmpNode;
        }
        printNode(head);
    }


    private void printNode(ListNode node) {
        while (node.next != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println(node.val);
        System.out.println("****************************");
    }


    public ListNode reverse1(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode temp = new ListNode(-1);
        temp.next = head;
        ListNode pre = temp.next;
        ListNode curr = pre.next;
        while (curr != null) {
            pre.next = curr.next;
            curr.next = temp.next;
            temp.next = curr;
            curr = pre.next;
        }
        return temp.next;
    }

    @Test
    public void test1() {
        ListNode rHead = reverse1(head);
        printNode(rHead);
    }


    public ListNode reverse2(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode temp = new ListNode(-1);
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = temp.next;
            temp.next = curr;
            curr = next;
        }
        return temp.next;
    }


    @Test
    public void test2() {
        ListNode rHead = reverse2(head);
        printNode(rHead);
    }

}
