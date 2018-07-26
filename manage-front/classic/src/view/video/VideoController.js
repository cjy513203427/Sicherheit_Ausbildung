/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.video.VideoController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.video',

    search: function () {
        var me = this,
            grid = me.lookupReference('grid'),
            form = me.lookupReference('form');
        if (!form.isValid()) {
            return false;
        }
        grid.getStore().loadPage(1);
    },

    addVideoInfo: function () {
        Ext.create('Admin.view.video.VideoForm', {
            action: 'create',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    modifyVideoInfo: function (grid,view, rowindex, colindex, item, record) {
        Ext.create('Admin.view.video.VideoForm', {
            action: 'update',
            title : '视频集锦修改',
            store: grid.getStore(),
            record:record,
            viewModel: {
                links: {
                    theVideo: {
                        type: 'video.Video',
                        create: record.data
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

    delVideoColletion:function(grid,view, rowindex, colindex, item, record){
        Ext.Msg.confirm(
            "请确认"
            , "确定删除吗？"
            , function (button, text) {
                if (button == 'yes') {
                    this.confirmDelVideoCollection('deleteVideoCollection', {ids: record.get('id')});
                }
            }, this);
    },

    batchDelVideoCollection:function(){
        var me = this,
            window = me.getView(),
            grid = window.down('grid'),
            selMod = grid.getSelectionModel(),
            records = selMod.getSelection(),
            ids = [],idString;
        if (records == undefined || records.length <= 0) {
            Ext.Msg.alert('提醒', '请勾选相关记录！');
            return;
        }
        Ext.Msg.confirm(
            '请确认'
            , '确定删除吗？'
            , function (button, text) {
                if (button == 'yes') {
                    for (var i = 0; i < records.length; i++) {
                        ids.push(records[i].get('id'));
                    }
                    idString = ids.join(',');
                    this.confirmDelVideoCollection('deleteVideoCollection', {ids: idString});
                }
            }, this);
    },

    confirmDelVideoCollection:function (action, params) {
        var me = this;
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('Video', action),
            method: 'post',
            params: params
        }, function (data) {
            me.search();
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