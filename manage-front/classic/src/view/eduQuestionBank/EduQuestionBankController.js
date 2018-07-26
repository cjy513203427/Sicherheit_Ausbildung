/**
 * @author cjy
 * @Date 2018/6/2 15:06.
 */
Ext.define('Admin.view.eduQuestionBank.EduQuestionBankController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.eduQuestionBank',

    requires: [
        'Admin.view.eduQuestionBank.EduQuestionBankForm'
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

    createEduQuestionBank: function () {
        Ext.create('Admin.view.eduQuestionBank.EduQuestionBankForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    modifyEduQuestionBank: function (grid,rowIndex,colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestionBank.EduQuestionBankForm', {
            action: 'update',
            title : '题库修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theEduQuestionBank: {
                        type: 'eduQuestionBank.EduQuestionBank',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
        }).show();
    },

    deleteEduQuestionBank: function (grid,rowIndex) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除该题库吗？"
            , function (button, text) {
                if (button == 'yes') {
                    var rec = grid.getStore().getAt(rowIndex);
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('EduQuestionBank', 'deleteEduQuestionBank'),
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



    showEduQuestionBank: function (grid,rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.eduQuestion.EduQuestion', {
            questionBankId: rec.get('id'),
        }).show();
    },
    /** grid 渲染之前 初始化操作
     * add beforeload listener to grid store
     * @param {Ext.Component} component
     */
    gridBeforeRender: function () {
        var me = this,
            form = me.lookupReference('form'),
            grid = me.lookupReference('grid');

        grid.getStore().addListener({
            'beforeload': function (store) {
                grid.getScrollTarget().scrollTo(0, 0);      //每次加载之前 scrolly to 0
                Ext.apply(store.getProxy().extraParams, form.getValues());
                return true;
            },
            'load': function (store) {
                store.getProxy().extraParams = {};
            },
            'beginupdate': function () {
                grid.setHeight(grid.getHeight());   //设置grid height，如果不这样则一页显示数据多了则不显示scrolly  估计是extjs6的bug
                return true;
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