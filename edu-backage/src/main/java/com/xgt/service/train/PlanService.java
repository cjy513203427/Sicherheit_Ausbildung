package com.xgt.service.train;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.UserDao;
import com.xgt.dao.train.PlanDao;
import com.xgt.dao.train.ProgramDao;
import com.xgt.dto.*;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.entity.train.EduTraplanLabourRel;
import com.xgt.enums.EnumPassStatus;
import com.xgt.enums.EnumQuestionType;
import com.xgt.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.util.*;

import static com.xgt.constant.SystemConstant.COMMA;
import static com.xgt.constant.SystemConstant.TypeCode.PROGRAM_TYPE;
import static com.xgt.enums.EnumQuestionType.MULTI_SELECTION;
import static com.xgt.enums.EnumQuestionType.SINGLE;

/**
 * @author HeLiu
 * @Description 培训计划service层
 * @date 2018/6/29 14:14
 */
@Service
public class PlanService {

    private final static Logger logger = LoggerFactory.getLogger(PlanService.class);

    @Autowired
    private PlanDao planDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private UserDao userDao ;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private BuildLabourerDao buildLabourerDao;

    @Autowired
    private ProgramDao programDao;

    /**
     * @Description 培训系统PC版-查询培训记录列表（分页）
     * @author HeLiu
     * @date 2018/6/29 14:37
     */
    public Map<String, Object> queryPlanList(EduPlanDto eduPlanDto) {
        Integer total = planDao.queryPlanCount(eduPlanDto);
        List<EduPlanDto> list = null;
        if (total != null && total > 0) {
            list = planDao.queryPlanList(eduPlanDto);
            //遍历组装数据
            for (EduPlanDto dto : list) {
                Integer programId = dto.getProgramId();
                Integer planId = dto.getPlanId();
                Integer labourCount = dto.getLabourCount();
                String startDate = dto.getStartDate();
                String endDate = dto.getEndDate();
                //查询培训计划合格人数
                Integer passCount = planDao.queryPassCount(SystemConstant.ExamType.TRAIN,
                        SystemConstant.passStatus.PASS, programId, planId);
                String percentOfPass = "0%";
                //防止分母为零
                if (labourCount != null && labourCount > 0) {
                    //计算合格率
                    percentOfPass = StringUtil.getNum(passCount, labourCount);
                }
                dto.setPercentOfPass(percentOfPass);
                //获取状态（未开始，正在进行，已结束）
                //当前系统时间
                String currentTime = DateUtil.getCurrentTime();
                String planStatus = DateUtil.compareDate(currentTime, startDate, endDate, "yyyy-MM-dd HH:mm:ss");
                dto.setPlanStatus(planStatus);

                dto.setProgramTypeName(dictionaryDao.getDictionaryText(PROGRAM_TYPE + dto.getProgramType()));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, EduPlanDto.class));
        map.put("total", total);
        return map;
    }

    /**
     * @Description 培训系统PC版-查询培训记录详情
     * @author HeLiu
     * @date 2018/6/30 15:44
     */
    public List<EduPlanDetailsDto> queryPlanDetails(Integer programId, Integer planId, String planStatus) {
        List<EduPlanDetailsDto> eduPlanDetailsDtos = planDao.queryPlanDetails(programId, planId, SystemConstant.ExamType.TRAIN);
        if (CollectionUtils.isNotEmpty(eduPlanDetailsDtos)) {
            for (EduPlanDetailsDto eduPlanDetailsDto : eduPlanDetailsDtos) {
                eduPlanDetailsDto.setStatus(planStatus);
            }
        }
        return eduPlanDetailsDtos;
    }

