/**
 * @author cc
 * @Date 2018/6/8 14:06.
 */
Ext.define('Admin.view.eduQuestion.EduQuestionSelect', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestion',
    title: '题目管理',
    width: '500px',
    height: '450px',
    requires: [
        'Admin.view.eduQuestion.EduQuestionSelectController'
    ],
    controller: 'eduQuestionSelect',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    modal:true,
    items: [{
        xtype: 'form',
        reference: 'form',
        id: 'notSelectEduQuestionForm',
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
            fieldLabel: '题库名称',
            flex: 1,
            name: 'bankTitle'
        }, {
            xtype: 'textfield',
            fieldLabel: '题目名称',
            flex: 1,
            name: 'questionTitle'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'eduQuestion.NotSelectEduQuestion',
        columns: [{
            xtype: 'rownumberer'
        }, {
            text: '题库名称',
            dataIndex: 'bankTitle',
            flex: 1
        }, {
            text: '题目类型',
            flex: 1,
            dataIndex: 'questionType',
            renderer: function (questionType) {
                if (questionType == 1) {
                    return "单选";
                } else if (questionType == 2) {
                    return "判断";
                } else if (questionType == 3) {
                    return "多选";
                }
            },
            width: 100
        }, {
            text: '题目名称',
            dataIndex: 'questionTitle',
            flex: 2
        }],
        selModel: {
            selType: 'checkboxmodel'
        },
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '加入考试题库',
                handler: "addQuestionsToExam",
                iconCls: 'fa fa-plus'
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
            store: 'eduQuestion.NotSelectEduQuestion',
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