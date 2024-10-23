package com.harsh.shah.threads.clone.model;

import androidx.annotation.NonNull;

public class PollOptions {
    private PollOptionsItem option3;
    private PollOptionsItem option4;
    private PollOptionsItem option1;
    private PollOptionsItem option2;

    public PollOptions() {
    }

    public PollOptions(PollOptionsItem option3, PollOptionsItem option4, PollOptionsItem option1, PollOptionsItem option2) {
        this.option3 = option3;
        this.option4 = option4;
        this.option1 = option1;
        this.option2 = option2;
    }

    public PollOptionsItem getOption3() {
        return option3;
    }

    public void setOption3(PollOptionsItem option3) {
        this.option3 = option3;
    }

    public PollOptionsItem getOption4() {
        return option4;
    }

    public void setOption4(PollOptionsItem option4) {
        this.option4 = option4;
    }

    public PollOptionsItem getOption1() {
        return option1;
    }

    public void setOption1(PollOptionsItem option1) {
        this.option1 = option1;
    }

    public PollOptionsItem getOption2() {
        return option2;
    }

    public void setOption2(PollOptionsItem option2) {
        this.option2 = option2;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "PollOptions{" +
                        "option3 = '" + option3 + '\'' +
                        ",option4 = '" + option4 + '\'' +
                        ",option1 = '" + option1 + '\'' +
                        ",option2 = '" + option2 + '\'' +
                        "}";
    }


    public static class PollOptionsItem {
        private int votes;
        private String text;

        public PollOptionsItem() {
        }

        public PollOptionsItem(int votes, String text) {
            this.votes = votes;
            this.text = text;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @NonNull
        @Override
        public String toString() {
            return
                    "Option{" +
                            "votes = '" + votes + '\'' +
                            ",text = '" + text + '\'' +
                            "}";
        }
    }

}
