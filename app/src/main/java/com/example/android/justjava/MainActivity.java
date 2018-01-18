package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //Customer name field
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText(). toString();

        //Check if customer wants whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        //Check if customer wants chocolate
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        //This displays the pric
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);

        //This sends the order in an Email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method creates an order summery
     * @param name of the customer
     * @param price of order
     * @param addWhippedCream is whether or not the customer wants whipped cream
     * @param addChocolate  is whether or not the customer wants whipped cream
     * @return text
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string .thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        //This add whipped cream to the order
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //This as chocolate to the order
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return basePrice * quantity;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {

            //This sends a message when to more than 100 cups are ordered
            Toast.makeText(this, "You cannot have more than 100 cups!", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuntity(quantity);
    }

    /**
     * This method is called when the miuns is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {


            //This sends a message when lower than 1 cups are ordered
            Toast.makeText(this, "You cannot have less than 1 cup!", Toast.LENGTH_SHORT).show();

            return;
        }

        quantity = quantity - 1;
        displayQuntity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuntity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}