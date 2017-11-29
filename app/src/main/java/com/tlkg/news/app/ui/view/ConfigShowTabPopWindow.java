package com.tlkg.news.app.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tlkg.news.app.BuildConfig;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.NewsTableBean;
import com.tlkg.news.app.db.NewsTable;
import com.tlkg.news.app.db.NewsTableDao;
import com.tlkg.news.app.event.TabShowRefreshEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/11/20.
 * 配置推荐页显示的tab
 */

public class ConfigShowTabPopWindow extends PopupWindow {

    private static final String TAG = "ConfigShowTabPopWindow";

    private RecyclerView recyclerView;

    private ImageView closeImg;

    private Context mContext;

    private NewsTableDao dao = new NewsTableDao();

    private ConfigShowTabAdapter adapter;

    /**
     * 编辑模式
     */
    private boolean editMode = false;

    public ConfigShowTabPopWindow(Context context) {
        this.mContext = context;
    }

    public void show(View parent) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_configshowtab, null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(contentView);
        setAnimationStyle(R.style.popwin_anim_style);
        showAtLocation(parent, Gravity.TOP, 0, 0);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.view_configshowtab_recyclerview);
        closeImg = (ImageView) contentView.findViewById(R.id.view_configshowtab_close_img);
        closeImg.animate().rotation(0).setDuration(0).start();
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeImg.clearAnimation();
                closeImg.animate().rotation(120).setDuration(100).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dismiss();
                    }
                }).start();
            }
        });

        initData();
    }

    private void initData() {
        final List<NewsTableBean> enableItems = dao.query(NewsTable.NEWS_TABLE_ENABLE);
        final List<NewsTableBean> disableItems = dao.query(NewsTable.NEWS_TABLE_DISABLE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new ConfigShowTabAdapter(helper, enableItems, disableItems);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return ((viewType == ConfigShowTabAdapter.TYPE_CONFIGSHOWTAB_HIDE_TITLE ||
                        viewType == ConfigShowTabAdapter.TYPE_CONFIGSHOWTAB_SHOW_TITLE) ? 4 : 1);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private class ItemDragHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlags, 0);//不支持滑动删除
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (viewHolder.getItemViewType() != target.getItemViewType()) {//不同typep之间不可移动
                return false;
            }
            if (recyclerView.getAdapter() instanceof IOnItemMoveListener) {
                IOnItemMoveListener listener = (IOnItemMoveListener) recyclerView.getAdapter();
                listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (viewHolder instanceof IOnDragViewHolderListener) {
                    IOnDragViewHolderListener itemViewHolder = (IOnDragViewHolderListener) viewHolder;
                    itemViewHolder.onItemSelected();
                }
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof IOnDragViewHolderListener) {
                IOnDragViewHolderListener itemViewHolder = (IOnDragViewHolderListener) viewHolder;
                itemViewHolder.onItemFinish();
            }
            super.clearView(recyclerView, viewHolder);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    }

    public interface IOnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }

    public interface IOnDragViewHolderListener {
        void onItemSelected();

        void onItemFinish();
    }


    private class ConfigShowTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IOnItemMoveListener {

        public static final int TYPE_CONFIGSHOWTAB_SHOW_TITLE = 0;//显示标题item

        public static final int TYPE_CONFIGSHOWTAB_SHOW_CONFIG = 1;//配置显示item

        public static final int TYPE_CONFIGSHOWTAB_HIDE_TITLE = 2;//隐藏标题item

        public static final int TYPE_CONFIGSHOWTAB_HIDE_CONFIG = 3;//配置隐藏item

        private LayoutInflater mLayoutInflater;

        private ItemTouchHelper mItemTouchHeler;

        private long startActionDownTime = 0;

        private List<NewsTableBean> enableItems, disableItems;

        public ConfigShowTabAdapter(ItemTouchHelper helper, List<NewsTableBean>... items) {
            mLayoutInflater = LayoutInflater.from(mContext);
            this.mItemTouchHeler = helper;
            this.enableItems = items[0];
            this.disableItems = items[1];
        }

        public List<NewsTableBean> getEnableItems() {
            return enableItems;
        }

        public List<NewsTableBean> getDisableItems() {
            return disableItems;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_CONFIGSHOWTAB_SHOW_TITLE;
            } else if (position == enableItems.size() + 1) {
                return TYPE_CONFIGSHOWTAB_HIDE_TITLE;
            } else if (position > 0 && position < enableItems.size() + 1) {
                return TYPE_CONFIGSHOWTAB_SHOW_CONFIG;
            }
            return TYPE_CONFIGSHOWTAB_HIDE_CONFIG;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view;
            switch (viewType) {
                case TYPE_CONFIGSHOWTAB_SHOW_TITLE:
                    view = mLayoutInflater.inflate(R.layout.item_config_show_title, parent, false);
                    final ShowTitleViewHolder showTitleViewHolder = new ShowTitleViewHolder(view);
                    showTitleViewHolder.editTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!editMode) {
                                showTitleViewHolder.editTv.setText(mContext.getString(R.string.finish));
                                startEditMode();
                            } else {
                                showTitleViewHolder.editTv.setText(mContext.getString(R.string.edit));
                                closeEditMode();
                            }
                        }
                    });
                    return showTitleViewHolder;
                case TYPE_CONFIGSHOWTAB_SHOW_CONFIG:
                    view = mLayoutInflater.inflate(R.layout.item_config_content, parent, false);
                    final ShowConfigViewHolder showConfigViewHolder = new ShowConfigViewHolder(view);
                    showConfigViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = showConfigViewHolder.getAdapterPosition();
                            if (editMode) {
                                View targetView = recyclerView.getLayoutManager().findViewByPosition(enableItems.size() + 2);
                                View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
                                if (recyclerView.indexOfChild(targetView) >= 0) {//在屏幕内
                                    int targetX, targetY;
                                    int span = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                                    if ((enableItems.size() - 1) % span == 0) {
                                        View preTargetView = recyclerView.getLayoutManager().findViewByPosition(enableItems.size() + 2 - 1);
                                        targetX = preTargetView.getLeft();
                                        targetY = preTargetView.getTop();
                                    } else {
                                        targetX = targetView.getLeft();
                                        targetY = targetView.getTop();
                                    }
                                    moveShowToHide(showConfigViewHolder);
                                    startAnimation(recyclerView, currentView, targetX, targetY);
                                } else {
                                    moveShowToHide(showConfigViewHolder);
                                }
                            } else {
                                v.clearAnimation();
                                v.animate().alpha(0).setDuration(0).start();
                                v.animate().alpha(1).setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                            }
                        }
                    });
                    showConfigViewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (!editMode) {
                                startEditMode();
                                View view = recyclerView.getChildAt(0);
                                if (view == recyclerView.getLayoutManager().findViewByPosition(0)) {
                                    TextView tvEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                                    tvEdit.setText(R.string.finish);
                                }
                            }
                            return true;
                        }
                    });
                    showConfigViewHolder.textView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (editMode) {
                                switch (MotionEventCompat.getActionMasked(event)) {
                                    case MotionEvent.ACTION_DOWN:
                                        startActionDownTime = System.currentTimeMillis();
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        if (System.currentTimeMillis() - startActionDownTime > 300) {
                                            mItemTouchHeler.startDrag(showConfigViewHolder);
                                        }
                                        break;
                                    case MotionEvent.ACTION_CANCEL:
                                    case MotionEvent.ACTION_UP:
                                        startActionDownTime = 0;
                                        break;
                                }
                            }
                            return false;
                        }
                    });
                    return showConfigViewHolder;
                case TYPE_CONFIGSHOWTAB_HIDE_TITLE:
                    view = mLayoutInflater.inflate(R.layout.item_config_hide_title, parent, false);
                    final HideTitleViewHolder hideTitleViewHolder = new HideTitleViewHolder(view);
                    return hideTitleViewHolder;
                case TYPE_CONFIGSHOWTAB_HIDE_CONFIG:
                    view = mLayoutInflater.inflate(R.layout.item_config_content, parent, false);
                    final HideConfigViewHolder hideConfigViewHolder = new HideConfigViewHolder(view);
                    hideConfigViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = hideConfigViewHolder.getAdapterPosition();

                            int startPosition = position - enableItems.size() - 2;
                            if (startPosition > disableItems.size() - 1) {
                                return;
                            }
                            NewsTableBean bean = disableItems.get(startPosition);
                            disableItems.remove(startPosition);
                            enableItems.add(bean);
                            notifyItemMoved(position, enableItems.size() - 1 + 1);
                        }
                    });
                    return hideConfigViewHolder;
                default:
                    break;
            }
            return null;
        }

        /**
         * 显示 移动到 隐藏
         *
         * @param viewHolder
         */
        private void moveShowToHide(ShowConfigViewHolder viewHolder) {
            int position = viewHolder.getAdapterPosition();
            int startPosition = position - 1;
            if (startPosition > enableItems.size() - 1) return;

            NewsTableBean bean = enableItems.get(startPosition);
            enableItems.remove(bean);
            disableItems.add(0, bean);
            notifyItemMoved(position, enableItems.size() + 2);
        }

        /**
         * 过渡动画
         *
         * @param recyclerView
         * @param currentView
         * @param targetX
         * @param targetY
         */
        private void startAnimation(RecyclerView recyclerView, final View currentView, float targetX, float targetY) {
            final ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();
            final ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView);
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "targetX:" + targetX);
                Log.i(TAG, "targetY:" + targetY);
                Log.i(TAG, "tranX:" + (targetX - currentView.getLeft()));
                Log.i(TAG, "tranY:" + (targetY - currentView.getTop()));
            }
            Animation animation = createTranslateAnimation(targetX - currentView.getLeft(), targetY - currentView.getTop());
            currentView.setVisibility(View.INVISIBLE);
            mirrorView.startAnimation(animation);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(mirrorView);
                    if (currentView.getVisibility() != View.VISIBLE)
                        currentView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        /**
         * 创建一个平移动画
         *
         * @param targetX
         * @param targetY
         * @return
         */
        private TranslateAnimation createTranslateAnimation(float targetX, float targetY) {
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.ABSOLUTE, targetX,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.ABSOLUTE, targetY
            );
            translateAnimation.setDuration(400);
            translateAnimation.setFillAfter(true);
            return translateAnimation;
        }

        /**
         * 获取一个镜像
         *
         * @param parent
         * @param recyclerView
         * @param view
         * @return
         */
        private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view) {
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            final ImageView mirrorView = new ImageView(recyclerView.getContext());
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            mirrorView.setImageBitmap(bitmap);
            view.setDrawingCacheEnabled(false);
            int[] locations = new int[2];
            view.getLocationOnScreen(locations);
            int[] parentLocations = new int[2];
            recyclerView.getLocationOnScreen(parentLocations);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
            params.setMargins(locations[0], locations[1] - parentLocations[1], 0, 0);
            parent.addView(mirrorView, params);
            return mirrorView;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_CONFIGSHOWTAB_SHOW_CONFIG) {
                NewsTableBean enableBean = enableItems.get((position - 1) % enableItems.size());
                ShowConfigViewHolder showConfigViewHolder = (ShowConfigViewHolder) holder;
                showConfigViewHolder.textView.setText(enableBean.name);
            } else if (getItemViewType(position) == TYPE_CONFIGSHOWTAB_HIDE_CONFIG) {
                if (disableItems.size() > 0) {
                    NewsTableBean disableBean = disableItems.get(position - enableItems.size() - 2);
                    HideConfigViewHolder hideConfigViewHolder = (HideConfigViewHolder) holder;
                    hideConfigViewHolder.textView.setText(disableBean.name);
                }
            }
        }

        /**
         * 开启编辑模式
         */
        private void startEditMode() {
            editMode = true;
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getItemViewType(i) == TYPE_CONFIGSHOWTAB_SHOW_CONFIG) {
                    View view = recyclerView.getChildAt(i);
                    View childView = view.findViewById(R.id.img_edit);
                    if (childView != null) childView.setVisibility(View.VISIBLE);
                }
            }
        }

        /**
         * 关闭编辑模式
         */
        private void closeEditMode() {
            editMode = false;
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getItemViewType(i) == TYPE_CONFIGSHOWTAB_SHOW_CONFIG) {
                    View view = recyclerView.getChildAt(i);
                    View childView = view.findViewById(R.id.img_edit);
                    if (childView != null) childView.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return enableItems.size() + disableItems.size() + 2;
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            NewsTableBean bean = enableItems.get(fromPosition - 1);
            enableItems.remove(fromPosition - 1);
            enableItems.add(toPosition - 1, bean);
            notifyItemMoved(fromPosition, toPosition);
        }

        class ShowTitleViewHolder extends RecyclerView.ViewHolder {
            TextView editTv;

            public ShowTitleViewHolder(View itemView) {
                super(itemView);
                editTv = (TextView) itemView.findViewById(R.id.tv_btn_edit);
            }
        }

        class ShowConfigViewHolder extends RecyclerView.ViewHolder implements IOnDragViewHolderListener {

            TextView textView;

            ImageView editImg;

            public ShowConfigViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv);
                editImg = (ImageView) itemView.findViewById(R.id.img_edit);
            }

            @Override
            public void onItemSelected() {
                textView.setBackgroundResource(R.color.colorAccent);
            }

            @Override
            public void onItemFinish() {
                textView.setBackgroundResource(R.drawable.bg_btn);
            }
        }

        class HideTitleViewHolder extends RecyclerView.ViewHolder {

            public HideTitleViewHolder(View itemView) {
                super(itemView);
            }
        }

        class HideConfigViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public HideConfigViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        saveData();//保存数据
    }

    private void saveData() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                List<NewsTableBean> query = dao.query(NewsTable.NEWS_TABLE_ENABLE);
                e.onNext(!compare(query, adapter.getEnableItems()));
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            List<NewsTableBean> enableItems = adapter.getEnableItems();
                            List<NewsTableBean> disableItems = adapter.getDisableItems();
                            dao.removeAll();
                            dao.addEnableList(enableItems);
                            dao.addDisableList(disableItems);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        EventBus.getDefault().post(new TabShowRefreshEvent());
                    }
                });

    }

    public synchronized <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
