/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.video.Video', {
    extend: 'Ext.Panel',
    xtype: 'video',
    title: '视频管理',

    requires: [
        'Admin.view.video.VideoController',
        'Ext.button.Button'
    ],
    controller: 'video',

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
            margin: '12px 0px 0px 0px'
        },
        items: [{
            xtype: 'textfield',
            reference: 'title',
            fieldLabel: '视频集锦名称',
            name: 'title'
        },{
            xtype: 'textfield',
            // reference: 'createUser',
            fieldLabel: '创建人',
            name: 'createUserName'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'video.Video',
        selModel: {
            selType: 'checkboxmodel'
        },
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '集锦名称',
            dataIndex: 'title',
            width: 180
        }, {
            text: '适用人群',
            dataIndex: 'forPeople',
            width: 100
        }, {
            text: '简介',
            dataIndex: 'introduction',
            width: 200
        }, {
            text: '岗位',
            editable:true,
            dataIndex: 'postTypeTxt',
            width: 100
        }, {
            text: '创建人',
            dataIndex: 'createUserName',
            width: 100
        }, {
            text: '缩略图地址',
            dataIndex: 'thumbnailPath',
            width: 200,
            renderer:function(v){
                if(v!=null){
                    return '<a href="'+Common.Config.imageAddressUrl + v+'" target="_blank"/><img style="height: 50px" src="'+Common.Config.imageAddressUrl + v+'"></a>';
                }
            }
        }, {
            text: '点击次数',
            dataIndex: 'clickNumber',
            width: 100
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                iconCls:'x-fa fa-pencil-square-o',
                handler: 'modifyVideoInfo',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("视频信息添加")) {
                        return true;
                    }
                    return false;
                }
            },'-',{
                tooltip: '删除集锦',
                iconCls: 'x-fa fa-trash',
                handler: 'delVideoColletion',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission('删除集锦')) {
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
                handler: "addVideoInfo",
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
                text: '删除集锦',
                handler: "batchDelVideoCollection",
                iconCls: 'fa fa-trash',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("删除集锦")){
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
            store: 'video.Video',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});