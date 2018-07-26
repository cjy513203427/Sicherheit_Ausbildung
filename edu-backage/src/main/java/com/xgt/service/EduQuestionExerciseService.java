package com.xgt.service;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.CollectionDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.EduQuestionExerciseDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduQuestionBank;
import com.xgt.entity.EduQuestionExercise;
import com.xgt.enums.EnumCorrectFlag;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.COMMA;

/**
 * @author cjy
 * @Date 2018/6/7 11:59.
 */
@Service
public class EduQuestionExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionExerciseService.class);
    @Autowired
    private EduQuestionExerciseDao eduQuestionExerciseDao;

    /**
     * 检索练习答案
     * 错题本
     * @param eduQuestionExercise
     * @return
     */
    @Autowired
    private EduQuestionDao questionDao;
    @Autowired
    private CollectionDao collectionDao ;


    public Map queryEduQuestionExercise(EduQuestionExercise eduQuestionExercise) {
        Map map = new HashMap();
        Integer total = eduQuestionExerciseDao.countEduQuestionExercise(eduQuestionExercise);
        List<EduQuestionExercise> list = eduQuestionExerciseDao.queryEduQuestionExercise(eduQuestionExercise);
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    /**
     * 获取细节
     * @return
     */
    public Map getDetails(String postType, Integer labourId){
        Map map = new HashMap();

        // 岗位对应的总题库
        List<EduQuestionBank> eduQuestionBanks = eduQuestionExerciseDao.queryEduQuestionBankTitle(postType);

        //  题库已答统计
        List<Map<String,Object>> answerdQuestions = eduQuestionExerciseDao.countAnswerdQuestion(labourId,null);

        Map<Integer, Integer> answeredMap = new HashMap<>();

        // 题库id 为key ， 题库已答数量为value
        for(Map<String,Object> answerdQuestion : answerdQuestions){
            Integer questionBankId =  MapUtils.getInteger(answerdQuestion, "question_bank_id");
            Integer answerdCount =  MapUtils.getInteger(answerdQuestion, "answerdCount");
            answeredMap.put(questionBankId, answerdCount);
        }

        // 题库id 为key ， 题库正确题目量为value
        Map<Integer, Integer> correctMap = new HashMap<>();
        //  总错误个数
        int totalWrong = 0 ;

        for(Map<String,Object> answerdQuestion : answerdQuestions){
            Integer questionBankId =  MapUtils.getInteger(answerdQuestion, "question_bank_id");
            Integer correctCount = MapUtils.getInteger(answerdQuestion, "correctCount");
            correctMap.put(questionBankId, correctCount);
        }

        // 设置题库已答数量
        for(EduQuestionBank eduQuestionBank : eduQuestionBanks) {
            Integer  questionBankId =  eduQuestionBank.getId();
            Integer answeredCount = MapUtils.getInteger(answeredMap, questionBankId);
            Integer correctCount = MapUtils.getInteger(correctMap,questionBankId);

            answeredCount = answeredCount !=null ? answeredCount : 0 ;
            correctCount = correctCount != null ? correctCount : 0;

            totalWrong = totalWrong + ( answeredCount - correctCount);

            eduQuestionBank.setAnsweredCount(answeredCount);
            eduQuestionBank.setCorrectCount(correctCount);

            Integer totalCount = eduQuestionBank.getTotalCount();
            if( totalCount != null && totalCount.compareTo(0) > 0
                    && correctCount != null && correctCount.compareTo(0) > 0) {

                BigDecimal correctCount2 = BigDecimal.valueOf(correctCount);
                BigDecimal totalCount2 = BigDecimal.valueOf(totalCount);
                BigDecimal accuracy = correctCount2.divide(totalCount2,2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
                eduQuestionBank.setAccuracy(accuracy.intValue());

            }else{
                eduQuestionBank.setAccuracy(0);
            }
        }
        // 总正确率
        Integer allAccuracy = eduQuestionExerciseDao.getAllAccuracy(labourId).multiply(new BigDecimal(100)).intValue();
        map.put("allAccuracy",allAccuracy);
        map.put("list",eduQuestionBanks);
        map.put("totalWrong", totalWrong);
        return map;
    }

    /**
     * 添加练习,如果已经搭过，就不需要添加答题记录
     * @return
     */
    public Integer addEduQuestionExercise(Integer questionBankId, Integer questionId ,
                                          String myAnswer, Integer labourId){

        if(null == questionBankId || null == questionId || StringUtils.isBlank(myAnswer)){
            logger.info("..addEduQuestionExercise.......参数不正确");
            return 0;
        }

        EduQuestionExercise oldQuestionExercise = eduQuestionExerciseDao.queryQuestionExercise(
                labourId, questionId, questionBankId);
        if(null == oldQuestionExercise){
            EduQuestionExercise eduQuestionExercise = new EduQuestionExercise();
            //获取正确答案，需要参数questionId
            String correctAnswer = getRightAnswer(questionId);

            eduQuestionExercise.setAnswerStatus(correctAnswer.equals(myAnswer)?// 答案是否正确标识
                    EnumCorrectFlag.RIGHT.code : EnumCorrectFlag.WRONG.code);
            eduQuestionExercise.setLabourId(labourId);// 工人id
            eduQuestionExercise.setQuestionBankId(questionBankId); // 题库id
            eduQuestionExercise.setQuestionId(questionId); // 题目id
            eduQuestionExercise.setMyAnswer(myAnswer); // 答案

            return eduQuestionExerciseDao.addEduQuestionExercise(eduQuestionExercise);
        }else {
            logger.info("答题记录已存在。。。。。。。。");
        }
        return 0;
    }

    /**
     * 获取正确答案(多选题，正确答案以逗号拼接)
     * @param questionId
     * @return
     */
    public String getRightAnswer(Integer questionId){
        return eduQuestionExerciseDao.getRightAnswer(questionId);
    }


    /**
     *  章节练习，上一题、下一题
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:13
     */
    public Map queryNextOrPrevQuestion(Integer questionBankId, Integer questionId, String nextPrevFlag, String myAnswer, Integer labourId) {

        /**
         * 练习时答题，会在练习表中新增记录
         * @return
         */
        addEduQuestionExercise(questionBankId, questionId, myAnswer, labourId);

        // 查询上一题 / 下一题
        if(null == questionId){
            nextPrevFlag = SystemConstant.NextPrevFlag.NEXT_FLAG;
            questionId = -1;
        }
        EduQuestion nextPrevQuestion = eduQuestionExerciseDao.queryNextOrPrevQuestion(questionBankId, questionId, nextPrevFlag);
        if(null == nextPrevQuestion){
            logger.info("没有符合条件的题目");
            return null ;
        }
        // 设置题目的选项
        List<EduQuestionAnswer> answerList = questionDao.queryEduQuestionAnswer(nextPrevQuestion.getId());
        nextPrevQuestion.setAnswers(answerList);

        // 如果该题已选过答案、设置已选答案
        EduQuestionExercise  questionExercise =  eduQuestionExerciseDao.queryQuestionExercise(labourId, nextPrevQuestion.getId(),
                nextPrevQuestion.getQuestionBankId());
        ;
        if( null != questionExercise && StringUtils.isNotBlank(questionExercise.getMyAnswer())){
            String nextMyAnswer = questionExercise.getMyAnswer();
            nextPrevQuestion.setMyAnswer(nextMyAnswer);
            nextPrevQuestion.setMyAnswerList(Arrays.asList(nextMyAnswer.split(COMMA)));
        }

        // 设置，是否已收藏标识
        boolean isExsit = collectionDao.isExisting(labourId, SystemConstant.CollectionType.QUESTION, nextPrevQuestion.getId());
        nextPrevQuestion.setCollectFlag(isExsit);

        Map<String,Object> result = new HashMap<>();

        //  题库已答统计
        Integer correctCount = 0;
        Integer wrongCount = 0;
        List<Map<String,Object>> answerdQuestions = eduQuestionExerciseDao.countAnswerdQuestion(labourId, questionBankId);
        if(null != answerdQuestions && answerdQuestions.size() > 0){
            Map answerdMap = answerdQuestions.get(0);
            Integer answerdCount = MapUtils.getInteger(answerdMap, "answerdCount");
            correctCount = MapUtils.getInteger(answerdMap, "correctCount");
            wrongCount = answerdCount - correctCount;
        }


        // 下一题/ 上一题的题目
        result.put("question", nextPrevQuestion);
        result.put("correctCount",correctCount);// 正确个数
        result.put("wrongCount",wrongCount);// 错误个数

        return result;
    }

    /**
     *  手机端 -  我的错题，上一题、下一题
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:06
     */
    public EduQuestion queryNextOrPrevQuestionForWrong(Integer questionId, String nextPrevFlag, Integer labourId) {

        if(null == questionId){
            nextPrevFlag = SystemConstant.NextPrevFlag.NEXT_FLAG;
            questionId = -1;
        }
        EduQuestion nextPrevQuestion = eduQuestionExerciseDao.queryNextOrPrevQuestionForWrong(questionId, nextPrevFlag, labourId);
        if(null == nextPrevQuestion){
            logger.info("没有符合条件的题目");
            return null ;
        }
        // 设置题目的选项
        List<EduQuestionAnswer> answerList = questionDao.queryEduQuestionAnswer(nextPrevQuestion.getId());
        nextPrevQuestion.setAnswers(answerList);

        // 如果该题已选过答案、设置已选答案
        EduQuestionExercise  questionExercise =  eduQuestionExerciseDao.queryQuestionExercise(labourId, nextPrevQuestion.getId(),
                nextPrevQuestion.getQuestionBankId());
        String nextMyAnswer = questionExercise.getMyAnswer();
        if( null != questionExercise && StringUtils.isNotBlank(nextMyAnswer)){
            nextPrevQuestion.setMyAnswer(nextMyAnswer);
            nextPrevQuestion.setMyAnswerList(Arrays.asList(nextMyAnswer.split(COMMA)));
        }
        return nextPrevQuestion;
    }
}
