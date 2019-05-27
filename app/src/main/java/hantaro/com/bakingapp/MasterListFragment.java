package hantaro.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment implements RecipeStepAdapter.RecipeStepClickListener {
    OnStepClickListener mOnStepClickListener;

    public MasterListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_list_fragment, container, false);

        Intent intent = getActivity().getIntent();
        Recipe recipe = (Recipe) intent.getExtras().getSerializable("recipe");
        if (recipe == null) {

        } else {

            final List<Step> stepList = recipe.getSteps();
            final List<String> stepsName = new ArrayList<>();

            for (Step step : recipe.getSteps()) {
                stepsName.add(step.getId() + " " + step.getShortDescription());
            }
            RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(stepList, this);
            RecyclerView recyclerView = view.findViewById(R.id.simple_list_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recipeStepAdapter);

            final Recipe recipe1 = recipe;

            final TextView ingredientsTV = view.findViewById(R.id.ingredients_tv);
            ingredientsTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getContext(), IngredientsDetails.class);
                    intent1.putExtra("recipe", recipe1);
                    startActivity(intent1);
                }
            });
        }
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnStepClickListener = (OnStepClickListener) context;
    }

    @Override
    public void onStepClick(Step step) {
        mOnStepClickListener.onStepSelected(step);
    }

    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }
}
