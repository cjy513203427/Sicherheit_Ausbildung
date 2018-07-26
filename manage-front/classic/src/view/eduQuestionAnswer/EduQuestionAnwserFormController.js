/**
 * @author cjy
 * @Date 2018/6/6 15:58.
 */
Ext.define('Admin.view.eduQuestionAnswer.EduQuestionAnswerFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.eduQuestionAnswerForm',

    /** 字典编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editEduQuestionAnswer: function () {
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
            if (!reg.test(form.form.findField('hiddenEduQuestionAnswerId').getValue())) {
                form.form.findField('hiddenEduQuestionAnswerId').setDisabled(true);
            }
            //需要获取到将id置空的formValues
            formValues = form.getValues();
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('EduQuestionAnswer', 'addEduQuestionAnswer');
        } else if(window.action === 'update') {
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theEduQuestionAnswer;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
            submitUrl = Common.Config.requestPath('EduQuestionAnswer', 'modifyEduQuestionAnswer');
        } else if(window.action === 'delete') {
            successMsg = '删除成功';
            submitUrl = Common.Config.requestPath('EduQuestionAnswer', 'deleteEduQuestionAnswer');
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        Common.util.Util.doAjax({
            url: submitUrl,
            params: {
                id:formValues['id'],
                questionId:formValues['questionId'],
                optionCode:formValues['optionCode'],
                correctFlag:formValues['correctFlag'],
                optionContent:formValues['optionContent'],
                questionId:window.questionId
            },
            callback: function() {
                store.reload();
            }
        }, function () {
            Common.util.Util.toast(successMsg);
            me.closeEduQuestionAnswerWindow();
        });
    },

    /**关闭 userWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeEduQuestionAnswerWindow: function () {
        this.getView().close();
    }
});