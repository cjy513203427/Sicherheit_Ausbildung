package com.xgt.constant;

import java.math.BigDecimal;

/**
 * 系统常量配置
 *
 * @author liuao
 * @date 2018/3/31 10:18
 */
public class SystemConstant {

    /**
     * 登陆类型
     *
     * @author liuao
     * @date 2018/4/7 11:21
     */
    public interface LoginType {
        // 后台管理人员登陆
        String LOGIN_TYPE_USER = "1";
        // 客户端人员登陆
        String LOGIN_TYPE_CUSTOMER = "2";
    }

    /**
     * @author Joanne
     * @Description 状态
     * @create 2018/4/10 18:29
     */
    public interface State {
        //有效
        String ACTIVE_STATE = "1";
        //无效
        String DISABLE_STATE = "0";
    }

    /**
     * @Description 上传视频至阿里云路径
     * @author Joanne
     * @create 2018/6/4 14:07
     */
    public static final String VIDEO_CONTENT_FOLDER = "edu/video/";

    /**
     * @Description 缩略图路径
     * @author Joanne
     * @create 2018/6/4 17:19
     */
    public static final String THUMBNAIL_PATH = "edu/video/thumbnail/";

    /**
     * @Description 上传至本地路径
     * @author Joanne
     * @create 2018/6/4 16:30
     */
    public static final String CUTPIC_FOLDER = "/opt/";

    //创建用户默认密码
    public static final String DEFAULT_PASSWORD = "123456";

    // oss 存放音频目录
    public static final String TXT_AUDIO_FOLDER = "edu/audio/";

    // oss 存放PDF目录
    public static final String TXT_PDG_FOLDER = "edu/pdf/";

    // oss 存放content缩略图目录 或知识库 封面图
    public static final String LITIMG_FOLDER = "edu/content/";

    //知识库默认封面图
    public static final String DEFAULT_PIC = "edu/default/白日梦想家.jpg";

    public static final String DISC = "D:/" ; //todo

    //oss 存放方案缩略图
    public static final String TRAIN_FOLDER = "edu/train/";

    //题库Excel上传路径
    public static final String UPLOADED_FILE_PATH = "/opt/edu/question/";

    //题目对应上传视频地址
    public static final String VIDEO_QUESTION_PATH = "edu/video/question/";

    public static final String QR_CODE_WORD_PATH = "D:/tmp/imgWord_%s.doc";

    //编码格式
    public static final String CHARSET_NAME = "utf-8";

    //图片制式
    public interface PicFormat {
        String JPG = ".jpg";
    }

    /**
     * 是否通过状态值
     */
    public interface passStatus {
        // 通过
        String PASS = "1";
        // 未通过
        String NOPASS = "2";
        //暂无记录
        String NOTHING = "3";
    }

    /**
     * 试卷类型
     */
    public interface ExamType {
        // 模拟试卷
        String MOCK = "1";
        // 正式考试
        String FORMAL = "2";
        //培训方案
        String TRAIN = "3";
    }

    /**
     * @author Joanne
     * @Description 视频观看状态
     * @create 2018/6/8 18:00
     */
    public interface PlayStatus {
        //已看完
        String FINISHED = "1";
        //进行中
        String UNDERWAY = "2";
    }

    /**
     * @author Joanne
     * @Description 集锦阅读状态
     * @create 2018/6/14 18:38
     */
    public interface ReadStatus {
        //已都
        String READED = "2";
        //未读
        String UNREADED = "1";
    }

    /**
     * 是否正确
     */
    public interface correctFlag {
        //正确
        String TRUE = "1";
        //错误
        String FALSE = "2";
    }

    /**
     * 收藏类型
     *
     * @author liuao
     * @date 2018/6/11 9:46
     */
    public interface CollectionType {
        // 视频
        String VIDEO = "1";
        // 图文
        String IMGTXT = "2";
        // 题目
        String QUESTION = "3";
    }

    /**
     * @author Joanne
     * @Description 通知模板
     * @create 2018/6/11 17:56
     */
    public interface ContentTemplate {
        //新增视频
        String NEW_VIDEO = "新增了视频课程：%s";
        //新增图文
        String NEW_PIC_ARTICLE = "新增图文案列：%s";
    }

