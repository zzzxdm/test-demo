package com.zzz.test.clazz;

import lombok.Data;

@Data
public class Person {

    String name;
    int age;

    @Override
    public boolean equals(Object obj) {
        return this.getAge() == ((Person)obj).getAge()
                && this.getName().equals(((Person)obj).getName());
    }
}
