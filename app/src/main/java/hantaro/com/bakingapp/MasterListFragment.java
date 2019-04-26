package hantaro.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment {

    public MasterListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.master_list_fragment, container, false);

        Recipe recipe = new Recipe();
        Bundle bundle = getArguments();
        recipe  = (Recipe) bundle.getSerializable("recipe");


        final List<Step> stepList = recipe.getSteps();
        final List<String> stepsName = new ArrayList<>();
        final List<String> ingredients = new ArrayList<>();
        for(Step step : recipe.getSteps()){
            stepsName.add( step.getId() + " " + step.getShortDescription());
        }
        stepsName.remove(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stepsName);
        ListView listView = view.findViewById(R.id.simple_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getContext(), RecipeStep.class);
                intent1.putExtra("step", stepList.get(i));
                startActivity(intent1);
            }
        });
        listView.setAdapter(adapter);

        final Recipe recipe1 = recipe;


        final TextView ingredientsTV =  view.findViewById(R.id.ingredients_tv);
        ingredientsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call new intent
                Intent intent1 = new Intent(getContext(), IngredientsDetails.class);
                intent1.putExtra("recipe", recipe1);
                startActivity(intent1);
            }
        });





       return view;
    }
}
