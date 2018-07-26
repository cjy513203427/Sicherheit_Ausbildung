/**
 * @author cjy
 * @Date 2018/6/6 14:09.
 */
Ext.define('Admin.view.eduQuestionAnswer.EduQuestionAnswer', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestionAnswer',
    title: '答案管理',
    width: '50%',
    height: '450px',
    modal: true,
    requires: [
        'Admin.view.eduQuestionAnswer.EduQuestionAnswerController',
        'Ext.button.Button'
    ],
    controller: 'eduQuestionAnswer',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'form',
        reference: 'form',
        id:'eduQuestionAnswerForm',
        defaultButton: 'btn_search',
        layout: 'column',
        defaults: {
            labelAlign: 'right'
        },
        style: {
            margin: '12px 0px 0px -28px'
        },
        items: [{
            xtype: 'combo',
            reference: 'correctFlag',
            fieldLabel: '是否正确',
            flex:1,
            name: 'correctFlag',
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '正确', value: 1
                }, {
                    label: '错误', value: 2
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
        store: 'eduQuestionAnswer.EduQuestionAnswer',
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '主键id',
            dataIndex: 'id',
            hidden:true,
            flex:1
        }, {
            text: '选项编码',
            dataIndex: 'optionCode',
            flex:1
        }, {
            text: '选项内容',
            dataIndex: 'optionContent',
            flex:1
        }, {
            text: '是否正确',
            flex:1,
            dataIndex: 'correctFlag',
            renderer: function (correctFlag) {
                if (correctFlag == 1) {
                    return "正确";
                } else if (correctFlag == 2) {
                    return "错误";
                }
            },
            width: 100
        },{
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
                handler: 'modifyEduQuestionAnswer',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题目修改")) {
                        return true;
                    }
                    return false;
                }
            },{
                tooltip: '删除',
                icon: 'resources/images/icons/ic_delete.png',
                handler: 'deleteEduQuestionAnswer',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题目删除")) {
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
                handler: "createEduQuestionAnswer",
                iconCls: 'fa fa-plus',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("题目添加")){
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
            store: 'eduQuestionAnswer.EduQuestionAnswer',
            dock: 'bottom',
            displayInfo: true
        }],
        listeners: {
            beforerender: 'loadStore',
            render: 'search'
        }
    }
    ]
});