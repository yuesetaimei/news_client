package com.tlkg.news.app.binder.joke;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.JokeContentBean;
import com.tlkg.news.app.util.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/10.
 */

public class JokeContentViewBinder extends ItemViewBinder<JokeContentBean.DataBean.GroupBean, JokeContentViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected JokeContentViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_joke_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final JokeContentBean.DataBean.GroupBean item) {

        final Context context = holder.itemView.getContext();

        try {
            String avatar_url = item.getUser().getAvatar_url();
            String name = item.getUser().getName();
            String text = item.getText();
            String digg_count = item.getDigg_count() + "";
            String bury_count = item.getBury_count() + "";
            int comment_count = item.getComment_count();

            ImageLoader.loadCenterCrop(context, avatar_url, holder.iv_avatar, R.color.viewBackground);
            holder.tv_username.setText(name);
            holder.tv_text.setText(text);
            holder.tv_digg_count.setText(digg_count);
            holder.tv_bury_count.setText(bury_count);
            if (comment_count > 0) {
                holder.tv_comment_count.setText(comment_count + "评论");
            } else {
                holder.tv_comment_count.setVisibility(View.GONE);
            }

//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                            JokeCommentActivity.launch(item);
//                        }
//                    });

            holder.iv_dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,
                            holder.iv_dots, Gravity.END, 0, R.style.MyPopupMenu);
                    popupMenu.inflate(R.menu.menu_joke_content);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menu) {
                            int itemId = menu.getItemId();
                            if (itemId == R.id.action_copy) {
                                ClipboardManager copy = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("text", item.getText());
                                copy.setPrimaryClip(clipData);
                                Snackbar.make(holder.itemView, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
                            }
                            if (itemId == R.id.action_comment_share) {
                                Intent shareIntent = new Intent()
                                        .setAction(Intent.ACTION_SEND)
                                        .setType("text/plain")
                                        .putExtra(Intent.EXTRA_TEXT, item.getText());
                                context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.action_share)));
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        } catch (Exception e) {
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_digg_count;
        private TextView tv_bury_count;
        private TextView tv_comment_count;
        private ImageView iv_dots;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_digg_count = (TextView) itemView.findViewById(R.id.tv_digg_count);
            this.tv_bury_count = (TextView) itemView.findViewById(R.id.tv_bury_count);
            this.tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
            this.iv_dots = (ImageView) itemView.findViewById(R.id.iv_dots);
        }
    }
}
