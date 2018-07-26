
/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.view.picArtiClassify.ClassifyFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.Classifyform',


    /**分类编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editClassify: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var classifyTree = window.classifyTree, successMsg, requestUrl, appendNode;
        var formValues = form.getValues(),
            paramForm = form.getValues(),
            selNode = classifyTree.selection || classifyTree.getRootNode();
        if (window.action === 'create') {
            requestUrl = Common.Config.requestPath('PicArticle', 'addClassify');
            successMsg = '添加成功';
            Ext.apply(formValues, {
                leaf: true,
                children: []
            });
            appendNode = selNode.appendChild(formValues);
            delete paramForm.id;
        } else if (window.action === 'update') {
            requestUrl = Common.Config.requestPath('PicArticle', 'updateClassify');
            successMsg = '修改成功';
            var record = me.getViewModel().getData().theClassify;
            if (!record.dirty) {
                Common.util.Util.toast('没有修改操作');
                return;
            }
            selNode.set(formValues);
            var tmpSubmitValues={};
            for(var i in record.modified) {
                tmpSubmitValues[i] = formValues[i];
            }
            paramForm = Ext.apply(tmpSubmitValues, {
                id: record.get('id')
            });
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }

        Common.util.Util.doAjax({
            url: requestUrl,
            params: paramForm
        }, function (res) {
            if (window.action === 'create') {
                appendNode.set('id', res.data);
                window.delClsfybtn.setDisabled(true);
            }
            me.closeClsfyWindow();
            Common.util.Util.toast(successMsg);
        });
    },

    /**关闭 menuWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closeClsfyWindow: function () {
        this.getView().close();
    }

});