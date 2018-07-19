package com.boolin.input.bottomhat;

import java.util.ArrayList;
import java.util.List;

public class Course {
    String name;

    Course(String _name){
        name = _name;
    }

    public static List<Course> courses = new ArrayList<>();

    //test courses for testing recyclerview
    public static void initializeCourse(){
        for (int i=0; i<10; i++){
            courses.add(new Course("test course " + i));
        }
    }
}
