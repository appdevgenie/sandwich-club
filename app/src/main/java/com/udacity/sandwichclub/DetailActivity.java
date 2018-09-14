package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @BindView(R.id.origin_tv)
    TextView tvPlaceOfOrigin;
    @BindView(R.id.description_tv)
    TextView tvDescription;
    @BindView(R.id.also_known_tv)
    TextView tvAlsoKnownAs;
    @BindView(R.id.ingredients_tv)
    TextView tvIngredients;
    @BindView(R.id.image_iv)
    ImageView ivSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        /*ivSandwich = findViewById(R.id.image_iv);
        tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);*/

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder)
                .into(ivSandwich);

        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        tvDescription.setText(sandwich.getDescription());

        String alsoKnownAs = TextUtils.join(", ", sandwich.getAlsoKnownAs());
        tvAlsoKnownAs.setText(alsoKnownAs);

        /*StringBuilder stringBuilderAKA = new StringBuilder();
        List<String> arrayAKA = sandwich.getAlsoKnownAs();
        for (int i = 0; i < arrayAKA.size(); i++) {

            stringBuilderAKA.append(arrayAKA.get(i));
            if (i != arrayAKA.size() - 1) {
                stringBuilderAKA.append(", ");
            }
        }
        tvAlsoKnownAs.setText(stringBuilderAKA);*/

        String ingredients = TextUtils.join(", ", sandwich.getIngredients());
        tvIngredients.setText(ingredients);

        /*StringBuilder stringBuilderIngredients = new StringBuilder();
        List<String> arrayIngredients = sandwich.getIngredients();
        for (int i = 0; i < arrayIngredients.size(); i++) {

            stringBuilderIngredients.append(arrayIngredients.get(i));
            if (i != arrayIngredients.size() - 1) {
                stringBuilderIngredients.append(", ");
            }

        }
        tvIngredients.setText(stringBuilderIngredients);*/
    }
}
