/**
 * @author cjy
 * @Date 2018/6/4 14:59.
 */
Ext.define('Admin.view.mockExam.MockExamForm', {
    extend: 'Ext.window.Window',
    xtype: 'mockExamForm',

    title: '添加模拟考试',
    requires: [
        'Admin.view.eduQuestion.EduQuestionFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 400,
    width: 350,
    viewModel: {
        links: {
            theMockExam: {
                type: 'mockExam.MockExam',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    controller: 'mockExamForm',
    items: {
        xtype: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'hidden',
            id: 'hiddenMockExamId',
            name: 'id',
            bind: {
                value: '{theMockExam.id}'
            }
        }, {
            xtype: 'textfield',
            name: 'examName',
            allowBlank: false,
            fieldLabel: '试卷名称',
            bind: {
                value: '{theMockExam.examName}'
            }
        }, {
            xtype: 'combo',
            name: 'postType',
            fieldLabel: '岗位',
            allowBlank: false,
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
            },
            bind: '{theMockExam.postType}'
        }, {
            xtype: 'numberfield',
            name: 'questionNumber',
            fieldLabel: '题目数量',
            allowBlank: false,
            bind: {
                value: '{theMockExam.questionNumber}'
            }
        }, {
            xtype: 'numberfield',
            name: 'totalScore',
            fieldLabel: '总分',
            allowBlank: false,
            bind: {
                value: '{theMockExam.totalScore}'
            }
        }, {
            xtype: 'numberfield',
            name: 'passScore',
            fieldLabel: '及格分',
            allowBlank: false,
            bind: {
                value: '{theMockExam.passScore}'
            }
        }, {
            xtype: 'numberfield',
            name: 'consumeMinute',
            fieldLabel: '时长（分钟）',
            allowBlank: false,
            bind: {
                value: '{theMockExam.consumeMinute}'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editorMockExam'
        }, {
            text: '取消',
            handler: 'closeMockExamWindow'
        }]
    }
});
