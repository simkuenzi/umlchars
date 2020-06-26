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

    public boolean startsFrom(int here, List<String> classNames) {
        return classNames.get(here).equals(classNameA);
    }

    public boolean endsNextFrom(int here, List<String> classNames) {
        return classNames.indexOf(classNameB) == here + 1;
    }

    public boolean startsBefore(int here, List<String> classNames) {
        return classNames.indexOf(classNameA) < here;
    }

    public boolean startsFarBefore(int here, List<String> classNames) {
        return classNames.indexOf(classNameA) < here - 1;
    }

    public boolean endsAfter(int here, List<String> classNames) {
        return classNames.indexOf(classNameB) > here;
    }

    public boolean ends(int here, List<String> classNames) {
        return classNames.indexOf(classNameB) == here;
    }

    public int sortOrder(List<String> classNames) {
        return classNames.indexOf(classNameA);
    }

    public boolean isFar(List<String> classNames) {
        return classNames.indexOf(classNameB) - classNames.indexOf(classNameA) > 1;
    }
}
