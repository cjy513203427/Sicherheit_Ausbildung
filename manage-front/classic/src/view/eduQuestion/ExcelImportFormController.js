/**
 * @author cjy
 * @Date 2018/6/5 9:47.
 */
Ext.define('Admin.view.eduQuestion.ExcelImportFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.excelImportForm',



    /**Excel上传
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editExcel: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var store = window.store, successMsg,submitUrl;

        var ff = Ext.getCmp('excelPath')
        var ffv = ff.getValue('filename')
        var index = ffv.lastIndexOf('\\')
        var ffv = Ext.util.Base64.encode(ffv.substring(index+1,ffv.length))
        Ext.getCmp('fileName').setValue(ffv);
        if (window.action === 'uploadExcel') {
            successMsg = '上传成功';
            submitUrl=Common.Config.requestPath('EduQuestion', 'importExcelForEduQuestion');
            form.form.findField('questionBankId').setValue(this.getView().questionBankId);
        } else if (window.action == 'uploadVideoQusExcel'){
            successMsg = '上传成功';
            submitUrl=Common.Config.requestPath('EduQuestion', 'importContentQuestion');
            form.form.findField('courseId').setValue(this.getView().courseId);
        }else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        //var questionBankId = store.getAt(1).get('questionBankId')
        form.submit({
            //method : 'get',默认是post，指定没用
            url: submitUrl,
            waitMsg: '正在上传Excel并导入，请稍等...',
            success: function(fp, o) {
                Common.util.Util.toast(successMsg);
                me.getView().close();
            },
            failure: function(form, action) {
                Common.util.Util.toast(successMsg);
                store.reload()
                me.getView().close();
            }
        });
    },

    /**关闭 userWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeWindow: function () {
        this.getView().close();
    }

});