    /**
     * @author Joanne
     * @Description 通知类型
     * @create 2018/6/11 17:59
     */
    public interface NoticeType {
        //视频课程通知
        String VIDEO_COURSE_NOTICE = "1";
        //图文课程通知
        String PIC_ARTICLE_COURSE_NOTICE = "2";
        //考试通知
        String EXAME_NOTICE = "3";
    }

    /**
     * 上一题、下一题标识
     *
     * @author liuao
     * @date 2018/6/12 16:00
     */
    public interface NextPrevFlag {
        // 下一题
        String NEXT_FLAG = "1";
        // 上一题
        String PREV_FLAG = "2";
    }

    /**
     * 逗号
     *
     * @author liuao
     * @date 2018/6/13 9:51
     */
    public static final String COMMA = ",";

    /**
     * 角色名
     */
    public static final String ADMINISTRATOR = "超级管理员";

    public static final String BUILDHEAD = "工地负责人";

    /**
     * redis key 前缀
     *
     * @author liuao
     * @date 2018/6/19 15:41
     */
    public static final String REDIS_KEY_PREFIX = "EDU_";


    /**
     * @author Joanne
     * @Description 字典编码
     * @create 2018/6/27 15:43
     */
    public interface TypeCode {

        // 方案类型、培训类型
        String PROGRAM_TYPE = "PROGRAM_TYPE";

        //岗位
        String POST_TYPE = "POST_TYPE";
    }

    /**
     * 总分
     *
     * @author liuao
     * @date 2018/6/27 10:32
     */
    public static final BigDecimal TOTAL_SCORE = BigDecimal.valueOf(100L);

    /**
     * @author HeLiu
     * @Description 培训计划状态
     * @date 2018/6/29 17:14
     */
    public interface planStatus {
        // 未开始
        String notStart = "0";
        //正在进行
        String running = "1";
        // 已结束
        String over = "2";
    }

    /**
     *
     * @author liuao
     * @date 2018/7/12 14:54
     */
    public static final int FINGER_MATCH_STANDARD_SORCE = 90;

    /**
     * 点
     *
     * @author liuao
     * @date 2018/7/13 14:31
     */
    public static final String SPOT = ".";

    public static final String OBLIQUE = "/" ;

    //视频解密后的 临时文件
    public static final String VIDEO_TEMP_PATH = "D:/VideoDecrypt/";
    // 视频加密/解密时的 key
    public static final String VIDEO_ENCRYPT_DECRYPT_KEY = "fuckyourself";

    /**
     * 远程服务接口
     *
     * @author liuao
     * @date 2018/7/18 20:30
     */
    public interface RemoteServer {
        // 根据  视频章节内容Id ，查询id 大于当前该 视频章节内容Id 的下一个视频章节内容信息
        String video_queryNextChapterContentById = "videoRemoteServer/queryNextChapterContentById";

        // 根据  视频章节内容Id ，查询视频章节内容信息
        String video_queryChapterContentById = "videoRemoteServer/queryChapterContentById";

        // 根据视频集锦id ,查询视频集锦
        String video_queryVideoById = "videoRemoteServer/queryVideoById";

        //根据图文id,查询id和大于当前该图文id的下一个图文信息
        String imgtext_queryNextImgTestById = "imgTextRemoteServer/queryNextImgTestById";

        //根据classifyId到服务器端数据库查询图文分类信息
        String imgtext_queryClassifyById = "imgTextRemoteServer/queryClassifyById ";
        //同步本地分公司至远程服务器
        String labourer_syncBuildLabourInfo = "labourRemoteServer/syncBuildLabourInfo" ;

        // 根据id ,查询视频和题目关系信息
        String question_queryNextQuestionRelById = "remoteServerQuestionRel/queryNextQuestionRelById";

        // 根据题目id 查询题目信息和题目的选项
        String question_queryQuestionAndOptionById = "remoteServerQuestionRel/queryQuestionAndOptionById";
    }

}
