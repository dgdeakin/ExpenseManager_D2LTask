package tasks.sit305.expensemanager_d2ltask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {
    private static final String TAG = "AddExpenseActivity";
    private EditText amountEditText;
    private TextView categoryTextView;
    private ImageView categoryImage;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        amountEditText = findViewById(R.id.amount_input);
        categoryTextView = findViewById(R.id.category_title);
        categoryImage = findViewById(R.id.category_image);
        addButton = findViewById(R.id.add_button);

        // Get category from intent
        String category = getIntent().getStringExtra("category");
        categoryTextView.setText(category);

        // Set appropriate image based on category
        switch (category) {
            case "Home Rent":
                categoryImage.setImageResource(R.drawable.ic_home);
                break;
            case "Eating Out":
                categoryImage.setImageResource(R.drawable.ic_food);
                break;
            case "Transportation":
                categoryImage.setImageResource(R.drawable.ic_transport);
                break;
            case "Entertainment":
                categoryImage.setImageResource(R.drawable.ic_entertainment);
                break;
        }

        addButton.setOnClickListener(v -> {
            String amountStr = amountEditText.getText().toString();
            if (!amountStr.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("amount", amount);
                    setResult(RESULT_OK, resultIntent);
                    Log.d(TAG, "Sending result: amount = " + amount);
                    finish();
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid amount entered: " + amountStr);
                    // Optionally show an error to the user
                    amountEditText.setError("Please enter a valid number");
                }
            } else {
                Log.w(TAG, "Amount field is empty");
                amountEditText.setError("Please enter an amount");
            }
        });
    }
}