/**
 * @author cjy
 * @Date 2018/6/4 14:59.
 */

Ext.define('Admin.view.eduQuestion.EduQuestionFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.eduQuestionform',

    /** 字典编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editEduQuestion: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var formValues = form.getValues();
        var store = window.store, successMsg,submitUrl;
        if (window.action === 'create') {
            var reg =  /^\d+$/;
            if (!reg.test(form.form.findField('hiddenEduQuestionId').getValue())) {
                form.form.findField('hiddenEduQuestionId').setDisabled(true);
            }
            formValues = form.getValues();
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('EduQuestion', 'addEduQuestion');
        } else if(window.action === 'update') {
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theEduQuestion;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
            submitUrl = Common.Config.requestPath('EduQuestion', 'modifyEduQuestion');
        } else if(window.action === 'delete') {
            successMsg = '删除成功';
            submitUrl = Common.Config.requestPath('EduQuestion', 'deleteEduQuestion');
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }

        Common.util.Util.doAjax({
            url: submitUrl,
            params: {
                id:formValues['id'],
                title:formValues['title'],
                questionType:formValues['questionType'],
                createUserId:formValues['createUserId'],
                answerAnalysis:formValues['answerAnalysis'],
                questionBankId:window.questionBankId
            },
            callback: function() {
                store.reload();
            }
        }, function () {
            Common.util.Util.toast(successMsg);
            me.closeEduQuestionWindow();
        });

    },

    /**关闭 userWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeEduQuestionWindow: function () {
        this.getView().close();
    }
});