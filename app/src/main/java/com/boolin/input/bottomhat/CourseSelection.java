package com.boolin.input.bottomhat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.boolin.input.bottomhat.Course.initializeCourse;

public class CourseSelection extends AppCompatActivity {

    //Context c = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        //creates test courses
        initializeCourse();

        //sets recyclerview
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        //sets layout manager to the recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //attaches recyclerview to its adapter
        RVAdapter adapter = new RVAdapter(Course.courses);
        rv.setAdapter(adapter);
    }

    //is supposed to make a toast to test recyclerview onclick listener
    public void writeToast(){
        Toast.makeText(getApplicationContext(), "It worked?" + User.coursesLearning.size(), Toast.LENGTH_SHORT).show();
    }
}
