Ext.define('Admin.view.buildCompany.BuildCompanyController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.buildCompany',

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
                Ext.apply(store.getProxy().extraParams, form.getValues(false, true));
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

    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    },

    /**
     * 新增建筑公司
     */
    createBuildCompany: function () {
        Ext.create('Admin.view.buildCompany.BuildCompanyForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    /**
     * 修改建筑公司
     * @param grid
     * @param rowIndex
     */
    updateBuildCompany: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.buildCompany.BuildCompanyForm', {
            action: 'update',
            title: '修改建筑公司',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theBuildCompany: {
                        type: 'buildCompany.BuildCompany',
                        create: rec.data
                    }
                },
                data: {}
            }
        }).show();
    },

    /**
     * 建筑公司启用和禁用
     * @param grid
     * @param rowIndex
     */
    enOrDisBuildCompany: function (grid, rowIndex) {
        var rec = grid.getStore().getAt(rowIndex),
            rowStatus = rec.get('status'),
            rowId = rec.get('id')
        var str;
        if (rowStatus == '0') {
            str = "启用";
        } else {
            str = "禁用"
        }
        Ext.Msg.confirm(
            "请确认"
            , "确定" + str + "吗？"
            , function (button) {
                if (button == 'yes') {
                    Common.util.Util.doAjax({
                        url: Common.Config.requestPath('BuildCompany', 'enOrDisBuildCompany'),
                        method: 'post',
                        params: {id: rowId, status: rowStatus}
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

})