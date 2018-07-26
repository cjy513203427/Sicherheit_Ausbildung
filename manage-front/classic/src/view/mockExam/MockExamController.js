/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.mockExam.MockExamController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.mockExam',

    /*requires: [
     'Admin.view.mockExam.MockExamForm'
     ],*/

    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    /**
     * 添加模拟考试
     */
    createMockExam: function () {
        Ext.create('Admin.view.mockExam.MockExamForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
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
    },
    editExaminationQuestions: function (grid, rowIndex, colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.mockExam.MockExamQuestions', {
            title: rec.get('examName') + '题库',
            mockExamId: rec.get('id')
        }).show();
    },

    /**
     * 模拟考试修改
     * @param grid
     * @param rowIndex
     */
    modifyMockExam: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.mockExam.MockExamForm', {
            action: 'update',
            title: '修改模拟考试',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theMockExam: {
                        type: 'mockExam.MockExam',
                        create: rec.data
                    }
                },
                data: {}
            }
        }).show();
    },

    /**
     * 删除模拟考试
     * @param grid
     * @param rowIndex
     */
    deleteExamMock: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex),
            rowId = rec.get('id');
        Ext.Msg.confirm(
            "请确认"
            , "确定删除试卷吗？"
            , function (button) {
                if (button == 'yes') {
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('MockExam', 'deleteExamMock'),
                        method: 'post',
                        params: {examId: rowId}
                    }, function (data) {
                        console.log(data);
                        if (data.code == '0') {
                            Common.util.Util.toast("操作成功！");
                            grid.getStore().reload();
                        }
                    });
                }
            }, this
        );
    },

});