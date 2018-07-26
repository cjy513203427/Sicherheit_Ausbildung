/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.dictionary.DictionaryForm', {
    extend: 'Ext.window.Window',
    xtype: 'dictionaryForm',

    title: '字典添加',
    requires: [
        'Admin.view.dictionary.DictionaryFormController',
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

    controller: 'dictionaryform',

    viewModel: {
        links: {
            theDictionaey: {
                type: 'dictionary.Dictionary',
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
            id :'hiddenDicId',
            bind:{
                value:'{theDictionary.id}'
            }
        },{
            xtype: 'textfield',
            name: 'typeDesc',
            allowBlank:false,
            fieldLabel: '字典类别',
            bind: {
                value: '{theDictionary.typeDesc}'
            }
        }, {
            xtype: 'textfield',
            name: 'typeCode',
            fieldLabel: '字典编码',
            allowBlank:false,
            bind: '{theDictionary.typeCode}'
        }, {
            xtype: 'textfield',
            name: 'text',
            fieldLabel: '字典文本',
            allowBlank:false,
            bind: '{theDictionary.text}'
        }, {
            xtype: 'textfield',
            name: 'value',
            fieldLabel: '字典值',
            allowBlank:false,
            bind: '{theDictionary.value}'
        }, {
            xtype: 'numberfield',
            name: 'sort',
            fieldLabel: '排序',
            allowBlank:false,
            bind: '{theDictionary.sort}',
            minValue:0
        }, {
            xtype: 'numberfield',
            name: 'param1',
            fieldLabel: '参数1',
            allowBlank:false,
            hideTrigger: true,
            bind: '{theDictionary.param1}',
            minValue:0
        }, {
            xtype: 'numberfield',
            name: 'param2',
            fieldLabel: '参数2',
            hideTrigger: true,
            bind: '{theDictionary.param2}',
            minValue:0
        }],
        buttons: [{
            text: '确定',
            handler: 'editDictionary'
        }, {
            text: '取消',
            handler: 'closeDictionaryWindow'
        }]
    }
});
