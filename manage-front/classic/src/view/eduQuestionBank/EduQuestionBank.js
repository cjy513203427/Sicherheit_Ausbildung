/**
 * @author cjy
 * @Date 2018/6/2 15:06.
 */
Ext.define('Admin.view.eduQuestionBank.EduQuestionBank', {
    extend: 'Ext.Panel',
    xtype: 'eduQuestionBank',
    title: '题库管理',

    requires: [
        'Admin.view.eduQuestionBank.EduQuestionBankController',
        'Ext.button.Button'
    ],
    controller: 'eduQuestionBank',
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
            reference: 'title',
            fieldLabel: '题库名称',
            flex:1,
            name: 'title'
        },{
            xtype: 'combo',
            name: 'postType',
            fieldLabel: '对应岗位',
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '管理人员', value: 1
                }, {
                    label: '作业人员', value: 2
                }, {
                    label: '普通工人', value: 3
                }, {
                    label: '其他', value: 9
                }]
            }
        },{
            xtype: 'textfield',
            reference: 'value',
            fieldLabel: '创建人',
            hidden:true,
            flex:1,
            name: 'value'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'eduQuestionBank.EduQuestionBank',
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '主键',
            dataIndex: 'id',
            hidden:true,
            flex:1
        },{
            text: '题库名称',
            dataIndex: 'title',
            flex:1
        }, {
            text: '对应岗位',
            flex:1,
            dataIndex: 'postType',
            renderer: function (postType) {
                if (postType == 1) {
                    return "管理人员";
                } else if (postType == 2) {
                    return "作业人员";
                }else if (postType == 3) {
                    return "普通工人";
                }else if (postType == 9) {
                    return "其他";
                }
            },
            width: 100
        }, {
            text: '创建人Id',
            flex:1,
            dataIndex: 'createUserId',
            hidden:true,
            width: 100
        },{
            text: '创建人名',
            flex:1,
            dataIndex: 'realName',
            width: 100
        }, {
            text: '创建时间',
            flex:1,
            dataIndex: 'createTime',
            width: 100
        },{
            text: '操作',
            xtype:'actioncolumn',
            width: 100,
            items:[{
                tooltip: '编辑',
                icon: 'resources/images/icons/ic_edit.png',
                handler: 'modifyEduQuestionBank',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题库修改")) {
                        return true;
                    }
                    return false;
                }
            },{
                tooltip: '删除',
                icon: 'resources/images/icons/ic_delete.png',
                handler: 'deleteEduQuestionBank',
                isDisabled: function (view, rowindex, colindex, item, record) {
                 if (!Common.permission.Permission.hasPermission("题库删除")) {
                 return true;
                 }
                 return false;
                 }
            },{
                tooltip: '查看题目',
                iconCls: 'x-fa fa-github',
                handler: 'showEduQuestionBank',
                style:'margin-bottom:-2px;'
            }]
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: "createEduQuestionBank",
                iconCls: 'fa fa-plus',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("题库添加")){
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
            store: 'eduQuestionBank.EduQuestionBank',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }
    ]
});