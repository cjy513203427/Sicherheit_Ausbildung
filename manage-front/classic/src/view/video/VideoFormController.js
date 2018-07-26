/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.view.video.VideoFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.videoform',


    /**编辑  添加|修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editVedioInfo: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var successMsg, requestUrl,store = window.store;

        if (window.action === 'create') {
            form.form.findField('id').setDisabled(true);
            requestUrl = Common.Config.requestPath('Video', 'insert');
            successMsg = '添加成功';
        } else if (window.action === 'update') {
            requestUrl = Common.Config.requestPath('Video', 'update');
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theVideo;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        if (form.isValid()) {
            form.submit({
                url:requestUrl,
                waitMsg: '正在上传附件...',
                success: function (fp, o) {
                    store.loadPage(1);
                    Common.util.Util.toast(successMsg);
                    window.close();
                },
                failure: function (form, action) {
                    store.loadPage(1);
                    Common.util.Util.toast(successMsg);
                    window.close();
                }
            });
        }
    },

    /**关闭 Window
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeFormWindow: function () {
        this.getView().close();
    }

});