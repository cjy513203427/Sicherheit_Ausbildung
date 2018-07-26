/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.view.videoContent.ContentFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.contentform',


    /**编辑  添加|修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editContent: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');

        var successMsg, requestUrl,store = window.store;

        if (window.action === 'create') {
            var file = form.form.findField('file');
            if(file.value == null || file.value == ""){
                Ext.Msg.alert('温馨提示', '请选择文件');
                return false ;
            }
            if (!form.isValid()) {
                return false;
            }
            form.form.findField('id').setDisabled(true);
            requestUrl = Common.Config.requestPath('Video', 'insertContent');
            successMsg = '添加成功';
        } else if (window.action === 'update') {
            requestUrl = Common.Config.requestPath('Video', 'updateContent');
            successMsg = '修改成功';
            // var record = me.getViewModel().getData().theContent;
            // if (!record.dirty) {
            //     Common.util.Util.toast('没有修改操作');
            //     return;
            // }
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

    // comboBeforeRender:function () {
    //     var me = this,
    //         chapterCombo = Ext.getCmp('chapterF').form.findField('videoChapterId');
    //     // var provinceText= me.getViewModel().data.theCustomer.data.province;
    //     cityCombo.getStore().addListener({
    //         'beforeload': function (store) {
    //             //每次加载之前 scrolly to 0
    //             var provinceCombo=Ext.getCmp('customerF').form.findField('province'),
    //                 provinceComboValue=provinceCombo.getValue();
    //             if(!provinceComboValue){
    //                 provinceComboValue=provinceText;
    //             }
    //             Ext.apply(store.getProxy().extraParams, {provinceText:provinceComboValue});
    //             return true;
    //         },
    //         'load': function (store) {
    //             store.getProxy().extraParams = {};
    //         }
    //     });
    //     cityCombo.getStore().load();
    // },

    /**关闭 Window
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeContentWindow: function () {
        this.getView().close();
    }

});