    /**
     * @Description 新增培训计划
     * @author Joanne
     * @create 2018/7/12 17:21
     */
    public void addPlan(EduTrainPlan eduTrainPlan, String ids) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //新增方案
                Integer planId = planDao.addPlan(eduTrainPlan);
                List<String> labourIdList = Arrays.asList(ids.split(COMMA));
                //保存人员计划关系
                planDao.savePlanLabourRel(planId, labourIdList);
                return true;
            }
        });
    }

    /**
     * @Description 修改培训计划
     * @author Joanne
     * @create 2018/7/13 18:43
     */
    public void updatePlan(EduTrainPlan eduTrainPlan, String ids) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //修改培训计划
                planDao.updatePlan(eduTrainPlan);
                if (StringUtils.isNotBlank(ids)) {
                    //删除计划人员关系
                    planDao.deleteLabPlanRel(eduTrainPlan.getId());
                    //保存计划人员关系
                    planDao.savePlanLabourRel(eduTrainPlan.getId(), Arrays.asList(ids.split(COMMA)));
                }
                return null;
            }
        });
    }

    /**
     * @Description 培训系统PC版 - 方案计划修改查询详情
     * @author Joanne
     * @create 2018/7/16 20:25
     */
    public EduTrainPlan queryPlanById(Integer id) {
        EduTrainPlan eduTrainPlan = planDao.queryPlanById(id);
        List<EduTraplanLabourRel> eduTraplanLabourRels = planDao.queryRel(id);
        eduTrainPlan.setEduTraplanLabourRels(eduTraplanLabourRels);
        return eduTrainPlan;
    }

    /**
     * @Description 删除计划
     * @author Joanne
     * @create 2018/7/16 21:23
     */
    public void deletePlan(Integer id) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //删除计划
                planDao.deletePlan(id);
                //删除人员关系
                planDao.deleteLabPlanRel(id);
                return true;
            }
        });
    }

    /**
     * @Description 培训系统PC版-导出培训记录接口（Word）
     * @author HeLiu
     * @date 2018/7/13 14:27
     */
    public void exportTrainRecord(Integer companyId, Integer programId, Integer planId) throws IOException {
        InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("template/trainRecord.ftl");
        String targetFiletaPath = "D:\\tmp\\trainRecord.doc";
        File targetFile = FileToolUtil.createNewFile(targetFiletaPath);
        Writer resultFileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
        Reader templateFileReader = new InputStreamReader(is, "utf-8");

        Map param = new HashMap();

        //查询培训信息
        EduPlanDto eduPlanDto = planDao.queryTrainInfo(companyId, planId);
        eduPlanDto.setProgramTypeName(dictionaryDao.getDictionaryText(SystemConstant.TypeCode.PROGRAM_TYPE + eduPlanDto.getProgramType()));
        //查询培训内容
        List<TrainContentDto> trainContentDtos = planDao.queryTrainContent(programId);
        //查询全部学员的考试成绩
        List<UserDto> userDtos = planDao.queryAllUserExamScore(programId, planId, SystemConstant.ExamType.TRAIN);
        if (CollectionUtils.isNotEmpty(userDtos)) {
            for (UserDto dto : userDtos) {
                Integer labourerId = dto.getLabourerId();
                //查询我的答案
                List<String> answers = planDao.queryMyAnswers(programId, labourerId, SystemConstant.ExamType.TRAIN);
                dto.setAnswers(StringUtil.getMyAnswerStr(answers));
            }
        }
        //查询题目
        ExamDto examDto = new ExamDto();
        //单选集合
        List<QuestionsDto> unitList = getQuestionsList(programId, SystemConstant.ExamType.TRAIN, SINGLE.code);
        //多选集合
        List<QuestionsDto> duoList = getQuestionsList(programId, SystemConstant.ExamType.TRAIN, MULTI_SELECTION.code);
        examDto.setTitle(planDao.queryProgramNameById(programId));
        examDto.setUnitList(unitList);
        examDto.setDuoList(duoList);


        param.put("eduPlanDto", eduPlanDto);
        param.put("trainContentDtos", trainContentDtos);
        param.put("userDtos", userDtos);
        param.put("examDto", examDto);

        FreeMakerParser.process(param, resultFileWriter, templateFileReader);
    }

    /**
     * 导出学员培训情况Word
     * @param labourerId
     * @param companyId
     */
    public void exportBuildLabourer(Integer labourerId,Integer companyId) throws IOException {
        labourerId = 12;companyId = 1;
        //查询个人基本信息
        BuildLabourerDto buildLabourer = buildLabourerDao.queryBuildLabourerById(labourerId);
        buildLabourer.setPostTypeTxt(dictionaryDao.getDictionaryText(SystemConstant.TypeCode.POST_TYPE + buildLabourer.getPostType()));
        buildLabourer.setSex(buildLabourer.getSex());
        Map<String,Object> map = new HashMap<>();
        map.put("buildLabourer",buildLabourer);
        List<Integer> examId = planDao.getExamIdFromEduExamlabourRel(labourerId);
        Integer examScore = null;
        List<Integer> examScores = new ArrayList<>();
        List<EduPlanDto> eduPlanDtoListAll = new ArrayList<>();
            for (Integer examid : examId) {

                //查询培训信息
                List<EduPlanDto> eduPlanDtoList = planDao.queryBuildLabourerTrainInfo(labourerId, companyId, examid);
                for (EduPlanDto eduPlanDto : eduPlanDtoList) {
                    eduPlanDto.setProgramTypeName(dictionaryDao.getDictionaryText(SystemConstant.TypeCode.PROGRAM_TYPE + eduPlanDto.getProgramType()));
                    eduPlanDto.setPassStatus(EnumPassStatus.enumOfByCode(eduPlanDto.getPassStatus()).desc);
                }
                map.put("eduPlanDtoList", eduPlanDtoList);
                //查询培训内容
                List<TrainContentDto> trainContentDtoList = planDao.queryBuildLabourerTrainContent(labourerId, examid);
                int i = 0;
                for (TrainContentDto trainContentDto : trainContentDtoList) {
                    i++;
                    trainContentDto.setIndex(i);
                }
                map.put("trainContentDtoList", trainContentDtoList);

                //查询答题记录
                examScore = planDao.getExamScoreFromEduExamlabourRel(labourerId, examid);
                examScores.add(examScore);
                List<UserDto> answerList = planDao.getAnswerFromEduExamAnswercard(labourerId, examid);
                map.put("examScores", examScores);
                int j = 0;
                for (UserDto userDto : answerList) {
                    j++;
                    userDto.setIndex(j);
                }
                map.put("answerList", answerList);
                //查询正确答案
                List<AnswerKeyDto> answerKeyDtoList = planDao.getTrueAnswerFromEduQuestion();
                map.put("answerKeyDtoList", answerKeyDtoList);
                int k = 0;
                for (AnswerKeyDto answerKeyDto : answerKeyDtoList) {
                    k++;
                    answerKeyDto.setIndex(k);
                }
                //获取单选试卷题目
                List<EduQuestionDto> eduQuestionDtoSingleTitleList = planDao.getEduQuestionTitleInfo(SINGLE.code, examid);
                int l = 0;
                for (EduQuestionDto eduQuestionDto : eduQuestionDtoSingleTitleList) {
                    l++;
                    eduQuestionDto.setIndex(l);
                    //获取单选试卷选项和选项内容
                    List<EduQuestionDto> eduQuestionDtoSingleList = planDao.getEduQuestionInfo(SINGLE.code, examid, eduQuestionDto.getId());
                    eduQuestionDto.setCodeContent(eduQuestionDtoSingleList);
                }
                //获取多选试卷题目
                List<EduQuestionDto> eduQuestionDtoMultiTitleList = planDao.getEduQuestionTitleInfo(MULTI_SELECTION.code, examid);
                for (EduQuestionDto eduQuestionDto : eduQuestionDtoMultiTitleList) {
                    l++;
                    eduQuestionDto.setIndex(l);
                    //获取多选试卷选项和选项内容
                    List<EduQuestionDto> eduQuestionDtoMultiList = planDao.getEduQuestionInfo(MULTI_SELECTION.code, examid, eduQuestionDto.getId());
                    eduQuestionDto.setCodeContent(eduQuestionDtoMultiList);
                }

                map.put("eduQuestionDtoSingleTitleList", eduQuestionDtoSingleTitleList);
                map.put("eduQuestionDtoMultiTitleList", eduQuestionDtoMultiTitleList);

        }
        WordUtil test = new WordUtil();
        test.createWord(map);
    }

    /**
     * @Description 获取试卷题目集合
     * @author HeLiu
     * @date 2018/7/17 12:23
     */
    public List<QuestionsDto> getQuestionsList(Integer programId, String examType, String questionType) {
        List<QuestionsDto> list = planDao.queryQuestionsList(programId, examType, questionType);
        if (CollectionUtils.isNotEmpty(list)) {
            for (QuestionsDto dto : list) {
                Integer questionId = dto.getQuestionId();
                //查询选项Questions
                List<OptionsDto> optionsDtos = planDao.queryOptions(questionId);
                dto.setOptions(optionsDtos);
            }
        }
        return list;
    }

    /**
     * @Description 培训系统PC版- 查询培训记录（为修改查询）
     * @author Joanne
     * @create 2018/7/18 14:59
     */
    public Map queryPlanInfo(Integer planId) {
        EduTrainPlan eduTrainPlan = planDao.queryPlanById(planId);
        List<EduTraplanLabourRel> trainLabourerDtos = userDao.queryLabourIdsByPlanId(planId);
        Map map = new HashMap();
        map.put("planInfo",eduTrainPlan);
        map.put("userInfo",trainLabourerDtos) ;
        return map ;
    }
}
