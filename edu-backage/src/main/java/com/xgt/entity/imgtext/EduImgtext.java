package com.xgt.entity.imgtext;

/**
 * @author Joanne
 * @Description 图文实体类
 * @create 2018-06-05 9:35
 **/
public class EduImgtext {
    private Integer id ;
    //文章标题
    private String title ;
    //简介
    private String introduce ;
    //分类
    private Integer contentClassify ;
    //分类名称
    private String classifyName ;
    //内容
    private String content ;
    //图片地址
    private String imagePath ;
    //浏览次数
    private Integer browseTimes ;
    //音频地址
    private String audioPath ;
    private String pdfPath ;
    //岗位
    private String postType;

    private String postTypeTxt;
    private String createTime ;
    //查询模式
    private Integer checkModel;

    //创建人id
    private Integer createUserId ;
    //形如"(1,2)"
    private String createUserIds ;

    //收藏id
    private Integer collectId ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(Integer browseTimes) {
        this.browseTimes = browseTimes;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCheckModel() {
        return checkModel;
    }

    public void setCheckModel(Integer checkModel) {
        this.checkModel = checkModel;
    }

    public Integer getContentClassify() {
        return contentClassify;
    }

    public void setContentClassify(Integer contentClassify) {
        this.contentClassify = contentClassify;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getPostTypeTxt() {
        return postTypeTxt;
    }

    public void setPostTypeTxt(String postTypeTxt) {
        this.postTypeTxt = postTypeTxt;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserIds() {
        return createUserIds;
    }

    public void setCreateUserIds(String createUserIds) {
        this.createUserIds = createUserIds;
    }
}
