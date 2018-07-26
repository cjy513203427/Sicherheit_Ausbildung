/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.mockExam.MockExam', {
    extend: 'Ext.Panel',
    xtype: 'mockExam',
    title: '模式考试管理',

    requires: [
        'Admin.view.mockExam.MockExamController'
    ],
    controller: 'mockExam',
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
            fieldLabel: '试卷名称',
            flex: 1,
            name: 'examName'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'mockExam.MockExam',
        columns: [{
            xtype: 'rownumberer'
        }, {
            text: 'ID',
            dataIndex: 'id',
            hidden: true
        }, {
            text: '试卷名称',
            dataIndex: 'examName',
            width: 200
        }, {
            text: '试题数',
            dataIndex: 'questionNumber'
        }, {
            text: '总分',
            dataIndex: 'totalScore'
        }, {
            text: '及格分',
            dataIndex: 'passScore'
        }, {
            text: '耗时（分钟）',
            dataIndex: 'consumeMinute'
        }, {
            text: '岗位',
            dataIndex: 'postType',
            /*1:管理人员、2：作业人员、3：普通工人、9：其他*/
            renderer: function (value) {
                if (value == 1) {
                    return '管理人员';
                } else if (value == 2) {
                    return '作业人员';
                } else if (value = 3) {
                    return '普通工人';
                } else {
                    return '其他';
                }
            }
        }, {
            text: '创建时间',
            dataIndex: 'createTime',
            width: 200
        }, {
            text: '操作',
            xtype: 'actioncolumn',
            width: 195,
            items: [{
                tooltip: '编辑',
                icon: 'resources/images/icons/ic_edit.png',
                handler: 'modifyMockExam',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("编辑模拟考试")) {
                        return true;
                    }
                    return false;
                }
            }, '-', {
                tooltip: '编辑考试题目',
                iconCls: 'x-fa fa-superscript',
                handler: 'editExaminationQuestions',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("编辑考试题目")) {
                        return true;
                    }
                    return false;
                }
            }, '-', {
                tooltip: '删除',
                icon: 'resources/images/icons/ic_delete.png',
                handler: 'deleteExamMock',
                isDisabled: function (view, rowindex, colindex, item, record) {
                    if (!Common.permission.Permission.hasPermission("删除模拟考试")) {
                        return true;
                    }
                    return false;
                }
            }]
        }],
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加模拟考试',
                handler: "createMockExam",
                iconCls: 'fa fa-plus',
                hidden: true,
                listeners: {
                    render: function (b) {
                        if (Common.permission.Permission.hasPermission("添加模拟考试")) {
                            b.show();
                        }
                    }
                }
            }, '->', {
                text: '查询',
                iconCls: 'fa fa-search',
                reference: 'btn_search',
                handler: 'search'
            }, {
                text: '清空条件',
                iconCls: 'fa fa-search',
                listeners: {
                    click: 'reset'
                }
            }]
        }, {
            xtype: 'pagingtoolbar',
            store: 'mockExam.MockExam',
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