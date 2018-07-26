package com.xgt.service.train;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.train.InTheTrainDao;
import com.xgt.dao.train.ProgramDao;
import com.xgt.dto.EduPlanDto;
import com.xgt.dto.EduProgramDto;
import com.xgt.entity.EduAnswertoolRel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.TypeCode.PROGRAM_TYPE;

/**
 * @author HeLiu
 * @Description 进入培训Service层
 * @date 2018/7/6 15:01
 */
@Service
public class InTheTrainService {

    private static final Logger logger = LoggerFactory.getLogger(InTheTrainService.class);

    @Autowired
    private InTheTrainDao inTheTrainDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    /**
     * @Description 培训系统PC版-查询进入培训列表（培训方案，进行中的培训计划）
     * @author HeLiu
     * @date 2018/7/6 14:58
     */
    public Map<String, Object> queryInTheTrainProgramList(Integer companyId) {
        //查询培训方案列表
        List<EduProgramDto> eduProgramDtoList = inTheTrainDao.queryEduProgramList(companyId);
        if (CollectionUtils.isNotEmpty(eduProgramDtoList)) {
            for (EduProgramDto eduProgramDto : eduProgramDtoList) {
                eduProgramDto.setProgramTypeName(dictionaryDao.getDictionaryText(PROGRAM_TYPE + eduProgramDto.getProgramType()));
            }
        }

        //查询进行中的培训计划列表
        List<EduPlanDto> eduPlanDtoList = inTheTrainDao.queryRunningPlanList(companyId);
        if (CollectionUtils.isNotEmpty(eduPlanDtoList)) {
            for (EduPlanDto eduPlanDto : eduPlanDtoList) {
                eduPlanDto.setProgramTypeName(dictionaryDao.getDictionaryText(PROGRAM_TYPE + eduPlanDto.getProgramType()));
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("programs", eduProgramDtoList);
        map.put("runingPlans", eduPlanDtoList);
        return map;
    }

    /**
     * @Description 培训系统PC版-查询考试题目（视频题目）
     * @author HeLiu
     * @date 2018/7/11 15:09
     */
    public Map<String, Object> queryTestQuestion(Integer programId, Integer planId, Integer questionId) {
        return inTheTrainDao.queryTestQuestion(programId, planId, questionId);
    }

    /**
     *  保存人员和答题器的,对应关系
     * @author liuao
     * @date 2018/7/16 14:12
     */
    public void saveAnswertoolRel(String jsonStr) {
        List<EduAnswertoolRel> answerToolRelList = JSONArray.parseArray(jsonStr, EduAnswertoolRel.class);

        if(CollectionUtils.isEmpty(answerToolRelList)) {
            return ;
        }

        Integer planId = answerToolRelList.get(0).getTrainPlanId();

        // 设置试卷id
        for (EduAnswertoolRel eduAnswertoolRel : answerToolRelList) {
            Integer examId = programDao.queryProgramTestPaperId(eduAnswertoolRel.getTrainProgramId());
            eduAnswertoolRel.setTrainExamId(examId);
        }

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                // 删除现原有的 答题器 对应关系
                inTheTrainDao.deleteAnswerToolRel(planId);
                // 保存新的答题器对应关系
                inTheTrainDao.saveAnswertoolRel(answerToolRelList);
                return true;
            }
        });

    }
}
