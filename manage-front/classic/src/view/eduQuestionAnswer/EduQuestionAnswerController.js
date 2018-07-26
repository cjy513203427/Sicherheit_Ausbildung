/**
 * @author cjy
 * @Date 2018/6/6 14:12.
 */
Ext.define('Admin.view.eduQuestionAnswer.EduQuestionAnswerController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.eduQuestionAnswer',


    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    loadStore:function () {
        var me = this,
            grid = me.lookupReference('grid'),
            questionId=me.getView().questionId;

        grid.getStore().addListener({
            'beforeload': function (store) {
                var form=Ext.getCmp('eduQuestionAnswerForm');
                store.getProxy().extraParams.questionId=questionId;
                Ext.apply(store.getProxy().extraParams, form.getValues());
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            }
        });
    },

    createEduQuestionAnswer: function () {
        Ext.create('Admin.view.eduQuestionAnswer.EduQuestionAnswerForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore(),
            questionId: this.getView().questionId
        }).show();
    },

    modifyEduQuestionAnswer: function (grid,rowIndex,colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestionAnswer.EduQuestionAnswerForm', {
            action: 'update',
            title : '答案修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theEduQuestionAnswer: {
                        type: 'eduQuestionAnswer.EduQuestionAnswer',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
        }).show();
    },

    deleteEduQuestionAnswer: function (grid,rowIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除该答案选项吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex);
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('EduQuestionAnswer', 'deleteEduQuestionAnswer'),
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


    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    }
});