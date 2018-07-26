/**
 * @author cjy
 * @Date 2018/6/4 14:59.
 */

Ext.define('Admin.view.mockExam.MockExamFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.mockExamForm',

    /**模拟试卷编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editorMockExam: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        // 解决ID传值问题（方法一）
        /*var reg = /^\d+$/;
         if (!reg.test(form.form.findField('hiddenSiteId').getValue())) {
         form.form.findField('hiddenSiteId').setDisabled(true);
         }*/
        // 解决ID传值问题（方法二）
        var id = form.getValues().id;
        if (id.indexOf("mockExam") == 0) {
            Ext.getCmp('hiddenMockExamId').setValue(0);
        }
        // 方法二结束
        var store = window.store, successMsg, submitUrl;
        if (!form.isValid()) {
            return false;
        }
        if (window.action === 'create') {
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('MockExam', 'create');
        } else if (window.action === 'update') {
            successMsg = '修改成功';
            submitUrl = Common.Config.requestPath('MockExam', 'update');
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        form.submit({
            url: submitUrl,
            waitMsg: '请稍等...',
            success: function (fp, o) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            },
            failure: function (form, action) {
                store.loadPage(1);
                Common.util.Util.toast(successMsg);
                me.getView().close();
            }
        });
    },

    /**关闭 MockExamWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeMockExamWindow: function () {
        this.getView().close();
    }
});