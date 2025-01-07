package my.utem.ftmk.labtest2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.utem.ftmk.labtest2.R;

public class BMIRecordAdapter extends RecyclerView.Adapter<BMIRecordAdapter.BMIRecordViewHolder> {
    private List<BMIRecord> bmiRecordList;

    public BMIRecordAdapter(List<BMIRecord> bmiRecordList) {
        this.bmiRecordList = bmiRecordList;
    }

    @NonNull
    @Override
    public BMIRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bmi_record_item, parent, false);
        return new BMIRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BMIRecordViewHolder holder, int position) {
        BMIRecord bmiRecord = bmiRecordList.get(position);
        holder.nameTextView.setText("Name: " + bmiRecord.getName());
        holder.weightTextView.setText("Weight: " + bmiRecord.getWeight() + " KG");
        holder.heightTextView.setText("Height: " + bmiRecord.getHeight() + " CM");
        holder.statusTextView.setText("Status: " + bmiRecord.getStatus());
    }

    @Override
    public int getItemCount() {
        return bmiRecordList.size();
    }

    public static class BMIRecordViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, weightTextView, heightTextView, statusTextView;

        public BMIRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            heightTextView = itemView.findViewById(R.id.heightTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
