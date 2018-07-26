/**
 * @author cjy
 * @Date 2018/6/6 15:58.
 */
Ext.define('Admin.view.eduQuestionAnswer.EduQuestionAnswerForm', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestionAnswerForm',

    title: '答案添加',
    requires: [
        'Admin.view.eduQuestionAnswer.EduQuestionAnswerFormController',
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

    controller: 'eduQuestionAnswerForm',

    viewModel: {
        links: {
            theEduQuestionAnswer: {
                type: 'eduQuestionAnswer.EduQuestionAnswer',
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
            id :'hiddenEduQuestionAnswerId',
            bind:{
                value:'{theEduQuestionAnswer.id}'
            }
        },{
            xtype: 'textfield',
            name: 'optionCode',
            allowBlank:false,
            fieldLabel: '选项编码',
            bind: {
                value: '{theEduQuestionAnswer.optionCode}'
            }
        }, {
            xtype: 'combo',
            name: 'correctFlag',
            fieldLabel: '是否正确',
            allowBlank:false,
            displayField: 'label',
            valueField: 'value',
            store: {
                data: [{
                    label: '正确', value: 1
                }, {
                    label: '错误', value: 2
                }]
            },
            bind: '{theEduQuestionAnswer.correctFlag}'
        },{
            xtype: 'textfield',
            name: 'optionContent',
            allowBlank:false,
            fieldLabel: '选项内容',
            bind: {
                value: '{theEduQuestionAnswer.optionContent}'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editEduQuestionAnswer'
        }, {
            text: '取消',
            handler: 'closeEduQuestionAnswerWindow'
        }]
    }
});
