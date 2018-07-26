/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.videoChapter.VideoChapter', {
    extend: 'Ext.Panel',
    xtype: 'videoChapter',
    title: '视频管理',

    requires: [
        'Admin.view.videoChapter.VideoChapterController',
        'Ext.button.Button'
    ],
    controller: 'videochapter',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'form',
        reference: 'form',
        defaultButton: 'btn_search',
        layout: 'column',
        defaults: {
            labelAlign: 'right'
        },
        style: {
            margin: '12px 0px 0px -28px'
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: '集锦名称',
            name: 'eduVideoName'
        },{
            xtype: 'textfield',
            fieldLabel: '章节名称',
            name: 'chapterName'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'video.VideoChapter',
        selModel: {
            selType: 'checkboxmodel'
        },
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '视频集锦名称',
            dataIndex: 'eduVideoName',
            width : 250
        },{
            text: '章节名称',
            dataIndex: 'chapterName',
            width : 250
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                iconCls:'x-fa fa-pencil-square-o',
                handler: 'modifyChapter',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("章节修改")) {
                        return true;
                    }
                    return false;
                }
            },'-',{
                tooltip: '删除',
                iconCls: 'x-fa fa-trash',
                handler: 'delChapter',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission('删除章节')) {
                        return true;
                    }
                    return false;
                }
            }]
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: "addChapter",
                iconCls: 'fa fa-plus',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("视频章节添加")){
                            b.show();
                        }
                    }
                }
            },{
                text: '删除章节',
                handler: "batchDelChapter",
                iconCls: 'fa fa-trash',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("删除章节")){
                            b.show();
                        }
                    }
                }
            }, '->', {
                text: '查询',
                iconCls: 'fa fa-search',
                reference: 'btn_search',
                handler: 'search'
            },{
                text: '清空条件',
                iconCls: 'fa fa-search',
                listeners: {
                    click: 'reset'
                }
            }]
        }, {
            xtype: 'pagingtoolbar',
            store: 'video.VideoChapter',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});