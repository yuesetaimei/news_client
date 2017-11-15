package com.tlkg.news.app.binder.questionanswer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlkg.news.app.BuildConfig;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.QuestionAnswerDataBean;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.DataUtils;
import com.tlkg.news.app.util.ImageLoader;

import me.drakeet.multitype.ItemViewBinder;


public class WendaArticleThreeImgViewBinder extends ItemViewBinder<QuestionAnswerDataBean, WendaArticleThreeImgViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected WendaArticleThreeImgViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_question_answer_three_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final QuestionAnswerDataBean item) {

        Context context = holder.itemView.getContext();

        try {
            int size = item.getExtraBean().getWenda_image().getThree_image_list().size();
            String[] ivs = new String[size];
            for (int i = 0; i < size; i++) {
                ivs[i] = item.getExtraBean().getWenda_image().getThree_image_list().get(i).getUrl();
            }
            switch (ivs.length) {
                case 1:
                    ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                    break;
                case 2:
                    ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                    ImageLoader.loadCenterCrop(context, ivs[1], holder.iv_1, R.color.viewBackground);
                    break;
                case 3:
                    ImageLoader.loadCenterCrop(context, ivs[0], holder.iv_0, R.color.viewBackground);
                    ImageLoader.loadCenterCrop(context, ivs[1], holder.iv_1, R.color.viewBackground);
                    ImageLoader.loadCenterCrop(context, ivs[2], holder.iv_2, R.color.viewBackground);
                    break;
            }

            String tv_title = item.getQuestionBean().getTitle();
            String tv_answer_count = item.getQuestionBean().getNormal_ans_count() + item.getQuestionBean().getNice_ans_count() + "回答";
            String tv_datetime = item.getQuestionBean().getCreate_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = DataUtils.getTimeStampAgo(tv_datetime);
            }
            holder.tv_title.setText(tv_title);
            holder.tv_title.setTextSize(CommonSettingUtil.getInstance().getTextSize());
            holder.tv_answer_count.setText(tv_answer_count);
            holder.tv_time.setText(tv_datetime);

//            RxView.clicks(holder.itemView)
//                    .throttleFirst(1, TimeUnit.SECONDS)
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                            WendaContentActivity.launch(item.getQuestionBean().getQid() + "");
//                        }
//                    });
        } catch (Exception e) {
            if (BuildConfig.DEBUG) e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private TextView tv_answer_count;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_0 = (ImageView) itemView.findViewById(R.id.iv_0);
            this.iv_1 = (ImageView) itemView.findViewById(R.id.iv_1);
            this.iv_2 = (ImageView) itemView.findViewById(R.id.iv_2);
            this.tv_answer_count = (TextView) itemView.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
