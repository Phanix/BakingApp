package hantaro.com.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    RecipeStepClickListener mRecipeStepClickListener;

    List<Step> mSteps;

    public RecipeStepAdapter(List<Step> steps, RecipeStepClickListener recipeStepClickListener) {
        mSteps = steps;
        mRecipeStepClickListener = recipeStepClickListener;
    }

    public interface RecipeStepClickListener{
         void onStepClick(Step step);
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_step_layout, viewGroup, false);
        return new RecipeStepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder recipeStepAdapterViewHolder, int i) {
        recipeStepAdapterViewHolder.bind();
    }

    @Override
    public int getItemCount() {
        if(mSteps == null){
            return 0;
        }else{
            return mSteps.size();
        }
    }

    public class RecipeStepAdapterViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextView;

        public RecipeStepAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_step);
            itemView.setOnClickListener(this);
        }

        public void bind(){
            String stepString = mSteps.get(getAdapterPosition()).getShortDescription();
            mTextView.setText(getAdapterPosition() + " " + stepString);
        }

        @Override
        public void onClick(View view) {

            mRecipeStepClickListener.onStepClick(mSteps.get(getAdapterPosition()));
        }
    }

}
