package com.github.simkuenzi.umlchars;

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
}
