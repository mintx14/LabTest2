package my.utem.ftmk.labtest2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import my.utem.ftmk.labtest2.adapter.BMIRecord;
import my.utem.ftmk.labtest2.adapter.BMIRecordAdapter;
import my.utem.ftmk.labtest2.databinding.ActivityMainBinding;
import my.utem.ftmk.labtest2.sqlite.Personalbmi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Personalbmi personalbmi;
    List<BMIRecord> bmiRecordList;
    BMIRecordAdapter bmiRecordAdapter;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personalbmi = new Personalbmi(this);
        bmiRecordList = new ArrayList<>();
        bmiRecordAdapter = new BMIRecordAdapter(bmiRecordList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bmiRecordAdapter);

        // Retrieve data on app start
        new Thread(() -> {
            Cursor cursor = personalbmi.getAllData();
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("Name"));
                    float weight = cursor.getFloat(cursor.getColumnIndex("Weight"));
                    float height = cursor.getFloat(cursor.getColumnIndex("Height"));
                    String status = cursor.getString(cursor.getColumnIndex("Status"));
                    BMIRecord record = new BMIRecord(name, weight, height, status);
                    bmiRecordList.add(record);
                } while (cursor.moveToNext());

                runOnUiThread(() -> bmiRecordAdapter.notifyDataSetChanged());
            }
            cursor.close();
        }).start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fnCalculateBMI(View view) {
        String name = binding.edtName.getText().toString();
        String heightString = binding.edtHeight.getText().toString();
        String weightString = binding.edtWeight.getText().toString();

            Float height = Float.parseFloat(heightString) / 100;
            Float heightCm = Float.parseFloat(heightString);
            Float weight = Float.parseFloat(weightString);

            Float totalBMI = weight / (height * height);
            String status;

            if (totalBMI < 18.5) {
                status = "You are Underweight. Careful during strong wind!";
                binding.edtStatus.setText(status);
            } else if (totalBMI >= 18.5 && totalBMI <= 24.9) {
                status = "That’s ideal! Please maintain.";
                binding.edtStatus.setText(status);
            } else if (totalBMI >= 25.0 && totalBMI <= 29.9) {
                status = "Overweight! Workout please.";
                binding.edtStatus.setText(status);
            } else {
                status = "Whoa Obese!! Dangerous Mate.";
                binding.edtStatus.setText(status);
            }

            binding.edtStatus.setText(status);

            // Save to database in a separate thread
            new Thread(() -> {
                boolean isInserted = personalbmi.insertData(name, weight, heightCm, status);
                runOnUiThread(() -> {
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "Information saved successfully", Toast.LENGTH_SHORT).show();
                        BMIRecord record = new BMIRecord(name, weight, heightCm, status);
                        bmiRecordList.add(record);
                        bmiRecordAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save information", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
    }
}

