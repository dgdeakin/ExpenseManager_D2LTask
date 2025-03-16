package tasks.sit305.expensemanager_d2ltask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView expenseListView;
    private TextView totalSpendTextView;
    private double totalSpend = 0.0;
    private ArrayList<String> expenseCategories;
    private ArrayAdapter<String> adapter;
    private ActivityResultLauncher<Intent> expenseResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        expenseListView = findViewById(R.id.expense_list);
        totalSpendTextView = findViewById(R.id.total_spend);

        // Initialize expense categories
        expenseCategories = new ArrayList<>();
        expenseCategories.add("Home Rent");
        expenseCategories.add("Eating Out");
        expenseCategories.add("Transportation");
        expenseCategories.add("Entertainment");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                expenseCategories);
        expenseListView.setAdapter(adapter);

        // Initialize the ActivityResultLauncher
        expenseResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.d(TAG, "Result received with code: " + result.getResultCode());
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            double amount = data.getDoubleExtra("amount", 0.0);
                            Log.d(TAG, "Received amount: " + amount);
                            totalSpend += amount;
                            updateTotalSpend();
                        } else {
                            Log.w(TAG, "Result data is null");
                        }
                    } else {
                        Log.w(TAG, "Result not OK: " + result.getResultCode());
                    }
                }
        );

        // Set click listener for list items
        expenseListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = expenseCategories.get(position);
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            intent.putExtra("category", selectedCategory);
            Log.d(TAG, "Launching AddExpenseActivity for category: " + selectedCategory);
            expenseResultLauncher.launch(intent);
        });

        updateTotalSpend();
    }

    private void updateTotalSpend() {
        totalSpendTextView.setText(String.format("Total Spend: $%.2f", totalSpend));
        Log.d(TAG, "Updated total spend to: " + totalSpend);
    }
}