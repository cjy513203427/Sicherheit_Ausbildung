/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.videoContent.VideoContent', {
    extend: 'Ext.Panel',
    xtype: 'videoContent',
    title: '视频管理',

    requires: [
        'Admin.view.videoContent.VideoContentController',
        'Ext.button.Button'
    ],
    controller: 'videocontent',

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
            fieldLabel: '视频名称',
            name: 'title'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'video.VideoContent',
        selModel: {
            selType: 'checkboxmodel'
        },

        columns: [{
            xtype: 'rownumberer'
        },{
            text: '集锦名称',
            dataIndex: 'eduVideoName',
            width:180
        // },{
        //     text: '章节名称',
        //     dataIndex: 'videoChapterName',
        //     width:180
        },{
            text: '视频名称',
            dataIndex: 'title',
            width:150
        },{
            text: '视频路径',
            dataIndex: 'videoPath',
            width:485,
            renderer:function (v) {
                if (v!=null){
                    // return '<a target="_blank" href="'+Common.Config.imageAddressUrl + v +'">'+Common.Config.imageAddressUrl + v+'<a/>' ;
                    return '<a target="_blank" href="'+Common.Config.imageAddressUrl + v +'">' +
                        '<video height="50px" src="'+Common.Config.imageAddressUrl + v+'"></video><a/>' ;
                }
            }
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 200,
            items:[{
                tooltip: '编辑',
                iconCls:'x-fa fa-pencil-square-o',
                handler: 'modifyContent',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("内容修改")) {
                        return true;
                    }
                    return false;
                }
            },'-',{
                tooltip: '删除',
                iconCls: 'x-fa fa-trash',
                handler: 'delContent',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission('删除视频')) {
                        return true;
                    }
                    return false;
                }
            },'-', {
                tooltip: '查看题目',
                iconCls: 'x-fa fa-github',
                handler: 'scanQuestion',
                style: 'margin-bottom:-2px;',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission('查看视频题目')) {
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
                handler: "addContent",
                iconCls: 'fa fa-plus',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("视频信息添加")){
                            b.show();
                        }
                    }
                }
            },{
                text: '删除视频',
                handler: "batchDelVideo",
                iconCls: 'fa fa-trash',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("删除视频")){
                            b.show();
                        }
                    }
                }
            },'->', {
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
            store: 'video.VideoContent',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});