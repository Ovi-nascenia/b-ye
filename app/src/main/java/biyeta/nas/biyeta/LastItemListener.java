package biyeta.nas.biyeta;

/**
 * Created by god father on 1/27/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

public  abstract class LastItemListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // init
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (layoutManager.getChildCount() > 0) {
            // Calculate
            int indexOfLastItemViewVisible = layoutManager.getChildCount() -1;
            View lastItemViewVisible = layoutManager.getChildAt(indexOfLastItemViewVisible);
            int adapterPosition = layoutManager.getPosition(lastItemViewVisible);
            boolean isLastItemVisible = (adapterPosition == adapter.getItemCount() -1);

            // check
            if (isLastItemVisible)
                onLastItemVisible(); // callback
        }
    }


    public abstract void onLastItemVisible();

}