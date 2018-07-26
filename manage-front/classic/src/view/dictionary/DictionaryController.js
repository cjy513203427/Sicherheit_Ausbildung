/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.dictionary.DictionaryController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.dictionary',

    requires: [
        'Admin.view.dictionary.DictionaryForm'
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

    createDictionary: function () {
        Ext.create('Admin.view.dictionary.DictionaryForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    modifyDictionary: function (grid,rowIndex,colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.dictionary.DictionaryForm', {
            action: 'update',
            title : '字典修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theDictionary: {
                        type: 'dictionary.Dictionary',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
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

    /**
     * 字典修改
     */
    updateDictionary: function (grid, rowIndex, colIndex) {
        var rec = grid.getStore().getAt(rowIndex);
        Ext.create('Admin.view.dictionary.DictionaryForm', {
            action: 'update',
            title:'字典修改',
            store: this.lookupReference('grid').getStore(),
            viewModel: {
                links: {
                    theDictionary: {
                        type: 'dictionary.Dictionary',
                        create: rec.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local'
                }
            }
        }).show();
    },

    /** 清除 查询 条件
     * @param {Ext.button.Button} component
     * @param {Event} e
     */
    reset: function () {
        this.lookupReference('form').reset();
    }
});