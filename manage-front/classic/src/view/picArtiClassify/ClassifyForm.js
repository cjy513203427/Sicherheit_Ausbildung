/**
 * Created by jonnyLee on 2016/09/27.
 */
Ext.define('Admin.view.picArtiClassify.ClassifyForm', {
    extend: 'Ext.window.Window',
    xtype: 'classifyForm',

    title: '添加分类',

    requires: [
        'Admin.model.picArticle.ClassifyTree',
        'Admin.view.picArtiClassify.ClassifyFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 200,
    width: 300,

    controller: 'Classifyform',

    viewModel: {
        links: {
            theClassify: {
                type: 'picArticle.ClassifyTree',
                create: true
            }
        }
    },

    items: {
        xtype: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            name: 'id',
            fieldLabel: 'Id',
            hidden: true,
            bind: '{theClassify.id}'
        }, {
            xtype: 'textfield',
            name: 'parentId',
            fieldLabel: '父级Id',
            hidden: true,
            bind: '{theClassify.parentId}'
        }, {
            xtype: 'textfield',
            name: 'parentName',
            fieldLabel: '父级',
            disabled: true,
            bind: '{theClassify.parentName}'
        }, {
            xtype: 'textfield',
            name: 'text',
            fieldLabel: '名称',
            bind: '{theClassify.text}'
        }],
        buttons: [{
            text: '确定',
            handler: 'editClassify'
        }, {
            text: '取消',
            handler: 'closeClsfyWindow'
        }]
    }
});
