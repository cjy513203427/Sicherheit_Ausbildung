/**
 * Created by Wwei on 2016/9/9.
 */
Ext.define('Admin.view.picArticle.PicArticle', {
    extend: 'Ext.container.Container',

    xtype: 'picArticle',

    requires: [
        'Admin.view.picArticle.PicArticleController',
        'Ext.button.Button'
    ],
    controller: 'picarticle',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'picArticle.PicArticle',
        selModel: {
            selType: 'checkboxmodel'
        },
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '标题',
            dataIndex: 'title',
            width: 150
        }, {
            text: '简介',
            dataIndex: 'introduce',
            width: 100
        }, {
            text: '内容分类',
            dataIndex: 'contentClassify',
            hidden:true,
            width: 200
        }, {
            text: '图片路径',
            dataIndex: 'imagePath',
            width: 100
        }, {
            text: '浏览次数',
            dataIndex: 'browseTimes',
            width: 100
        }, {
            text: '内容音频',
            dataIndex: 'audioPath',
            width: 200
        }, {
            text: 'PDF地址',
            dataIndex: 'pdfPath',
            width: 100
        }, {
            text: '岗位',
            dataIndex: 'postTypeTxt',
            width: 100
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                // icon: 'resources/images/icons/ic_edit.png',
                iconCls:'x-fa fa-pencil-square-o',
                handler: 'modifyPicArticleInfo',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("修改HTML图文")) {
                        if(!Common.permission.Permission.hasPermission("修改PDF图文")){
                            return true;
                        }else {
                            if(record.data.pdf==null){
                                return true ;
                            }
                            return false ;
                        }
                    }
                    if (!Common.permission.Permission.hasPermission("修改PDF图文")) {
                        if(!Common.permission.Permission.hasPermission("修改HTML图文")){
                            return true;
                        }else {
                            if(record.data.content==null){
                                return true ;
                            }
                            return false ;
                        }
                    }
                    return false;
                }
            },'-',{
                tooltip: '上传缩略图',
                // icon: 'resources/images/icons/ic_edit.png',
                iconCls: 'x-fa fa-cloud-upload',
                handler: 'uploadLitimg',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("上传缩略图")) {
                        return true;
                    }
                    if(record.data.pdfPath!=null){
                        return true;
                    }
                    return false;
                }
            },'-',{
                tooltip: '删除图文',
                iconCls: 'x-fa fa-trash',
                handler: 'delPicArticle',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission('删除图文')) {
                        return true;
                    }
                    return false;
                }
            }]
        }],

        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加HTML图文',
                handler: "addPicArticleInfo",
                iconCls: 'fa fa-plus',
                hidden:true
            },{
                text: '添加PDF图文',
                handler: "addPicArticlePDF",
                iconCls: 'fa fa-plus',
                hidden:true
            },{
                text: '删除图文',
                handler: "batchDelPicArticle",
                iconCls: 'fa fa-trash',
                hidden:true
            }]
        },{
            xtype: 'pagingtoolbar',
            store: 'picArticle.PicArticle',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            // beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});