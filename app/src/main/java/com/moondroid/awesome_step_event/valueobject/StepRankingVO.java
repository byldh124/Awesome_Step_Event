package com.moondroid.awesome_step_event.valueobject;

public class StepRankingVO {
    private String no;
    private String name;
//    private String imgUrl;

    private int imgRes;
    private String stepCount;

    public StepRankingVO() {
    }

    public StepRankingVO(String no, String name, int imgRes, String stepCount) {
        this.no = no;
        this.name = name;
        this.imgRes = imgRes;
        this.stepCount = stepCount;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public String getStepCount() {
        return stepCount;
    }
}
