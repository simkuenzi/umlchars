package com.github.simkuenzi.umlchars;

import java.util.List;

public class Association {
    private String classNameA;
    private String classNameB;

    public Association(String classNameA, String classNameB) {
        this.classNameA = classNameA;
        this.classNameB = classNameB;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Association) {
            Association other = (Association) obj;
            return classNameA.equals(other.classNameA) && classNameB.equals(other.classNameB);
        }
        return false;
    }

    public Association normalized(List<String> classNames) {
        if (classNames.indexOf(classNameA) > classNames.indexOf(classNameB)) {
            return new Association(classNameB, classNameA);
        } else {
            return this;
        }
    }
}
