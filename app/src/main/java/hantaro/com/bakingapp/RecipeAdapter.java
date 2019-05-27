package hantaro.com.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    RecipeClickListener mRecipeClickListener;
    private Context mContext;
    List<Recipe> mRecipeList;

    public RecipeAdapter(List<Recipe> recipeList, RecipeClickListener recipeClickListener) {
        mRecipeList = recipeList;
        mRecipeClickListener = recipeClickListener;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recipe_layout, viewGroup, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder recipeAdapterViewHolder, int i) {
        recipeAdapterViewHolder.bind();
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null)
            return 0;
        return mRecipeList.size();
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        ImageView mImageView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.recipe_tv);
            mImageView = itemView.findViewById(R.id.recipe_iv);
        }

        public void bind() {
            mTextView.setText(mRecipeList.get(getAdapterPosition()).getName());
            //Check if has image
            if(!TextUtils.isEmpty(mRecipeList.get(getAdapterPosition()).getImageURL())){
                //Add the image
                Picasso.with(mContext).load(mRecipeList.get(getAdapterPosition()).getImageURL()).into(mImageView);
            }
        }

        @Override
        public void onClick(View view) {
            mRecipeClickListener.onRecipeClick(mRecipeList.get(getAdapterPosition()));
        }
    }
}
