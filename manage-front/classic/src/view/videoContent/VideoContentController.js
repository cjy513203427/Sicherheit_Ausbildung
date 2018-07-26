/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.videoContent.VideoContentController', {
    extend: 'Admin.view.BaseViewController',
    alias: 'controller.videocontent',

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
     * @Description 添加视频
     * @author Joanne
     * @create 2018/6/14 17:35
     */
    addContent: function () {
        Ext.create('Admin.view.videoContent.ContentForm', {
            action: 'create',
            title:'添加视频',
            store: this.lookupReference('grid').getStore()
        }).show();
    },

    /**
     * @Description 修改视频
     * @author Joanne
     * @create 2018/6/14 17:35
     */
    modifyContent: function (grid,view, rowindex, colindex, item, record) {
        Ext.create('Admin.view.videoContent.ContentForm', {
            action: 'update',
            title : '视频信息修改',
            store: grid.getStore(),
            viewModel: {
                links: {
                    theContent: {
                        type: 'video.VideoContent',
                        create: record.data
                    }
                },
                data: {
                    roleComboQueryMode: 'local',
                    disabled:true
                }
            }
        }).show();
    },

    /**
     * @Description 删除视频
     * @author Joanne
     * @create 2018/6/14 17:35
     */
    delContent: function (grid,view, rowindex, colindex, item, record) {
        Ext.Msg.confirm(
            "请确认"
            , "确定删除吗？"
            , function (button, text) {
                if (button == 'yes') {
                    this.confirmDelVideo('delete', {ids: record.get('id')});
                }
            }, this);
    },

    /**
     * @Description 批量删除视频
     * @author Joanne
     * @create 2018/6/14 17:40
     */
    batchDelVideo: function (btn, event) {
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
                    this.confirmDelVideo('delete', {ids: idString});
                }
            }, this);
    },

    confirmDelVideo:function (action, params) {
        var me = this;
        Common.util.Util.doAjax({
            url: Common.Config.requestPath('Video', action),
            method: 'post',
            params: params
        }, function (data) {
            me.search();
        });
    },

    /**
     * @Description 查看视频题目
     * @author Joanne
     * @create 2018/6/28 10:12
     */
    scanQuestion:function (grid,view, rowindex, colindex, item, record) {
        Ext.create('Admin.view.videoContent.ContentQuestion', {
            courseId: record.get('id')
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