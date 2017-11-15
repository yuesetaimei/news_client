package com.tlkg.news.app.binder.questionanswer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlkg.news.app.BuildConfig;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.QuestionAnswerDataBean;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.DataUtils;

import me.drakeet.multitype.ItemViewBinder;


public class QuestionAnswerTextViewBinder extends ItemViewBinder<QuestionAnswerDataBean, QuestionAnswerTextViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected QuestionAnswerTextViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_question_answer_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final QuestionAnswerDataBean item) {
        try {
            String tv_title = item.getQuestionBean().getTitle();
            String tv_answer_count = item.getQuestionBean().getNormal_ans_count() + item.getQuestionBean().getNice_ans_count() + "回答";
            String tv_datetime = item.getQuestionBean().getCreate_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = DataUtils.getTimeStampAgo(tv_datetime);
            }
            String tv_content = item.getAnswerBean().getAbstractX();
            holder.tv_title.setText(tv_title);
            holder.tv_title.setTextSize(CommonSettingUtil.getInstance().getTextSize());
            holder.tv_answer_count.setText(tv_answer_count);
            holder.tv_time.setText(tv_datetime);
            holder.tv_content.setText(tv_content);

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
        private TextView tv_content;
        private TextView tv_answer_count;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_answer_count = (TextView) itemView.findViewById(R.id.tv_answer_count);
            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
