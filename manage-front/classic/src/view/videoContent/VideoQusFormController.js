/**
 * Created by IntelliJ IDEA.
 * User: Joanne
 * Date: 2018/6/29
 * Time: 9:56
 */
Ext.define('Admin.view.videoContent.VideoQusFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.videoQusform',

    /** 字典编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editVdoQuestion: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var formValues = form.getValues();
        var store = window.store, successMsg,submitUrl,courseId = window.courseId;
        form.form.findField('courseId').setValue(courseId);
        if (window.action === 'create') {
            var reg =  /^\d+$/;
            if (!reg.test(form.form.findField('id').getValue())) {
                form.form.findField('id').setDisabled(true);
            }
            formValues = form.getValues();
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('EduQuestion', 'createVideoQus');
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

        if (form.isValid()) {
            form.submit({
                url:submitUrl,
                waitMsg: '正在上传附件...',
                success: function (fp, o) {
                    store.loadPage(1);
                    Common.util.Util.toast(successMsg);
                    me.closeVdoQuestionWindow();
                },
                failure: function (form, action) {
                    store.loadPage(1);
                    Common.util.Util.toast(successMsg);
                    me.closeVdoQuestionWindow();
                }
            });
        }
        // Common.util.Util.doAjax({
        //     url: submitUrl,
        //     params: {
        //         id:formValues['id'],
        //         title:formValues['title'],
        //         questionType:formValues['questionType'],
        //         createUserId:formValues['createUserId'],
        //         answerAnalysis:formValues['answerAnalysis'],
        //         questionBankId:window.questionBankId
        //     },
        //     callback: function() {
        //         store.reload();
        //     }
        // }, function () {
        //     Common.util.Util.toast(successMsg);
        //     me.closeEduQuestionWindow();
        // });

    },

    /**关闭 userWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeVdoQuestionWindow: function () {
        this.getView().close();
    }
});
