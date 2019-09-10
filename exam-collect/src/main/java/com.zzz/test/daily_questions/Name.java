package com.zzz.test.daily_questions;

import java.util.HashSet;
import java.util.Set;

public class Name {
    private String first, last;

    public Name(String first, String last) {
        if (first == null || last == null)
            throw new NullPointerException();
        this.first = first;
        this.last = last;
    }

    // shit,not override equals method
    public boolean equals(Name o) {
        return first.equals(o.first) && last.equals(o.last);
    }


    public int hashCode() {
        return 31 * first.hashCode() + last.hashCode();
    }

    public static void main(String[] args) {
        Set s = new HashSet();
        s.add(new Name("Mickey", "Mouse"));
        System.out.println(s.contains(new Name("Mickey", "Mouse")));
    }
}