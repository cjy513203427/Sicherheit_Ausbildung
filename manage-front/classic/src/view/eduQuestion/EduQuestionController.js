/**
 * @author cjy
 * @Date 2018/6/2 18:27.
 */
Ext.define('Admin.view.eduQuestion.EduQuestionController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.eduQuestion',

    requires: [
    ],

    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    importExcel:function(){
        Ext.create('Admin.view.eduQuestion.ExcelImportForm', {
            action: 'uploadExcel',
            store: this.lookupReference('grid').getStore(),
            questionBankId: this.getView().questionBankId
        }).show();
    },

    createEduQuestion: function () {
        Ext.create('Admin.view.eduQuestion.EduQuestionForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore(),
            questionBankId: this.getView().questionBankId
        }).show();
    },

    modifyEduQuestion: function (grid,rowIndex,colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestion.EduQuestionForm', {
            action: 'update',
            title : '题目修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theEduQuestion: {
                        type: 'eduQuestion.EduQuestion',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
        }).show();
    },

    showEduQuestionAnswer: function (grid,rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestionAnswer.EduQuestionAnswer', {
            questionId: rec.get('id'),
        }).show();
    },

    deleteEduQuestion: function (grid,rowIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除该题目吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex);
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('EduQuestion', 'deleteEduQuestion'),
                        method: 'post',
                        params: {
                            id:rec.get('id')
                        }
                    }, function (data) {
                        grid.getStore().reload();
                        Common.util.Util.toast("删除成功");
                    });
                }
            }, this);

    },


    loadStore:function () {
        var me = this,
            grid = me.lookupReference('grid'),
            questionBankId=me.getView().questionBankId;

        grid.getStore().addListener({
            'beforeload': function (store) {
                var form=Ext.getCmp('eduQuestionForm');
                store.getProxy().extraParams.questionBankId=questionBankId;
                Ext.apply(store.getProxy().extraParams, form.getValues());
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            }
        });
    },

    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    }
});