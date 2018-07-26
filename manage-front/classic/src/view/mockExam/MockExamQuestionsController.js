/**
 * Created by CC on 2016/9/5.
 */
Ext.define('Admin.view.mockExam.MockExamQuestionsController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.mockExamQuestions',

    requires: [
        'Admin.view.eduQuestion.EduQuestionSelect'
    ],
    search: function () {
        var me = this,
            grid = me.lookupReference('grid');
        grid.getStore().loadPage(1);
    },
    /** grid 渲染之前 初始化操作
     * add beforeload listener to grid store
     * @param {Ext.Component} component
     */
    gridBeforeRender: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            view = me.getView();
        grid.getStore().addListener({
            'beforeload': function (store) {
                store.getProxy().extraParams.id = view.mockExamId;
                var form = Ext.getCmp('mockExamQuestionsForm');
                Ext.apply(store.getProxy().extraParams, form.getValues(false, true));
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            }
        });
    },
    addQuestions: function () {
        var grid = this.lookupReference('grid');
        var mockExamId = this.getView().mockExamId;
        var selectWindow = Ext.create('Admin.view.eduQuestion.EduQuestionSelect', {
            title: '题库',
            examId: mockExamId,
            examType: 1,
            callback: function () {
                grid.getStore().reload();
                selectWindow.close();
            }
        });
        selectWindow.show();
    },

    /**
     * 删除模拟试卷的题目
     */
    deleteMockDownQuestion: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            examId = me.getView().mockExamId,
            questionIds = [], questionIdString;
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
            url: Common.Config.requestPath('MockExam', 'deleteMockDownQuestion'),
            method: 'post',
            params: {
                examId: examId,
                questionIds: questionIdString
            }
        }, function () {
            grid.getStore().reload();
            me.getView().callback;
        });
    }
});