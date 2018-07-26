/**
 * Created by CC on 2016/9/5.
 */
Ext.define('Admin.view.dictionary.DictionaryFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.dictionaryform',

    /** 字典编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editDictionary: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var formValues = form.getValues();
        var id = formValues.id ;
        if(id == '' ||id == 'dictionary.Dictionary-2' ){
            form.form.findField('id').setValue(0);
        }
    //    formValues = form.getValues();
        var store = window.store, successMsg,submitUrl;
        if (window.action === 'create') {
            successMsg = '添加成功';
            submitUrl = Common.Config.requestPath('Dictionary', 'create');
        } else if(window.action === 'update') {
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theDictionary;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
            submitUrl = Common.Config.requestPath('Dictionary', 'update');
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
    closeDictionaryWindow: function () {
         this.getView().close();
    }
});