/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.view.picArticle.PicArtiFormController', {
    extend: 'Admin.view.BaseViewController',

    alias: 'controller.picarticleform',


    /**菜单编辑  添加/修改
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    editPicArticle: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var  successMsg, requestUrl,
            store = window.store,
            contentClassify = store.proxy.extraParams.contentClassify;
        form.form.findField('contentClassify').setValue(contentClassify);
        if (window.action === 'create') {
            form.form.findField('id').setDisabled(true);
            requestUrl = Common.Config.requestPath('PicArticle', 'addPicArticle');
            successMsg = '添加成功';
        } else if (window.action === 'update') {
            requestUrl = Common.Config.requestPath('PicArticle', 'updateHTML');
            successMsg = '修改成功';
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        Common.util.Util.doAjax({
            url: requestUrl,
            params: form.getValues()
        }, function (res) {
            store.reload({
                contentClassify:contentClassify
            });
            me.closePicArticleWindow();
            Common.util.Util.toast(successMsg);
        });
    },

    editPicArticlePdf: function () {
        var me = this,
            window = me.getView(),
            form = window.down('form');
        if (!form.isValid()) {
            return false;
        }
        var  successMsg, requestUrl,
            store = window.store,
            contentClassify = store.proxy.extraParams.contentClassify;
        form.form.findField('contentClassify').setValue(contentClassify);
        if (window.action === 'create') {
            form.form.findField('id').setDisabled(true);
            requestUrl = Common.Config.requestPath('PicArticle', 'addPdfPicArticle');
            successMsg = '添加成功';
        } else if (window.action === 'update') {
            requestUrl = Common.Config.requestPath('PicArticle', 'updatePDF');
            successMsg = '修改成功';
        } else {
            Ext.Msg.alert('温馨提示', '表单操作错误，请联系管理员');
            return;
        }
        form.submit({
            url: requestUrl,
            waitMsg: '正在上传，请稍等...',
            success: function(fp, o) {
                Common.util.Util.toast(successMsg);
                me.closePicArticleWindow();
            },
            failure: function(form, action) {
                Common.util.Util.toast(successMsg);
                store.reload();
                me.closePicArticleWindow();
            }
        })
    },

    /**关闭 menuWindow
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    closePicArticleWindow: function () {
        this.getView().close();
    }
});