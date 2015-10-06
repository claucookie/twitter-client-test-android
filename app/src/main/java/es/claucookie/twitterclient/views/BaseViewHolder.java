package es.claucookie.twitterclient.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by claucookie on 03/10/15.
 */
public abstract class BaseViewHolder<D> extends RecyclerView.ViewHolder {

    protected View rootView;
    protected Context context;
    private ListEventListener<D> listEventListener;


    public BaseViewHolder(Context context, View view) {
        super(view);
        this.rootView = view;
        this.context = context;
    }

    public abstract void bind(final D object);

    /**
     * Interface methods
     */

    public interface ListEventListener<D> {
        public void onListEvent(int actionId, D item, View view);
    }

    public void setListEventListener(ListEventListener<D> listEventListener) {
        this.listEventListener = listEventListener;
    }
    public ListEventListener<D> getListEventListener()
    {
        return this.listEventListener;
    }
}
