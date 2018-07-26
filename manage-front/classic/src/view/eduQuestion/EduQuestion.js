/**
 * @author cjy
 * @Date 2018/6/2 15:06.
 */
Ext.define('Admin.view.eduQuestion.EduQuestion', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestion',
    title: '题目管理',
    width: '50%',
    height: '450px',
    modal: true,
    requires: [
        'Admin.view.eduQuestion.EduQuestionController',
        'Ext.button.Button'
    ],
    controller: 'eduQuestion',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [{
        xtype: 'form',
        reference: 'form',
        id:'eduQuestionForm',
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
            fieldLabel: '题目名称',
            flex:1,
            name: 'title'
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
        id:'eduQuestionGrid',
        reference: 'grid',
        flex: 1,
        store: 'eduQuestion.EduQuestion',
        columns: [{
            xtype: 'rownumberer'
        },{
            text: '主键id',
            dataIndex: 'id',
            hidden:true,
            flex:1
        },{
            text: '题目名称',
            dataIndex: 'title',
            flex:1
        }, {
            text: '题目类型',
            flex:1,
            dataIndex: 'questionType',
            renderer: function (questionType) {
                if (questionType == 1) {
                    return "单选";
                } else if (questionType == 2) {
                    return "判断";
                }else if (questionType == 3) {
                    return "多选";
                }
            },
            width: 100
        },{
            text: '答案解析',
            dataIndex: 'answerAnalysis',
            flex:1
        },  {
            text: '创建人Id',
            flex:1,
            hidden:true,
            dataIndex: 'createUserId',
            width: 100
        }, {
            text: '创建人名',
            flex:1,
            dataIndex: 'realName',
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
                handler: 'modifyEduQuestion',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题目修改")) {
                        return true;
                    }
                    return false;
                }
            },{
                tooltip: '删除',
                icon: 'resources/images/icons/ic_delete.png',
                handler: 'deleteEduQuestion',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("题目删除")) {
                        return true;
                    }
                    return false;
                }
            },{
                tooltip: '答案管理',
                iconCls: 'x-fa fa-key',
                handler: 'showEduQuestionAnswer'
            }]
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加',
                handler: "createEduQuestion",
                iconCls: 'fa fa-plus',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("题目添加")){
                            b.show();
                        }
                    }
                }
            },{
                text: '导入表格',
                handler: "importExcel",
                iconCls: 'fa fa-file-excel-o',
                listeners:{
                    render:function (b) {
                        if(Common.permission.Permission.hasPermission("导入表格")){
                            b.show();
                        }
                    }
                }
            } ,'->', {
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
            store: 'eduQuestion.EduQuestion',
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