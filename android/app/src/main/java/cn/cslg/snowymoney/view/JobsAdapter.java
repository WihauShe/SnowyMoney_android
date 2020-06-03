package cn.cslg.snowymoney.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.entity.Job;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
    private List<Job> jobs;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public JobsAdapter(List<Job> jobList){
        jobs = jobList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView jobCategory,jobDuration,jobPosition,jobSalary,jobDate;

        private ViewHolder (View view) {
            super(view);
            itemView = view;
            jobCategory = view.findViewById(R.id.job_category);
            jobDuration = view.findViewById(R.id.job_duration);
            jobPosition = view.findViewById(R.id.job_position);
            jobSalary = view.findViewById(R.id.job_salary);
            jobDate = view.findViewById(R.id.job_date);
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface  OnItemLongClickListener{
        void onItemLongClick(int position);
    }
    public void setItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        if(onItemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAdapterPosition();
                    onItemLongClickListener.onItemLongClick(position);
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.jobCategory.setText(job.getCategory());
        holder.jobDuration.setText(job.getDuration());
        holder.jobPosition.setText(job.getPosition());
        holder.jobSalary.setText(job.getSalary());
        holder.jobDate.setText(job.getDate());
    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }
}
