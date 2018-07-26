/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.mockExam.MockExamQuestions', {
    extend: 'Ext.window.Window',
    xtype: 'mockExamQuestions',

    title: '模拟考试题库',
    requires: [
        'Admin.view.mockExam.MockExamQuestionsController'
    ],
    controller: 'mockExamQuestions',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    modal: true,
    height: 740,
    width: 700,
    items: [{
        xtype: 'form',
        reference: 'formSelect',
        id: 'mockExamQuestionsForm',
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
            fieldLabel: '题目名称',
            name: 'questionTitle'
        }]
    }, {
        xtype: 'grid',
        sortableColumns: false,
        reference: 'grid',
        flex: 1,
        store: 'mockExam.MockExamQuestions',
        columns: [{
            xtype: 'rownumberer'
        }, {
            text: '题库名',
            dataIndex: 'bankTitle',
            flex: 1
        }, {
            text: '题目类型',
            flex: .5,
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
            text: '题目名',
            dataIndex: 'questionTitle',
            flex: 2
        }, {
            text: '创建时间',
            dataIndex: 'createTime',
            flex: 1.5
        }],
        selModel: {
            selType: 'checkboxmodel'
        },
        dockedItems: [{
            xtype: 'toolbar',
            items: [{
                text: '添加题目',
                handler: "addQuestions",
                iconCls: 'fa fa-plus'
            }, {
                text: '删除',
                handler: "deleteMockDownQuestion",
                iconCls: 'fa fa-minus-square-o'
            }, '->', {
                text: '查询',
                iconCls: 'fa fa-search',
                reference: 'btn_search',
                handler: 'search'
            }]
        }],
        listeners: {
            beforerender: 'gridBeforeRender',
            render: 'search'
        }
    }]
});
