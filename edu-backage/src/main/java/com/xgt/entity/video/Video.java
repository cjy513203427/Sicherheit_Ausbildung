package com.xgt.entity.video;

import com.xgt.common.PageQueryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Joanne
 * @Description 视频管理实体类
 * @create 2018-06-01 16:17
 **/
public class Video extends PageQueryEntity {
    private Integer id ;
    //视频系列名称
    private String title ;
    //适用人群
    private String forPeople ;
    //简介
    private String introduction ;
    //岗位
    private String postType ;
    //岗位文本
    private String postTypeTxt ;
    //缩略图地址（封面）
    private String thumbnailPath ;
    //点击次数
    private Integer clickNumber ;
    //创建人ID
    private Integer createUserId ;
    //创建人 对应ct_user realname
    private String createUserName ;
    //创建时间
    private String createTime ;
    //更新时间
    private String updateTime ;

    private List<VideoChapter> chapterList ;

    private List<ChapterContent> contentList ;

    //评分均分
    private BigDecimal average ;

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

    public String getForPeople() {
        return forPeople;
    }

    public void setForPeople(String forPeople) {
        this.forPeople = forPeople;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPostTypeTxt() {
        return postTypeTxt;
    }

    public void setPostTypeTxt(String postTypeTxt) {
        this.postTypeTxt = postTypeTxt;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public List<VideoChapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<VideoChapter> chapterList) {
        this.chapterList = chapterList;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<ChapterContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<ChapterContent> contentList) {
        this.contentList = contentList;
    }
}
