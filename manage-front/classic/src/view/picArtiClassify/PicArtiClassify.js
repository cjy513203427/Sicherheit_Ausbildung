/**
 * Created by Wwei on 2016/9/9.
 */
Ext.define('Admin.view.picArtiClassify.PicArtiClassify', {
    extend: 'Ext.container.Container',

    xtype: 'picArtiClassify',

    requires: [
        'Ext.form.Panel',
        'Ext.layout.container.HBox',
        'Ext.tree.Panel'
        ,'Admin.view.picArtiClassify.ClassifyController'
    ],

    controller: 'classify',

    layout: 'hbox',

    listeners: {
        beforerender: 'classifyBeforeRender'
    },

    defaults: {
        height: '100%'
    },

    items: [{
        title: '图文菜单',
        flex: 2,
        xtype: 'treepanel',
        reference: 'classifyTree',
        useArrows: true,
        store: 'picArticle.ClassifyTree',
        listeners: {
            select: 'onTreeSelected'
        },
        dockedItems: [{
            xtype: 'toolbar',
            dock: 'top',
            items: [{
                text: '添加',
                disabled: true,
                handler: "addClassify",
                reference: 'addClsfybtn',
                iconCls: 'fa fa-plus',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("添加图文分类")){
                            b.show();
                        }
                    }
                }
            }, {
                text: '修改',
                handler: "updateClassify",
                iconCls: 'fa fa-pencil-square-o',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("修改图文分类")){
                            b.show();
                        }
                    }
                }
            }, {
                text: '删除',
                reference: 'delClsfybtn',
                disabled: true,
                handler: "delClassify",
                iconCls: 'fa fa-times',
                hidden:true,
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("删除图文分类")){
                            b.show();
                        }
                    }
                }
            }]
        }]
    }, {
        flex: 7,
        frame: true,
        title: '图文编辑',
        xtype: 'tabpanel',
        reference: 'editPanel',
        items:[{
            title:'类别信息',
            xtype:'form',
            reference:'fp',
            items:[{
                xtype : 'fieldset',
                title : '分类',
                items : [{
                    xtype : 'textfield',
                    id : 'parentId',
                    name : 'parentId',
                    hidden : true,
                    allowBlank : false
                }, {
                    xtype : 'textfield',
                    id : 'parentName',
                    fieldLabel : '所属类别',
                    width : 300,
                    readOnly : true,
                    allowBlank : false
                }, {
                    xtype : 'textfield',
                    // id : 'id',
                    name : 'id',
                    hidden : true,
                    fieldLabel : 'id',
                    allowBlank : false
                }, {
                    xtype : 'textfield',
                    id : 'text',
                    name : 'text',
                    fieldLabel : '名称',
                    width : 300,
                    allowBlank : false
                }]
            }],
            buttons : [{
                text : '保存',
                id : 'saveBtn',
                handler : 'saveClassifyInfo'
            }]
        },{
            // title:'tab2',
            xtype:'picArticle',
            reference:'gp',
            disabled : true
            // ,listeners: {
            //     beforerender: 'gridBeforeRender',
            //     render: 'search'
            // }
        }]
    }]
});