package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.entity.train.EduExamTrain;
import com.xgt.entity.train.EduTrainProgram;
import com.xgt.entity.train.EduTraprogramExamCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-26 17:32
 **/
@Repository
public class ProgramDao {
    private static final Logger logger = LoggerFactory.getLogger(ProgramDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "program.";

    /**
     * @Description 培训系统PC版 - 新建方案
     * @author Joanne
     * @create 2018/6/27 11:05
     */
    public Integer saveProgram(EduTrainProgram eduTrainProgram) {
        String sqlId = NAMESPACE + "saveProgram" ;
        return daoClient.insertAndGetId(sqlId,eduTrainProgram);
    }

    /**
     * @Description 培训系统PC版 - 新建方案试卷
     * @author Joanne
     * @create 2018/6/27 11:26
     */
    public Integer saveProgramTest(EduExamTrain eduExamTrain) {
        String sqlId = NAMESPACE + "saveProgramTest" ;
        return daoClient.insertAndGetId(sqlId,eduExamTrain);
    }

    /**
     * @Description 培训系统PC版 - 保存课程方案
     * @author Joanne
     * @create 2018/6/27 11:34
     */
    public void saveTrainLesson(Integer programId,Integer eduVideoId, List<String> courseIds) {
        String sqlId = NAMESPACE + "saveTrainLesson" ;
        Map params = new HashMap();
        params.put("programId",programId);
        params.put("eduVideoId",eduVideoId);
        params.put("courseIds",courseIds);
        daoClient.batchInsertAndGetId(sqlId,params);
    }

    /**
     * @Description 培训系统PC版 - 查询方案总数
     * @author Joanne
     * @create 2018/6/27 15:42
     */
    public Integer countProgram(EduTrainProgram eduTrainProgram) {
        String sqlId = NAMESPACE + "countProgram" ;
        Long totalLong = daoClient.queryForObject(sqlId,eduTrainProgram,Long.class);
        return totalLong.intValue();
    }

    /**
     * @Description 培训系统PC版 - 获取培训方案列表
     * @author Joanne
     * @create 2018/6/27 15:44
     */
    public List<EduTrainProgram> queryProgramInfo(EduTrainProgram eduTrainProgram) {
        String sqlId = NAMESPACE + "queryProgramInfo" ;
        return daoClient.queryForList(sqlId,eduTrainProgram,EduTrainProgram.class);
    }

    /**
     * @Description 培训系统PC版 - 保存方案自动组卷配置信息
     * @author Joanne
     * @create 2018/6/27 15:44
     */
    public int savetraProgramExamCfg(EduTraprogramExamCfg traprogramExamCfg) {
        String sqlId = NAMESPACE + "savetraProgramExamCfg" ;
        return daoClient.insertAndGetId(sqlId,traprogramExamCfg);
    }

    /**
     * @Description 删除课程方案（根据方案ID）
     * @author Joanne
     * @create 2018/7/11 14:50
     */
    public void deleteCourseProgram(Integer programId) {
        String sqlId = NAMESPACE + "deleteCourseProgram";
        Map param = new HashMap();
        param.put("programId",programId);
        daoClient.excute(sqlId,param);
    }

    /**
     * @Description 删除自动组卷配置
     * @author Joanne
     * @create 2018/7/11 14:59
     */
    public void deleteCfg(Integer programId) {
        String sqlId = NAMESPACE + "deleteCfg";
        Map param = new HashMap();
        param.put("programId",programId);
        daoClient.excute(sqlId,param);
    }

    /**
     * @Description 根据方案ID查询试卷ID
     * @author Joanne
     * @create 2018/7/11 18:15
     */
    public Integer queryProgramTestPaperId(Integer programId) {
        String sqlId = NAMESPACE + "queryProgramTestPaper";
        Map param = new HashMap();
        param.put("programId",programId);
        Long id = daoClient.queryForObject(sqlId,param,Long.class);
        if (id!=null){
            return id.intValue();
        }else {
            return null ;
        }
    }

    /**
     * @Description 删除课程试卷
     * @author Joanne
     * @create 2018/7/11 18:19
     */
    public void deleteProgramTestPaper(Integer programId) {
        String sqlId = NAMESPACE + "deleteProgramTestPaper" ;
        Map param = new HashMap();
        param.put("programId",programId);
        daoClient.excute(sqlId,param);
    }

    /**
     * @Description 删除方案试卷关系
     * @author Joanne
     * @create 2018/7/11 18:22
     */
    public void deleteProgramTestPaperRel(Integer programTestPaperId,String examType) {
        String sqlId = NAMESPACE + "deleteProgramTestPaperRel";
        Map params = new HashMap();
        params.put("programTestPaperId",programTestPaperId);
        params.put("examType",examType);
        daoClient.excute(sqlId,params);
    }

    /**
     * @Description 删除方案
     * @author Joanne
     * @create 2018/7/11 20:15
     */
    public void deleteProgram(Integer programId) {
        String sqlId = NAMESPACE + "deleteProgram" ;
        Map param = new HashMap();
        param.put("programId",programId);
        daoClient.excute(sqlId,param);
    }

    /**
     * @Description 根据方案ID查询方案封面
     * @author Joanne
     * @create 2018/7/12 14:14
     */
    public String queryProgramCoverPath(Integer programId) {
        String sqlId = NAMESPACE + "queryProgramCoverPath" ;
        Map map = new HashMap() ;
        map.put("programId",programId);
        return daoClient.queryForObject(sqlId,map,String.class);
    }

    /**
     * @Description 根据方案ID更新方案
     * @author Joanne
     * @create 2018/7/12 15:55
     */
    public void updateProgram(EduTrainProgram eduTrainProgram) {
        String sqlId = NAMESPACE + "updateProgram" ;
        daoClient.excute(sqlId,eduTrainProgram);
    }

    /**
     * @Description
     * 培训方案PC端 - 根据方案ID查询方案详情
     * @author Joanne
     * @create 2018/7/16 18:08
     */
    public List<EduTrainProgram> queryProgramInfoById(Integer id , Integer companyId) {
        String sqlId = NAMESPACE + "queryProgramInfo" ;
        Map map = new HashMap();
        map.put("id",id);
        map.put("companyId",companyId);
        return daoClient.queryForList(sqlId,map,EduTrainProgram.class);
    }
}