package com.github.simkuenzi.umlchars;

import java.util.List;

public class Association {
    private Association identity;
    private String classNameA;
    private String classNameB;

    public Association(String classNameA, String classNameB) {
        this.classNameA = classNameA;
        this.classNameB = classNameB;
        this.identity = this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Association) {
            Association other = (Association) obj;
            return this.identity == other.identity;
        }
        return false;
    }

    public Association normalized(List<String> classNames) {
        if (classNames.indexOf(classNameA) > classNames.indexOf(classNameB)) {
            Association association = new Association(classNameB, classNameA);
            association.identity = this;
            return association;
        } else {
            return this;
        }
    }

    public boolean startsFrom(int here, List<String> classNames) {
        return classNames.get(here).equals(classNameA);
    }


    public int sortOrder(List<String> classNames) {
        return classNames.indexOf(classNameA);
    }

    public boolean isFar(List<String> classNames) {
        return classNames.indexOf(classNameB) - classNames.indexOf(classNameA) > 1;
    }

    public boolean isFromHere(int here, List<String> classNames) {
        return classNames.indexOf(classNameA) == here;
    }

    public boolean isToHere(int here, List<String> classNames) {
        return classNames.indexOf(classNameB) == here;
    }

    public boolean isThroughHere(int here, List<String> classNames) {
        return classNames.indexOf(classNameA) < here && classNames.indexOf(classNameB) > here;
    }
}
