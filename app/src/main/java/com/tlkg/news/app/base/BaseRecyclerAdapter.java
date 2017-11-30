package com.tlkg.news.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tlkg.news.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wuxiaoqi on 2017/10/12.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder> {

    private static final String TAG = "BaseRecyclerAdapter";

    public static final int TYPE_HEAD = 0x0001;

    public static final int TYPE_FOOTER = 0x0002;

    public static final int TYPE_GENERAL = 0x0003;

    /**
     * 暂时只能添加一个头View
     */
    private View mHeadView;

    private BaseRecyclerViewHolder mHeadViewHolder;

    private List<T> mData = new ArrayList<>();

    /**
     * 默认不可以加载更多
     */
    private boolean enableLoadMore = false;

    private boolean isFirstLoad = true;

    private ILoadMoreListener loadMoreListener;

    public void setLoadMoreListener(ILoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public List<T> getData() {
        return mData;
    }

    public void addHeadView(View v) {
        mHeadView = v;
        notifyDataSetChanged();
    }

    public void setEnableLoadMore(boolean enable) {
        this.enableLoadMore = enable;
    }

    public void setHeadViewHolder(BaseRecyclerViewHolder headViewHolder) {
        this.mHeadViewHolder = headViewHolder;
    }

    public View getHeadView() {
        return mHeadView;
    }

    public void refreshData(List<T> data) {
        isFirstLoad = true;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
        isFirstLoad = false;
    }

    public void refreshAddData(List<T> data) {
        int start = mData.size();
        mData.addAll(data);
        notifyItemRangeChanged(start, data.size());
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD && mHeadView != null && mHeadViewHolder != null) {
            return mHeadViewHolder;
        } else if (viewType == TYPE_FOOTER && enableLoadMore) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FooterViewHolder<>(v);
        }
        return getRecyclerViewHolder(parent, viewType);
    }

    public abstract BaseRecyclerViewHolder getRecyclerViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            if (isFirstLoad)
                ((FooterViewHolder) holder).itemView.setVisibility(View.GONE);
            else
                ((FooterViewHolder) holder).itemView.setVisibility(View.VISIBLE);

            if (!isFirstLoad && position == ((mHeadView != null && mHeadViewHolder != null) ? mData.size() + 1 : mData.size())) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();//加载更多
                }
            }
        }
        if (mData.size() == 0) return;
        int mPosition = position;
        if (mHeadView != null && mHeadViewHolder != null) {
            mPosition--;
        }
        if (mPosition <= 0) {
            mPosition = 0;
        } else if (mPosition >= mData.size()) {
            mPosition = mData.size() - 1;
        }
        holder.onBindViewHolder(mData.get(mPosition), mPosition);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadViewHolder != null && mHeadView != null) {
            if (position == 0)
                return TYPE_HEAD;
            else if (position == mData.size() + 1 && enableLoadMore) {
                return TYPE_FOOTER;
            }
        }
        if (position == mData.size() && enableLoadMore) {
            return TYPE_FOOTER;
        }
        return TYPE_GENERAL;
    }

    @Override
    public int getItemCount() {
        int count = mData.size();
        if (mHeadView != null)
            count++;
        if (enableLoadMore)
            count++;
        return count;
    }

    public abstract static class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public abstract void onBindViewHolder(T object, final int position);
    }

    public static class FooterViewHolder<Object> extends BaseRecyclerViewHolder {
        ProgressBar progressBar;
        TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_footer_progressbar);
            textView = (TextView) itemView.findViewById(R.id.item_footer_textview);
        }

        @Override
        public void onBindViewHolder(java.lang.Object object, int position) {

        }
    }

    public interface ILoadMoreListener {
        void onLoadMore();
    }
}
