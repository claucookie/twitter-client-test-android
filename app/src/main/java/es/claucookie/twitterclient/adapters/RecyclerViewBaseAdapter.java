package es.claucookie.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import es.claucookie.twitterclient.views.BaseViewHolder;

/**
 * Created by claucookie on 03/10/15.
 */
public class RecyclerViewBaseAdapter<D, V extends BaseViewHolder<D>> extends RecyclerView.Adapter<BaseViewHolder<D>> {

    protected Class objectClass;
    protected Class viewClass;
    protected List<D> items;
    protected BaseViewHolder.ListEventListener listEventListener;
    protected int viewHolderId;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewBaseAdapter(Class<D> objectClass, Class<V> viewClass, int viewHolderId, List<D> items) {
        this.objectClass = objectClass;
        this.viewHolderId = viewHolderId;
        this.items = items;
        this.viewClass = viewClass;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BaseViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseViewHolder<D> viewHolder = null;

        try {
            // Create the view using the Base view holder.
            Constructor constructor = viewClass.getConstructor(Context.class, View.class);
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewHolderId, parent, false);
            viewHolder = (BaseViewHolder<D>) constructor.newInstance(parent.getContext(), itemView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BaseViewHolder<D> holder, int position) {
        holder.setListEventListener(listEventListener);
        holder.bind(getItem(position));
    }

    public void setListEventListener(BaseViewHolder.ListEventListener listEventListener) {
        this.listEventListener = listEventListener;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public D getItem(int position) {
        return items == null ? null : items.get(position);
    }

    public void setItems(List<D> auxItems) {
        items = auxItems;
        notifyDataSetChanged();
    }

    public List<D> getItems() {
        return items;
    }
}
