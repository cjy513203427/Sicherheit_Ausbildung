/**
 * @author cjy
 * @Date 2018/6/4 14:59.
 */
Ext.define('Admin.view.eduQuestion.EduQuestionForm', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestionForm',

    title: '题目添加',
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
    height: 440,
    width: 500,

    controller: 'eduQuestionform',

    viewModel: {
        links: {
            theEduQuestion: {
                type: 'eduQuestion.EduQuestion',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    items: {
        xtype: 'form',
        id:'dictionaryF',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype:'hidden',
            name:'id',
            id :'hiddenEduQuestionId',
            bind:{
                value:'{theEduQuestion.id}'
            }
        },{
            xtype: 'textfield',
            name: 'title',
            allowBlank:false,
            fieldLabel: '题目名称',
            bind: {
                value: '{theEduQuestion.title}'
            }
        }, {
            xtype: 'combo',
            name: 'questionType',
            fieldLabel: '题目类型',
            allowBlank:false,
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '单选', value: 1
                },{
                    label: '多选', value: 3
                }]
            },
            bind: '{theEduQuestion.questionType}'
        }, {
            xtype: 'textfield',
            name: 'questionBankId',
            id:'questionBankId',
            fieldLabel: '题库id',
            hidden:true,
            allowBlank:false,
            bind: '{theEduQuestion.questionBankId}'
        }, {
            xtype: 'textarea',
            name: 'answerAnalysis',
            id:'answerAnalysis',
            fieldLabel: '答案解析',
            allowBlank:false,
            bind: '{theEduQuestion.answerAnalysis}'
        }],
        buttons: [{
            text: '确定',
            handler: 'editEduQuestion'
        }, {
            text: '取消',
            handler: 'closeEduQuestionWindow'
        }]
    }
});
