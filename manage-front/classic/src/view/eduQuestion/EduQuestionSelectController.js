/**
 * @author cjy
 * @Date 2018/6/2 18:27.
 */
Ext.define('Admin.view.eduQuestion.EduQuestionSelectController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.eduQuestionSelect',



    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    loadStore:function () {
        var me = this,
            grid = me.lookupReference('grid'),
            examId=me.getView().examId,
            examType=me.getView().examType;

        grid.getStore().addListener({
            'beforeload': function (store) {
                var form=Ext.getCmp('notSelectEduQuestionForm');
                store.getProxy().extraParams.examId=examId;
                store.getProxy().extraParams.examType=examType;
                Ext.apply(store.getProxy().extraParams, form.getValues());
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            }
        });
    },
    addQuestionsToExam:function () {
        var me = this,
            grid=me.lookupReference('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            examId=me.getView().examId,
            examType=me.getView().examType,
            questionIds=[],questionIdString;
        if (records == undefined || records.length <= 0) {
            Ext.Msg.alert('提醒', '请勾选相关记录！');
            return;
        }

        for (var i = 0; i < records.length; i++) {
            questionIds.push(records[i].get('questionId'));
        }
        //将数组以","连接为字符串
        questionIdString = questionIds.join(',');
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('MockExam', 'addExamQuestions'),
            method: 'post',
            params: {
                id:examId,
                examType:examType,
                questionIds:questionIdString
            }
        }, function () {
            grid.getStore().reload();
            me.getView().callback;
        });
    },
    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    }
});