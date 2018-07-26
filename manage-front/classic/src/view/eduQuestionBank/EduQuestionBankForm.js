/**
 * @author cjy
 * @Date 2018/6/2 15:06.
 */
Ext.define('Admin.view.eduQuestionBank.EduQuestionBankForm', {
    extend: 'Ext.window.Window',
    xtype: 'eduQuestionBankForm',

    title: '题库添加',
    requires: [
        'Admin.view.eduQuestionBank.EduQuestionBankFormController',
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

    controller: 'eduQuestionBankForm',

    viewModel: {
        links: {
            theEduQuestionBank: {
                type: 'eduQuestionBank.EduQuestionBank',
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
            id :'hiddenEduQuestionBankId',
            bind:{
                value:'{theEduQuestionBank.id}'
            }
        },{
            xtype: 'textfield',
            name: 'title',
            allowBlank:false,
            fieldLabel: '题库名称',
            bind: {
                value: '{theEduQuestionBank.title}'
            }
        }, {
            xtype: 'combo',
            name: 'postType',
            fieldLabel: '对应岗位',
            allowBlank:false,
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
            bind: '{theEduQuestionBank.postType}'
        }],
        buttons: [{
            text: '确定',
            handler: 'editEduQuestionBank'
        }, {
            text: '取消',
            handler: 'closeEduQuestionBankWindow'
        }]
    }
});
