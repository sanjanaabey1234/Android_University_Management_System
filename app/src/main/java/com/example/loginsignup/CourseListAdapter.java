package com.example.loginsignup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courseList;

    public CourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNameTextView, courseFeeTextView, startDateTextView, registrationCloseDateTextView, branchTextView, durationTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            courseFeeTextView = itemView.findViewById(R.id.courseFeeTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            registrationCloseDateTextView = itemView.findViewById(R.id.registrationCloseDateTextView);
            branchTextView = itemView.findViewById(R.id.branchTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
        }

        public void bind(Course course) {
            courseNameTextView.setText("Course Name: " + course.getCourseName());
            courseFeeTextView.setText("Course Fee: " + course.getCourseFee());
            startDateTextView.setText("Start Date: " + course.getStartingDate());
            registrationCloseDateTextView.setText("Registration Close Date: " + course.getRegistrationCloseDate());
            branchTextView.setText("Branch: " + course.getSelectedBranch());
            durationTextView.setText("Duration: " + course.getSelectedDuration());
        }
    }
}
