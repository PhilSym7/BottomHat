package com.boolin.input.bottomhat;

import android.app.Notification;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CourseViewHolder>{

    //List<Course> courses;
    Course coursectx;
    RVAdapter(List<Course> courses){
        //Course.courses = courses;
        this.coursectx = coursectx;
    }

    @Override
    public int getItemCount(){
        return Course.courses.size();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_item, viewGroup, false);
        CourseViewHolder cvh = new CourseViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final CourseViewHolder courseViewHolder, final int i) {
        courseViewHolder.courseName.setText(Course.courses.get(i).name);

        //onclick listener for individual items in the list
        CourseViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adds selected course to new list of courses
                Course courseToAdd = new Course(Course.courses.get(i).name);
                User.coursesLearning.add(courseToAdd);

                courseViewHolder.courseName.setText("the position is: " );
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        static CardView cv;
        TextView courseName;

        CourseViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            courseName = (TextView)itemView.findViewById(R.id.course_name);

        }
    }
}