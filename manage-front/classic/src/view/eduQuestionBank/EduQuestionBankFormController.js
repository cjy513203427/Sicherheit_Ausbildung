/**
 * @author cjy
 * @Date 2018/6/2 15:06.
 */
Ext.define('Admin.view.eduQuestionBank.EduQuestionBankFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.eduQuestionBankForm',

    /** 字典编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editEduQuestionBank: function () {
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
            if (!reg.test(form.form.findField('hiddenEduQuestionBankId').getValue())) {
                form.form.findField('hiddenEduQuestionBankId').setDisabled(true);
            }
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('EduQuestionBank', 'addEduQuestionBank');
        } else if(window.action === 'update') {
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theEduQuestionBank;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
            submitUrl = Common.Config.requestPath('EduQuestionBank', 'modifyEduQuestionBank');
        } else if(window.action === 'delete') {
            successMsg = '删除成功';
            submitUrl = Common.Config.requestPath('EduQuestionBank', 'deleteEduQuestionBank');
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        form.submit({
            url: submitUrl,
            waitMsg: '请稍等...',
            success: function(fp, o) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            },
            failure: function(form, action) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            }
        });
    },

    /**关闭 userWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeEduQuestionBankWindow: function () {
        this.getView().close();
    }
});